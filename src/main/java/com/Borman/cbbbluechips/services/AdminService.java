package com.Borman.cbbbluechips.services;

import com.Borman.cbbbluechips.builders.MarketValueBuilder;
import com.Borman.cbbbluechips.builders.UpdatePointSpreadRequestBuilder;
import com.Borman.cbbbluechips.builders.UpdateSeedRequestBuilder;
import com.Borman.cbbbluechips.daos.AdminDao;
import com.Borman.cbbbluechips.daos.TeamDao;
import com.Borman.cbbbluechips.models.MarketValue;
import com.Borman.cbbbluechips.models.SportsDataAPI.SportsDataTeam;
import com.Borman.cbbbluechips.models.UpdatePointSpreadRequest;
import com.Borman.cbbbluechips.models.UpdateSeedRequest;
import com.Borman.cbbbluechips.twilio.TwiloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@Service
public class AdminService {

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    private RestTemplate restTemplate;
    private AdminDao adminDao;
    private String sportsDataUrl;
    private TeamDao teamDao;
    private TwiloService twiloService;

    public AdminService(RestTemplate restTemplate, AdminDao adminDao, @Qualifier("sportsDataUrl") String sportsDataUrl, TeamDao teamDao, TwiloService twiloService) {
        this.restTemplate = restTemplate;
        this.adminDao = adminDao;
        this.sportsDataUrl = sportsDataUrl;
        this.teamDao = teamDao;
        this.twiloService = twiloService;
    }

    @Transactional
    public void updateTeamsStoredInDataBase() {
        List<SportsDataTeam> updatedTeamInfo = getTeamsFromSportsDataApi();
        updatedTeamInfo.forEach(this::updateDatabase);
    }

    private void updateDatabase(SportsDataTeam team) {
        logger.info(String.format("Updating %s's Info", team.getSchool()));
        adminDao.updateTeamInfo(team);
    }

    private List<SportsDataTeam> getTeamsFromSportsDataApi() {
        ResponseEntity<SportsDataTeam[]> response = restTemplate.getForEntity(sportsDataUrl, SportsDataTeam[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public void processUpdateSeedRequest(List<String> allTeams, List<String> seedsValue) {
        if (allTeams.size() != seedsValue.size())
            System.out.println("Error. Not same size");
        List<UpdateSeedRequest> updates = new ArrayList<>();
        for (int x = 0; x < allTeams.size(); x++)
            updates.add(UpdateSeedRequestBuilder.anUpdateSeedRequest().withTeamName(allTeams.get(x)).withNewSeed(seedsValue.get(x)).build());
        updates.removeIf(team -> Integer.valueOf(team.getNewSeed()) <= 0);
        adminDao.setSeedsToDefault();
        updates.forEach(team -> adminDao.updateSeedRequest(team));
    }

    public void updateLockedAndEliminated(String teamName, boolean isEliminated, boolean isLocked) {
        logger.info(String.format("Updating %s's Info. Locked: %s Out: %s", teamName, isEliminated, isLocked));
        adminDao.updateLockedStatusAndEliminated(teamName, isEliminated, isLocked);
    }


    @Transactional
    public void updateMarketPrice(String teamName, double nextRoundPrice, int roundId) {
        MarketValue newMarketValue = MarketValueBuilder.aMarketValue()
                .withPrice(nextRoundPrice)
                .withRoundId(String.valueOf(roundId))
                .withTeamName(teamName)
                .withTeamId(teamDao.getTeamByName(teamName).getTeamId())
                .build();
        logger.info(String.format("New Price submitted: %s", newMarketValue.toString()));
        adminDao.updateMarketPriceByTeamAndRound(newMarketValue);

        boolean isThere = adminDao.checkForRoundPriceExists(newMarketValue);
        if (isThere)
            adminDao.archivePriceUpdateRenew(newMarketValue);
        else
            adminDao.archivePriceUpdateCreate(newMarketValue);

        twiloService.sendPriceChangeAlert(newMarketValue);

    }


    public void processUpdatePointSpreadRequest(List<String> teamNames, List<String> pointSpreads) {
        if (teamNames.size() != pointSpreads.size())
            System.out.println("Error. PointSpreadRequest Not same size");

        List<UpdatePointSpreadRequest> updates = new ArrayList<>();
        for (int x = 0; x < teamNames.size(); x++)
            updates.add(UpdatePointSpreadRequestBuilder.anUpdatePointSpreadRequest().withTeamName(teamNames.get(x)).withNextPointSpread(attemptToGetPointSpread(pointSpreads.get(x))).build());

        validateChangeOfPointSpread(updates);
        updates.forEach(team -> adminDao.updatePointSpreadRequest(team));
    }



    public void validateChangeOfPointSpread(List<UpdatePointSpreadRequest> updates) {

        BiPredicate<UpdatePointSpreadRequest, UpdatePointSpreadRequest> hasOpposite = (request, bulk) -> (swapPointSpreadToOppo(request.getNextPointSpread()).equals(String.valueOf(bulk.getNextPointSpread())));

//        updates.forEach(updateRequest -> {
//            if(updates.stream().noneMatch(bulk -> hasOpposite.test(updateRequest, bulk)))
//                throw new RuntimeException("Point Spreads do not match up");
//        });

    }


    private String swapPointSpreadToOppo(double pointSpread) {
        return  String.valueOf((pointSpread * -1));
    }


    private double attemptToGetPointSpread(String pointSpread) {

        try {
           return Double.parseDouble(pointSpread);
        }
        catch (Exception e) {
            return 0;
        }
    }

}
