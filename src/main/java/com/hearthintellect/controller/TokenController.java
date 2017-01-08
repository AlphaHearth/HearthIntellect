package com.hearthintellect.controller;

import com.hearthintellect.exception.InvalidUserCredentialException;
import com.hearthintellect.exception.TokenNotFoundException;
import com.hearthintellect.model.Token;
import com.hearthintellect.model.User;
import com.hearthintellect.repository.TokenRepository;
import com.hearthintellect.repository.UserRepository;
import com.hearthintellect.security.PasswordEncoder;
import com.hearthintellect.security.TokenIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class TokenController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenIDGenerator tokenIDGenerator;

    @Autowired
    public TokenController(UserRepository userRepository, TokenRepository tokenRepository,
                           PasswordEncoder passwordEncoder, TokenIDGenerator tokenIDGenerator) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenIDGenerator = tokenIDGenerator;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Token userLogin(@RequestBody LoginRequest loginRequest) {
        // Examine user credential
        if (StringUtils.isBlank(loginRequest.username)) // Invalid Username
            throw new InvalidUserCredentialException();
        User user = userRepository.findByUsername(loginRequest.username);
        if (user == null)                                    // Not existed username
            throw new InvalidUserCredentialException();
        if (!passwordEncoder.matches(loginRequest.username, loginRequest.password, user.getPassword()))
            throw new InvalidUserCredentialException();      // Invalid password

        // Examination passed. Return token.
        Token token = tokenRepository.findByUsername(loginRequest.username);
        if (token != null && token.getExpireTime().isAfter(LocalDateTime.now()))
            return token;
        token = new Token(loginRequest.username);
        tokenIDGenerator.setTokenID(token);
        tokenRepository.save(token);
        return token;
    }

    @RequestMapping(path = "/tokens/{tokenID}", method = RequestMethod.GET)
    public Token getToken(@PathVariable String tokenID) {
        Token token = tokenRepository.findOne(tokenID);
        if (token == null || token.getExpireTime().isBefore(LocalDateTime.now()))
            throw new TokenNotFoundException(tokenID);
        return token;
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {}

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
