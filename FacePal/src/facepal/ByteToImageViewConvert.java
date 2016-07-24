/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tapos
 */
public class ByteToImageViewConvert {
    
    
    public static ImageView getImgae(String imageDataString){
        ImageView imView=null;
        try {
        // Converting a Base64 String into Image byte array
            byte[] imageByteArray = decodeImage(imageDataString);
            
            
            
            
            BufferedImage bf = null;
     
        ByteArrayInputStream bais = new ByteArrayInputStream(imageByteArray);
       
            bf = ImageIO.read(bais);

            WritableImage wr = null;
            if (bf != null) {
                wr = new WritableImage(bf.getWidth(), bf.getHeight());
                PixelWriter pw = wr.getPixelWriter();
                for (int x = 0; x < bf.getWidth(); x++) {
                    for (int y = 0; y < bf.getHeight(); y++) {
                        pw.setArgb(x, y, bf.getRGB(x, y));
                    }
                }
            }

            imView = new ImageView(wr);
            imView.setFitWidth(150);
            imView.setFitHeight(120);
            imView.setPreserveRatio(true);
            imView.setSmooth(true);

            return imView;
        } 
         catch (Exception ex) {
            Logger.getLogger(ByteToImageViewConvert.class.getName()).log(Level.SEVERE, null, ex);
        } 
         return imView;
      
    }
    
   
    
     public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
    
}
