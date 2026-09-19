package com.geniq.user.controller;

import com.geniq.user.dto.LoginRequest;
import com.geniq.user.dto.RegisterRequest;
import com.geniq.user.dto.UserResponse;
import com.geniq.user.model.User;
import com.geniq.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "An account with this email already exists."));
        }
        User user = new User(req.email, passwordEncoder.encode(req.password), req.name,
                req.role != null ? req.role : "user");
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return userRepository.findByEmailIgnoreCase(req.email)
                .filter(u -> passwordEncoder.matches(req.password, u.getPasswordHash()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new UserResponse(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password.")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out."));
    }

    /**
     * Starts a password-reset flow. Always returns 200 with a generic message
     * so the endpoint cannot be used to discover which emails are registered.
     * In production this would generate a signed, time-limited token and email
     * a reset link; here it simply acknowledges the request.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "");
        if (email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email is required."));
        }
        // Intentionally do not reveal whether the account exists.
        return ResponseEntity.ok(Map.of(
                "message", "If an account exists for that email, a reset link has been sent."));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestParam(required = false) String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Not authenticated."));
        }
        return userRepository.findByEmailIgnoreCase(email)
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(new UserResponse(u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "User not found.")));
    }
}
