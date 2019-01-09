package mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import mongo.converter.PhotoDataConverter;
import mongo.model.PhotoData;

public class PhotoDataDAO {
	private DBCollection col;

	public PhotoDataDAO(MongoClient mongo) {
		this.col = mongo.getDB("PFE").getCollection("PhotosData");
	}

	public PhotoData createPhotoData(PhotoData p) {
		DBObject doc = PhotoDataConverter.toDBObject(p);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		p.setPhotoid(id.toString());
		return p;
	}

	public void updatePhotoData(PhotoData p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getPhotoid())).get();
		this.col.update(query, PhotoDataConverter.toDBObject(p));
	}

	public List<PhotoData> readAllPhotosData() {
		List<PhotoData> data = new ArrayList<PhotoData>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			PhotoData p = PhotoDataConverter.toPhotoData(doc);
			data.add(p);
		}
		return data;
	}

	public void deletePhotoData(PhotoData p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getPhotoid())).get();
		this.col.remove(query);
	}

	public PhotoData readPhotoData(PhotoData p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getPhotoid())).get();
		DBObject data = this.col.findOne(query);
		return PhotoDataConverter.toPhotoData(data);
	}
}
