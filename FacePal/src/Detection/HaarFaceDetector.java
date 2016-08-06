package Detection;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import org.opencv.core.CvType;

/* Step 1 : get the frame -> initialFrame
 * Step 2 : convert it to gray - grayFrame
 * Step 3 : equalize the grayFrame -> equalizedFrame
 * Step 4 : apply haar/lbp cascade for face detection -> detectFaces
 * Step 5 : draw rectangles on initialFrame
 * 
 */
public class HaarFaceDetector implements FaceDetector {

    private int MIN_FACE_SIZE;
    private String path;
    private String filePath;
    private Mat grayFrame;
    private Mat blurFrame;
    private Mat cannyFrame;
    private Mat initialFrame;
    private Mat equalizedFrame;
    private MatOfRect faces;
    // private VideoCapture videoCapture;
    private CascadeClassifier faceCascade;
    private ArrayList<Mat> croppedFacesMat;
    private ArrayList<Image> croppedFacesImg;
    private Path currentPath;

    /*webcam constructor */
    public HaarFaceDetector() {
        this.init();
    }
    /* read image from file. */

    public ArrayList<Image> HaarFaceDetectorPart(BufferedImage im,int index) {
        try {
            //this.init();
            this.path = filePath+"\\temp"+index+".temp";
             ImageIO.write(im, "png", new File(filePath+"\\temp"+index+".temp"));
           
            System.out.println("HaarDetector status:ok");

            readAndProcessPhoto();
            
            new File (filePath+"\\temp"+index+".temp").delete();

        } catch (Exception ex) {
            Logger.getLogger(HaarFaceDetector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getCroppedFaces();

    }

    private void init() {
        // this.videoCapture = new VideoCapture();
        this.initialFrame = new Mat();
        this.grayFrame = new Mat();
        this.equalizedFrame = new Mat();
        this.cannyFrame = new Mat();
        this.blurFrame = new Mat();
        this.croppedFacesImg = new ArrayList<Image>();
        this.croppedFacesMat = new ArrayList<Mat>();
        this.faces = new MatOfRect();
        this.currentPath = Paths.get("");

         filePath = currentPath.toAbsolutePath().toString();
        System.out.println("Hello world" + filePath);

        this.faceCascade = new CascadeClassifier("C:\\needed_xml.xml");
        this.MIN_FACE_SIZE = 80;

    }

    /* get the image from the given path -> initialFrame
     * initialFrame -> grayFrame -> equalizedFrame
     * apply haar cascade on the equalizedFrame
     */
    @Override
    public void readAndProcessPhoto() {
        System.out.println(path);
        initialFrame = Imgcodecs.imread(path);
        this.getGrayFrame();
        this.getEqualizedFrame();
        this.getBlurFrame();
        this.getCannyFrame();
        this.detectFace();
        System.out.println(path);
    }
    /*Open the camera, if it is not possible then throw an exception
     *
     */

//    @Override
//    public void openCamera(int cameraNr) throws MyException {
//        if (videoCapture.isOpened() == false) {
//            videoCapture.open(cameraNr);
//
//            if (videoCapture.isOpened() == false) {
//                throw new MyException("HaarDetector: Failed to open camera " + cameraNr);
//            }
//        }
//    }

    /*Return the initialFrame as an image*/
    @Override
    public Image getImage() {
        return this.mat2Image(initialFrame);
    }

    /* step 1  step2   step3     step4    step 5
     * read -> gray ->equalize->detect -> draw
     */


    //can't display the Mat type, so I will convert it to Image type
    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }


    //return arraylist with faces
    public ArrayList<Image> getCroppedFaces() {
        croppedFacesImg.clear();

        for (Mat faces : croppedFacesMat) {
            
            if(croppedFacesMat.size()>1){
            HaarEyeDetector eye = new HaarEyeDetector();
            
            if(eye.findEyes(faces)==true){
                   croppedFacesImg.add(this.mat2Image(faces));
            }
            }
            else{
                
            croppedFacesImg.add(this.mat2Image(faces));
            }
        }

        return croppedFacesImg;
    }


    private void getGrayFrame() {
        Imgproc.cvtColor(initialFrame, grayFrame, Imgproc.COLOR_BGR2GRAY);

    }

    private void getBlurFrame() {
        Imgproc.blur(equalizedFrame, blurFrame, new Size(3, 3));

    }

    private void getCannyFrame() {
        Imgproc.Canny(grayFrame, cannyFrame, 30, 100);

    }

    private void getEqualizedFrame() {
        Imgproc.equalizeHist(grayFrame, equalizedFrame);

    }

    /* step 4 and step 5
     * detect and draw
     */
    private void detectFace() {
        faceCascade.detectMultiScale(equalizedFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(MIN_FACE_SIZE, MIN_FACE_SIZE), new Size());
        System.out.println(MIN_FACE_SIZE);

        croppedFacesMat.clear();
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(initialFrame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));

            croppedFacesMat.add(new Mat(initialFrame, rect));

        }
    }

    @Override
    public void setMinFace(int minFaceSize) {
        this.MIN_FACE_SIZE = minFaceSize;

    }


}
