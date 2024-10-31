package com.finalproject.finalproject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    @Value("${nonsecret.key}")
    String nonsecretkey;

    // Combine both messages into one method
    @GetMapping("/")
    public String getIndexAndKey() {
        return "{'messages': 'updatering 41', 'nonsecretkey': '" + nonsecretkey + "'} Nu har jag tester för logg in";
    }
}
