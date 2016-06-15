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
import java.sql.Statement;

/**
 *
 * @author Tapos
 */
public class DeletePerson {

    private Long userId;
    public PreparedStatement strt = null;
    private Connection con = null;
    private boolean flag = true;
    DatabaseConnection db = new DatabaseConnection();
    GetAdminInfoId info_id=new GetAdminInfoId();

    public void execute(Object[] receive) {
        userId = (Long) receive[1];

        try {
            int length = receive.length;

            con = db.setupConnection();
            long infoId=info_id.adminInfoId(userId);

            for (int i = 2; i < length; i++) {
                
                long p_id = (Long) receive[i];
                
                //delete the existing file....................
                
                Statement st = con.createStatement();
                String query="select o_path from original where info_id='"+ infoId+"' and p_id='"+p_id+"'";
                 
                 ResultSet res=st.executeQuery(query);
                 
                 while(res.next()){
                     System.out.println("hello world in res");
                     String p=res.getString("o_path");
                     File file=new File(p);
                     
                     file.delete(); 
                 }
                 st.close();
                
                
                
                //............................................
                  //delete from database entity
                
                
                Statement stmt = con.createStatement();
                String sql = "DELETE from original " + " WHERE p_id='" + p_id + "'";
                stmt.executeUpdate(sql);
                stmt.close();
                
                stmt = con.createStatement();
                 sql = "DELETE from person " + " WHERE p_id='" + p_id + "'";
                stmt.executeUpdate(sql);
                stmt.close();

            }

        } catch (Exception ex) {
               flag=false;
        }
        MyServletPack.responsedObject=new Object[3];
        MyServletPack.responsedObject[0]=4;
        MyServletPack.responsedObject[1]=userId;
        if(flag==true){
            MyServletPack.responsedObject[2]=true;
        }
        else{
            MyServletPack.responsedObject[2]=false;
        }

    }

}
