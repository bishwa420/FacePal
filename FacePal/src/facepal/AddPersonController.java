/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.thehecklers.dialogfx.DialogFX;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class AddPersonController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    ScreenController myController;
    @FXML
    private TextField path1, path2, path3, path4, path5, personName;

    final FileChooser fileChooser = new FileChooser();
    public File file;
    private Object[] localReceive;

    public String addPersonName = "";
    public String fullPath1 = "";
    public String fullPath2 = "";
    public String fullPath3 = "";
    public String fullPath4 = "";
    public String fullPath5 = "";
    public String image1 = "";
    public String image2 = "";
    public String image3 = "";
    public String image4 = "";
    public String image5 = "";

    ImageTobyteConvert imgToByte;

    public void setScreenParent(ScreenController screenPage) {

        myController = screenPage;

    }

    @FXML
    void OpenFile1(ActionEvent e) {
        try {
            file = fileChooser.showOpenDialog(new Stage());

            if (file == null) {
                ;
            } else {

                fullPath1 = file.getCanonicalPath();

                path1.setText(fullPath1);
                   
                image1 = imgToByte.convertimage(fullPath1);
                //System.out.println(image1.length());

            }

        } catch (Exception ex) {
            Logger.getLogger(AddPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void OpenFile2(ActionEvent e) {
        try {
            file = fileChooser.showOpenDialog(new Stage());

            if (file == null) {
                ;
            } else {

                fullPath2 = file.getCanonicalPath();

                path2.setText(fullPath2);

                image2 = imgToByte.convertimage(fullPath2);

            }

        } catch (Exception ex) {
            Logger.getLogger(AddPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void OpenFile3(ActionEvent e) {
        try {
            file = fileChooser.showOpenDialog(new Stage());

            if (file == null) {
                ;
            } else {

                fullPath3 = file.getCanonicalPath();

                path3.setText(fullPath3);

                image3 = imgToByte.convertimage(fullPath3);

            }

        } catch (Exception ex) {
            Logger.getLogger(AddPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void OpenFile4(ActionEvent e) {
        try {
            file = fileChooser.showOpenDialog(new Stage());

            if (file == null) {
                ;
            } else {

                fullPath4 = file.getCanonicalPath();

                path4.setText(fullPath4);

                image4 = imgToByte.convertimage(fullPath4);

            }

        } catch (Exception ex) {
            Logger.getLogger(AddPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void OpenFile5(ActionEvent e) {
        try {
            file = fileChooser.showOpenDialog(new Stage());

            if (file == null) {
                ;
            } else {

                fullPath5 = file.getCanonicalPath();

                path5.setText(fullPath5);

                image5 = imgToByte.convertimage(fullPath5);

            }

        } catch (Exception ex) {
            Logger.getLogger(AddPersonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void addPersonAction(ActionEvent e) {

        // boolean valid=true;
        addPersonName = personName.getText();

        if (addPersonName.equals("") == false && (image1.equals("") == false || image2.equals("") == false || image3.equals("") == false || image4.equals("") == false || image5.equals("") == false)) 
        {
            System.out.println(addPersonName +" "+fullPath1+" t "+ fullPath2+"  t "+fullPath3+" t "+ fullPath4+" t "+fullPath5 );
            CommunicateServer.sendObject = new Object[9];

            CommunicateServer.sendObject[0] = 2;
            CommunicateServer.sendObject[1] = WelcomeController.adminId;
            CommunicateServer.sendObject[2] = addPersonName;
            CommunicateServer.sendObject[3] = image1;
            CommunicateServer.sendObject[4] = image2;
            CommunicateServer.sendObject[5] = image3;
            CommunicateServer.sendObject[6] = image4;
            CommunicateServer.sendObject[7] = image5;
            
            CommunicateServer.callSendObject(CommunicateServer.sendObject);

            localReceive = CommunicateServer.getObject();

            WelcomeController.serviceId = (int) localReceive[0];
            long adminId = (Long) localReceive[1];
            boolean update = (boolean) localReceive[2];

            if (WelcomeController.serviceId == 2 && WelcomeController.adminId == adminId && update == true) {

                DialogFX dialog = new DialogFX();
                dialog.setTitleText("Info");
                dialog.setMessage("Successfully Added.");
                dialog.showDialog();

                //clear field
                setTopText("");
            } else {
                setTopText("");
            }

        } else {
            setTopText("");
        }

    }
    
    @FXML
    private void homeAction(ActionEvent e){
       myController.setScreen(Main.screen2ID); 
    }
    @FXML
    private void addAction(ActionEvent e){
        myController.setScreen(Main.screen3ID);
    }
    @FXML
    private void showAction(ActionEvent e){
        myController.setScreen(Main.screen4ID);
       ShowPeopleController.trigger.setDisable(false);
       ShowPeopleController.trigger.fire();
      
    }
    @FXML
    private void settingsAction(ActionEvent e){
        myController.setScreen(Main.screen5ID);
    }
    @FXML
    private void logoutAction(ActionEvent e){
        myController.setScreen(Main.screen1ID);
        WelcomeController.logIn.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        setTopText("");
    }

    public void setTopText(String text) {
        // set text from another class
        path1.setText(text);
        path2.setText(text);
        path3.setText(text);
        path4.setText(text);
        path5.setText(text);
        
        image1=image2=image3=image4=image5=fullPath1=fullPath2=fullPath3=fullPath4=fullPath5="";

        path1.setEditable(false);
        path2.setEditable(false);
        path3.setEditable(false);
        path4.setEditable(false);
        path5.setEditable(false);

        personName.setText("");
        imgToByte = new ImageTobyteConvert();
    }

}
