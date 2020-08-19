package com.Borman.cbbbluechips.controllers;

import com.Borman.cbbbluechips.models.User;
import com.Borman.cbbbluechips.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private CookieService cookieService;
    private UserService userService;
    private OwnsService ownsService;
    private PortfolioService portfolioService;
    private LeaderboardService leaderboardService;

    public PortfolioController(CookieService cookieService, UserService userService, OwnsService ownsService, PortfolioService portfolioService, LeaderboardService leaderboardService) {
        this.cookieService = cookieService;
        this.userService = userService;
        this.ownsService = ownsService;
        this.portfolioService = portfolioService;
        this.leaderboardService = leaderboardService;
    }

    @RequestMapping("")
    public String portfolio(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        user.setTeamsOwned(ownsService.getTeamsUserOwns(user.getID()));
        model.addAttribute("user", user);
        model.addAttribute("leaderBoardPos", leaderboardService.getUsersLeaderPosition(userService.getUser(user.getID())));
        model.addAttribute("portfolio", portfolioService.getPortfolioDetails(user));
        return "portfolio";
    }

}
