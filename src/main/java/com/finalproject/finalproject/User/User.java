package com.finalproject.finalproject.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private boolean loggedIn;
    private List<String> friends; // List of friend IDs

    // Constructors
    public User() {
        this.friends = new ArrayList<>(); // Initialize friends list
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        this.friends = new ArrayList<>(); // Initialize friends list
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public void addFriend(String friendId) {
        if (!this.friends.contains(friendId)) {
            this.friends.add(friendId);
        }
    }

    public void removeFriend(String friendId) {
        this.friends.remove(friendId);
    }
}
