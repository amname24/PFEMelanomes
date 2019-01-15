package mongo.export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mongo.model.PhotoData;

public class WriteArff {
		 
	private static final String CSV_HEADER_TEST ="@relation melanome\r\n" + 
			"\r\n" + 
			"@attribute lightbrown {0,1}\r\n" + 
			"@attribute bluegray {0,1}\r\n" + 
			"@attribute darkbrown {0,1}\r\n" + 
			"@attribute black {0,1}\r\n" + 
			"@attribute red {0,1}\r\n" + 
			"@attribute white {0,1}\r\n" + 
			"@attribute labelClass{false, true}\r\n" + 
			"\r\n" + 
			"@data";
	private static final String CSV_HEADER_TRAIN = "@relation melanome\r\n" + 
			"\r\n" + 
			"@attribute lightbrown {0,1}\r\n" + 
			"@attribute bluegray {0,1}\r\n" + 
			"@attribute darkbrown {0,1}\r\n" + 
			"@attribute black {0,1}\r\n" + 
			"@attribute red {0,1}\r\n" + 
			"@attribute white {0,1}\r\n" + 
			"@attribute labelClass{false, true}\r\n" + 
			"\r\n" + 
			"@data";
	public static void run( List<PhotoData> dataPhotos, String path) {
 
		FileWriter fileWriter = null;
 
		try {
			fileWriter = new FileWriter(path);
 
			fileWriter.append(CSV_HEADER_TRAIN);
			fileWriter.append('\n');
 
			for (PhotoData dataPhoto : dataPhotos) {
				fileWriter.append(Integer.toString(dataPhoto.getLightBrown()));
				fileWriter.append(',');
				fileWriter.append(Integer.toString(dataPhoto.getBlueGray()));
				fileWriter.append(',');
				fileWriter.append(Integer.toString(dataPhoto.getDarkBrown()));
				fileWriter.append(',');
				fileWriter.append(Integer.toString(dataPhoto.getBlack()));
				fileWriter.append(',');
				fileWriter.append(Integer.toString(dataPhoto.getRed()));
				fileWriter.append(',');
				fileWriter.append(Integer.toString(dataPhoto.getWhite()));
				fileWriter.append(',');
				fileWriter.append(dataPhoto.getMelanome());
				fileWriter.append('\n');
			}
			System.out.println("Write Arff successfully!");
		} catch (Exception e) {
			System.out.println("Writing Arff error!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Flushing/closing error!");
				e.printStackTrace();
			}
		}
	}
	public static void run( PhotoData dataPhoto, String path) {
		 
		FileWriter fileWriter = null;
 
		try {
			fileWriter = new FileWriter(path);
 
			fileWriter.append(CSV_HEADER_TEST);
			fileWriter.append('\n');
 
			
			fileWriter.append(Integer.toString(dataPhoto.getLightBrown()));
			fileWriter.append(',');
			fileWriter.append(Integer.toString(dataPhoto.getBlueGray()));
			fileWriter.append(',');
			fileWriter.append(Integer.toString(dataPhoto.getDarkBrown()));
			fileWriter.append(',');
			fileWriter.append(Integer.toString(dataPhoto.getBlack()));
			fileWriter.append(',');
			fileWriter.append(Integer.toString(dataPhoto.getRed()));
			fileWriter.append(',');
			fileWriter.append(Integer.toString(dataPhoto.getWhite()));
			fileWriter.append(',');
			fileWriter.append("?");
			fileWriter.append('\n');
			
			System.out.println("Write arff successfully!");
		} catch (Exception e) {
			System.out.println("Writing arff error!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Flushing/closing error!");
				e.printStackTrace();
			}
		}
	}
}
