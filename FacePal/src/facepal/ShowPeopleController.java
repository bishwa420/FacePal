/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import facepal.ControlledScreen;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.thehecklers.dialogfx.*;
import org.thehecklers.dialogfx.DialogFX.Type;

/**
 * FXML Controller class
 *
 * @author Tapos
 */
public class ShowPeopleController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    // private ObservableList<String> observablePlantList;
    private ListView<String> plantList;
    public ScreenController myController;
    private ImageTobyteConvert conversion = new ImageTobyteConvert();
    private String image1 = "";
    private Object[] localReceive;
    private ArrayList<Long> deleteData;
    private int size = 0;
    
    private List<Information> list = new ArrayList<Information>();
    public static TableView<Information> staticTableView;


    @FXML
    TableView<Information> tableView;
    @FXML
    TableColumn<Information, CheckBox> checkId;
    @FXML
    TableColumn<Information, Label> name;
    @FXML
    TableColumn<Information, ImageView> image;

    @FXML
    private Button button;
    
    public static Button trigger;
    
    

    @FXML
    GridPane grid;

    @FXML
    public void addgrid(ActionEvent e) {

        // File f = new File("C:\\Users\\Tapos\\Pictures\\Tapos.png");
         //image1 = conversion.convertimage(f.getPath());
        
        CommunicateServer.sendObject = new Object[2];

        CommunicateServer.sendObject[0] = 3;
        CommunicateServer.sendObject[1] = WelcomeController.adminId;

       CommunicateServer.callSendObject(CommunicateServer.sendObject);

        localReceive = CommunicateServer.getObject();
        
        
        System.out.println("etotuk paiche");

         parseUserList(localReceive);
         System.out.println("etotuk paiche hi hi hi ");
        initializeTable();
        System.out.println("etotuk paiche hi hi hi ha haha ");
        try{
        button.setDisable(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }

    private int confirmation() {

        DialogFX dialog = new DialogFX(Type.QUESTION);
        dialog.setTitleText("Decision");
        dialog.setMessage("Are you sure to delete info?");

        int ok = dialog.showDialog();
        return ok;
    }

    private boolean findUncheck() {

        deleteData = new ArrayList<>();
        size = 0;
        for (Iterator<Information> it = list.iterator(); it.hasNext();) {
            Information element = it.next();

            if (element.isSelect() == false) {
                deleteData.add(element.getPersonId());
                size++;
            }
        }
        if (size > 0) {
            return true;
        } else {
            return false;
        }

    }

    private void delete() {

        CommunicateServer.sendObject = new Object[size + 2];
        CommunicateServer.sendObject[0] = 4;
        CommunicateServer.sendObject[1] = WelcomeController.adminId;

        int k = 2;
        for (Iterator<Long> it = deleteData.iterator(); it.hasNext();) {
            Long l = it.next();
            CommunicateServer.sendObject[k] = l;
            k++;
        }

        CommunicateServer.callSendObject(CommunicateServer.sendObject);

        localReceive = CommunicateServer.getObject();

        if ((Integer) localReceive[0] == 4 && (long) localReceive[1] == WelcomeController.adminId && (boolean) localReceive[2] == true) {

            DialogFX dialog = new DialogFX(Type.ACCEPT);
            dialog.setTitleText("Delete");
            dialog.setMessage("Successfully deleted information.");
            dialog.showDialog();

            for (Iterator<Information> it = list.iterator(); it.hasNext();) {
                Information element = it.next();

                if (element.isSelect() == false) {
                    it.remove();
                }
            }
            initializeTable();
        }

    }
    
   
    

    @FXML
    private void homeAction(ActionEvent e) {
        if (findUncheck()) {
            if (confirmation() != 1) {
                delete();

            }
        }
        
        myController.setScreen(Main.screen2ID);

    }

    @FXML
    private void addAction(ActionEvent e) {
        if (findUncheck()) {
            if (confirmation() != 1) {
                delete();

            }
        }

        myController.setScreen(Main.screen3ID);
    }

    @FXML
    private void showAction(ActionEvent e) {
         if (findUncheck()) {
            if (confirmation() != 1) {
                delete();

            }
        }
        myController.setScreen(Main.screen4ID);
        ShowPeopleController.trigger.fire();
        
    }

    @FXML
    private void settingsAction(ActionEvent e) {
        if (findUncheck()) {
            if (confirmation() != 1) {
                delete();

            }
        }
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void logoutAction(ActionEvent e) {
        if (findUncheck()) {
            if (confirmation() != 1) {
                delete();

            }
        }
        HomeController.cameraDispose();
        myController.setScreen(Main.screen1ID);
        WelcomeController.logIn.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.getItems().clear();
          if(WelcomeController.adminId!=0){
                button.fire();
          }
          
             trigger=button;
       
    }

    @Override
    public void setScreenParent(ScreenController screenPage) {
        myController = screenPage;

    }

    void initializeTable() {
        try {
            
            // TableColumn<Information, CheckBox> idCol = new TableColumn<>("ID");     
            checkId.setCellValueFactory(new PropertyValueFactory<Information, CheckBox>("checkBox"));
            name.setCellValueFactory(new PropertyValueFactory<Information, Label>("namePerson"));
            image.setCellValueFactory(new PropertyValueFactory<Information, ImageView>("imagePath"));

            final ObservableList<Information> data = FXCollections.observableArrayList(list);
            tableView.setItems(data);

            //custome cell properties
            name.setPrefWidth(270);

            image.setPrefWidth(190);
            checkId.setPrefWidth(50);

            checkId.setResizable(false);
            name.setResizable(false);
            image.setResizable(false);

        } catch (Exception e) {
            System.out.println("abu tanasdfkjskldufyhksjdfnk,");
            e.printStackTrace();
        }
    }

    private void parseUserList(Object[] local) {
        // parse and construct User datamodel list by looping your ResultSet rs
        // and return the list 
        
        if ((Integer) local[0] == 3 && (long) local[1] == WelcomeController.adminId && (boolean) local[2]==true) {

            int track = 2;
            
             System.out.println(local.length);
             for(int i=0;i<local.length;i++)
                 System.out.println(local[i]);
             
            for (int i = 3; i < local.length ; i += 3) {
       
                CheckBox box = new CheckBox();
                box.setSelected(true);
                box.setMaxSize(10, 10);

                long id = (long) local[i];

                String name = (String) local[i + 1];
                Label label = new Label(name);
                image1 = (String) local[i + 2];
                ImageView img = ByteToImageViewConvert.getImgae(image1);

                Information info = new Information(box, label, img, id);

                list.add(info);

            }

        }

    }

}
