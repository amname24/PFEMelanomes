package mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import mongo.converter.PhotoConverter;
import mongo.model.Photo;

public class PhotoDAO {
	private DBCollection col;

	public PhotoDAO(MongoClient mongo) {
		this.col = mongo.getDB("PFE").getCollection("Photos");
	}

	public Photo createPhoto(Photo p) {
		DBObject doc = PhotoConverter.toDBObject(p);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}

	public void updatePhoto(Photo p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.update(query, PhotoConverter.toDBObject(p));
	}

	public List<Photo> readAllPhotos() {
		List<Photo> data = new ArrayList<Photo>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			Photo p = PhotoConverter.toPhoto(doc);
			data.add(p);
		}
		return data;
	}

	public void deletePhoto(Photo p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.remove(query);
	}

	public Photo readPhoto(Photo p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		DBObject data = this.col.findOne(query);
		return PhotoConverter.toPhoto(data);
	}
}
