package db;

import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequestWrapper;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

public class ConnectionManager {
	public static MongoDatabase connectToDB(){
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("MongoDB");
		mongoClient.close();
		return database;
		
	}
	
	public static void main(String[] args) {
		try(MongoClient mongoClient = new MongoClient("localhost", 27017)){
		MongoDatabase database = mongoClient.getDatabase("test");
		
		
		MongoIterable<String> iterable = database.listCollectionNames();	
		
		MongoCollection<Document> collection = database.getCollection("projects");
		Document document = collection.find(Filters.eq("_id",new ObjectId("5e61dbfba652118982eccf7e"))).first();
		
		System.out.println(document.get("skills"));
		
//		iterable.forEach((Consumer<String>) string -> {
//			System.out.println(string);
//		});
		}
	}
}
