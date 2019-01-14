package test;

import org.opencv.core.Core;

import com.mongodb.MongoClient;

import mongo.converter.PhotoConverter;
import mongo.model.Photo;

public class ImageSegmentationDemo {
    public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new ImageSegmentation().run(args);
     
        

        
    }
}