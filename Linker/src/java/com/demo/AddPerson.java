/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

//import javafaces.JavaFaces;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tapos
 */
public class AddPerson {

    private long adminId = 0;
    private String name = "";
    private String[] path;
    private String[] enhancePath;
    public PreparedStatement strt = null;

    private long userId;
    private ByteToImageConvert saveImage = new ByteToImageConvert();
    private String[] pathArray;
    private String[] enhancePathArray;
    private Connection con;
    // public String path = "E:\\Study\\code\\Linker\\images";

    public void execute(Object[] receive) {

        userId = (Long) receive[1];
        name = (String) receive[2];
        path = new String[5];
        enhancePath = new String[5];
        
        for (int i = 0, k = 3,j=8; i < 5; i++, k++,j++) {
            path[i] = (String) receive[k];
            enhancePath[i] = (String) receive[j];
        }

        pathArray = new String[5];
        enhancePathArray=new String[5];
        
        databaseUpdate();

        MyServletPack.responsedObject = new Object[4];

        MyServletPack.responsedObject[0] = 2;
        MyServletPack.responsedObject[1] = userId;
        MyServletPack.responsedObject[2] = true;

        JavaFaces.trainingImagePath = MyServletPack.basePath+"\\"+userId+"\\enhanced";
        JavaFaces.eigenCachePath = MyServletPack.basePath+"\\"+userId+"\\eigencache\\eigen.cache";
        System.out.println("path is: " + path);
        //new File(MyServletPack.basePath+"\\"+userId+"\\eigencache"+"\\eigen.cache").delete();
        JavaFaces.trainImage();
        //JavaFaces.recognize();
    }

    private void databaseUpdate() {

        DatabaseConnection db = new DatabaseConnection();
        try {
            con = db.setupConnection();
            GetAdminInfoId info = new GetAdminInfoId();
            long infoId = info.adminInfoId(userId);
            long personId = 0;

            System.out.println("database connected tapos ");
            String query = "Insert into person (info_id,person_name) values (?,?)";

            strt = con.prepareStatement(query);
            strt.setLong(1, infoId);
            strt.setString(2, name);
            int check = strt.executeUpdate();
            
            System.out.println("check for update person table");

            if (check != 0) {
                
                System.out.println("now operate for getting p_d");
                
                query = "select p_id from person where person_name='" + name + "' and info_id='" + infoId + "'";

                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(query);

                while (res.next()) {

                    personId = res.getLong("p_id");
                }
                st.close();
                System.out.println("getting p_id");
                long p_id=personId*5;

                for (int i = 0; i < 5; i++) {
                    
                    if (path[i].equals("") == false) {
                        
                        pathArray[i] = saveImage.getImgae(userId, Long.toString(p_id+i), i, path[i],"original");
                        
                        enhancePathArray[i]=saveImage.getImgae(userId,Long.toString(p_id+i), i, enhancePath[i],"enhanced");
                        
                       
                    } else {
                        
                        pathArray[i] = "";
                        enhancePathArray[i]="";
                        
                    }
                }
                   
                for (int i = 0; i < 5; i++) {
                    System.out.println(pathArray[i]);

                    if (pathArray[i].equals("") == false) {
                        query = "Insert into original (info_id,p_id,o_path) values (?,?,?)";
                        strt = con.prepareStatement(query);
                        strt.setLong(1, infoId);
                        strt.setLong(2, personId);
                        strt.setString(3, pathArray[i]);
                        strt.executeUpdate();
                        strt.close();
                        System.out.println("ak side update hoise ");
                        query = "Insert into enhanced (info_id,p_id,e_path) values (?,?,?)";
                        strt = con.prepareStatement(query);
                        strt.setLong(1, infoId);
                        strt.setLong(2, personId);
                        strt.setString(3, enhancePathArray[i]);
                        strt.executeUpdate();
                        strt.close();
                         System.out.println("ak side update hoise  hi hi");

                    }
                }
                System.out.println("path successfully updated ");

            }

        } catch (SQLException ex) {
            Logger.getLogger(AddPerson.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
