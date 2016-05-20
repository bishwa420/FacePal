/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package availabledevices;

import com.googlecode.javacv.cpp.videoInputLib.videoInput;
import java.util.ArrayList;

/**
 *
 * @author bishw
 */
public class AvailableDevices {

    /**
     * This returns a list of available video input devices in an arrayList
     * of strings.
     * @return 
     */
    public static ArrayList<String> devices() {
        int numDevs = videoInput.listDevices();
        System.out.println("No of video input devices: " + numDevs);
        
        ArrayList<String> deviceList = new ArrayList<String>();
        for (int i = 0; i < numDevs; i++) {
            deviceList.add(videoInput.getDeviceName(i));
        }
        return deviceList;
    }

}
