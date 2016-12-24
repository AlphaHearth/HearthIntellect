package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

@Entity(value = "users", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field(value = "email", type = IndexType.TEXT))
})
public class User extends MongoEntity<String> {
    @Id
    private String email;
    private String nickname;
    private String password;

    public User() {}

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getId() {
        return email;
    }
    @Override
    public void setId(String id) {
        email = id;
    }
}
