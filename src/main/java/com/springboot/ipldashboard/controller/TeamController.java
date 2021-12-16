package com.springboot.ipldashboard.controller;

import com.springboot.ipldashboard.model.Match;
import com.springboot.ipldashboard.model.Team;
import com.springboot.ipldashboard.repository.MatchRepository;
import com.springboot.ipldashboard.repository.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/teams/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName,@RequestParam int year){
        System.out.println("Team Name: "+teamName);
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);
        System.out.println(startDate+" "+endDate);

        return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
    }

    @GetMapping("/teams")
    public Iterable<Team> getTeams(){
        return teamRepository.findAll();
    }
}
