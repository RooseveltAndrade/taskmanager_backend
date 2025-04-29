package com.taskmanager_backend.repository;

import com.taskmanager_backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // metodo que salva uma tarefa na base de dados
    default Team saveTeam(Team team) {
        return save(team);
    }
    // metodo que busca todas as tarefas na base de dados
    default List<Team> findAllTeams() {
        return findAll();
    }
    // metodo que busca uma tarefa pelo id na base de dados
    default Optional<Team> findByIdTeam(Long id) {
        return findById(id);
    }
    // metodo que deleta uma tarefa pelo id na base de dados
    default void deleteByIdTeam(Long id) {
        deleteById(id);
    }
    
}