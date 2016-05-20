/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facedetection;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.Loader;
import static org.bytedeco.javacpp.helper.opencv_imgproc.cvDrawContours;
import static org.bytedeco.javacpp.helper.opencv_imgproc.cvFindContours;
import org.bytedeco.javacpp.helper.opencv_objdetect;
import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import static org.bytedeco.javacpp.opencv_calib3d.cvRodrigues2;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMat;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_16U;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvLoad;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import org.bytedeco.javacpp.opencv_imgcodecs;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_CHAIN_APPROX_SIMPLE;
import static org.bytedeco.javacpp.opencv_imgproc.CV_POLY_APPROX_DP;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RETR_LIST;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.cvApproxPoly;
import static org.bytedeco.javacpp.opencv_imgproc.cvContourPerimeter;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvFillConvexPoly;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import static org.bytedeco.javacpp.opencv_imgproc.cvWarpPerspective;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;

/**
 *
 * @author bishw
 */
public class FaceDetection {

    private static int CAMERA_ID;

    static {
        CAMERA_ID = 0;
    }

    public FaceDetection() {
        CAMERA_ID = 0;
    }

    public FaceDetection(int camera_id) {
        CAMERA_ID = camera_id;
    }

    private static BufferedImage cropImage(BufferedImage src, int x, int y, Rectangle rect) {
        //System.out.println("line 68, x: " + x + " y: " + y + " rect: " + rect.width + ", " + rect.height);
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
     * This method is finds exactly one face with given camera_id.
     * it takes a snap and detect faces. If the number of faces in the captured
     * image is not one, then null is returned. For successful detection
     * only the face, cropped from the main snap, is returned. This method can
     * throw exception if the needed_xml file is not found at the local C drive.
     * Currently this is under construction.
     * @return
     * @throws Exception 
     */
    public IplImage detectFace() throws Exception {
        String classifierName = "C:\\javacv-1.1-bin bytec\\needed_xml.xml";
        //classifierName = "E:\\Google Drive\\FacePal\\All jar files\\javacv-1.1-bin bytec\\needed_xml.xml";

        // Preload the opencv_objdetect module to work around a known bug.
        Loader.load(opencv_objdetect.class);

        // We can "cast" Pointer objects by instantiating a new object of the desired class.
        CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(classifierName));
        if (classifier.isNull()) {
            //System.err.println("Error loading classifier file \"" + classifierName + "\".");
            //System.exit(1);
            throw new Exception("");
        }

        // The available FrameGrabber classes include OpenCVFrameGrabber (opencv_videoio),
        // DC1394FrameGrabber, FlyCaptureFrameGrabber, OpenKinectFrameGrabber,
        // PS3EyeFrameGrabber, VideoInputFrameGrabber, and FFmpegFrameGrabber.
        //System.out.println("starting before grabber");
        FrameGrabber grabber = FrameGrabber.createDefault(CAMERA_ID);
        grabber.start();
        //System.out.println("after grabber");

        // CanvasFrame, FrameGrabber, and FrameRecorder use Frame objects to communicate image data.
        // We need a FrameConverter to interface with other APIs (Android, Java 2D, or OpenCV).
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        // FAQ about IplImage and Mat objects from OpenCV:
        // - For custom raw processing of data, createBuffer() returns an NIO direct
        //   buffer wrapped around the memory pointed by imageData, and under Android we can
        //   also use that Buffer with Bitmap.copyPixelsFromBuffer() and copyPixelsToBuffer().
        // - To get a BufferedImage from an IplImage, or vice versa, we can chain calls to
        //   Java2DFrameConverter and OpenCVFrameConverter, one after the other.
        // - Java2DFrameConverter also has static copy() methods that we can use to transfer
        //   data more directly between BufferedImage and IplImage or Mat via Frame objects.
        IplImage grabbedImage = converter.convert(grabber.grab());
        Integer cnt = 0;

        int width = grabbedImage.width();
        int height = grabbedImage.height();
        IplImage grayImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
        IplImage rotatedImage = grabbedImage.clone();

        // Objects allocated with a create*() or clone() factory method are automatically released
        // by the garbage collector, but may still be explicitly released by calling release().
        // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
        CvMemStorage storage = CvMemStorage.create();

        // The OpenCVFrameRecorder class simply uses the CvVideoWriter of opencv_videoio,
        // but FFmpegFrameRecorder also exists as a more versatile alternative.
        //FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
        //recorder.start();
        // CanvasFrame is a JFrame containing a Canvas component, which is hardware accelerated.
        // It can also switch into full-screen mode when called with a screenNumber.
        // We should also specify the relative monitor/camera response for proper gamma correction.
        //CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        // Let's create some random 3D rotation...
        CvMat randomR = CvMat.create(3, 3), randomAxis = CvMat.create(3, 1);
        // We can easily and efficiently access the elements of matrices and images
        // through an Indexer object with the set of get() and put() methods.
        DoubleIndexer Ridx = randomR.createIndexer(), axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4, (Math.random() - 0.5) / 4);
        cvRodrigues2(randomAxis, randomR, null);
        double f = (width + height) / 2.0;
        Ridx.put(0, 2, Ridx.get(0, 2) * f);
        Ridx.put(1, 2, Ridx.get(1, 2) * f);
        Ridx.put(2, 0, Ridx.get(2, 0) / f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        System.out.println("Ridx: " + Ridx);

        // We can allocate native arrays using constructors taking an integer as argument.
        CvPoint hatPoints = new CvPoint(3);

        //while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
        grabbedImage = converter.convert(grabber.grab());
        grabber.stop();
        cvClearMemStorage(storage);

        // Let's try to detect some faces! but we need a grayscale image...
        cvCvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
        //opencv_imgcodecs.cvSaveImage("birrhi"+(cnt++).toString()+".jpg", grayImage);

        System.out.println("line 184");
        CvSeq faces = cvHaarDetectObjects(grayImage, classifier, storage,
                1.1, 3, CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);
        int total = faces.total();
        //System.out.println("line 188, total: " + total);
        if (total != 1) {
            return null;
        }
        CvRect r = new CvRect(cvGetSeqElem(faces, 0));
        int x = r.x(), y = r.y(), w = r.width(), h = r.height();
        cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x + w, y + h), CvScalar.RED, 1, CV_AA, 0);
        IplImage myImage = toIplImage(cropImage(IplImageToBufferedImage(grabbedImage), x, y, new Rectangle(w, h)));
        //System.out.println("saved image: dingdong");
        opencv_imgcodecs.cvSaveImage("birrhis" + (cnt++).toString() + ".jpg", myImage);
        return myImage;

        /*        
         // Let's find some contours! but first some thresholding...
         cvThreshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

         // To check if an output argument is null we may call either isNull() or equals(null).
         CvSeq contour = new CvSeq(null);
         cvFindContours(grayImage, storage, contour, Loader.sizeof(CvContour.class),
         CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
         while (contour != null && !contour.isNull()) {
         if (contour.elem_size() > 0) {
         CvSeq points = cvApproxPoly(contour, Loader.sizeof(CvContour.class),
         storage, CV_POLY_APPROX_DP, cvContourPerimeter(contour) * 0.02, 0);
         cvDrawContours(grabbedImage, points, CvScalar.BLUE, CvScalar.BLUE, -1, 1, CV_AA);
         }
         contour = contour.h_next();
         }

         cvWarpPerspective(grabbedImage, rotatedImage, randomR);

         org.bytedeco.javacv.Frame rotatedFrame = converter.convert(rotatedImage);
         frame.showImage(rotatedFrame);
         //recorder.record(rotatedFrame);
         //}
         */
        //frame.dispose();
    } //end of main

}
