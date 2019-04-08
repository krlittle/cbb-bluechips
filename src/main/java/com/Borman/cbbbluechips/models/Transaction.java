package com.Borman.cbbbluechips.models;

import java.time.LocalDateTime;

public class Transaction {

    private User user;
    private String team;
    private int volume;
    private double cashAmount;
    private LocalDateTime timeOfTransaction;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public LocalDateTime getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public void setTimeOfTransaction(LocalDateTime timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }
}
