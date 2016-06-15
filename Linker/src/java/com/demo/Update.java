/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Tapos
 */
public class Update {

    private String adminName = "";
    private String password = "";
    private long userId = 0;
    private String updateEmail = "";
    private String newPassword = "";

    DatabaseConnection db = new DatabaseConnection();

    public PreparedStatement strt = null;
    private Connection con = null;
    private boolean flag = false;

    public void updateData(Object[] receive) {

        userId = (Long) receive[1];
        //adminName=String)
        adminName = (String) receive[2];
        updateEmail = (String) receive[3];
        password = (String) receive[4];
        newPassword = (String) receive[5];
        try {
            con = db.setupConnection();
            String query = "select username " + "from login where log_id='" + userId + "' and password='" + password + "'";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {

                flag = true;

               

                if (newPassword.equals("") == false) {

                    stmt = con.createStatement();
                    String sql = "UPDATE login "
                            + "SET  password= '" + newPassword + "' WHERE log_id='" + userId + "'";
                    stmt.executeUpdate(sql);
                    stmt.close();

                }
                if (updateEmail.equals("") == false) {
                    
                    
                    GetAdminInfoId adminId = new GetAdminInfoId();
                    long info_id = adminId.adminInfoId(userId);
                    stmt = con.createStatement();
                    String sql = "UPDATE info "
                            + "SET Email = '" + updateEmail + "' WHERE info_id='" + info_id + "'";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    
                    
                }
                 if (adminName.equals("") == false) {
                    
                    
                    GetAdminInfoId adminId = new GetAdminInfoId();
                    long info_id = adminId.adminInfoId(userId);
                    stmt = con.createStatement();
                    String sql = "UPDATE info "
                            + "SET Admin_name = '" + adminName + "' WHERE info_id='" + info_id + "'";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    
                    
                }

            }

        } catch (Exception ex) {
            //ex.printStackTrace();
           // System.out.println("error hoise ");
            flag=false;
        }
        
        
        MyServletPack.responsedObject=new Object[4];
        MyServletPack.responsedObject[0]=5;
        MyServletPack.responsedObject[1]=userId;
        MyServletPack.responsedObject[2]=flag;

    }

}
