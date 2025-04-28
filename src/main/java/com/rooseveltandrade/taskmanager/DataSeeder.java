package com.rooseveltandrade.taskmanager;

import com.rooseveltandrade.taskmanager.model.Task;
import com.rooseveltandrade.taskmanager.model.Team;
import com.rooseveltandrade.taskmanager.model.User;
import com.rooseveltandrade.taskmanager.repository.TaskRepository;
import com.rooseveltandrade.taskmanager.repository.TeamRepository;
import com.rooseveltandrade.taskmanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(TeamRepository teamRepository, UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar equipes
        Team team1 = new Team();
        team1.setName("Equipe Backend");

        Team team2 = new Team();
        team2.setName("Equipe Frontend");

        teamRepository.saveAll(Arrays.asList(team1, team2));

        // Criar usuários
        User user1 = new User();
        user1.setUsername("joao");
        user1.setPassword(passwordEncoder.encode("123456")); // Criptografar a senha
        user1.setTeam(team1);

        User user2 = new User();
        user2.setUsername("maria");
        user2.setPassword(passwordEncoder.encode("123456")); // Criptografar a senha
        user2.setTeam(team2);

        userRepository.saveAll(Arrays.asList(user1, user2));

        // Criar tarefas
        Task task1 = new Task();
        task1.setTitle("Configurar servidor");
        task1.setDescription("Configurar o servidor de aplicação para o projeto.");
        task1.setTeam(team1);

        Task task2 = new Task();
        task2.setTitle("Criar layout");
        task2.setDescription("Desenvolver o layout inicial do sistema.");
        task2.setTeam(team2);

        taskRepository.saveAll(Arrays.asList(task1, task2));
    }
}