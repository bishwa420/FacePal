/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.thehecklers.dialogfx.DialogFX;
import org.thehecklers.dialogfx.DialogFX.Type;

/**
 *
 * @author Tapos
 */
public class Main extends Application {

    public static String screen1ID = "Welcome";
    public static String screen1file = "Welcome.fxml";
     public static String screen3ID = "AddPerson";
    public static String screen3file = "AddPerson.fxml";
    public static String screen4ID = "PeopleList";
    public static String screen4file = "ShowPeople.fxml";
    public static String screen5ID = "Settings";
    public static String screen5file = "Settings.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {

        ScreenController mainContainer = new ScreenController();
         mainContainer.loadScreen(Main.screen1ID, Main.screen1file);
         mainContainer.loadScreen(Main.screen3ID, Main.screen3file);
         mainContainer.loadScreen(Main.screen4ID, Main.screen4file);
         mainContainer.loadScreen(Main.screen5ID, Main.screen5file);

        mainContainer.setScreen(Main.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);

        Scene scene = new Scene(root);
      
            
           

        primaryStage.setMaxWidth(715);
        primaryStage.setHeight(505);
        Image icon = new Image(getClass().getResourceAsStream("download.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("FacePal");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
       // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
