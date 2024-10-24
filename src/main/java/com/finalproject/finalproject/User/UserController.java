package com.finalproject.finalproject.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        return loggedInUser != null ? ResponseEntity.ok(loggedInUser) : ResponseEntity.status(401).body(null);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody User user) {
        userService.logoutUser(user.getUsername());
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/addFriend")
    public ResponseEntity<String> addFriend(@RequestParam String username, @RequestParam String friendUsername) {
        userService.addFriend(username, friendUsername);
        return ResponseEntity.ok("Friend added successfully");
    }

    @PostMapping("/removeFriend")
    public ResponseEntity<String> removeFriend(@RequestParam String username, @RequestParam String friendUsername) {
        userService.removeFriend(username, friendUsername);
        return ResponseEntity.ok("Friend removed successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> results = userService.searchUsers(query);
        return ResponseEntity.ok(results);
    }
}
