package facepal;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author bishw
 */
public class HomeController implements Initializable, ControlledScreen {

    ScreenController myController;

    @FXML
    ComboBox<WebCamInfo> cbCameraOptions;
    @FXML
    ImageView imgWebCamCapturedImage;
    @FXML
    BorderPane bpWebCamPaneHolder;
    @FXML
    private  Button startMonitoringButton;
    @FXML
    private  Button stopMonitoringButton;
    @FXML
    ComboBox<WebCamInfo> secondaryCameraComboBox;
    
    public static Button start;
    public static Button stop;
    private static boolean stopCamera = false;
    private int secondaryCameraId = -1;
    private boolean doorOpen = true;

    private BufferedImage grabbedImage;
//	private WebcamPanel selWebCamPanel = null;
    private static  Webcam selWebCam = null;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

    private String cameraListPromptText = "Choose Camera";
    private Button doorButton = new Button();

    String primaryCameraChosen = null, secondaryCameraChosen = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void setScreenParent(ScreenController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        start=startMonitoringButton;
        stop=stopMonitoringButton;

        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
        int webCamCounter = 0;
        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }
        cbCameraOptions.setItems(options);
        secondaryCameraComboBox.setItems(options);
        cbCameraOptions.setPromptText(cameraListPromptText);

        secondaryCameraComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
                if (arg2 != null) {
                    secondaryCameraId = arg2.getWebCamIndex();
                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
                    //initializeWebCam(arg2.getWebCamIndex());
//                     if(doorOpen==true){
//                   // closeCamera();
//        initializeWebCam(secondaryCameraId);
//      }
                }
            }
        });

        cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
                if (arg2 != null) {

                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
                    initializeWebCam(arg2.getWebCamIndex());
                }
            }
        });

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                setImageViewSize();

            }
        });

//     //jokhon dorja khula er jonno request jabe tokhon secondary camera show korbe abar bondho hoye gele primary te fire jabe 
//    if(doorOpen==true){
//        closeCamera();
//        initializeWebCam(secondaryCameraId);
//      }
//    else if(doorOpen==false){
//        //primary camera abar start hobe 
//    }
    }

    protected void setImageViewSize() {

       // double height = bpWebCamPaneHolder.getHeight();
        //double width = bpWebCamPaneHolder.getWidth();
        double height =270.0;
        double width=460.0;
        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }

    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamIntilizer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (selWebCam == null) {
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();
                } else {
                    closeCamera();
                    selWebCam = Webcam.getWebcams().get(webCamIndex);
                    selWebCam.open();

                }
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntilizer).start();
        stopMonitoringButton.setDisable(false);
        startMonitoringButton.setDisable(true);
    }

    protected void startWebCamStream() {

        stopCamera = false;
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!stopCamera) {
                    try {
                        if ((grabbedImage = selWebCam.getImage()) != null) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    final Image mainiamge = SwingFXUtils
                                            .toFXImage(grabbedImage, null);
                                    imageProperty.set(mainiamge);
                                }
                            });

                            grabbedImage.flush();

                        }
                    } catch (Exception e) {
                    } finally {

                    }

                }

                return null;

            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    private static void closeCamera() {
        try {
            if (selWebCam != null) {
                selWebCam.close();
            }
        } catch (Exception e) {

        }
    }

    public void startAction(ActionEvent event) {
        stopCamera = false;
        startWebCamStream();
        startMonitoringButton.setDisable(true);
        stopMonitoringButton.setDisable(false);
    }

    public void stopAction(ActionEvent event) {

        stopCamera = true;
        startMonitoringButton.setDisable(false);
        stopMonitoringButton.setDisable(true);
    }

    public static void cameraDispose() {

        stopCamera = true;
        start.setDisable(false);
        stop.setDisable(true);
        closeCamera();
        //Webcam.shutdown();
    }

    class WebCamInfo {

        private String webCamName;
        private int webCamIndex;

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
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
        myController.setScreen(Main.screen4ID);
        ShowPeopleController.trigger.setDisable(false);
        ShowPeopleController.trigger.fire();

    }

    @FXML
    private void settingsAction(ActionEvent e) {
        myController.setScreen(Main.screen5ID);
    }

    @FXML
    private void logoutAction(ActionEvent e) {
        cameraDispose();
        myController.setScreen(Main.screen1ID);

        WelcomeController.logIn.setText("");
    }

}
