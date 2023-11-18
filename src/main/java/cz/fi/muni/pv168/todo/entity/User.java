package cz.fi.muni.pv168.todo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.UUID;

@JsonDeserialize(builder = User.UserBuilder.class)
public class User implements Entity {
    
    @JsonProperty
    private final UUID id;

    @JsonProperty
    private final String login;

    @JsonProperty
    private String password;

    public User(UUID id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public UUID getGuid() {
        return this.id;
    }

    public UUID getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    public static class UserBuilder {
        private UUID id;
        private String login;
        private String password;

        UserBuilder() {
        }

        @JsonProperty
        public UserBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        @JsonProperty
        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        @JsonProperty
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this.id, this.login, this.password);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", login=" + this.login + ", password=" + this.password + ")";
        }
    }
}
