package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckAuthController {
    @GetMapping("checkAuth")
    public @ResponseBody String checkAuth() {
        return "Этого авторизуем";
    }
}
