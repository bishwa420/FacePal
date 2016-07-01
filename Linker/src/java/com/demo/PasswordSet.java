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
public class PasswordSet {
    
    private boolean flag = false;
 
    private Connection con = null;
    DatabaseConnection db =new DatabaseConnection();
    
    public void passwordSet(Object[] receive){
        
        try {
            String email=(String )receive[1];
            String pass=(String) receive[2];
            
            con = db.setupConnection();
            
            String query="select log_id from info where Email='" + email + "'";
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                
                long id= result.getLong("log_id");
                
                   Statement st = con.createStatement();
                    String sql = "UPDATE login "
                            + "SET  password= '" + pass + "' WHERE log_id='" + id + "'";
                    st.executeUpdate(sql);
                    st.close();
                
                
            }
            
            flag=true;
            stmt.close();
            
        } catch (Exception ex) {
           flag=false;
        }
        
        MyServletPack.responsedObject=new Object[2];
        
        MyServletPack.responsedObject[0]=8;
        MyServletPack.responsedObject[1]=flag;
        
        
        
    }
    
}
