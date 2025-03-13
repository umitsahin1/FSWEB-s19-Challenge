package com.twitterClone.twitterClone.controller;

import com.twitterClone.twitterClone.dto.LoginDto;
import com.twitterClone.twitterClone.dto.RegisterCreateDto;
import com.twitterClone.twitterClone.dto.RegisterDto;
import com.twitterClone.twitterClone.dto.UserDto;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class  AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterDto register(@Validated @RequestBody RegisterCreateDto registerCreateDto) {
        User user = authService.register(registerCreateDto.getUsername(),registerCreateDto.getEmail(), registerCreateDto.getPassword());
        return new RegisterDto(user.getEmail(),user.getUsername() );
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto) {
        User user= authService.login(loginDto.getEmail(), loginDto.getPassword());
        return new UserDto(user.getId(),user.getUsername(),user.getEmail());
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
