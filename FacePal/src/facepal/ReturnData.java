/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.awt.image.BufferedImage;
import sun.java2d.pipe.BufferedBufImgOps;

/**
 *
 * @author Tapos
 */
public class ReturnData {
    
    public String imagePath="";
    public  BufferedImage buffered;
    
    ReturnData(String img,BufferedImage bi){
        imagePath=img;
        buffered=bi;
    }
    
    public BufferedImage bfImage(){
        return buffered;
    }
    public String getPath(){
        return imagePath;
    }
}
