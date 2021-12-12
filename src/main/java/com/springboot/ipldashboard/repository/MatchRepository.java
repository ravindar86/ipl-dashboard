package com.springboot.ipldashboard.repository;

import com.springboot.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findByTeam1IgnoreCaseOrTeam2IgnoreCaseOrderByDateDesc(String team1, String team2, Pageable page);

    @Query("select m from Match m where (upper(m.team1)=upper(:teamName) or upper(m.team2)=upper(:teamName)) " +
            "and m.date between :startDate and :endDate order by m.date desc")
    List<Match> getMatchesByTeamBetweenDates(
            @Param("teamName") String teamName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    default List<Match> findMatchesByTeam(String team){
        return findByTeam1IgnoreCaseOrTeam2IgnoreCaseOrderByDateDesc(team, team, PageRequest.of(0,4));
    }
}
