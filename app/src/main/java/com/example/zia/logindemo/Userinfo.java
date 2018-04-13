package com.example.zia.logindemo;

/**
 * Created by zia on 4/7/18.
 */

public class Userinfo {
    public String email;
    public  String name;
    public String usertype;
    public String school;
    public String gender;
    public Userinfo(String name,String em,String usert, String sc, String g){
        this.email = em;
        this.name= name;
        this.usertype = usert;
        this.school = sc;
        this.gender = g;
    }
}
