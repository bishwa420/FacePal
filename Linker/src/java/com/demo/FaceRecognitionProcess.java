/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import static com.demo.ByteToImageConvert.decodeImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tapos
 */
public class FaceRecognitionProcess {

    private long adminId;
    private boolean flag=false;
    public DatabaseConnection db=new DatabaseConnection();
    private Connection con;

    public void processing(Object[] receive) {

        adminId = (Long) receive[1];
//        JavaFaces.trainingImagePath = MyServletPack.basePath+"\\"+userId+"\\enhanced";
       boolean cacheExist = new File(MyServletPack.basePath + "\\" + adminId + "\\eigencache\\eigen.cache").exists();
       
       
       if(cacheExist!=false){
           flag=true;
           JavaFaces.eigenCachePath = MyServletPack.basePath + "\\" + adminId + "\\eigencache\\eigen.cache";
        //F:\project300Backup\13\eigencache\eigen.cache
        System.out.println("Path is : " + JavaFaces.eigenCachePath);
        
        MyServletPack.responsedObject = new Object[14];
        MyServletPack.responsedObject[0] = 6;
        MyServletPack.responsedObject[1] = adminId;
        
            int k=2;
        for (int i = 2; i < receive.length; i++) {
            
            String image = (String) receive[i];
            MyServletPack.responsedObject[k++]=JavaFaces.recognize(byteToImage(image));
            
            long personId=JavaFaces.fileNo/5;
            MyServletPack.responsedObject[k++]=getPersonName(personId);
            System.out.println("server, i: " + i + " name is: " + JavaFaces.matchedName);
        }
           
       }      

    }

    public BufferedImage byteToImage(String img) {
        BufferedImage bf = null;
        try {
            byte[] imageByteArray = decodeImage(img);
            

            ByteArrayInputStream bais = new ByteArrayInputStream(imageByteArray);

            bf = ImageIO.read(bais);
             // ImageIO.write(bf, "png", new File("F:\\temp"+2345+".png"));
            

        } catch (IOException ex) {
            Logger.getLogger(FaceRecognitionProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return bf;

    }
    
    private String getPersonName(long personId){
        String personName="";
        try {
            con=db.setupConnection();
            GetAdminInfoId info = new GetAdminInfoId();
             long infoId = info.adminInfoId(adminId);
            String  query = "select person_name from person where p_id='" + personId + "' and info_id='" + infoId + "'";
            
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(query);
            
            while (res.next()) {
                
                personName = res.getString("person_name");
            }
            st.close();
        } catch (SQLException ex) {
            System.out.println("exception got in sql query in face ");
            ex.printStackTrace();
           // Logger.getLogger(FaceRecognitionProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     return personName;   
    } 

}
