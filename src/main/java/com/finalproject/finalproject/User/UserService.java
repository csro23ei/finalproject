package com.finalproject.finalproject.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public ResponseEntity<User> registerUser(User user) {
        Query query = Query.query(Criteria.where("username").is(user.getUsername()));
        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // New user registration
        user.setLoggedIn(false); // New users are not logged in by default
        mongoTemplate.save(user);
        return ResponseEntity.ok(user);
    }

    public User loginUser(String username, String password) {
        Query query = Query.query(Criteria.where("username").is(username).and("password").is(password));
        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser != null && !existingUser.isLoggedIn()) {
            // Set the user as logged in
            existingUser.setLoggedIn(true);
            mongoTemplate.save(existingUser); // Update the loggedIn status in the database
            return existingUser;
        }

        // User is either logged in already or doesn't exist
        return null; // Return null on failure
    }

    public void logoutUser(String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser != null && existingUser.isLoggedIn()) {
            // Set the user as logged out
            existingUser.setLoggedIn(false);
            mongoTemplate.save(existingUser); // Update the loggedIn status in the database
        }
    }
}
