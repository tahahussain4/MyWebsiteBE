package db;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.Project;

public class ProjectDBLayer {
	static MongoClient mongoClient ;
	static String dataBaseName = "test";
	static String collectionName = "projects";
	static MongoDatabase database ;
	static MongoCollection<Project> projectCollection ;
	
	public ProjectDBLayer(MongoClient mongoClient,CodecRegistry registry) {
		super();
		this.mongoClient = mongoClient;
		database = mongoClient.getDatabase(dataBaseName).withCodecRegistry(registry);  ;
		projectCollection = database.getCollection(collectionName,Project.class);
	}
	
	public ArrayList<String> getAllProjectNames(){
		ArrayList<String> names = new ArrayList<String>();
 		
		try{
 		FindIterable<Project> iterable = projectCollection.find();
		iterable.forEach(new Consumer<Project>() {
			@Override
			public void accept(Project t) {
				if(t!= null){
					System.out.println(t.toString());
					names.add(t.getName());
				}
			}
		});;
		} catch(Exception e){
			e.printStackTrace();
		}
		return names;
	}
	
	public ArrayList<Project> getAllProjects(){
		ArrayList<Project> projects = new ArrayList<Project>();
 		
		try{
	 		FindIterable<Project> iterable = projectCollection.find();
			iterable.forEach(new Consumer<Project>() {
				@Override
				public void accept(Project t) {
					if(t!= null){
						projects.add(t);
					}
				}
			});;
		} catch(Exception e){
			e.printStackTrace();
		}
		return projects;
	}
	
	public Project getProject(String id){
		Project document = projectCollection.find(Filters.eq("_id",new ObjectId(id))).first();
		return document;
	}
	
	public Project putProject(Project project){
		projectCollection.insertOne(project);
		return project;
	}
	
	public Project deleteProject(String id){
		return null;
	}
	
	public static void main(String[] args) {
		try(MongoClient mongoClient = new MongoClient("localhost", 27017)){
			CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries
					.fromProviders(PojoCodecProvider.builder()
					.register(Project.class)
					.automatic(true).build()));
			ProjectDBLayer l = new ProjectDBLayer(mongoClient,pojoCodecRegistry);
			ArrayList<String> skills = new ArrayList<String>();
			skills.add("s1");
			skills.add("s2");
			
			 l.putProject(new Project("tes", skills, "test", 2020, 2020, null));
//			System.out.println(names.toString());
		}
	}
//	public getAllProjects(){
//		
//	}
	
}
