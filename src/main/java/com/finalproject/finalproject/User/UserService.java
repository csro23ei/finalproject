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

        mongoTemplate.save(user);
        return ResponseEntity.ok(user);
    }

    public User loginUser(String username, String password) {

        Query query = Query.query(Criteria.where("username").is(username).and("password").is(password));
        return mongoTemplate.findOne(query, User.class);
    }

    public void logoutUser(String username) {

    }
}