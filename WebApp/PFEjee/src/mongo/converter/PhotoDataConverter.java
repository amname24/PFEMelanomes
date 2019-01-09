package mongo.converter;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import mongo.model.PhotoData;

public class PhotoDataConverter {
	public static DBObject toDBObject(PhotoData p) {		
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("asymmetry", p.getAsymmetry()).append("white", p.getWhite())
				.append("red", p.getRed()).append("light_brown", p.getLightBrown())
				.append("dark_brown", p.getDarkBrown()).append("blue_gray", p.getBlueGray())
				.append("black", p.getBlack()).append("pigment_network", p.getPigmentNetwork())
				.append("dots_globules", p.getDotsGlobules()).append("streaks", p.getStreaks())
				.append("regression_areas", p.getRegressionAreas()).append("blue_whitish_veil", p.getBlueWhitishVeil());
		if (p.getPhotoid() != null)
			builder = builder.append("_id", new ObjectId(p.getPhotoid()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static PhotoData toPhotoData(DBObject doc) {
		PhotoData p = new PhotoData();
		p.setAsymmetry((int) doc.get("asymmetry"));
		p.setWhite((int) doc.get("white"));
		p.setRed((int) doc.get("red"));
		p.setLightBrown((int) doc.get("light_brown"));
		p.setDarkBrown((int) doc.get("dark_brown"));
		p.setBlueGray((int) doc.get("blue_gray"));
		p.setBlack((int) doc.get("black"));
		p.setPigmentNetwork((String) doc.get("pigment_network"));
		p.setDotsGlobules((String) doc.get("dots_globules"));
		p.setStreaks((String) doc.get("streaks"));
		p.setRegressionAreas((String) doc.get("regression_areas"));
		p.setBlueWhitishVeil((String) doc.get("blue_whitish_veil"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setPhotoid(id.toString());
		return p;
	}

}
