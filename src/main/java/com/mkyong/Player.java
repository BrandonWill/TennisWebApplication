/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Brandon
 */
public class Player {

    private String name;
    private int id;
    private String gender;
    private String nationality;
    private LocalDate age;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", id=" + id + '}';
    }
}
