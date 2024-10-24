package com.finalproject.finalproject.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

        user.setLoggedIn(false);
        mongoTemplate.save(user);
        return ResponseEntity.ok(user);
    }

    public User loginUser(String username, String password) {
        Query query = Query.query(Criteria.where("username").is(username).and("password").is(password));
        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser != null && !existingUser.isLoggedIn()) {
            existingUser.setLoggedIn(true);
            mongoTemplate.save(existingUser);
            return existingUser;
        }
        return null;
    }

    public void logoutUser(String username) {
        Query query = Query.query(Criteria.where("username").is(username));
        User existingUser = mongoTemplate.findOne(query, User.class);

        if (existingUser != null && existingUser.isLoggedIn()) {
            existingUser.setLoggedIn(false);
            mongoTemplate.save(existingUser);
        }
    }

    public void addFriend(String username, String friendUsername) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), User.class);
        User friend = mongoTemplate.findOne(Query.query(Criteria.where("username").is(friendUsername)), User.class);

        if (user != null && friend != null && !user.getFriends().contains(friend.getId())) {
            user.addFriend(friend.getId());
            mongoTemplate.save(user);
        }
    }

    public void removeFriend(String username, String friendUsername) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), User.class);
        User friend = mongoTemplate.findOne(Query.query(Criteria.where("username").is(friendUsername)), User.class);

        if (user != null && friend != null && user.getFriends().contains(friend.getId())) {
            user.removeFriend(friend.getId());
            mongoTemplate.save(user);
        }
    }

    public List<User> searchUsers(String query) {
        Query searchQuery = Query.query(Criteria.where("username").regex(query, "i"));
        return mongoTemplate.find(searchQuery, User.class);
    }
}
