package Detection;

import java.util.ArrayList;

import org.opencv.core.Mat;

import javafx.scene.image.Image;

public interface EyeDetector {

	void findEyes(Mat frame);
	public ArrayList<Image> getCroppedEyes();

}
