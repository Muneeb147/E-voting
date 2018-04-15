package com.example.zia.logindemo;

/**
 * Created by zia on 4/7/18.
 */

public class Candidate {
    public String email;
    public  String name;
    public String usertype;
    public String school;
    public String gender;
    public int votecount;
    public String agenda;
    public String position;
    public boolean isadmin;

    public Candidate(String name,String em,String usert, String sc, String g,String ag, int v, String p){
        this.email = em;
        this.name= name;
        this.usertype = usert;
        this.school = sc;
        this.gender = g;
        this.votecount = v;
        this.agenda = ag;
        this.position= p;
        this.isadmin = false;
    }
}
