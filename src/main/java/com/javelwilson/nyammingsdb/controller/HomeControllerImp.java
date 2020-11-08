package com.javelwilson.nyammingsdb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeControllerImp implements HomeController {

    @GetMapping("/")
    public String home() {
        return "wah gwan";
    }
}
