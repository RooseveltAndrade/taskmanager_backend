package com.taskmanager_backend.service;

import com.taskmanager_backend.model.User;
import com.taskmanager_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Retorna todos os usuários
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Retorna um usuário pelo ID
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Salva um novo usuário
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Deleta um usuário pelo ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Retorna um usuário pelo username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o username: " + username));
    }
}