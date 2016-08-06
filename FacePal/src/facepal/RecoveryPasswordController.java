/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.binding.*;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.lang.Integer;
import org.thehecklers.dialogfx.DialogFX;
import org.thehecklers.dialogfx.DialogFX.Type;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class RecoveryPasswordController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
     TextField recoveryEmail;
    @FXML
     TextField recoveryCode;
    @FXML
     TextField recoveryNewPassword;
    @FXML
     TextField recoveryConfirmPassword;
    @FXML
     Button submit;
    @FXML
     Button sendingCode;
    @FXML
     Button cancel;

    private int pinCode = 0;
    private String backupEmail = "";
    private boolean bind=false;

    ScreenController myController;

    @FXML
    private void sendingCodeAction(ActionEvent e) {

        String email = recoveryEmail.getText();

        if (email.equals("")==false &&  WelcomeController.isValidEmailAddress(email)) {

            CommunicateServer.sendObject = new Object[3];
            CommunicateServer.sendObject[0] = 7;
            CommunicateServer.sendObject[1] = email;
            
            CommunicateServer.callSendObject(CommunicateServer.sendObject,false);

            Object[] receive = CommunicateServer.getObject();
            if ((Integer) receive[0] == 7 && (boolean) receive[1] == true) {

                pinCode = (Integer) receive[2];
                backupEmail = email;
                DialogFX dialog = new DialogFX();
                dialog.setTitleText("Info");
                dialog.setMessage("Please check your mailbox.");
                dialog.showDialog();
                
                if(bind==false){
                submit.setDisable(false);
                recoveryCode.setDisable(false);
                recoveryNewPassword.setDisable(false);
                recoveryConfirmPassword.setDisable(false);

                submit.disableProperty().bind(
                        Bindings.isEmpty(recoveryCode.textProperty()));
                }
                bind=true;

            } else {

                DialogFX dialog = new DialogFX(Type.ERROR);
                dialog.setTitleText("Error");
                dialog.setMessage("Please,Enter valid Email Address");
                dialog.showDialog();
            }

        }
        else{
               DialogFX dialog = new DialogFX(Type.ERROR);
                dialog.setTitleText("Error");
                dialog.setMessage("Please,Enter valid Email Address");
                dialog.showDialog();
            
        }

    }

    @FXML
    private void cancelAction(ActionEvent e) {

        myController.setScreen(Main.screen1ID);
    }

    @FXML
    private void submitAction(ActionEvent e) {
        
        String a = recoveryNewPassword.getText();
        String b = recoveryConfirmPassword.getText();
        
        
        if (Integer.parseInt(recoveryCode.getText()) == pinCode && a.equals("") == false && b.equals("") == false && a.equals(b) == true) {

            CommunicateServer.sendObject = new Object[4];
            CommunicateServer.sendObject[0] = 8;
            CommunicateServer.sendObject[1] = backupEmail;
            CommunicateServer.sendObject[2] = a;

            CommunicateServer.callSendObject(CommunicateServer.sendObject,false);

            Object[] receive = CommunicateServer.getObject();

            if ((Integer) receive[0] == 8 && (boolean) receive[1] == true) {
                DialogFX dialog = new DialogFX(Type.ACCEPT);
                dialog.setTitleText("Done");
                dialog.setMessage("Successfully changed password");
                dialog.showDialog();

                myController.setScreen(Main.screen1ID);
                
            } else {
                DialogFX dialog = new DialogFX(Type.ERROR);
                dialog.setTitleText("Error");
                dialog.setMessage("Please,Try Again");
                dialog.showDialog();
            }

        } else {
            DialogFX dialog = new DialogFX(Type.ERROR);
            dialog.setTitleText("Error");
            dialog.setMessage("Please,Enter Valid Data");
            dialog.showDialog();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        recoveryEmail.setText("");
        sendingCode.disableProperty().bind(
               Bindings.isEmpty(recoveryEmail.textProperty()));

        submit.setDisable(true);
        recoveryCode.setDisable(true);
        recoveryNewPassword.setDisable(true);
        recoveryConfirmPassword.setDisable(true);

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        myController = screenPage;
    }

}
