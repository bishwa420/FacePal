/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class LoadingController implements Initializable,ControlledScreen {

    /**
     * Initializes the controller class.
     */
    ScreenController myScreen;
    public static Label loadingEx;
    @FXML private Label loading;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadingEx=loading;
    }    

    @Override
    public void setScreenParent(ScreenController screenPage) {
        myScreen=screenPage;
    }
    
}
