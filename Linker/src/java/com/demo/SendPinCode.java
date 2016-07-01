/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tapos
 */
public class SendPinCode {

    private boolean flag = false;
    private int pinCode;
    private Connection con = null;
    private int max = 999999;
    private int min = 100000;

    DatabaseConnection db = new DatabaseConnection();

    public void sendPinCode(Object[] receive) {

        try {
            String email = (String) receive[1];

            con = db.setupConnection();
            System.out.println("database connected");
            String query = "select info_id from info where Email='" + email + "'";

            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {

                Random rand = new Random();
                int code = rand.nextInt((max - min) + 1) + min;

                EmailConfiguration ec = new EmailConfiguration();

                SendMail sendingMail = new SendMail();

                sendingMail.sendMail(ec.from, ec.username, ec.password, email, ec.subject, ec.getMessage(code));
                
                flag=true;
                
                pinCode=code;
                
                break;
            }
            System.out.println("pincode = "+pinCode);
            stmt.close();
            
        } catch (SQLException ex) {
            // Logger.getLogger(SendPinCode.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        }
        
        MyServletPack.responsedObject=new Object[3];
        MyServletPack.responsedObject[0]=7;
        MyServletPack.responsedObject[1]=flag;
        MyServletPack.responsedObject[2]=pinCode;

    }

}
