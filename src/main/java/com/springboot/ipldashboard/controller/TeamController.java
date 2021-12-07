package com.springboot.ipldashboard.controller;

import com.springboot.ipldashboard.model.Team;
import com.springboot.ipldashboard.repository.MatchRepository;
import com.springboot.ipldashboard.repository.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    public TeamRepository teamRepository;
    public MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/teams/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team =  teamRepository.findTeam(teamName);
        team.setMatches(matchRepository.findMatchesByTeam(teamName));
        return team;
    }
}
