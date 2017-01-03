package com.hearthintellect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tokens")
public class Token implements Entity<String> {
    private @Id String id;
    private @Indexed(unique = true) String username;
    private @Indexed(expireAfterSeconds = 0) LocalDateTime expireTime;

    public Token() {}

    public Token(String username) {
        this(username, LocalDateTime.now().plusDays(1));
    }

    public Token(String username, LocalDateTime expireTime) {
        this.username = username;
        this.expireTime = expireTime;
    }

    /* Getters and Setters */
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public LocalDateTime getExpireTime() { return expireTime; }
    public void setExpireTime(LocalDateTime expireTime) { this.expireTime = expireTime; }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String s) {
        this.id = s;
    }
}
