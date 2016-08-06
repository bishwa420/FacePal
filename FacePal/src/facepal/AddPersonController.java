/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import Detection.HaarFaceDetector;
//import facedetection.*;
import static facepal.Main.mainContainer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.thehecklers.dialogfx.DialogFX;
import org.thehecklers.dialogfx.DialogFX.Type;
import net.sf.image4j.util.ConvertUtil;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

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
    private BufferedImage image;
    private String  originalImageFile;
    private BufferedImage[] imagebuf=new BufferedImage[2];

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
               // image1 = imgToByte.convertimage(fullPath1);
                path1.setText(fullPath1);
                imagebuf[0] = ImageIO.read(new File(fullPath1));
                 imagebuf[1] = Scalr.rotate(imagebuf[0], Rotation.CW_90);

                if (faceDetect(imagebuf) == true) {
                    enhance1 = imgToByte.convertString(image);
                    image1=originalImageFile;
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
                //image2 = imgToByte.convertimage(fullPath2);
                path2.setText(fullPath2);
                imagebuf[0] = ImageIO.read(new File(fullPath2));
                 imagebuf[1] = Scalr.rotate(imagebuf[0], Rotation.CW_90);

                if (faceDetect(imagebuf) == true) {
                    enhance2 = imgToByte.convertString(image);
                    image2=originalImageFile;
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
                //image3 = imgToByte.convertimage(fullPath3);
                path3.setText(fullPath3);
                imagebuf[0] = ImageIO.read(new File(fullPath3));
                 imagebuf[1] = Scalr.rotate(imagebuf[0], Rotation.CW_90);

                if (faceDetect(imagebuf) == true) {
                    enhance3 = imgToByte.convertString(image);
                    image3=originalImageFile;
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
                //image4 = imgToByte.convertimage(fullPath4);

                path4.setText(fullPath4);
                 imagebuf[0] = ImageIO.read(new File(fullPath4));
                 imagebuf[1] = Scalr.rotate(imagebuf[0], Rotation.CW_90);

                if (faceDetect(imagebuf) == true) {
                    enhance4 = imgToByte.convertString(image);
                    image4=originalImageFile;
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
                //image5 = imgToByte.convertimage(fullPath5);

                 imagebuf[0] = ImageIO.read(new File(fullPath5));
                 imagebuf[1] = Scalr.rotate(imagebuf[0], Rotation.CW_90);
                
//                  ImageIO.write(imagebuf[0], "png", new File("F:\\temp"+5+".png"));
//                   ImageIO.write(imagebuf[1], "png", new File("F:\\temp"+6+".png"));
//              //  System.out.println("width = "+ imagebuf.getWidth() + " height= "+ imagebuf.getHeight());

                if (faceDetect(imagebuf) == true) {
                    enhance5 = imgToByte.convertString(image);
                    image5= originalImageFile;
                } else {
                    DialogFX dialog = new DialogFX(Type.ERROR);
                    dialog.setTitleText("Error");
                    dialog.setMessage("No Face or Multiple Faces detected.Add a Single Image");
                    dialog.showDialog();

                    path5.setText("");
                    image5 = "";
                    enhance5 = "";
                }
     
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    void processingImage(ArrayList<Image> list) {

        image = null;
        int k=1;
        for (Image faces : list) {
            BufferedImage bImage = SwingFXUtils.fromFXImage(faces, null);

            BufferedImage gray = Detection.Conversion.createResizedCopy(bImage, 125, 150, true);
            image = gray;
//            try {
//                ImageIO.write(gray, "png", new File("F:\\output"+k+++".png"));
//
//                //  opencv_imgcodecs.cvSaveImage("birrhis sdf sdf"  + ".jpg", ip);
//            } catch (Exception ex) {
//
//            }
        }

    }

    public boolean faceDetect(BufferedImage[] imagebuf) {
        isFace=false;
        originalImageFile=null;
        HaarFaceDetector hf = new HaarFaceDetector();
        try {
            int i=0;
            while(isFace!=true && i<2){
                
            System.out.println("Call method");
            ArrayList<Image> arrayOfImage = hf.HaarFaceDetectorPart(imagebuf[i],2);

            System.out.println("face found " + arrayOfImage.size());

            if (arrayOfImage.size() == 1) {
                originalImageFile=imgToByte.convertimage(imagebuf[i]);
                processingImage(arrayOfImage);
                isFace = true;
            }
            i++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            if (ex.equals("No")) {
//
//                isFace = false;
//                System.out.println("No face in the photo");
//            } else if (ex.equals("Multiple")) {
//                isFace = false;
//
//                System.out.println("Multiple faces.");
//            }
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

            CommunicateServer.callSendObject(CommunicateServer.sendObject,false);

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
