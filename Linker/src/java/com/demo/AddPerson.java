/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

//import javafaces.JavaFaces;
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
    public PreparedStatement strt = null;

    private long userId;
    private ByteToImageConvert saveImage = new ByteToImageConvert();
    private String[] pathArray;
    private Connection con;
    // public String path = "E:\\Study\\code\\Linker\\images";

    public void execute(Object[] receive) {

        userId = (Long) receive[1];
        name = (String) receive[2];
        path = new String[10];

        for (int i = 0, k = 3; i < 5; i++, k++) {
            path[i] = (String) receive[k];
        }

        pathArray = new String[5];

        databaseUpdate();

        MyServletPack.responsedObject = new Object[4];

        MyServletPack.responsedObject[0] = 2;
        MyServletPack.responsedObject[1] = userId;
        MyServletPack.responsedObject[2] = true;

//        JavaFaces.trainingImagePath = "E:\\Study\\code\\Linker\\images";
//        JavaFaces.eigenCachePath = "E:\\Study\\code\\Linker\\eigenpaths";
//        System.out.println("path is: " + path);
//        JavaFaces.trainImage();
//        JavaFaces.recognize()
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

            if (check != 0) {

                query = "select p_id from person where person_name='" + name + "' and info_id='" + infoId + "'";

                Statement st = con.createStatement();
                ResultSet res = st.executeQuery(query);

                while (res.next()) {

                    personId = res.getLong("p_id");
                }
                st.close();

                for (int i = 0; i < 5; i++) {
                    if (path[i].equals("") == false) {
                        pathArray[i] = saveImage.getImgae(userId, personId+"_", i, path[i]);
                    } else {
                        pathArray[i] = "";
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

                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(AddPerson.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
