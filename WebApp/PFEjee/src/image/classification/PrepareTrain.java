package image.classification;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import mongo.model.Photo;

public class PrepareTrain {
	private static FilenameFilter bmpFileFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return name.endsWith(".bmp");
		}
	};
	
	public static List<Photo> run() {
		File repertoire = new File("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMGTRAIN");
		File[] files = repertoire.listFiles(bmpFileFilter);
		List<Photo> photos = new ArrayList<Photo>();
		for (File file : files  ) {
		    new File("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMGTRAIN/"+file.getName().split(".bmp")[0]).mkdir();
			Photo nouvelle = new Photo();
		    ObjectId id = new ObjectId();
		    nouvelle.setId(id.toString());
		    nouvelle.setName(file.getName());
		    nouvelle.setPath("C:/Users/me/eclipse-workspace2/PFEjee/WebContent/IMGTRAIN/"+file.getName());
		    photos.add(nouvelle);
		}
		return photos;
	}
}
