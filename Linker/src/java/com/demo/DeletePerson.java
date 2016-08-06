/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.io.File;
import java.nio.file.Files;
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
    GetAdminInfoId info_id = new GetAdminInfoId();

    public void execute(Object[] receive) {
        userId = (Long) receive[1];

        try {
            int length = receive.length;

            con = db.setupConnection();
            long infoId = info_id.adminInfoId(userId);

            for (int i = 2; i < length; i++) {

                long p_id = (Long) receive[i];

                //delete the existing file....................
//                Statement st = con.createStatement();
//                String query="select o_path from original where info_id='"+ infoId+"' and p_id='"+p_id+"'";
//                 
//                 ResultSet res=st.executeQuery(query);
                long fileNumber = p_id * 5;

                for (long j = fileNumber; j < fileNumber + 5; j++) {
                    //String originalPath=MyServletPack.basePath+"\\"+userId+"\\original\\"+p_id+"_"+j+".png";

                    new File(MyServletPack.basePath + "\\" + userId + "\\original\\" + j + ".png").delete();
                    new File(MyServletPack.basePath + "\\" + userId + "\\enhanced\\" + j + ".png").delete();

                }

//                 while(res.next()){
//                     System.out.println("hello world in res");
//                     String p=res.getString("o_path");
//                     File file=new File(p);
//                     
//                     file.delete(); 
//                 }
//                 st.close();
//                
//                 //delete from enhanced table
//                 st = con.createStatement();
//                query="select e_path from enhanced where info_id='"+ infoId+"' and p_id='"+p_id+"'";
                //   ResultSet res=st.executeQuery(query);
                //............................................
                //delete from database entity
                Statement stmt = con.createStatement();
                String sql = "DELETE from original " + " WHERE p_id='" + p_id + "'";
                stmt.executeUpdate(sql);
                stmt.close();

                stmt = con.createStatement();
                sql = "DELETE from enhanced " + " WHERE p_id='" + p_id + "'";
                stmt.executeUpdate(sql);
                stmt.close();

                stmt = con.createStatement();
                sql = "DELETE from person " + " WHERE info_id='" + info_id + " and p_id=" + p_id + "'";
                stmt.executeUpdate(sql);
                stmt.close();

            }

            File folder = new File(MyServletPack.basePath + "\\" + userId + "\\enhanced");

            File[] listOfFiles = folder.listFiles();
            
            boolean fileFound=false;
            
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileFound=true;
                    JavaFaces.trainingImagePath = MyServletPack.basePath + "\\" + userId + "\\enhanced";
                    JavaFaces.eigenCachePath = MyServletPack.basePath + "\\" + userId + "\\eigencache\\eigen.cache";
                    JavaFaces.trainImage();
                    break;
                }
            }
            if(fileFound==false)
            {
                new File(MyServletPack.basePath + "\\" + userId + "\\eigencache\\eigen.cache").delete();
            }

//System.out.println("path is: " + );
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        MyServletPack.responsedObject = new Object[3];
        MyServletPack.responsedObject[0] = 4;
        MyServletPack.responsedObject[1] = userId;
        if (flag == true) {
            MyServletPack.responsedObject[2] = true;
        } else {
            MyServletPack.responsedObject[2] = false;
        }

    }

}
