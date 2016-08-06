/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tapos
 */
public class ByteToImageConvert {
    
    
    public String getImgae(long userId,String name, int i,String imageDataString,String destination){
        String path="";
        try {
            System.out.println("REsdkfjlskdf");
        // Converting a Base64 String into Image byte array
            byte[] imageByteArray = decodeImage(imageDataString);
 
            // Write a image byte array into file system
            FileOutputStream imageOutFile;
            path=MyServletPack.basePath+"\\"+userId+"\\"+destination+"\\" + name  + ".png";
        
            imageOutFile = new FileOutputStream(path);
            
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
            
            return path;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ByteToImageConvert.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ByteToImageConvert.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return path;
    }
    
     public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
    
}
