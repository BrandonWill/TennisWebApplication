/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

/**
 *
 * @author Brandon
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "player")
@SessionScoped
public class PlayerBean implements Serializable {

    private static Connection c = null;
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private ArrayList<Player> filteredPlayers = new ArrayList<Player>();
    private static final String genders[];

    static {
        genders = new String[2];
        genders[0] = "Male";
        genders[1] = "Female";
    }

    public List<String> getAllGenders() {
        return Arrays.asList(genders);
    }

    public ArrayList<Player> getFilteredPlayers() {
        return filteredPlayers;
    }

    public void setFilteredPlayers(ArrayList<Player> filteredPlayers) {
        this.filteredPlayers = filteredPlayers;
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

    public ArrayList<Player> getPlayerArrayList() {
        if (!playerList.isEmpty()) {
            if (filteredPlayers != null && filteredPlayers.isEmpty()) {
                filteredPlayers.addAll(playerList);
            }
            return playerList;
        }
        if (c == null) {
            loadPlayerData();
            return playerList;
        } else {
            return playerList;
        }
    }

    public static Connection getConn() throws Exception {
        if (c == null) {
            Class.forName("org.sqlite.JDBC");
//            System.out.println("Location: " +System.getProperty("user.dir"));
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Brandon\\Documents\\NetBeansProjects\\JavaServerFaces\\src\\main\\resources\\TennisPlayers.db");
        }
        return c;
    }

    public void loadPlayerData() {
        loadPlayers();
        loadTournaments();
        loadGames();
    }

    private void loadPlayers() {

        Statement stmt = null;

        try {
            c = getConn();

            stmt = c.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery("SELECT * FROM PLAYERS");
            System.out.println("Reading players");
            while (rs.next()) {
                Player player = new Player();
                int playerID = rs.getInt("PLAYERID");
                String name = rs.getString("PLAYERNAME");
                String gender = rs.getString("GENDER");
                String nationality = rs.getString("NATIONALITY");
                String age = rs.getString("AGE");
                ArrayList<Integer> tournamentIDs = stringToIntegerArray(rs.getString("TOURNAMENTS"));

                player.setId(playerID);
                player.setName(name);
                player.setGender(gender);
                player.setNationality(nationality);
                player.setInATournament(tournamentIDs.size() > 0);
                if (!age.equals("null") && age.length() > 1) {
                    player.setAge(LocalDate.parse(age));
                } else {
                    player.setAge(LocalDate.MIN);
                }

                if (player.isInATournament()) {
                    player.setTournamentIDs(tournamentIDs);
                }

                playerList.add(player);

            }

            filteredPlayers.addAll(playerList);
            c.close();
            c = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                    c = null;
                } catch (SQLException e) {
                }
            }
        }
    }

    private boolean createCSV() {
        try {
            FileWriter fw = new FileWriter(new File("TennisPlayers.csv"));

            fw.write("URL,Id,Name,Nationality,Gender,Age,Grade A,Record,Grade 1,Record,Grade 2,Record,Grade 3,Record,Grade 4,Record,Grade 5,Record\n");

            ArrayList<Player> playerArrayList;

            if (filteredPlayers != null && !filteredPlayers.isEmpty()) {
                playerArrayList = filteredPlayers;
            } else {
                playerArrayList = playerList;
            }

            for (Player player : playerArrayList) {
                if (player.getName() != null) {
                    fw.write("http://www.itftennis.com/juniors/players/player/profile.aspx?playerid=" + String.valueOf(player.getId()));
                    fw.write(",");
                    fw.write(String.valueOf(player.getId()));
                    fw.write(",");
                    fw.write(player.getName().replaceAll(",", ""));
                    fw.write(",");
                    fw.write(player.getNationality().replaceAll(",", ""));
                    fw.write(",");
                    fw.write(player.getGender());
                    fw.write(",");
                    fw.write(String.valueOf(player.getActualAge()));
                    fw.write(",");
                    fw.write(player.getFurthestInGradeA());
                    fw.write(",");
                    fw.write(player.getFurthestInGradeARecord());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade1());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade1Record());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade2());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade2Record());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade3());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade3Record());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade4());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade4Record());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade5());
                    fw.write(",");
                    fw.write(player.getFurthestInGrade5Record());
                    fw.write("\n");
                    fw.flush();
                }
            }

            fw.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public StreamedContent getDownloadFile() {

        if (createCSV()) {
            String path = "TennisPlayers.csv";
            String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(path);

            try {
                return new DefaultStreamedContent(new FileInputStream(path), contentType, "TennisPlayers.csv");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PlayerBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
//        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/table/test.csv");
//        StreamedContent file = new DefaultStreamedContent(stream, "csv", "test.csv");
//
//        return file;
    }

    private void loadTournaments() {
        //                    System.out.println("Player found with name: " +player.getName());
        Statement stmt = null;
        try {
            c = getConn();

            System.out.println("Reading Tournaments");
            for (Player player : playerList) {
                if (player.isInATournament()) {
                    ArrayList<Integer> tournamentIDs = player.getTournamentIDs();
                    for (Integer tournamentID : tournamentIDs) {
                        stmt = c.createStatement();
                        ResultSet tournamentResult = stmt.executeQuery("SELECT * FROM TOURNAMENTS WHERE TOURNAMENTID =" + tournamentID);

                        while (tournamentResult.next()) {
                            Tournament tournament = new Tournament();
                            String tournyName = tournamentResult.getString("NAME");
                            String tournyDate = tournamentResult.getString("TOURNAMENTDATE");
                            String tournyVenue = tournamentResult.getString("VENUE");
                            ArrayList<Integer> gameIDs = stringToIntegerArray(tournamentResult.getString("GAMES"));

                            tournament.setName(tournyName);
                            tournament.setDate(tournyDate);
                            tournament.setVenue(tournyVenue);
                            tournament.setGameIDs(gameIDs);

//                            System.out.println("Tournament found name:" + tournyName + " with games: " +gameIDs);
                            player.getTournaments().add(tournament);
                        }
                    }
                }

            }

            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                    c = null;
                } catch (SQLException e) {
                }
            }
        }
    }

    private void loadGames() {
        Statement stmt = null;
        try {
            c = getConn();

            stmt = c.createStatement();
            System.out.println("Reading Games");
            for (Player player : playerList) {
                if (player.isInATournament()) {
                    for (Tournament tournament : player.getTournaments()) {
                        ArrayList<Integer> gameIDs = tournament.getGameIDs();

                        for (Integer gameID : gameIDs) {
                            stmt = c.createStatement();
                            ResultSet gameResult = stmt.executeQuery("SELECT * FROM GAMES WHERE GAMEID=" + gameID);
                            while (gameResult.next()) {
                                Game game = new Game();
                                String numberOfPlayers = gameResult.getString("NUMBEROFPLAYERS");
                                String grade = gameResult.getString("GRADE");
                                String entry = gameResult.getString("ENTRY");
                                String surface = gameResult.getString("SURFACE");
                                ArrayList<Integer> matchIDs = stringToIntegerArray(gameResult.getString("MATCHES"));

                                game.setNumberOfPlayers(numberOfPlayers);
                                game.setGrade(grade);
                                game.setEntry(entry);
                                game.setSurface(surface);

//                                    System.out.println("Match found of grade: " +grade);
                                tournament.getGames().add(game);

                                for (Integer matchID : matchIDs) {
                                    stmt = c.createStatement();
                                    ResultSet matchResult = stmt.executeQuery("SELECT * FROM MATCHES WHERE MATCHID=" + matchID);

                                    while (matchResult.next()) {
                                        Match match = new Match();
                                        String matchType = matchResult.getString("MATCHTYPE");
                                        String record = matchResult.getString("RECORD");
                                        String opponentName = matchResult.getString("OPPONENTNAME");
                                        String score = matchResult.getString("SCORE");

                                        match.setMatchType(matchType);
                                        match.setRecord(record);
                                        match.setOpponentName(opponentName);
                                        match.setScore(score);

                                        game.getMatches().add(match);
                                    }
                                }
                            }
                        }
                    }
                    player.setupTournamentData();
                }

            }
            c.close();
            c = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                    c = null;
                } catch (SQLException e) {
                }
            }
        }
    }

    private static ArrayList<Integer> stringToIntegerArray(String string) {
        string = string.replaceAll(" ", "").replace("[", "").replace("]", "");
        String[] sArray = string.split(",");
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (String s : sArray) {
            if (isInteger(s)) {
                integerArrayList.add(Integer.parseInt(s));
            }
        }
        return integerArrayList;
    }

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

}
