package com.Borman.cbbbluechips.services;

import com.Borman.cbbbluechips.daos.TeamDao;
import com.Borman.cbbbluechips.models.SportsDataAPI.SportsDataGamesToday;
import com.Borman.cbbbluechips.models.SportsDataAPI.SportsDataTeam;
import com.Borman.cbbbluechips.utilities.SportsDataApiRoutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SportsDataApiService {

    private RestTemplate restTemplate;
    private TeamDao teamDao;
    private final String apiKey = "df57fbe761424db78e5c16a103ceb0d0";


    public SportsDataApiService(RestTemplate restTemplate, TeamDao teamDao) {
        this.restTemplate = restTemplate;
        this.teamDao = teamDao;
    }

    void updateTeamsRecords() {
        List<SportsDataTeam> updatedTeamInfo = callTeamsFromSportsDataApi();
        updatedTeamInfo.forEach(this::updateTeamInfo);
        System.out.println("Test");
    }

    private List<SportsDataTeam> callTeamsFromSportsDataApi() {
        UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(SportsDataApiRoutes.getTeamData)
                .queryParam("key", apiKey);
        ResponseEntity<SportsDataTeam[]> response = restTemplate.getForEntity(url.build().getPath(), SportsDataTeam[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    //Todo maybe
    private void updateTeamInfo(SportsDataTeam team) {
        //here
    }




    public void updateTeamsPlayingToday() {
        List<SportsDataGamesToday> updatedTeamInfo = callGamesByDay();
        updatedTeamInfo.forEach(game -> {
            updateTeamNextGame(game.getAwayTeam(), game.getHomeTeamId());
            updateTeamNextGame(game.getHomeTeam(), game.getAwayTeamId());
        });
    }

    public List<SportsDataGamesToday> callGamesByDay() {
        UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(SportsDataApiRoutes.getGamesByDay + "2019-Apr-08")
                .queryParam("key", apiKey);
        ResponseEntity<SportsDataGamesToday[]> response = restTemplate.getForEntity(url.build().toUriString(), SportsDataGamesToday[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    private void updateTeamNextGame(String teamPlayingShortName, String teamToUpdateId) {
        System.out.println(String.format("UPDATE GAME: teamID: %s plays %s", teamToUpdateId, teamPlayingShortName));
        teamDao.updateNextTeamPlayingByTeamID(teamToUpdateId, teamPlayingShortName);
    }

}
