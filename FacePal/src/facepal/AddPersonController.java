/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import facedetection.*;
import static facepal.Main.mainContainer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.thehecklers.dialogfx.DialogFX;
import org.thehecklers.dialogfx.DialogFX.Type;

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
    private boolean isFace;
    private Object[] localReceive;
    private IplImage[] iplImg = new IplImage[20];
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
    public String enhance1 = "";
    public String enhance2 = "";
    public String enhance3 = "";
    public String enhance4 = "";
    public String enhance5 = "";

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
                image1 = imgToByte.convertimage(fullPath1);
                path1.setText(fullPath1);
                BufferedImage imagebuf = ImageIO.read(new File(fullPath1));

                if (faceDetect(imagebuf) == true) {
                    enhance1 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg[0]));
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Image");
                    dialog.showDialog();

                    path1.setText("");
                    image1 = "";
                    enhance1 = "";
                }

                // 
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
                image2 = imgToByte.convertimage(fullPath2);
                path2.setText(fullPath2);
                BufferedImage imagebuf = ImageIO.read(new File(fullPath2));

                if (faceDetect(imagebuf) == true) {
                    enhance2 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg[0]));
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Face Image");
                    dialog.showDialog();

                    path2.setText("");
                    image2 = "";
                    enhance2 = "";
                }

                // 
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
                image3 = imgToByte.convertimage(fullPath3);
                path3.setText(fullPath3);
                BufferedImage imagebuf = ImageIO.read(new File(fullPath3));

                if (faceDetect(imagebuf) == true) {
                    enhance3 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg[0]));
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Image");
                    dialog.showDialog();

                    path3.setText("");
                    image3 = "";
                    enhance3 = "";
                }

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
                image4 = imgToByte.convertimage(fullPath4);

                path4.setText(fullPath4);
                BufferedImage imagebuf = ImageIO.read(new File(fullPath4));

                if (faceDetect(imagebuf) == true) {
                    enhance4 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg[0]));
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Image");
                    dialog.showDialog();

                    path4.setText("");
                    image4 = "";
                    enhance4 = "";
                }

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

                BufferedImage imagebuf = ImageIO.read(new File(fullPath5));

                if (faceDetect(imagebuf) == true) {
                    enhance5 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg[0]));
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Image");
                    dialog.showDialog();

                    path5.setText("");
                    image5 = "";
                    enhance5 = "";
                }
//                FaceDetection fd = new FaceDetection(0);
//                try{
//                   iplImg =fd.detectFace(imagebuf);
//                    
//                } catch(Exception ex){
//                    if(ex.equals("No"))
//                        System.out.println("No face in the photo");
//                    else if(ex.equals("Multiple")){
//                        System.out.println("Multiple faces.");
//                    }
//                }
                //  enhance5 = imgToByte.convertString(FaceDetection.IplImageToBufferedImage(iplImg));

                //IplImage iplImage = facesOnly.facesOnly();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean faceDetect(BufferedImage imagebuf) {
        
       
        FaceDetection fd = new FaceDetection(0);
        try {
            iplImg = fd.detectFace(imagebuf);
            if (iplImg.length == 1) {
                isFace = true;
            }

        } catch (Exception ex) {
            if (ex.equals("No")) {

                isFace = false;
                System.out.println("No face in the photo");
            } else if (ex.equals("Multiple")) {
                isFace = false;

                System.out.println("Multiple faces.");
            }
        }
        return isFace;
   
                
       
                
                
    }

    @FXML
    public void addPersonAction(ActionEvent e) {

        // boolean valid=true;
        addPersonName = personName.getText();
        System.out.println(addPersonName + " " + fullPath1 + " t " + fullPath2 + "  t " + fullPath3 + " t " + fullPath4 + " t " + fullPath5);
        System.out.println(addPersonName + " " + image1.length() + " t " + image2.length() + "  t " + image3.length() + " t " + image4.length() + " t " + image5.length());

        if (addPersonName.equals("") == false && (image1.equals("") == false || image2.equals("") == false || image3.equals("") == false || image4.equals("") == false || image5.equals("") == false)) {
            System.out.println(addPersonName + " " + fullPath1 + " t " + fullPath2 + "  t " + fullPath3 + " t " + fullPath4 + " t " + fullPath5);
            CommunicateServer.sendObject = new Object[14];

            CommunicateServer.sendObject[0] = 2;
            CommunicateServer.sendObject[1] = WelcomeController.adminId;
            CommunicateServer.sendObject[2] = addPersonName;
            CommunicateServer.sendObject[3] = image1;
            CommunicateServer.sendObject[4] = image2;
            CommunicateServer.sendObject[5] = image3;
            CommunicateServer.sendObject[6] = image4;
            CommunicateServer.sendObject[7] = image5;
            CommunicateServer.sendObject[8] = enhance1;
            CommunicateServer.sendObject[9] = enhance2;
            CommunicateServer.sendObject[10] = enhance3;
            CommunicateServer.sendObject[11] = enhance4;
            CommunicateServer.sendObject[12] = enhance5;

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
            DialogFX dialog = new DialogFX(Type.ERROR);
            dialog.setTitleText("Error");
            dialog.setMessage("Upload Failed");
            dialog.showDialog();

            setTopText("");

        }

    }

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
//       ShowPeopleController.trigger.setDisable(false);
//       ShowPeopleController.trigger.fire();

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

        image1 = image2 = image3 = image4 = image5 = fullPath1 = fullPath2 = fullPath3 = fullPath4 = fullPath5 = "";

        path1.setEditable(false);
        path2.setEditable(false);
        path3.setEditable(false);
        path4.setEditable(false);
        path5.setEditable(false);

        personName.setText("");
        imgToByte = new ImageTobyteConvert();
    }

}
