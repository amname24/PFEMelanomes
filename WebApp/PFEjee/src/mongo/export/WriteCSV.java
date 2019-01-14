package mongo.export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mongo.model.PhotoData;

public class WriteCSV {
		 
	private static final String CSV_HEADER_TEST = "id,lightbrown,bluegray,darkbrown,black,red,white,melanome";
	private static final String CSV_HEADER_TRAIN = "id,lightbrown,bluegray,darkbrown,black,red,white,melanome";
	public static void run( List<PhotoData> dataPhotos, String path) {
 
		FileWriter fileWriter = null;
 
		try {
			fileWriter = new FileWriter(path);
 
			fileWriter.append(CSV_HEADER_TRAIN);
			fileWriter.append('\n');
 
			for (PhotoData dataPhoto : dataPhotos) {
				fileWriter.append(dataPhoto.getPhotoid());
				fileWriter.append(',');
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
			System.out.println("Write CSV successfully!");
		} catch (Exception e) {
			System.out.println("Writing CSV error!");
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
 
			
			fileWriter.append(dataPhoto.getPhotoid());
			fileWriter.append(',');
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
			fileWriter.append("");
			fileWriter.append('\n');
			
			System.out.println("Write CSV successfully!");
		} catch (Exception e) {
			System.out.println("Writing CSV error!");
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
