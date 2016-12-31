package com.hearthintellect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    private @Id String username;
    private String password;
    private String email;
    private @TextIndexed String nickname;

    public User() {}

    public User(String username, String email, String nickname, String password) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    /* Getters and Setters */
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
