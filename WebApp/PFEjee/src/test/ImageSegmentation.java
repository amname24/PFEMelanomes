package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class ImageSegmentation {
    public void run(String[] args) {
        // Load the image
        String filename = args.length > 0 ? args[0] : "WebContent/IMG/old/IMD002.bmp";
        Mat srcOriginal = Imgcodecs.imread(filename);
        if (srcOriginal.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        
    
       
        
        //Blur the images with various low pass filters
        //Imgproc.GaussianBlur(srcOriginal, srcOriginal,new Size(45,45),0);
        
        // a median filter with a 5x5 rectangular window. 
        Imgproc.medianBlur(srcOriginal, srcOriginal,5);

        
//        Mat bw = new Mat();
//        Imgproc.cvtColor(srcOriginal, bw, Imgproc.COLOR_BGR2HSV);
       
        
        
        // a morphological closing operation is applied with a 5x5 
        Mat element = Imgproc.getStructuringElement( Imgproc.MORPH_RECT, new Size( 5, 5 ), new Point( 2, 2 ) );
        Imgproc.morphologyEx(srcOriginal, srcOriginal, Imgproc.MORPH_CLOSE,element);
        
      //Conversion to grayscale image
        Mat gray = new Mat();	
        Imgproc.cvtColor(srcOriginal, gray, Imgproc.COLOR_BGR2GRAY);
        
//        Mat canny_output = new Mat();
//        Imgproc.Canny(srcOriginal, canny_output, 100, 300, 3, false);
//        Imgproc.findContours(canny_output, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0) );
        Mat image32S = new Mat();
    	

        gray.convertTo(image32S, CvType.CV_32SC1);
        System.out.println(image32S);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat h = new Mat();
        Imgproc.findContours(image32S,contours , h, Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE,new Point(0,0));
        Mat contourImg = new Mat(image32S.size(), image32S.type());
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(contourImg, contours, i, new Scalar(255, 255, 255));
        }
        //        Scalar lower = new Scalar(10,100,20);
//        Scalar upper = new Scalar(20,255,200);
//        	
//        
//        Scalar lower_brown = new Scalar(10, 100, 20);
//        Scalar upper_brown = new Scalar(20, 255, 200);
//        Scalar lower_red = new Scalar(0, 100, 100);
//        Scalar upper_red = new Scalar(10, 255, 255);
//        Scalar lower_red = new Scalar(0,0, 230);
//        Scalar upper_red = new Scalar(0, 0, 255);
//      Scalar lower_red = new Scalar(0,0, 150);
//      Scalar upper_red = new Scalar(0, 0, 255);
//        Mat mask = new Mat();
//        Core.inRange(bw, lower_brown,upper_brown, mask);
//        Imgproc.threshold(mask,mask,0,255,Imgproc.THRESH_BINARY_INV|Imgproc.THRESH_OTSU);

       System.out.println(contourImg.toString());
        Imgcodecs.imwrite("WebContent/IMG/new/FinalResult2.jpg",image32S);

        
        System.exit(0);
    }
    
}
