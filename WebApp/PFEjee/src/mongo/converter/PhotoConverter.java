package mongo.converter;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import mongo.model.Photo;

public class PhotoConverter {
	public static DBObject toDBObject(Photo p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", p.getName()).append("path", p.getPath());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static Photo toPhoto(DBObject doc) {
		Photo p = new Photo();
		p.setName((String) doc.get("name"));
		p.setPath((String) doc.get("path"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}
}
