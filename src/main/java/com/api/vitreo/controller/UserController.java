package com.api.vitreo.controller;

import com.api.vitreo.components.TokenBlacklist;
import com.api.vitreo.dto.user.UserRequestDTO;
import com.api.vitreo.dto.user.UserResponseDTO;
import com.api.vitreo.entity.User;
import com.api.vitreo.repository.UserRepository;
import com.api.vitreo.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    TokenBlacklist tokenBlacklist;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO userData) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(userData.email(), userData.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal(), userData.rememberMe());
            return ResponseEntity.ok(new UserResponseDTO(userData.email(), null, token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar o login.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRequestDTO userData) {
        if (this.repository.findByEmail(userData.email()).isPresent()) return ResponseEntity.badRequest().build();

        String encryptedPassword = passwordEncoder.encode(userData.password());
        User newUser = new User(userData.email(), encryptedPassword);

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = recoverToken(request);
        if (token != null) {
            tokenBlacklist.add(token);
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout realizado com sucesso");
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
