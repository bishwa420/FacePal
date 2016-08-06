/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tapos
 */
public class Register {

    private static String name = "";
    private static String password;
    private static String email;
    private static String userName;
    public PreparedStatement strt = null;
    private Connection con = null;
    private static long adminId;
    private boolean success = true;
    private boolean duplicate=false; 
    DatabaseConnection db = new DatabaseConnection();
    private boolean emailFound=false;

    public void register(Object[] receive) {

        name = (String) receive[1];
        email = (String) receive[2];
        userName = (String) receive[3];
        password = (String) receive[4];
        int check=0;

        try {

            //upadating data to login and info table and create root folder for Admin
            con = db.setupConnection();
            
            String q = "select log_id " + "from info where Email='" + email + "'";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(q);
             emailFound=false;
             
            while (result.next()) {
                
                emailFound=true;
                throw new NullPointerException();
            }
            stmt.close();
            
            
            
            System.out.println("database connected tapos ");
            String query = "Insert into LOGIN (username,password) values (?,?)";

            strt = con.prepareStatement(query);
            strt.setString(1, userName);
            strt.setString(2, password);
           
            duplicate=false;
            System.out.println("database connected datta ");
            
           
             check = strt.executeUpdate();
          
                
            
            if (check == 1) {
                System.out.println("database connected 2");
            }

            //get user id 
            query = "select log_id " + "from login where username='" + userName + "' and password='" + password + "'";
             stmt = con.createStatement();
             result = stmt.executeQuery(query);

            while (result.next()) {
                adminId = result.getLong("log_id");
            }
            strt.close();
            stmt.close();

            createDirectory();

            query = "insert into info(log_id,Admin_name,Email) values (?,?,?)";

            strt = con.prepareStatement(query);
            strt.setLong(1, adminId);
            strt.setString(2, name);
            strt.setString(3, email);
            System.out.println("database connected");
            
           
            int check1 = strt.executeUpdate();
            if (check1 == 1) {
                System.out.println("database connected 2");
            }

            strt.close();

        } catch (SQLException ex) {
            //ex.printStackTrace();
            success = false;
            if(check==0)
                duplicate=true;
            System.out.println("database not connected 1");
        }

        MyServletPack.responsedObject = new Object[6];
        MyServletPack.responsedObject[0] = 0;
        MyServletPack.responsedObject[1] = adminId;
        MyServletPack.responsedObject[2] = name;
        if (success == true) {
            MyServletPack.responsedObject[3] = true;

        } else {
            MyServletPack.responsedObject[3] = false;
        }
        
        if(duplicate==true){
            MyServletPack.responsedObject[4]=true;
        }
        else{
            MyServletPack.responsedObject[4]=false;
        }
        if(emailFound==true){
            MyServletPack.responsedObject[5]=true;
        }
        else{
            MyServletPack.responsedObject[5]=false;
        }

    }

    public static void createDirectory() {

        new File(MyServletPack.basePath + "\\" +adminId ).mkdirs();
        new File(MyServletPack.basePath + "\\" +adminId + "\\original").mkdirs();
        new File(MyServletPack.basePath + "\\" +adminId + "\\enhanced").mkdirs();
        new File(MyServletPack.basePath + "\\" +adminId + "\\eigencache").mkdirs();

    }

}
