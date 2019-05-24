package com.Borman.cbbbluechips.controllers;

import com.Borman.cbbbluechips.services.TeamService;
import com.Borman.cbbbluechips.services.TransactionService;
import com.Borman.cbbbluechips.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService ;

    @Autowired
    TransactionService transactionService;

    @RequestMapping("/")
    public String welcome() {
        return "apiHelp";
    }


    @RequestMapping("/portfolio")
    public String portfolio(Model model) {
        return "portfolio";
    }

    @RequestMapping("/market")
    public String market(@RequestParam(defaultValue = "false") String allTeams, Model model) {
        model.addAttribute("teams", allTeams.toLowerCase().equals("true")
                ? teamService.getAllTeams(false) : teamService.getAllTeams(true));
        return "market";
    }

}
