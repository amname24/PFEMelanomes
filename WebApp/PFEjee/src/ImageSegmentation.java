
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
        Imgproc.GaussianBlur(srcOriginal, srcOriginal,new Size(45,45),0);
        
        
        Mat bw = new Mat();
        Imgproc.cvtColor(srcOriginal, bw, Imgproc.COLOR_BGR2HSV);
       
        Mat gray = new Mat();	
        Imgproc.cvtColor(srcOriginal, gray, Imgproc.COLOR_BGR2GRAY);

        
        Scalar lower = new Scalar(10,100,20);
        Scalar upper = new Scalar(20,255,200);
        	
        
        Scalar lower_brown = new Scalar(10, 100, 20);
        Scalar upper_brown = new Scalar(20, 255, 200);
//        Scalar lower_red = new Scalar(0, 100, 100);
//        Scalar upper_red = new Scalar(10, 255, 255);
//        Scalar lower_red = new Scalar(0,0, 230);
//        Scalar upper_red = new Scalar(0, 0, 255);
//      Scalar lower_red = new Scalar(0,0, 150);
//      Scalar upper_red = new Scalar(0, 0, 255);
        Mat mask = new Mat();
        Core.inRange(bw, lower_brown,upper_brown, mask);
        Imgproc.threshold(mask,mask,0,255,Imgproc.THRESH_BINARY_INV|Imgproc.THRESH_OTSU);
       
       
        Imgcodecs.imwrite("WebContent/IMG/new/FinalResult2.jpg",mask);

        
        System.exit(0);
    }
}
