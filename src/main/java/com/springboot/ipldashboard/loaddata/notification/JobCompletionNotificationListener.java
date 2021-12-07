package com.springboot.ipldashboard.loaddata.notification;

import com.springboot.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  //private final JdbcTemplate jdbcTemplate;
  private final EntityManager entityManager;

  @Autowired
  public JobCompletionNotificationListener(EntityManager entityManager) {
    //this.jdbcTemplate = jdbcTemplate;
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      /*jdbcTemplate.query("SELECT team1, team2, date, match_winner FROM match",
        (rs, row) -> "Team 1 "+rs.getString(1)+" Team 2 "+rs.getString(2)+" Date "+rs.getString(3)+" Winner -> "+rs.getString(4)
      ).forEach(str -> System.out.println(str));*/

      Map<String, Team> teamData = new HashMap<>();

      entityManager.createQuery("select m.team1, count(m) from Match m group by m.team1", Object[].class)
              .getResultList()
              .stream()
              .map(e -> new Team((String) e[0], (long) e[1]))
              .forEach(team -> teamData.put(team.getTeamName(), team));

      entityManager.createQuery("select m.team2, count(m) from Match m group by m.team2", Object[].class)
              .getResultList()
              .stream()
              .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                team.setTotalMatches(team.getTotalMatches()+(long)e[1]);
              });

      entityManager.createQuery("select m.matchWinner, count(m) from Match m group by m.matchWinner", Object[].class)
              .getResultList()
              .stream()
              .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                if(team!=null)
                  team.setTotalWins((long) e[1]);
              });

      teamData.values().stream().forEach(data -> System.out.println(data));

      // Saving the team details
      teamData.values().forEach(data -> entityManager.persist(data));
    }
  }
}