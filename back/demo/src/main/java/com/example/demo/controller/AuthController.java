package com.example.demo.controller;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.User;
import com.example.demo.model.utils.UserMapper;
import com.example.demo.security.JwtProvider;
import com.example.demo.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserServiceImpl userService;

    @PostMapping("sign-in")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginDTO dto) {
        Authentication authentication = authenticate(dto.getUsername(), dto.getPassword());

        String token = jwtProvider.generateToken(authentication);

        return new JwtResponse(token);
    }

    @PostMapping("register")
    public User registerUser(@Valid @RequestBody RegisterDTO dto) {
        User user = UserMapper.registerDtoToUser(dto);
        return userService.createUser(user);
    }

    protected Authentication authenticate(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
