package com.finalproject.finalproject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {

    @Value("${nonsecret.key}")
    String nonsecretkey;

    // Combine both messages into one method
    @GetMapping("/")
    public String getIndexAndKey() {
        return "{'messages': 'Hello world 11', 'nonsecretkey': '" + nonsecretkey + "'}";
    }
}
