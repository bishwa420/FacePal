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
public class Analysis {
        
  
     
    
    public static void check(Object[]  receive){
        
        
        
        if((Integer)receive[0]==0){
            
            Register r=new Register();
            r.register(receive);
        }
        else if((Integer)receive[0]==1){

            LogIn l = new LogIn();
            l.login(receive);
        }
        else if((Integer)receive[0]==2){
            
            AddPerson addperson=new AddPerson();
            addperson.execute(receive);   
        }
        else if((Integer)receive[0]==3){
            
            ShowPerson sp=new ShowPerson();
            sp.execute(receive);
            
        }
        else if((Integer)receive[0]==4){
            
            DeletePerson dp=new DeletePerson();
            dp.execute(receive);
            
        }
        else if((Integer)receive[0]==5){
            
            Update u=new Update();
            u.updateData(receive);
            
        }
         else if((Integer)receive[0]==6){
             FaceRecognitionProcess fr=new FaceRecognitionProcess();
             fr.processing(receive);
            
        }
        else if((Integer)receive[0]==7){
            
             SendPinCode s=new SendPinCode();
             s.sendPinCode(receive);
        }
        else if((Integer)receive[0]==8){
            
            PasswordSet ps=new PasswordSet();
            ps.passwordSet(receive);
            
        }
        
        
    }
    
    
}
