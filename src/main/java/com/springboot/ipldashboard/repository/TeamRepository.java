package com.springboot.ipldashboard.repository;

import com.springboot.ipldashboard.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team,Long> {

    Team findByTeamNameIgnoreCase(String teamName);

    default Team findTeam(String teamName) {
        return findByTeamNameIgnoreCase(teamName);
    }
}
