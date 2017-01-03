package com.hearthintellect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class User implements Entity<String> {
    private @Id String username;
    private String password;
    private String email;
    private String nickname;

    private List<String> roles;

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
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    @Override
    public String getID() {
        return username;
    }

    @Override
    public void setID(String s) {
        username = s;
    }
}
