package com.springboot.ipldashboard.data.processor;

import com.springboot.ipldashboard.data.MatchInput;
import com.springboot.ipldashboard.model.Match;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;


public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(MatchInput matchInput) throws Exception {

        final Match match = new Match();

        match.setId(Long.valueOf(matchInput.getId()));
        match.setCity(matchInput.getCity());
        match.setDate(LocalDate.parse(matchInput.getDate()));
        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        String firstInnTeam, secondInnTeam;
        if(matchInput.getToss_decision().equals("bat")) {
            firstInnTeam = matchInput.getToss_winner();
            secondInnTeam = StringUtils.equals(matchInput.getTeam1(),matchInput.getToss_winner())
                    ? match.getTeam2() :  match.getTeam1();
        }else{
            secondInnTeam = matchInput.getToss_winner();
            firstInnTeam = StringUtils.equals(matchInput.getTeam1(),matchInput.getToss_winner())
                    ? match.getTeam2() :  match.getTeam1();
        }

        match.setTeam1(firstInnTeam);
        match.setTeam2(secondInnTeam);
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setMatchWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        return match;
    }
}
