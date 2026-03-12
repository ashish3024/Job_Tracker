package com.ashish.jobtracker.controller.auth;

import com.ashish.jobtracker.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String email) {

        return jwtUtil.generateToken(email);
    }
    @GetMapping("/test")
    public String test() {
        return "JWT is working";
    }
}