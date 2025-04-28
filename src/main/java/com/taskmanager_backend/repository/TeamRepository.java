package com.taskmanager_backend.repository;

import com.taskmanager_backend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {

}
