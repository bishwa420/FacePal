/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framereturner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

/**
 *
 * @author bishw
 */
public class FrameReturner {

    /**
     * @param args the command line arguments
     */
    private int CAMERA_ID = 0;

    /**
     * Initialize with camera id.
     * @param id 
     */
    public FrameReturner(int id) {
        CAMERA_ID = id;
    }
    
    /**
     * Converting it to BufferedImage from IplImage.
     * @param src
     * @return 
     */
    private static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
    }
    
    /**
     * This method returns the captured image with the given camera id.
     * @return 
     */
    public BufferedImage frameReturner() {
        BufferedImage img=null;
        try {
            
            FrameGrabber grabber = new VideoInputFrameGrabber(CAMERA_ID);
            //FrameGrabber grabber = FrameGrabber.createDefault(CAMERA_ID);
            grabber.start();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
            IplImage grabbedImage = converter.convert(grabber.grab());
            img = IplImageToBufferedImage(grabbedImage);
            grabber.stop();
            grabber.release();
        }
            catch (Exception e) {
        }
        return img;
    }

    
    public static void main(String[] args) {
        FrameReturner returner = new FrameReturner(0);
        BufferedImage bufImage = returner.frameReturner();
        try {
            ImageIO.write(bufImage, "jpg", new File("thisfile.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(FrameReturner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
















