package com.amazon.ask.recomo.dao;

import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.exception.PersistenceException;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.recomo.domain.movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SQLSyntax  {
    public Connection con;

    public SQLSyntax(){
        ConnectionDao.RetriveConnection();
        con = ConnectionDao.con;
    }

    public ArrayList<movie> SelectByType(String movietype) throws SQLException, InterruptedException {
        //      step1: Get all the movie type in the database
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select distinct Type from Movie");



        //      step2: Use Map tp store the relevence of name to avoid identification error
        Map<String,Double> movieCheck = new HashMap<>();
        while(rs.next()){
            String type = rs.getString(1);
            movieCheck.put(type,revevenceCalculate(type,movietype));
        }

        //      step3. Get the real type we need by choose the highest revelence;
        String typeNeed = ""; Double revelence = 0.0;
        for(Map.Entry<String,Double> entry: movieCheck.entrySet()){
            if(entry.getValue()>revelence){
                typeNeed  = entry.getKey();
                revelence = entry.getValue();
            }
        }

        //      Step4. Get the result we need.
        ArrayList<movie> ret = new ArrayList<>();
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select  * from Movie where Type = '"+typeNeed+"'");
        while(rs1.next()){
            movie movie = new movie();
            movie.setMovieName(rs1.getString(1));
            movie.setType(rs1.getString(2));
            movie.setDescription(rs1.getString(3));
            movie.setActor(rs1.getString(4));
            movie.setActress(rs1.getString(5));
            movie.setRank(rs1.getInt(6));
            ret.add(movie);
        }
        return ret;

    }



    public ArrayList<movie> SelectByPeople(String Name) throws SQLException, InterruptedException {
        ArrayList<movie> ret = new ArrayList<>();

        //      step1: Get all the movie actor and actress in the database
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select Actor, Actress from Movie");

        //      step2: Use Map tp store the relevence of name to avoid identification error
        Map<String,Double> nameCheck = new HashMap<>();
        while(rs.next()){
            String Actor = rs.getString(1);
            nameCheck.put(Actor,revevenceCalculate(Actor,Name));
            String Actress = rs.getString(2);
            nameCheck.put(Actress,revevenceCalculate(Actress,Name));
        }
        //      step3. Get the real type we need by choose the highest revelence;
        String nameNeed = ""; Double revelence = 0.0;
        for(Map.Entry<String,Double> entry: nameCheck.entrySet()){
            if(entry.getValue()>revelence){
                nameNeed  = entry.getKey();
                revelence = entry.getValue();
            }
        }

        //      Step4. Get the result we need.
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select  * from Movie where Actor = '"+nameNeed+"' or Actress = '"+nameNeed+"'");
        while(rs1.next()){
            movie movie = new movie();
            movie.setMovieName(rs1.getString(1));
            movie.setType(rs1.getString(2));
            movie.setDescription(rs1.getString(3));
            movie.setActor(rs1.getString(4));
            movie.setActress(rs1.getString(5));
            movie.setRank(rs1.getInt(6));
            ret.add(movie);
        }
        return ret;
    }

    public movie SelectByMovieName(String Name) throws SQLException, InterruptedException {

//      step1: Get all the movies in the database
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select  MovieName from Movie");

//      step2: Use Map tp store the relevence of name to avoid identification error
        Map<String,Double> movieCheck = new HashMap<>();
        while(rs.next()){
            String MovieName = rs.getString(1);
            movieCheck.put(MovieName,revevenceCalculate(MovieName,Name));
        }

//      step3. Get the real name we need by choose the highest revelence;
        String nameNeed = ""; Double revelence = 0.0;
        for(Map.Entry<String,Double> entry: movieCheck.entrySet()){
            if(entry.getValue()>revelence){
                nameNeed  = entry.getKey();
                revelence = entry.getValue();
            }
        }

//      Step4. Get the result we need.
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select  * from Movie where MovieName = '"+nameNeed+"'");
        movie movie = new movie();
        while(rs1.next()){
            movie.setMovieName(rs1.getString(1));
            movie.setType(rs1.getString(2));
            movie.setDescription(rs1.getString(3));
            movie.setActor(rs1.getString(4));
            movie.setActress(rs1.getString(5));
            movie.setRank(rs1.getInt(6));
        }
        return movie;
    }

    public double revevenceCalculate(String nameFromDatabase, String nameFromAlexa){

        if(nameFromDatabase == null){
            return 0;
        }
        nameFromDatabase = nameFromDatabase.toLowerCase();
        nameFromAlexa =  nameFromAlexa.toLowerCase();
        int nameLength = nameFromAlexa.length();
        int characterCount = 0;
        for(int i = 0; i<nameFromAlexa.length(); i++){
            if(nameFromDatabase.contains(nameFromAlexa.charAt(i)+"")){
                characterCount++;
            }
        }
        return (double) characterCount/nameLength;
    }


    /*Test code here*/
//    public static void main(String[] args) throws SQLException, InterruptedException {
//        SQLSyntax one = new SQLSyntax();
//        System.out.println(one.SelectByMovieName("Shining"));
//    }
}
