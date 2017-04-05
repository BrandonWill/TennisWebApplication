/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

import java.util.ArrayList;

/**
 * Created by Brandon on 1/20/2017.
 */
public class Tournament {

    private String name;
    private String date;
    private String venue;
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Integer> gameIDs = new ArrayList<>();

    public ArrayList<Integer> getGameIDs() {
        return gameIDs;
    }

    public void setGameIDs(ArrayList<Integer> gameIDs) {
        this.gameIDs = gameIDs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public Game addNewGame() {
        games.add(new Game());
        return games.get(games.size() - 1);
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public String getBestSinglesGameGrade() {
        for (Game game : games) {
            if (game.getNumberOfPlayers().equals("Singles")) {
                return game.getGrade();
            }
        }
        return null;
    }
}
