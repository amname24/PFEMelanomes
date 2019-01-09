package image.segmentation;



import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import mongo.model.Photo;
import mongo.model.PhotoData;

public class ImageSegmentation {
    public PhotoData run(Photo photo) {
        // Load the image
    	 
    	String filename = "C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+photo.getName().split(".jpg")[0]+"\\"+photo.getName();
        Mat srcOriginal = Imgcodecs.imread(filename);
        if (srcOriginal.empty()) {
            System.err.println("Cannot read image: " + filename);
            System.exit(0);
        }
        Mat srcOriginalG = new Mat();
        Imgproc.GaussianBlur(srcOriginal, srcOriginalG,new Size(45,45),0);
        Mat bw = new Mat();
        Imgproc.cvtColor(srcOriginalG, bw, Imgproc.COLOR_BGR2HSV);
        // define range of blue color in HSV
        Scalar lower_blue = new Scalar(110, 50, 50);
        Scalar upper_blue = new Scalar(130, 255, 255);
        Mat maskblue = new Mat();
        Core.inRange(bw, lower_blue,upper_blue, maskblue);
	    new File("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMG/"+photo.getName().split(".jpg")[0]+"/seg").mkdir();
        Imgcodecs.imwrite("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+photo.getName().split(".jpg")[0]+"\\seg\\blue_"+photo.getName(),maskblue);
        Mat masklightbrown = new Mat();
        Scalar lower_lightbrown = new Scalar(10, 100, 50);
        Scalar upper_lightbrown = new Scalar(20, 200, 200);
        Core.inRange(bw, lower_lightbrown,upper_lightbrown, masklightbrown);
        Imgcodecs.imwrite("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+photo.getName().split(".jpg")[0]+"\\seg\\lightbrown_"+photo.getName(),masklightbrown);
       
        Mat maskdarkbrown = new Mat();
        Scalar lower_darkbrown = new Scalar(10, 200, 50);
        Scalar upper_darkbrown = new Scalar(20, 255, 200);
        Core.inRange(bw, lower_darkbrown,upper_darkbrown, maskdarkbrown);
        Imgcodecs.imwrite("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+photo.getName().split(".jpg")[0]+"\\seg\\darkbrown_"+photo.getName(),maskdarkbrown);
       
        Mat gray = new Mat();	
        Imgproc.cvtColor(srcOriginal, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray,gray,0,255,Imgproc.THRESH_BINARY_INV);
        Imgcodecs.imwrite("C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\IMG\\"+photo.getName().split(".jpg")[0]+"\\seg\\gray_"+photo.getName(),gray);
        Mat cap = new Mat();
        Core.findNonZero(gray, cap);
        Mat covar = new Mat();
        Mat avg = new Mat();
        Core.calcCovarMatrix(cap, covar, avg, Core.COVAR_COLS);
       
        PhotoData p = new PhotoData();
        p.setPhotoid(photo.getId());
        p.setAsymmetry(0);
		p.setWhite(1);
		p.setRed(0);
		if(Core.countNonZero(masklightbrown)==0)
    		p.setLightBrown(0);
		else p.setLightBrown(1);
		if(Core.countNonZero(maskdarkbrown)==0)
    		p.setDarkBrown(0);
		else p.setDarkBrown(1);
		if(Core.countNonZero(maskblue)==0)
    		p.setBlueGray(0);
		else p.setBlueGray(1);
		p.setBlack(0);
		
		
		p.setPigmentNetwork("T");
		p.setDotsGlobules("T");
		p.setStreaks("T");
		p.setRegressionAreas("T");
		p.setBlueWhitishVeil("T");
       // System.exit(0);
		
		return p;
    }
    
   
    
}
