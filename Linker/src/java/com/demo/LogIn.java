/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tapos
 */
public class LogIn {

    private static String password;
    private static long userId = 0;
    private static String userName;
    public PreparedStatement strt = null;
    private Connection con = null;
    private boolean flag = false;
    private String adminName="";
    DatabaseConnection db = new DatabaseConnection();

    public void login(Object[] receive) {

        userName = (String) receive[1];

        password = (String) receive[2];

        try {
            con = db.setupConnection();
            System.out.println("database connected");
            String query = "select log_id from login where username='" + userName + "' and password='" + password + "'";

            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                flag = true;
                userId = result.getLong("log_id");

                query = "select Admin_name from info where log_id='" + userId + "'";

                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(query);

                while (res.next()) {
                    
                    adminName = res.getString("Admin_name");
                }
                st.close();

            }
            stmt.close();
            
            System.out.println("paichi eto tuk porjonto ");
            MyServletPack.responsedObject = new Object[4];

            MyServletPack.responsedObject[0] = 1;
            MyServletPack.responsedObject[1] = flag;
            MyServletPack.responsedObject[2] = userId;
            MyServletPack.responsedObject[3] = adminName;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("database not connected 1");
        }

    }

}
