package com.rooseveltandrade.taskmanager.controller;

import com.rooseveltandrade.taskmanager.model.Team;
import com.rooseveltandrade.taskmanager.model.Task;
import com.rooseveltandrade.taskmanager.model.TaskStatus;
import com.rooseveltandrade.taskmanager.model.User;
import com.rooseveltandrade.taskmanager.repository.TeamRepository; // Importação adicionada
import com.rooseveltandrade.taskmanager.repository.UserRepository;
import com.rooseveltandrade.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository; // Adicionada a variável teamRepository

    public TaskController(TaskService taskService, UserRepository userRepository, TeamRepository teamRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository; // Inicialização do teamRepository
    }

    // Retorna todas as tarefas da equipe do usuário autenticado
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        return taskService.findTasksByTeamId(user.getTeam().getId()); // Retorna tarefas da equipe
    }

    // Cria uma nova tarefa associada à equipe do usuário autenticado
    @PostMapping
    public Task createTask(@RequestBody Task task, Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        task.setTeam(user.getTeam()); // Define a equipe da tarefa
        return taskService.saveTask(task); // Salva a tarefa
    }

    // Atualiza uma tarefa existente
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco
        Task existingTask = taskService.findTaskById(id); // Busca a tarefa pelo ID

        if (existingTask == null || !existingTask.getTeam().equals(user.getTeam())) {
            throw new IllegalStateException("Você não tem permissão para editar esta tarefa.");
        }

        updatedTask.setId(id); // Garante que o ID da tarefa seja mantido
        updatedTask.setTeam(existingTask.getTeam()); // Garante que a equipe não seja alterada
        return taskService.saveTask(updatedTask); // Atualiza a tarefa
    }

    // Retorna tarefas por status da equipe do usuário autenticado
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status, Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco
        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }
        return taskService.findTasksByStatus(user.getTeam().getId(), status); // Retorna tarefas por status
    }

    // Atualiza o status de uma tarefa
    @PatchMapping("/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus status, Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco
        Task existingTask = taskService.findTaskById(id); // Busca a tarefa pelo ID

        if (existingTask == null || !existingTask.getTeam().equals(user.getTeam())) {
            throw new IllegalStateException("Você não tem permissão para alterar o status desta tarefa.");
        }

        return taskService.updateTaskStatus(id, status); // Atualiza o status da tarefa
    }

    // Filtra tarefas por status e/ou responsável
    @GetMapping("/filter")
    public List<Task> filterTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long responsibleId,
            Authentication authentication) {
        String username = authentication.getName(); // Obtém o nome do usuário autenticado
        User user = userRepository.findByUsername(username); // Busca o usuário no banco

        if (user.getTeam() == null) {
            throw new IllegalStateException("Usuário não pertence a nenhuma equipe.");
        }

        return taskService.filterTasks(user.getTeam().getId(), status, responsibleId); // Filtra as tarefas
    }

    // Retorna os membros de uma equipe
    @GetMapping("/{teamId}/members")
    public List<User> getTeamMembers(@PathVariable Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
        return team.getUsers(); // Retorna os membros da equipe
    }
}