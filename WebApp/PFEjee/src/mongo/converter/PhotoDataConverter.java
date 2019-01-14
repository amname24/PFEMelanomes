package mongo.converter;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import mongo.model.PhotoData;

public class PhotoDataConverter {
	public static DBObject toDBObject(PhotoData p) {		
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("white", p.getWhite())
				.append("red", p.getRed()).append("light_brown", p.getLightBrown())
				.append("dark_brown", p.getDarkBrown()).append("blue_gray", p.getBlueGray())
				.append("black", p.getBlack())
				.append("melanome", p.getMelanome());
		if (p.getPhotoid() != null)
			builder = builder.append("_id", new ObjectId(p.getPhotoid()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static PhotoData toPhotoData(DBObject doc) {
		PhotoData p = new PhotoData();
		p.setWhite((int) doc.get("white"));
		p.setRed((int) doc.get("red"));
		p.setLightBrown((int) doc.get("light_brown"));
		p.setDarkBrown((int) doc.get("dark_brown"));
		p.setBlueGray((int) doc.get("blue_gray"));
		p.setBlack((int) doc.get("black"));
		p.setMelanome((String) doc.get("melanome"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setPhotoid(id.toString());
		return p;
	}

}
