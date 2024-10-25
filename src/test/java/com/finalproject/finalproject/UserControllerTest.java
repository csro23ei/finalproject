package com.finalproject.finalproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.finalproject.finalproject.User.User;
import com.finalproject.finalproject.User.UserController;
import com.finalproject.finalproject.User.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterUser() throws Exception {
        User user = new User("testUser", "password");
        when(userService.registerUser(any(User.class))).thenReturn(ResponseEntity.ok(user));

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testLoginUser() throws Exception {
        User user = new User("testUser", "password");
        when(userService.loginUser("testUser", "password")).thenReturn(user);

        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void testLogoutUser() throws Exception {
        mockMvc.perform(post("/user/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User logged out successfully"));
    }

    @Test
    void testAddFriend() throws Exception {
        mockMvc.perform(post("/user/addFriend")
                .param("username", "testUser")
                .param("friendUsername", "friendUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend added successfully"));
    }

    @Test
    void testRemoveFriend() throws Exception {
        mockMvc.perform(post("/user/removeFriend")
                .param("username", "testUser")
                .param("friendUsername", "friendUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend removed successfully"));
    }

    @Test
    void testSearchUsers() throws Exception {
        User user1 = new User("testUser1", "password");
        User user2 = new User("testUser2", "password");
        when(userService.searchUsers("testUser")).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/user/search").param("query", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser1"))
                .andExpect(jsonPath("$[1].username").value("testUser2"));
    }
}
