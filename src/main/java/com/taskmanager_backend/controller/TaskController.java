package com.taskmanager_backend.controller;

import com.taskmanager_backend.model.Team;
import com.taskmanager_backend.model.Task;
import com.taskmanager_backend.model.TaskStatus;
import com.taskmanager_backend.model.User; // Corrigido: Import da classe User
import com.taskmanager_backend.repository.TeamRepository;
import com.taskmanager_backend.repository.UserRepository;
import com.taskmanager_backend.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TaskController(TaskService taskService, UserRepository userRepository, TeamRepository teamRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    // Retorna todas as tarefas da equipe do usuário autenticado
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado")); // Corrigido: Busca o usuário no banco
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        return taskService.findTasksByTeamId(user.getTeam().getId());
    }

    // Cria uma nova tarefa associada à equipe do usuário autenticado
    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        task.setTeam(user.getTeam());
        return taskService.saveTask(task);
    }

    // Atualiza uma tarefa existente
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        Task existingTask = taskService.findTaskById(id);

        if (existingTask == null || !existingTask.getTeam().equals(user.getTeam())) {
            throw new IllegalStateException("Você não tem permissão para editar esta tarefa.");
        }

        updatedTask.setId(id);
        updatedTask.setTeam(existingTask.getTeam());
        return taskService.saveTask(updatedTask);
    }

    // Retorna tarefas por status da equipe do usuário autenticado
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        return taskService.findTasksByStatus(user.getTeam().getId(), status);
    }

    // Atualiza o status de uma tarefa
    @PatchMapping("/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));
        Task existingTask = taskService.findTaskById(id);

        if (existingTask == null || !existingTask.getTeam().equals(user.getTeam())) {
            throw new IllegalStateException("Você não tem permissão para alterar o status desta tarefa.");
        }

        return taskService.updateTaskStatus(id, status);
    }

    // Filtra tarefas por status e/ou responsável
    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long responsibleId,
            Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado"));

        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }

        return taskService.filterTasks(user.getTeam().getId(), status, responsibleId);
    }

    // Retorna os membros de uma equipe
    @GetMapping("/{teamId}/members")
    public List<User> getTeamMembers(@PathVariable Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
        return team.getUsers();
    }
}