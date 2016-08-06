/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;
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
    public static String screen2ID = "Home";
    public static String screen2file = "Home.fxml";
    public static String screen6ID = "RecoveryPassword";
    public static String screen6file = "RecoveryPassword.fxml";
    public static String screen7ID = "Loading";
    public static String screen7file = "Loading.fxml";

    public static ScreenController mainContainer;
    public static String basePath = "";

    @Override
    public void start(Stage primaryStage) throws Exception {

        mainContainer = new ScreenController();
        mainContainer.loadScreen(Main.screen1ID, Main.screen1file);
        mainContainer.loadScreen(Main.screen7ID, Main.screen7file);
        basePath = getClass().getResource("").getPath();

//         mainContainer.loadScreen(Main.screen2ID, Main.screen2file);
//         mainContainer.loadScreen(Main.screen3ID, Main.screen3file);
//         mainContainer.loadScreen(Main.screen4ID, Main.screen4file);
//         mainContainer.loadScreen(Main.screen5ID, Main.screen5file);
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
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            
            public void handle(WindowEvent we) {
                
                System.out.println("Stage is closing");
                DialogFX dialog = new DialogFX(Type.QUESTION);
                dialog.setTitleText("Exit");
                dialog.setMessage("Are you sure to exit ?");
                int ok = dialog.showDialog();
               // System.out.println("ok =" +ok);
                if (ok != 1) {
                    primaryStage.close();  //exit 
                }
                we.consume();    //stop this event

            }
        });
        // primaryStage.close();

    }
    

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);

    }

}
