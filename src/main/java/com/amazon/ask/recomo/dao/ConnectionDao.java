package com.amazon.ask.recomo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDao {
    public static Connection con = null;
    public static void RetriveConnection(){
        try{
            Class.forName("software.aws.rds.jdbc.mysql.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql:aws://alexa.cioxcub7egry.us-east-1.rds.amazonaws.com:3306/Recomo","admin","Fys70719610"
            );
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
