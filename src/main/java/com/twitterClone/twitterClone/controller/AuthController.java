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
        return new RegisterDto(user.getEmail(),user.getUserNameReal() );
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginDto loginDto,HttpServletRequest request) {
        User user= authService.login(loginDto.getEmail(), loginDto.getPassword());

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        return new UserDto(user.getId(),user.getUserNameReal(),user.getEmail());
    }

    @GetMapping("/check")
    public ResponseEntity<UserDto> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return ResponseEntity.ok(new UserDto(user.getId(), user.getUserNameReal(), user.getEmail()));
            }
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }

}
