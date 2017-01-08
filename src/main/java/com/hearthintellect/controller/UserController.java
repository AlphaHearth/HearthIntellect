package com.hearthintellect.controller;

import com.hearthintellect.exception.*;
import com.hearthintellect.model.User;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.repository.UserRepository;
import com.hearthintellect.security.PasswordEncoder;
import com.hearthintellect.utils.CreatedMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, TokenRepository tokenRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException(username);
        user.setPassword(null);
        return user;
    }

    @RequestMapping(path = "/{username}", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<CreatedMessage> updateUser(@RequestParam String token, @PathVariable String username,
                                                     @RequestBody User user) {
        tokenRepository.tokenVerify(token, username);
        User userInDB = userRepository.findByUsername(username);
        if (userInDB == null)
            throw new UserNotFoundException(username);

        if (StringUtils.isNotBlank(user.getEmail()))
            userInDB.setEmail(user.getEmail());
        if (StringUtils.isNotBlank(user.getNickname()))
            userInDB.setNickname(user.getNickname());
        userRepository.save(userInDB);

        return ResponseEntity.created(URI.create("/users/" + username))
                .body(new CreatedMessage("/users/" + username, "User `" + username + "` updated."));
    }

    // TODO Resolve the Concurrency Caveat here: two current client trying to create
    // TODO users with the same username. Consider requesting for a document lock?

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CreatedMessage> createUser(@RequestBody User user) {
        String username = user.getUsername();
        if (StringUtils.isBlank(username))
            throw new BadRequestException("Username cannot be empty.");
        if (userRepository.exists(username))
            throw new DuplicateUserException(user.getUsername());
        if (StringUtils.isBlank(user.getPassword()))
            throw new BadRequestException("User password cannot be empty.");

        user.setPassword(passwordEncoder.encode(user.getUsername(), user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.created(URI.create("/users/" + username))
                .body(new CreatedMessage("/users/" + username, "User `" + username + "` created."));
    }

    @RequestMapping(path = "/{username}/password", method = { RequestMethod.PUT, RequestMethod.PATCH })
    public ResponseEntity<CreatedMessage> updateUserPassword(@PathVariable String username,
                                                             @RequestBody PasswordUpdateRequest request) {
        User userInDB = userRepository.findOne(username);
        if (userInDB == null)
            throw new UserNotFoundException(username);
        if (!passwordEncoder.matches(username, request.oldPassword, userInDB.getPassword()))
            throw new InvalidUserCredentialException();
        if (StringUtils.isBlank(request.newPassword))
            throw new BadRequestException("New password cannot be empty.");

        userInDB.setPassword(request.newPassword);
        passwordEncoder.encodeUserPassword(userInDB);
        userRepository.save(userInDB);

        return ResponseEntity.created(URI.create("/users/" + username))
                .body(new CreatedMessage("/users/" + username, "User `" + username + "` created."));
    }

    public static class PasswordUpdateRequest {
        private String oldPassword;
        private String newPassword;

        public PasswordUpdateRequest() {}

        public PasswordUpdateRequest(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }
}
