/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.awt.event.ActionListener;
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
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Tapos
 */
public class CommunicateServer {

    public static Object[] sendObject;
    private static Object[] receivedObject;
    private static boolean flag = false;
    public static Button closeButton;
    static CommunicateServer com;
    static Parent root;

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

    public static void operation(Object[] object) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    flag = false;
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
                    flag = true;
//                       

                    receivedObject = (Object[]) result;
                    System.out.println(receivedObject.length);
                    inputFromServlet.close();
                    instr.close();
                    if (flag == true) {
                        closeButton.fire();
                    }
                    //show result
                    System.out.println("complete");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommunicateServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

    }

    public void load() {
        try {
            root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void callSendObject(Object[] object) {

        com = new CommunicateServer();
        closeButton = new Button();
        Stage newStage = new Stage();
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newStage.close();
            }
        });

        com.load();
        FadeTransition ft = new FadeTransition(Duration.millis(100), LoadingController.loadingEx);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(10);
        ft.setAutoReverse(true);

        ft.play();

        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.initModality(Modality.APPLICATION_MODAL);
        operation(object);

        newStage.showAndWait();

    }

    public static Object[] getObject() {

        return receivedObject;
    }

}
