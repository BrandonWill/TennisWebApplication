/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Brandon
 */
public class Player {

    private String name = "";
    private int id;
    private String gender = "";
    private String nationality = "";
    private LocalDate age;
    private boolean inATournament;

    ArrayList<Integer> tournamentIDs = new ArrayList<>();

    ArrayList<Integer> matchIDs = new ArrayList<>();

    private HashMap<String, Object[]> bestMatch = new HashMap<>(); //Object[] = [match type, record]... ex, [FR, W]
    private ArrayList<Tournament> tournaments = new ArrayList<>();
    private static final String genders[] = {"Male", "Female"};

    public List<String> getAllGenders() {
        return Arrays.asList(genders);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean filterByAge(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        if (!filterText.contains("-")) {
            return (Objects.equals(Integer.valueOf(value.toString()), Integer.valueOf(filterText)));
        } else {
            String[] range = filterText.split("-");
            int rangeBegin = Integer.valueOf(range[0]);
            int rangeEnd = Integer.valueOf(range[1]);

            for (int i = rangeBegin; i < rangeEnd + 1; i++) {
                if (Objects.equals(Integer.valueOf(value.toString()), i)) {
                    return true;
                }
            }
            return false;
        }
    }

    public ArrayList<Integer> getMatchIDs() {
        return matchIDs;
    }

    public void setMatchIDs(ArrayList<Integer> matchIDs) {
        this.matchIDs = matchIDs;
    }

    public ArrayList<Integer> getTournamentIDs() {
        return tournamentIDs;
    }

    public void setTournamentIDs(ArrayList<Integer> tournamentIDs) {
        this.tournamentIDs = tournamentIDs;
    }

    public boolean isInATournament() {
        return inATournament;
    }

    public void setInATournament(boolean inATournament) {
        this.inATournament = inATournament;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public ArrayList<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(ArrayList<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public int getActualAge() {
        LocalDate today = LocalDate.now();
        Period p = Period.between(age, today);
        if (p.getYears() > 100) {
            return -1;
        }
        return p.getYears();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setupTournamentData() {
        getBestMatch("Grade A");
        getBestMatch("1");
        getBestMatch("2");
        getBestMatch("3");
        getBestMatch("4");
        getBestMatch("5");
    }

    public String getFurthestInGradeA() {
        if (bestMatch.containsKey("Grade A")) {
            return bestMatch.get("Grade A")[0].toString();
        }
        return "";
    }

    public String getFurthestInGradeARecord() {
        if (bestMatch.containsKey("Grade A")) {
            return bestMatch.get("Grade A")[1].toString();
        }
        return "";
    }
    
    public String getFurthestInGrade1() {
        if (bestMatch.containsKey("1")) {
            return bestMatch.get("1")[0].toString();
        }
        return "";
    }

    public String getFurthestInGrade1Record() {
        if (bestMatch.containsKey("1")) {
            return bestMatch.get("1")[1].toString();
        }
        return "";
    }

    public String getFurthestInGrade2() {
        if (bestMatch.containsKey("2")) {
            return bestMatch.get("2")[0].toString();
        }
        return "";
    }

    public String getFurthestInGrade2Record() {
        if (bestMatch.containsKey("2")) {
            return bestMatch.get("2")[1].toString();
        }
        return "";
    }
    
        public String getFurthestInGrade3() {
        if (bestMatch.containsKey("3")) {
            return bestMatch.get("3")[0].toString();
        }
        return "";
    }

    public String getFurthestInGrade3Record() {
        if (bestMatch.containsKey("3")) {
            return bestMatch.get("3")[1].toString();
        }
        return "";
    }
    
        public String getFurthestInGrade4() {
        if (bestMatch.containsKey("4")) {
            return bestMatch.get("4")[0].toString();
        }
        return "";
    }

    public String getFurthestInGrade4Record() {
        if (bestMatch.containsKey("4")) {
            return bestMatch.get("4")[1].toString();
        }
        return "";
    }
    
        public String getFurthestInGrade5() {
        if (bestMatch.containsKey("5")) {
            return bestMatch.get("5")[0].toString();
        }
        return "";
    }

    public String getFurthestInGrade5Record() {
        if (bestMatch.containsKey("5")) {
            return bestMatch.get("5")[1].toString();
        }
        return "";
    }
    

    private void getBestMatch(String grade) {
        for (Tournament tournament : tournaments) {
            for (Game game : tournament.getGames()) {
                //We use contains here because some matches are like Grade B2. This will still consider it as a Grade B Game
                if (game.getGrade().contains(grade) && game.getNumberOfPlayers().equals("Singles")) { //&& game.getNumberOfPlayers().equals("Singles")
                    for (Match match : game.getMatches()) {
                        if (!bestMatch.containsKey(grade)) {
                            bestMatch.put(grade, new Object[]{match.getMatchType(), match.getRecord()});
//                           System.out.println(name + "(" +id + ") has a " +game.getGrade() +" at tournament " +tournament.getName() +": " +match.getMatchType() + " with record of: " +match.getRecord());
                        } else {
                            int currentMatchValue = currentMatchTypeValue(match.getMatchType(), match.getRecord());
                            String bestMatchType = bestMatch.get(grade)[0].toString();
                            String bestMatchRecord = bestMatch.get(grade)[1].toString();
                            int bestMatchValue = currentMatchTypeValue(bestMatchType, bestMatchRecord);
                            //lower is better! Equal puts the most recent matchup first
                            if (currentMatchValue <= bestMatchValue) {
                                bestMatch.put(grade, new Object[]{match.getMatchType(), match.getRecord()});
                            }
                        }
                    }
                }
            }
        }
    }

    private int currentMatchTypeValue(String matchType, String record) {
        int matchValue = 99999;
        switch (matchType) {
            case "64":
                matchValue = 64;

            case "32":
                matchValue = 32;

            case "16":
                matchValue = 16;

            case "QF":
                matchValue = 8;

            case "SF":
                matchValue = 4;

            case "FR":
                matchValue = 2;
        }
        if (record.equals("L")) {
            matchValue += 1;
        } else if (record.equals("W")) {
            matchValue -= 1;
        }
        return matchValue;
    }

//    private boolean isABetterMatch(String grade, String matchType, String record) {
//        if (!bestMatch.containsKey(grade)) {
//            return true;
//        } else {
//            String currentBestMatchType = bestMatch.get(grade);
//            if (currentBestMatchType.equals("FR") && record.equals("W")) {
//                return false;
//            } else if (matchType.equals("FR") && record.equals("W")) {
//                return true;
//            } else {
//                switch (matchType) {
//                    case "FR":
//                        //Will only be here if the record is L and it doesn't 
//                        return true;                   
//                    
//                    case "SF":
//                        //
//                        break;
//                }
//            }
//        }
//    }
}
