package com.rooseveltandrade.taskmanager.repository;

import com.rooseveltandrade.taskmanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {

}
