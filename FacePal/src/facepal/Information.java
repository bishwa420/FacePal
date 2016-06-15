/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tapos
 */
public class Information {
    
     private final CheckBox checkBox;
    private final Label namePerson;
    private final ImageView imagePath; 
    private final long personId; 
    
   public Information(CheckBox cb,Label name,ImageView imagePath,long id){
       
       this.checkBox=cb;
       this.namePerson=name;
       this.imagePath=imagePath;
       this.personId=id;
   }

    public long getPersonId() {
        return personId;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Label getNamePerson() {
        return namePerson;
    }

    public ImageView getImagePath() {
        return imagePath;
    }
    
    public boolean isSelect(){
        return this.checkBox.isSelected();
    }
}
