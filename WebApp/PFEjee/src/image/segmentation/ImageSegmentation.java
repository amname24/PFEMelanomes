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
    public PhotoData run(Photo photo, boolean test) {
        // Load the image
    
    	String filename = photo.getPath();
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
        if(test)
        	new File(photo.getPath().split(".jpg")[0]+"/seg").mkdir();
       // else
        	//new File(photo.getPath().split(".bmp")[0]+"/seg").mkdir();
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\blue_"+photo.getName(),maskblue);
        //else 
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\blue_"+photo.getName(),maskblue);
        Mat masklightbrown = new Mat();
        Scalar lower_lightbrown = new Scalar(10, 100, 50);
        Scalar upper_lightbrown = new Scalar(20, 200, 200);
        Core.inRange(bw, lower_lightbrown,upper_lightbrown, masklightbrown);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\lightbrown_"+photo.getName(),masklightbrown);
        //else 
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\lightbrown_"+photo.getName(),masklightbrown);
        Mat maskdarkbrown = new Mat();
        Scalar lower_darkbrown = new Scalar(10, 200, 50);
        Scalar upper_darkbrown = new Scalar(20, 255, 200);
        Core.inRange(bw, lower_darkbrown,upper_darkbrown, maskdarkbrown);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\darkbrown_"+photo.getName(),maskdarkbrown);
       // else
        //	Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\darkbrown_"+photo.getName(),maskdarkbrown);
        
        Scalar lower_red = new Scalar(0, 100, 100);
        Scalar upper_red = new Scalar(10, 255, 255);
        Mat maskred = new Mat();
        Core.inRange(bw, lower_red,upper_red, maskred);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\red_"+photo.getName(),maskred);
        //else
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\red_"+photo.getName(),maskred);
        
        Scalar lower_white= new Scalar(0, 0, 200);
        Scalar upper_white = new Scalar(180, 255, 255);
        Mat maskwhite = new Mat();
        Core.inRange(bw, lower_white,upper_white, maskwhite);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\white_"+photo.getName(),maskwhite);
        //else
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\white_"+photo.getName(),maskwhite);
        
        Scalar lower_black= new Scalar(0, 0, 0);
        Scalar upper_black = new Scalar(180,255 , 30);
        Mat maskblack = new Mat();
        Core.inRange(bw, lower_black,upper_black, maskblack);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\white_"+photo.getName(),maskblack);
        //else
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\white_"+photo.getName(),maskblack);
        
        
        Mat gray = new Mat();	
        Imgproc.cvtColor(srcOriginal, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray,gray,0,255,Imgproc.THRESH_BINARY_INV);
        if(test)
        	Imgcodecs.imwrite(photo.getPath().split(".jpg")[0]+"\\seg\\gray_"+photo.getName(),gray);
       // else
        	//Imgcodecs.imwrite(photo.getPath().split(".bmp")[0]+"\\seg\\gray_"+photo.getName(),gray); 
        
        PhotoData p = new PhotoData();
        p.setPhotoid(photo.getId());
        //p.setAsymmetry(0);
       
        if(Core.countNonZero(maskwhite)==0)
        	p.setWhite(0);
		else p.setWhite(1);
        if(Core.countNonZero(maskred)==0)
        	p.setRed(0);
		else p.setRed(1);
		if(Core.countNonZero(masklightbrown)==0)
    		p.setLightBrown(0);
		else p.setLightBrown(1);
		if(Core.countNonZero(maskdarkbrown)==0)
    		p.setDarkBrown(0);
		else p.setDarkBrown(1);
		if(Core.countNonZero(maskblue)==0)
    		p.setBlueGray(0);
		else p.setBlueGray(1);
		if(Core.countNonZero(maskblack)==0)
        	p.setBlack(0);
		else p.setBlack(1);
		
		
		//p.setPigmentNetwork("T");
		//p.setDotsGlobules("T");
		//p.setStreaks("T");
		//p.setRegressionAreas("T");
		//p.setBlueWhitishVeil("T");
       // System.exit(0);
		
		
		return p;
    }
    
   
    
}
