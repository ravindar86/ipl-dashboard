package com.springboot.ipldashboard.repository;

import com.springboot.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findByTeam1OrTeam2IgnoreCaseOrderByDateDesc(String team1, String team2, Pageable page);

    default List<Match> findMatchesByTeam(String team){
        return findByTeam1OrTeam2IgnoreCaseOrderByDateDesc(team, team, PageRequest.of(0,4));
    }
}
