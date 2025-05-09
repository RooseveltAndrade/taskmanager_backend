package com.taskmanager_backend.controller;

import com.taskmanager_backend.model.User;
import com.taskmanager_backend.security.JwtUtil;
import com.taskmanager_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Log para depuração
            System.out.println("Tentativa de login para o usuário: " + authRequest.getUsername());

            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Define o contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Recupera o usuário autenticado pelo username
            User user = userService.findByUsername(authRequest.getUsername());

            // Gera o token JWT
            String token = jwtUtil.generateToken(user.getUsername());

            // Retorna o token e o userId
            return ResponseEntity.ok(new AuthResponse(token, user.getId()));
        } catch (AuthenticationException e) {
            // Log para depuração em caso de erro
            System.err.println("Erro de autenticação: " + e.getMessage());
            return ResponseEntity.status(401).body(new ErrorResponse("Credenciais inválidas. Verifique seu username e senha."));
        }
    }
}

// Classe para a requisição de autenticação
class AuthRequest {
    private String username;
    private String password;

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// Classe para a resposta de autenticação
class AuthResponse {
    private String token;
    private Long userId;

    public AuthResponse(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

// Classe para a resposta de erro
class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}