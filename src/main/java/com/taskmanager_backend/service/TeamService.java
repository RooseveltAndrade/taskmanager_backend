package com.taskmanager_backend.service;

import com.taskmanager_backend.model.Team;
import com.taskmanager_backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team saveTeam(Team team) {
        return teamRepository.saveTeam(team);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAllTeams();
    }

    public Team findTeamById(Long id) {
        return teamRepository.findByIdTeam(id).orElseThrow(() -> new RuntimeException("Team not found"));
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteByIdTeam(id);
    }
    
}
