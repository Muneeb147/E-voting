package com.example.zia.logindemo;

/**
 * Created by zia on 4/14/18.
 */

public class Voter {
    public String email;
    public  String name;
    public String usertype;
    public String school;
    public String gender;
    public boolean canvote;

    public Voter(String name,String em,String usert, String sc, String g,boolean cv){
        this.email = em;
        this.name= name;
        this.usertype = usert;
        this.school = sc;
        this.gender = g;
        this.canvote= cv;
    }
}
