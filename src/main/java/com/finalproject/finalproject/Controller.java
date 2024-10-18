package com.finalproject.finalproject;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Controller {
    @GetMapping
    public String getIndex() {
        return "{'messages': 'Hello world 4'}";
    }

}
