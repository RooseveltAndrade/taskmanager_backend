package com.roosevelttandrade.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {

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
}