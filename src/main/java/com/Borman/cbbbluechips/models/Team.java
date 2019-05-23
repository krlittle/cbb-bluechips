package com.Borman.cbbbluechips.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

//TODO Update where ever to include new isLocked mapper sql database

public class Team {

    private String teamId;
    private String teamName;
    private boolean isEliminated;
    private boolean locked;
    private String seed;
    private String logoULR;
    private LocalDateTime nextGameTime;
    private String nextTeamPlaying;
    private String nextPointSpread;
    private double currentMarketPrice;
    private String sharesOutstanding;
    private LinkedHashMap<String, String> priceHistory;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setEliminated(boolean eliminated) {
        isEliminated = eliminated;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getLogoULR() {
        return logoULR;
    }

    public void setLogoULR(String logoULR) {
        this.logoULR = logoULR;
    }

    public LocalDateTime getNextGameTime() {
        return nextGameTime;
    }

    public void setNextGameTime(LocalDateTime nextGameTime) {
        this.nextGameTime = nextGameTime;
    }

    public String getNextTeamPlaying() {
        return nextTeamPlaying;
    }

    public void setNextTeamPlaying(String nextTeamPlaying) {
        this.nextTeamPlaying = nextTeamPlaying;
    }

    public String getNextPointSpread() {
        return nextPointSpread;
    }

    public void setNextPointSpread(String nextPointSpread) {
        this.nextPointSpread = nextPointSpread;
    }

    public double getCurrentMarketPrice() {
        return currentMarketPrice;
    }

    public void setCurrentMarketPrice(double currentMarketPrice) {
        this.currentMarketPrice = currentMarketPrice;
    }

    public String getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(String sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public LinkedHashMap<String, String> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(LinkedHashMap<String, String> priceHistory) {
        this.priceHistory = priceHistory;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write Team as string", e);
        }
    }

}
