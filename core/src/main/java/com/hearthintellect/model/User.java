package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

@Entity(value = "users", noClassnameStored = true)
@Indexes(
        @Index(fields = @Field(value = "nickname", type = IndexType.TEXT))
)
public class User extends MongoEntity<String> implements JsonEntity {
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

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("email", email);
        result.put("nickname", nickname);

        return result;
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
