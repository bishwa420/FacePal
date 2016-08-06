/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo;

/**
 *
 * @author Tapos
 */
public class EmailConfiguration {

    public final String from = "starsopnil@gmail.com";
   // public  String to = "taposdatta2013@gmail.com";
    public final String username = "starsopnil@gmail.com";
    public final String password = "01715175342";
    public  String message ="";
    public final String subject = "FacePal Account";
    
    public String getMessage(int pinCode){
        
        this.message="<!DOCTYPE html><body> <div style=\"color:blue;\"><h1> Security code</h1> </div>"
            + "                           <div><p style=\"font-size:14px;\">Please use the following security code for "
            + "                            recovering password of  your FacePal account. If you did not request for recovering password \n"
            + "                            simply ignore this e-mail.</p><br><p style=\"font-size:22px;\">"
            + "                            Security code: " + pinCode + "</p><br><p style=\"font-size:14px;\">Thanks,<br>The "
            + "                            FacePal team</p></div></body>";
        
        return this.message;
        
    }
}
