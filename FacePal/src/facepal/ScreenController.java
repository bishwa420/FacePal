/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facepal;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Tapos
 */
public class ScreenController extends StackPane{
    
    private HashMap<String, Node> screens = new HashMap<>();
    
    public ScreenController(){
        super();
    }
    
     //add screen name with a node number
     // add the screen to collection
    public void addScreen(String name,Node screen){
        screens.put(name, screen);
    }
    
    //Returns the Noe with the appropiate name
    public Node getScreen(String name){
        return screens.get(name);
    }
    
    //Loads the fxml file,add the screen to the screens collection and
    //finally injects the screenPane to the controller
    
    public boolean loadScreen(String name,String resource){
        try{
            FXMLLoader myLoader=new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen= (Parent) myLoader.load();
            ControlledScreen myScreenControler =(ControlledScreen) myLoader.getController();
            myScreenControler.setScreenParent(this);
            addScreen(name,loadScreen);
            System.out.println(name);
            return true;
        } catch (Exception ex) {
           //  System.out.println(name+" sdfkj");
          System.out.println(ex.getMessage());
           return false;
        }
    }
    
    // this method tries to display the screen with a predifine name 
    //First it make sure the screen has been already loaded.Then if there is more
    //than one screen the new screen is been added second,and then
    //the current screen is removed. 
    //if there isn't any screen being displayed the new screen is just added to the root
 
      public boolean setScreen(final String name) { 

     if(screens.get(name) != null) { //screen loaded 
       final DoubleProperty opacity = opacityProperty(); 

       //Is there is more than one screen 
       if(!getChildren().isEmpty()){ 
           
         Timeline fade = new Timeline( 
           new KeyFrame(Duration.ZERO, 
                        new KeyValue(opacity,1.0)), 
           new KeyFrame(new Duration(900), 
                
               new EventHandler<ActionEvent>(){ 
                 @Override
                 public void handle(ActionEvent t) { 
                   //remove displayed screen 
                   getChildren().remove(0); 
                   //add new screen 
                   getChildren().add(0, screens.get(name)); 
                   Timeline fadeIn = new Timeline( 
                       new KeyFrame(Duration.ZERO, 
                              new KeyValue(opacity, 0.0)), 
                       new KeyFrame(new Duration(900), 
                              new KeyValue(opacity, 1.0))); 
                   fadeIn.play(); 
                 } 

            
               }, new KeyValue(opacity, 0.0))); 
         fade.play(); 
       } else { 
         //no one else been displayed, then just show 
         setOpacity(0.0); 
         getChildren().add(screens.get(name));
         Timeline fadeIn = new Timeline( 
             new KeyFrame(Duration.ZERO, 
                          new KeyValue(opacity, 0.0)), 
             new KeyFrame(new Duration(900), 
                          new KeyValue(opacity, 1.0))); 
         fadeIn.play(); 
       } 
       return true; 
     } else { 
         System.out.println("screen hasn't been loaded!\n");
         return false; 
    }
    }
      
     public boolean unloadScreen(String name) { 
     if(screens.remove(name) == null) { 
       System.out.println("Screen didn't exist"); 
       return false; 
     } else { 
       return true; 
     } 
   } 

    
}
