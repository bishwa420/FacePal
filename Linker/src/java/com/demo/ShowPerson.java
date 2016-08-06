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
import java.util.ArrayList;
//import org.jcp.xml.dsig.internal.dom.DOMUtils;

/**
 *
 * @author Tapos
 */
public class ShowPerson {

    private long userId = 0;
    DatabaseConnection db = new DatabaseConnection();
    ImageTobyteConvert img = new ImageTobyteConvert();

    public PreparedStatement strt = null;
    private Connection con = null;
    private boolean flag = false;
    private ArrayList<Long> personId = new ArrayList<Long>();
    private ArrayList<String> personName = new ArrayList<String>();
    private ArrayList<String> personImage = new ArrayList<String>();

    public void execute(Object[] receive) {

        userId = (Long) receive[1];
        int cnt = 0;
        try {
            System.out.println("i am entering");
            GetAdminInfoId info_id = new GetAdminInfoId();
            long infoId = info_id.adminInfoId(userId);
            System.out.println(infoId);
            con = db.setupConnection();

            Statement stmt = con.createStatement();
            String query = "select p_id,person_name from person where info_id='" + infoId + "'";

            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {

                long p_id = result.getLong("p_id");
                String name = result.getString("person_name");
                String path = "";
                Statement st = con.createStatement();
                query = "select o_path from original where info_id='" + infoId + "' and p_id='" + p_id + "'";

                ResultSet res = st.executeQuery(query);

                boolean fileExist = false;

                while (res.next()) {
                    fileExist = true;
                    System.out.println("hello world in res");
                    String p = res.getString("o_path");
                    path = img.convertimage(p);

                    break;

                }
                st.close();

                if (fileExist == true) {
                    personId.add(p_id);
                    personName.add(name);
                    personImage.add(path);
                    cnt++;
                }

            }

            stmt.close();
            flag = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }

        if (flag == true) {

            MyServletPack.responsedObject = new Object[(cnt * 3) + 3];
            MyServletPack.responsedObject[0] = 3;
            MyServletPack.responsedObject[1] = userId;
            MyServletPack.responsedObject[2] = flag;

            for (int i = 0, k = 3; i < cnt; i++) {

                MyServletPack.responsedObject[k++] = (Long) personId.get(i);
                MyServletPack.responsedObject[k++] = (String) personName.get(i);
                MyServletPack.responsedObject[k++] = (String) personImage.get(i);
            }
        } else {
            MyServletPack.responsedObject = new Object[4];
            MyServletPack.responsedObject[0] = 3;
            MyServletPack.responsedObject[1] = userId;
            MyServletPack.responsedObject[2] = flag;

        }

    }

}
