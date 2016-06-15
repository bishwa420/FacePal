/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.thehecklers.dialogfx.DialogFX;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class WelcomeController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    TextField logInUsername, signUpName, signUpEmail, signUpUsername;
    @FXML
    PasswordField logInPassword, signUpPassword, signUpConfirmPassword;

    ScreenController myController;
    private Object[] localReceive;
    public static int serviceId = -1;
    public static long adminId = 0;
    public static String adminName = "";
    private boolean success;
    public static PasswordField logIn;
    public String signName, signEmail, signUsername, signPassword, logUsername, logPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logIn=logInPassword;

    }

    @FXML
    public void signUpAction(ActionEvent e) {

        boolean valid = true;

        if (signUpName.getText().equals("") == false && signUpEmail.getText().equals("") == false && signUpUsername.getText().equals("") == false && signUpPassword.getText().equals("") == false && signUpPassword.getText().equals(signUpConfirmPassword.getText()) && isValidEmailAddress(signUpEmail.getText())) {

            signName = signUpName.getText();
            signEmail = signUpEmail.getText();
            signUsername = signUpUsername.getText();
            signPassword = signUpPassword.getText();

        } else {
            valid = false;
            System.out.println("sadfjhkdsfsdfsadf");
        }

        if (valid == true) {

            CommunicateServer.sendObject = new Object[7];

            CommunicateServer.sendObject[0] = 0;
            CommunicateServer.sendObject[1] = signName;
            CommunicateServer.sendObject[2] = signEmail;
            CommunicateServer.sendObject[3] = signUsername;
            CommunicateServer.sendObject[4] = signPassword;

            CommunicateServer.callSendObject(CommunicateServer.sendObject);
            //System.out.println("sadkfjlksadfhaskdjfhsakdjfhsakdjfhsakjdfhskdjfhskdjf");

            localReceive = CommunicateServer.getObject();
           // System.out.println("tumi amar janbsadfkjasldkfn");
            while(localReceive==null){
                localReceive = CommunicateServer.getObject();
            }
            //System.out.println("ami tumar jshfdkasjdfhksjdfk");
            try{
            serviceId = (int) localReceive[0];
            adminId = (long) localReceive[1];
            adminName = (String) localReceive[2];
            success  = (boolean) localReceive[3];

            if (adminId != 0 && adminName.equals("") == false && success) {
                System.out.println(serviceId +"  "+ adminId +"  "+ adminName+ " " + success);
                DialogFX dialog = new DialogFX();
                dialog.setTitleText("Info");
                dialog.setMessage("Successfully registered.");
                dialog.showDialog();
                
              // checnge scene to home page

            }
            }catch(Exception ex){
                ex.printStackTrace();
            }

        }

    }

    public static boolean isValidEmailAddress(String email) {
        //if(email.equals(""))
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    @FXML
    public void resetAction(ActionEvent e) {

        signUpName.setText("");
        signUpEmail.setText("");
        signUpUsername.setText("");
        signUpPassword.setText("");
        signUpConfirmPassword.setText("");
    }

    @FXML
    public void logInAction(ActionEvent e) {

        logUsername = logInUsername.getText();
        logPassword = logInPassword.getText();

        if (!logUsername.isEmpty() && !logPassword.isEmpty()) {
            
            CommunicateServer.sendObject =new Object[3];
            
            CommunicateServer.sendObject[0]=1;
            CommunicateServer.sendObject[1]=logUsername;
            CommunicateServer.sendObject[2]=logPassword;
            
            CommunicateServer.callSendObject(CommunicateServer.sendObject);
            
            localReceive=CommunicateServer.getObject();
            
            serviceId = (int) localReceive[0];
            boolean access = (boolean) localReceive[1];
            
            
            if(access==true && serviceId==1){
                
                 adminId = (long) localReceive[2];
                 adminName = (String) localReceive[3]; 
                 
                 myController.setScreen(Main.screen3ID);
                 //scene change to home 
                
            }
            
            

        } else {
            logInUsername.setText("");
            logInPassword.setText("");
        }

    }


    @Override
    public void setScreenParent(ScreenController screenPage) {
        myController = screenPage;
    }

}
