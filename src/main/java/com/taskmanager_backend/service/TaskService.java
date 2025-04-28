package com.taskmanager_backend.service;

import com.taskmanager_backend.model.Task;
import com.taskmanager_backend.model.TaskStatus;
import com.taskmanager_backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Busca tarefas por ID da equipe
    public List<Task> findTasksByTeamId(Long teamId) {
        return taskRepository.findByTeamId(teamId);
    }

    // Busca tarefas por status e ID da equipe
    public List<Task> findTasksByStatus(Long teamId, TaskStatus status) {
        return taskRepository.findByTeamIdAndStatus(teamId, status);
    }

    // Salva uma nova tarefa ou atualiza uma existente
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // Busca uma tarefa pelo ID
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // Atualiza o status de uma tarefa
    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setStatus(status);
            return taskRepository.save(task);
        }
        return null;
    }

    // Filtra tarefas por status e/ou responsável
    public List<Task> filterTasks(Long teamId, TaskStatus status, Long responsibleId) {
        if (status != null && responsibleId != null) {
            return taskRepository.findByTeamIdAndStatusAndResponsibleId(teamId, status, responsibleId);
        } else if (status != null) {
            return taskRepository.findByTeamIdAndStatus(teamId, status);
        } else if (responsibleId != null) {
            return taskRepository.findByTeamIdAndResponsibleId(teamId, responsibleId);
        } else {
            return taskRepository.findByTeamId(teamId);
        }
    }
}