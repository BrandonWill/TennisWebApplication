/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

import java.util.ArrayList;

/**
 * Created by Brandon on 1/25/2017.
 */
public class Game {
    private String numberOfPlayers;
    private String grade;
    private String entry;
    private String surface;
    private ArrayList<Match> matches = new ArrayList<>();

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(String matchType, String record, String partnerData, String opponentName, String score) {
        matches.add(new Match(matchType, record, partnerData, opponentName, score));
    }
}
