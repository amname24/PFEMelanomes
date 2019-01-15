package mongo.export;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFSDBFile;

import mongo.converter.PhotoDataConverter;
import mongo.model.PhotoData;

public class ExportData {
	public static void run(PhotoData datatest) {
		
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient("localhost", 27017);
			DBCollection photosdatasTrain = mongoClient.getDB("PFE").getCollection("PhotosDataTrain");
			List<PhotoData> datatrain = new ArrayList<PhotoData>();
			DBCursor cursor = photosdatasTrain.find();
			while (cursor.hasNext()) {
				DBObject doc = cursor.next();
				PhotoData p = PhotoDataConverter.toPhotoData(doc);
				datatrain.add(p);
			}
			WriteArff.run(datatest,"C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\DOCS\\dataTest.arff");
			WriteArff.run(datatrain,"C:\\Users\\me\\eclipse-workspace2\\PFEjee\\WebContent\\DOCS\\dataTrain.arff");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
