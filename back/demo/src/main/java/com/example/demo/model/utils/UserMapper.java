package com.example.demo.model.utils;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.User;
import com.example.demo.security.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class UserMapper {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static UserPrincipal userToPrincipal(User user) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        UserPrincipal userPrincipal = new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                List.of(authority)
        );

        userPrincipal.setEnabled(true);

        return userPrincipal;
    }

    public static User registerDtoToUser(RegisterDTO dto) {
        return new User(
                dto.getEmail(),
                dto.getUsername(),
                encoder.encode(dto.getPassword()),
                dto.getAvatarUrl()
        );
    }
}
