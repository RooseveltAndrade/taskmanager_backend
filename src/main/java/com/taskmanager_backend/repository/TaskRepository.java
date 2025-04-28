package com.taskmanager_backend.repository;

import com.taskmanager_backend.model.Task;
import com.taskmanager_backend.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Busca todas as tarefas de uma equipe
    List<Task> findByTeamId(Long teamId);

    // Busca tarefas por status e equipe
    List<Task> findByTeamIdAndStatus(Long teamId, TaskStatus status);

    // Busca tarefas por responsável e equipe
    List<Task> findByTeamIdAndResponsibleId(Long teamId, Long responsibleId);

    // Busca tarefas por status, responsável e equipe
    List<Task> findByTeamIdAndStatusAndResponsibleId(Long teamId, TaskStatus status, Long responsibleId);
}