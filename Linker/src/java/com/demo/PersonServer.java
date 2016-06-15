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
import java.io.Serializable;

/**
 *
 * @author Tapos
 */
public class PersonServer implements Serializable{
    
    public String personName;
    public String[] imageFile= new String[7];
    
    PersonServer(String[] path){
        this.personName=personName;
        imageFile[0]=path[0];
        imageFile[1]=path[1];
        imageFile[2]=path[2];
        imageFile[3]=path[3];
        imageFile[4]=path[4];
        this.personName=path[5];
    }
}
