/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grayconvertandresize;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

/**
 *
 * @author bishw
 */
public class GrayConvertAndResize {

    IplImage sourceImage; // this image is the source Ipl image given at the instantiation.
    private String imgPath = ""; // this imgPath is for testing purpose only.
    // On the final version this variable will be omitted.
    private int width = 125; // width of the new image
    
    private int height = 150; // hight of the new image

    private GrayConvertAndResize() {
        imgPath = "http://www.wonderslist.com/wp-content/uploads/2015/06/Paradisiacal-Kashmir-Valley.jpg";
        BufferedImage img;
        try {
            img = ImageIO.read(new URL(imgPath));
        } catch (IOException ex) {
            Logger.getLogger(GrayConvertAndResize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * initialize with an ipl image
     * @param img 
     */
    public GrayConvertAndResize(IplImage img) {
        imgPath = null;
        sourceImage = img;
    }

    public static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
    }

    private static IplImage toIplImage(BufferedImage bufImage) {
        ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }

    /**
     * It performs gray scale conversion of 8 bit
     * @param inputImage
     * @return 
     */
    private BufferedImage grayScale(BufferedImage inputImage) {
        BufferedImage img = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.drawImage(inputImage, 0, 0, null);
        g.dispose();
        return img;
    }

    private IplImage resizeImage() {
        BufferedImage bufImage = null;
        IplImage resizedImage;
        try {
            if (imgPath.equals(null)) { // real data
                /*
                    First resize the IplImage to desired width and height
                */
                resizedImage = IplImage.create(width, height, sourceImage.depth(), sourceImage.nChannels());
                cvResize(sourceImage, resizedImage);
                bufImage = IplImageToBufferedImage(resizedImage);
                cvReleaseImage(resizedImage);
            } else { // testing data
                bufImage = ImageIO.read(new File("E:\\Google Drive\\FacePal\\Client site\\FaceDetection\\birrhis0.jpg"));
            }
        } catch (Exception e) {
        }
        bufImage = grayScale(bufImage);
        sourceImage = toIplImage(bufImage);
        return sourceImage;
    }

    /**
     * Resizes the image to desired width and height and then gray scales
     * it. Finally returns the IplImage.
     * @return 
     */
    public IplImage grayConvertAndResize() {
        // TODO code application logic here
        return resizeImage();
    }

}
