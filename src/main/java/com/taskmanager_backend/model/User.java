package com.taskmanager_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "USERS")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Garante que o username seja único e obrigatório
    private String username;

    @Column(nullable = false) // Garante que a senha seja obrigatória
    private String password;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false) // Relaciona o usuário a uma equipe
    @JsonBackReference // Evita problemas de recursão infinita na serialização JSON
    private Team team;

    // Métodos obrigatórios da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Retorne as permissões do usuário, se necessário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}