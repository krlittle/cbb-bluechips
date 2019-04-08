package com.Borman.cbbbluechips.controllers;

import com.Borman.cbbbluechips.models.GameInfo;
import com.Borman.cbbbluechips.services.TeamService;
import com.Borman.cbbbluechips.services.TransactionService;
import com.Borman.cbbbluechips.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;


    @Autowired
    TransactionService transactionService;

    @GetMapping("/api")
    public ResponseEntity<GameInfo> getData() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setGameLobby("Shelter Insurance Crew");
        gameInfo.setAllUsers(userService.getUsers());
        gameInfo.setHighSore("235,000");
        gameInfo.setMoneyInPlay(2342352);
        gameInfo.setRoundOfPlay("Sweet Sixteen");
        gameInfo.setTeams(teamService.getTeams());
        gameInfo.setAllTransactions(transactionService.getAllTransactions());

        gameInfo.getTeams().get(0).setTeamData(teamService.getTeamData());

        return ResponseEntity.ok(gameInfo);
    }


}
