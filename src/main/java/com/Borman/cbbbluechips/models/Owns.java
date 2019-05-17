package com.Borman.cbbbluechips.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties({"ownsId", "teamId", "userId"})
public class Owns {

    private String ownsId;
    private String teamId;
    private String userId;
    private int amountOwned;
    private String teamName;
    private String nextPointSpread;
    private int seed;
    private boolean isOut;

    public String getOwnsId() {
        return ownsId;
    }

    public void setOwnsId(String ownsId) {
        this.ownsId = ownsId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmountOwned() {
        return amountOwned;
    }

    public void setAmountOwned(int amountOwned) {
        this.amountOwned = amountOwned;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNextPointSpread() {
        return nextPointSpread;
    }

    public void setNextPointSpread(String nextPointSpread) {
        this.nextPointSpread = nextPointSpread;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write Owns as string", e);
        }
    }

}
