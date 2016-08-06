package Detection;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import javafx.scene.image.Image;

public class HaarEyeDetector  {

	CascadeClassifier eyeCascade;
	Mat initialFrame;
	ArrayList<Mat> croppedEyes;
	ArrayList<Image> croppedEyesImg;
	MatOfRect eyesMat;
	Path currentPath;
	
	public HaarEyeDetector() {
		this.currentPath = Paths.get("");
		
		String filePath = currentPath.toAbsolutePath().toString();
		
		eyeCascade = new CascadeClassifier("C:\\needed_xml_for_eyedetection.xml");
		initialFrame = new Mat();
		croppedEyes = new ArrayList<Mat>();
		croppedEyesImg = new ArrayList<Image>();
		eyesMat = new MatOfRect();
	}
	
	public boolean findEyes(Mat frame){
		croppedEyes.clear();
		croppedEyesImg.clear();
		initialFrame = frame;
		croppedEyes.clear();
		if(detectEyes()==true){
		System.out.println("ok");
                return true;
                }
                
                return false;
		
		
	}
	
	public  boolean detectEyes(){
             boolean flag=false;
		eyeCascade.detectMultiScale(initialFrame, eyesMat);
		for(Rect eye : eyesMat.toArray()){
			
			flag=true;
			//croppedEyes.add(new Mat(initialFrame,eye));
			System.out.println("eye");
		}
                return flag;

	}
	

}
