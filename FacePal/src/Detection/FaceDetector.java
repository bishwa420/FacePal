package Detection;


import java.util.ArrayList;
import javafx.scene.image.Image;



public interface FaceDetector {

	public void readAndProcessPhoto();
	public Image getImage();
	public ArrayList<Image> getCroppedFaces();
	public void setMinFace(int i);

	
	
	
}
