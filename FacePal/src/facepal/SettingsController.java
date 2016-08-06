/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import static facepal.Main.mainContainer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.thehecklers.dialogfx.DialogFX;
import org.thehecklers.dialogfx.DialogFX.Type;

/**
 * FXML Controller class
 *
 * @author bishw
 */
public class SettingsController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    private Object[] localReceive;
    ScreenController myController;
    public String updatedName="";
    public String updatedEmail="";
    public String updatedNewPassword="";
    public String cPassword="";

    @FXML
    private Button saveUpdate;

    @FXML
    private TextField updateName, updateEmail;

    @FXML
    private PasswordField currentPassword, newPassword, confirmNewPassword;

    @FXML
    private void homeAction(ActionEvent e) {
        myController.setScreen(Main.screen2ID);
    }

    @FXML
    private void addAction(ActionEvent e) {
        myController.setScreen(Main.screen3ID);
    }

    @FXML
    private void showAction(ActionEvent e) {
        mainContainer.loadScreen(Main.screen4ID, Main.screen4file);
        myController.setScreen(Main.screen4ID);
//        ShowPeopleController.trigger.setDisable(false);
//        ShowPeopleController.trigger.fire();
    }

    @FXML
    private void settingsAction(ActionEvent e) {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void logoutAction(ActionEvent e) {
        HomeController.cameraDispose();
        myController.setScreen(Main.screen1ID);
        WelcomeController.logIn.setText("");
    }

    @FXML
    private void resetUpdateAction(ActionEvent e) {

        resetField();

    }

    @FXML
    private void saveUpdateAction(ActionEvent e) {

        updatedName = updatedEmail = updatedNewPassword = "";

        if (updateName.getText().equals("") == false && updateName.getText().equals(WelcomeController.adminName) == false) {
            updatedName = updateName.getText();
        }

        if (updateEmail.getText().equals("") == false && WelcomeController.isValidEmailAddress(updateEmail.getText())) {
            updatedEmail = updateEmail.getText();
        }

        cPassword = currentPassword.getText();

        if (newPassword.getText().equals("") == false && newPassword.getText().equals(confirmNewPassword.getText()) == true) {
            updatedNewPassword = newPassword.getText();
        }

        CommunicateServer.sendObject = new Object[8];
        CommunicateServer.sendObject[0] = 5;
        CommunicateServer.sendObject[1] = WelcomeController.adminId;
        CommunicateServer.sendObject[2] = updatedName;
        CommunicateServer.sendObject[3] = updatedEmail;
        CommunicateServer.sendObject[4] = cPassword;
        CommunicateServer.sendObject[5] = updatedNewPassword;

        CommunicateServer.callSendObject(CommunicateServer.sendObject,false);

        localReceive = CommunicateServer.getObject();
        System.out.println("etotuk pariochi");

        if ((int) localReceive[0] == 5 && (long) localReceive[1] == WelcomeController.adminId && (boolean) localReceive[2] == true) {

            DialogFX dialog = new DialogFX(Type.ACCEPT);
            dialog.setTitleText("Accept");
            dialog.setMessage("Successfully Updated Information.");
            dialog.showDialog();
            
            resetField();
            
        } else {
            DialogFX dialog = new DialogFX(Type.ERROR);
            dialog.setTitleText("Error ");
            dialog.setMessage("Error is found. Enter Valid info and try again");
            dialog.showDialog();
        }

    }
    
    void resetField(){
        
         updateName.setText("");
        updateEmail.setText("");
        currentPassword.setText("");
        newPassword.setText("");
        confirmNewPassword.setText("");
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        myController = screenPage;
        updateName.setText(WelcomeController.adminName);

        saveUpdate.disableProperty().bind(
                Bindings.isEmpty(currentPassword.textProperty()));
        // updateEmail.setText("taposdatta2013@gmail.com");
    }

}
