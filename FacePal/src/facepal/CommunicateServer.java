/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Tapos
 */
public class CommunicateServer {

    public static Object[] sendObject;
    private static Object[] receivedObject;
    private static boolean flag = false;

    public static URLConnection getConnection() throws MalformedURLException {
        URL urlServlet = new URL("Http://localhost:8259/Linker/MyServletPack");
        try {
            URLConnection con = urlServlet.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            return con;
        } catch (IOException ex) {
            // Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void callSendObject(Object[] object) {
       

       
        try {

            URLConnection con = getConnection();
            //send data to servlet
            OutputStream outstream = con.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outstream);

            //System.out.println((String)ob[0]+" sdjfh "+ (Integer)ob[1]);
            oos.writeObject(object);
            oos.flush();
            oos.close();

            System.out.println(object.length + " dfgjkdlfkg");

            // System.out.println((String)ob[0]+" sdjfh "+ (Integer)ob[1]);
            // receive result from servlet
            InputStream instr = con.getInputStream();
            ObjectInputStream inputFromServlet = new ObjectInputStream(instr);
           
            // receivedObject = (Object[]) inputFromServlet.readObject();
            Object result = inputFromServlet.readObject();
            System.out.println(result);

            receivedObject = (Object[]) result;
            System.out.println(receivedObject.length);
            inputFromServlet.close();
            instr.close();
            //show result
            System.out.println("complete");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Object[] getObject() {

        return receivedObject;
    }

}
