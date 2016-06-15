/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tapos
 */
public class DatabaseConnection {
    
     public final static String DB_URL="jdbc:mysql://localhost:3306/projectdemo";
     public final static String USER="root";
     public final static String PASS="";
     private Connection con=null;

    public  Connection setupConnection(){
        
         try {
             
             Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection(DB_URL,USER,PASS);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);   
         } catch (SQLException ex) {
             Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return con;
    }
}
