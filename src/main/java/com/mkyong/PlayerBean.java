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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "player")
@SessionScoped
public class PlayerBean implements Serializable {

    private static Connection c = null;
    private static boolean loadedPlayers = false;
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private ArrayList<Player> filteredPlayers;
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

    public ArrayList<Player> getPlayerArrayList() {
        if (!playerList.isEmpty()) {
            return playerList;
        }

        if (c == null) {
            Statement stmt = null;

            try {
                playerList.clear();
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
                    String age = rs.getString("AGE");
                    String nationality = rs.getString("NATIONALITY");

                    player.setId(playerID);
                    player.setName(name);
                    player.setGender(gender);
                    if (!age.equals("null") && age.length() > 1) {
                        player.setAge(LocalDate.parse(age));
                    } else {
                        player.setAge(LocalDate.MIN);
                    }
                    if (nationality != null && !nationality.equals("null") && nationality.length() > 1) {
                        player.setNationality(nationality);
                    } else {
                        player.setNationality("");
                    }
                    playerList.add(player);

//                    if (player.getActualAge() == 17 || player.getActualAge() == 18) {
//                        playerList.add(player);
//                    }
//                    if (playerList.size() >= 1000) {
//                        break;
//                    }
                }
                c = null;
                c.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (c != null) {
                    try {
                        c = null;
                        c.close();
                    } catch (SQLException e) {
                    }
                }
            }
        }
        return playerList;

    }

    public static Connection getConn() throws Exception {
        if (c == null) {
            Class.forName("org.sqlite.JDBC");
//            System.out.println("Location: " +System.getProperty("user.dir"));
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Administrator\\Documents\\NetBeansProjects\\JavaServerFaces\\src\\main\\resources\\TennisPlayers.db");
        }
        return c;
    }

}
