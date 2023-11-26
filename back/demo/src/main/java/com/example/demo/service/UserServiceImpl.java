package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //TODO: Add checks
    public User createUser(User user) {
        return userRepo.save(user);
    }
}
