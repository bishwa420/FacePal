/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facesonly;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.helper.opencv_objdetect;
import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import static org.bytedeco.javacpp.opencv_calib3d.cvRodrigues2;
import org.bytedeco.javacpp.opencv_core.CvMat;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvRect;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

/**
 *
 * @author bishw
 */
public class FacesOnly {

    /**
     * @param args the command line arguments
     */
    BufferedImage sourceImg; // the main image from which faces to detect

    public FacesOnly(BufferedImage img) {
        sourceImg = img;
    }

    private static BufferedImage cropImage(BufferedImage src, int x, int y, Rectangle rect) {
        BufferedImage dest = src.getSubimage(x, y, rect.width, rect.height);
        return dest;
    }

    public static BufferedImage IplImageToBufferedImage(IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        org.bytedeco.javacv.Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame, 1);
    }

    private static IplImage toIplImage(BufferedImage bufImage) {

        ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
        IplImage iplImage = iplConverter.convert(java2dConverter.convert(bufImage));
        return iplImage;
    }

    /**
     * Returns the faces only
     *
     * @return
     */
    public IplImage[] facesOnly() throws Exception {
        String classifierName = "C:\\javacv-1.1-bin bytec\\needed_xml.xml";

        IplImage[] images; // this array will contain faces and will be returned
        Loader.load(opencv_objdetect.class);
        CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(classifierName));
        if (classifier.isNull()) {
            //System.err.println("Error loading classifier file \"" + classifierName + "\".");
            //System.exit(1);
            throw new Exception("classifier file not found");
        }
        IplImage grabbedImage = toIplImage(sourceImg);
        Integer cnt = 0;
        int width = grabbedImage.width();
        int height = grabbedImage.height();
        IplImage grayImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
        IplImage rotatedImage = grabbedImage.clone();

        // Objects allocated with a create*() or clone() factory method are automatically released
        // by the garbage collector, but may still be explicitly released by calling release().
        // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
        CvMemStorage storage = CvMemStorage.create();
        CvMat randomR = CvMat.create(3, 3), randomAxis = CvMat.create(3, 1);
        DoubleIndexer Ridx = randomR.createIndexer(), axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4);
        cvRodrigues2(randomAxis, randomR, null);
        double f = (width + height) / 2.0;
        Ridx.put(0, 2, Ridx.get(0, 2) * f);
        Ridx.put(1, 2, Ridx.get(1, 2) * f);
        Ridx.put(2, 0, Ridx.get(2, 0) / f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        cvClearMemStorage(storage);
        // Let's try to detect some faces! but we need a grayscale image...
        cvCvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
        CvSeq faces = (CvSeq) cvHaarDetectObjects(grayImage, classifier, storage,
                1.1, 3, CV_HAAR_FIND_BIGGEST_OBJECT /*|*/
        /*CV_HAAR_DO_ROUGH_SEARCH CV_HAAR_DO_CANNY_PRUNING*/);

        int total = faces.total(); //total number of faces
        System.out.println("total faces: " + total);

        images = new IplImage[total]; // initializing the images array with number of faces
        //if(total != 1) return null;
        opencv_imgcodecs.cvSaveImage("yoyo" + (cnt++).toString() + ".jpg", grayImage);
        /*
         CvRect r1 = new CvRect(cvGetSeqElem(faces, 0));
         CvRect r2 = new CvRect(cvGetSeqElem(faces, 1));
         CvRect r3 = new CvRect(cvGetSeqElem(faces, 2));
         int x = r3.x(), y = r3.y(), w = r3.width(), h = r3.height();
         cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x + w, y + h), CvScalar.RED, 1, CV_AA, 0);
         opencv_imgcodecs.cvSaveImage("aami" + ".jpg" , toIplImage(cropImage(IplImageToBufferedImage(grabbedImage), x, y, new Rectangle(w, h))));
         */
        if (total == 1) {
            cvGetSeqElem(faces, 0);
            CvRect r = new CvRect(cvGetSeqElem(faces, 0));
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x + w, y + h), CvScalar.RED, 1, CV_AA, 0);
            images[0] = toIplImage(cropImage(IplImageToBufferedImage(grabbedImage), x, y, new Rectangle(w, h)));
        }
        /*
         for (int i = 0; i < total; i++) {
         System.out.println("i: " + i);
         images[i] = getFaces(faces, i, grabbedImage);
         opencv_imgcodecs.cvSaveImage("birrhis" + (cnt++).toString() + ".jpg", images[i]);
         //System.out.println("(x,y,w,h): (" + x + "," + y + "," + w + "," + h + ")");

         }
         */
//        opencv_imgcodecs.cvSaveImage("birrhis" + (cnt++).toString() + ".jpg", images[]);
        return images;
    }

    private IplImage getFaces(CvSeq faces, int faceNo, IplImage grabbedImage) {
        final int faceno = faceNo;
        CvRect r = new CvRect(cvGetSeqElem(faces, faceno));
        int x = r.x(), y = r.y(), w = r.width(), h = r.height();
        cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x + w, y + h), CvScalar.RED, 1, CV_AA, 0);
        return toIplImage(cropImage(IplImageToBufferedImage(grabbedImage), x, y, new Rectangle(w, h)));

    }

    public static void main(String[] args) throws IOException, Exception {
        // TODO code application logic here
        String fileName = "E:\\Dropbox\\Dropbox\\Camera Uploads\\2016-03-03 18.05.20.jpg";
        BufferedImage img = ImageIO.read(new File(fileName));
        FacesOnly var = new FacesOnly(img);
        var.facesOnly();
    }

}
