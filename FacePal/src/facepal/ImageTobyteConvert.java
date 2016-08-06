/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import org.apache.commons.codec.binary.Base64;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Tapos
 */
public class ImageTobyteConvert {

    public String convertimage(BufferedImage img) {

//        File file = new File(path);
//        System.out.println(file.getPath());
//        String mimetype = new MimetypesFileTypeMap().getContentType(file);
//        String type = mimetype.split("/")[0];
//           System.out.println(type);
//        if (type.equals("image")) {
        //  System.out.println("It's an image");
//        System.out.println(file.getPath());
        String imageDataString = null;

        // Reading a Image file from file system
//        FileInputStream imageInFile;
        try {
            //Resizing image 

//            imageInFile = new FileInputStream(file);
//            Image image = ImageIO.read(imageInFile);
            //resize with (X*Y) ratio
            BufferedImage bi = this.createResizedCopy(img, 160, 120, true);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            int length = is.available();

            //converting image to byte array
            byte imageData[] = new byte[(int) length];

            is.read(imageData);

            // Converting Image byte array into Base64 String
            imageDataString = encodeImage(imageData);
           // imageInFile.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageTobyteConvert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImageTobyteConvert.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageDataString;
//        } else {
//            return "";
//           // System.out.println("It's NOT an image");
//        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    private BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {

        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();

        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    public static String convertString(BufferedImage bf) {
        String imageString = "";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bf, "png", os);

            InputStream is = new ByteArrayInputStream(os.toByteArray());
            int length = is.available();

            //converting image to byte array
            byte imageData[] = new byte[(int) length];

            is.read(imageData);

            // Converting Image byte array into Base64 String
            imageString = encodeImage(imageData);
            
           
        } catch (IOException ex) {
              Logger.getLogger(ImageTobyteConvert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageString;

    }

}
