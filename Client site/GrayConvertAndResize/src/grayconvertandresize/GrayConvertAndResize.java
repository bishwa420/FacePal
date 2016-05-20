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
    int width; // width of the new image
    int height; // hight of the new image

    private GrayConvertAndResize() {
        imgPath = "http://www.wonderslist.com/wp-content/uploads/2015/06/Paradisiacal-Kashmir-Valley.jpg";
        width = 150;
        height = 125;
        BufferedImage img;
        try {
            img = ImageIO.read(new URL(imgPath));
        } catch (IOException ex) {
            Logger.getLogger(GrayConvertAndResize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public GrayConvertAndResize(IplImage img) {
        imgPath = null;
        width = 150;
        height = 125;
        sourceImage = img;
    }

    public GrayConvertAndResize(IplImage img, int width, int height) {
        imgPath = null;
        this.width = width;
        this.height = height;
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

    private BufferedImage grayScale(BufferedImage bufImage) {
        BufferedImage retImage = bufImage;
        try {
            int widths = bufImage.getWidth();
            int heights = bufImage.getHeight();

            for (int i = 0; i < heights; i++) {

                for (int j = 0; j < widths; j++) {

                    Color c = new Color(bufImage.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(red + green + blue,
                            red + green + blue, red + green + blue);

                    retImage.setRGB(j, i, newColor.getRGB());
                }
            }

        } catch (Exception e) {
            return null;
        }
        return retImage;
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
