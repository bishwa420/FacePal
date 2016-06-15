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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tapos
 */
public class GetAdminInfoId {
    
    private Connection con;
    DatabaseConnection db=new DatabaseConnection();
    
    public long adminInfoId(long userId){
        
        long infoId=0;
        con = db.setupConnection();
         String query = "select info_id " + " from info where log_id='" + userId+ "'";
            Statement stmt;
            
        try {
            stmt = con.createStatement();
            
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                infoId= result.getLong("info_id");
            }
            //strt.close();
            stmt.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GetAdminInfoId.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return infoId;
        
    }
    
}
