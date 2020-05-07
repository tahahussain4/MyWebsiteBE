package api;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.mail.BodyPart;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;
import javax.servlet.jsp.tagext.BodyContent;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;


import com.mongodb.MongoClientSettings;

import db.ProjectDBLayer;
import model.Project;
import model.RestAPIException;
import spark.ExceptionHandler;
import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import transformers.ExceptionTransformer;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import com.mongodb.MongoClientSettings;

public class Main {
	public static void main(String[] args) {
		//project end points
		Spark.port(getHerokuAssignedPort());
		setup();
		
//		Spark.get("/allProjects", (req, res) -> "Hello World");
//		
//		Spark.get("/", (req, res) -> {
//		    Map<String, Object> model = new HashMap<>();
//		    return render(model, "uploadTemplate.html");
//		});
		
//		Spark.post("/testUpload", (req,res) -> {
//			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
//			   
//			byte[] fileBytes = new byte[1];
//			
//			try(InputStream is =  req.raw().getPart("filename").getInputStream()){
//				System.out.println("in filename");
//				FileOutputStream fos = new FileOutputStream("filename.png");
//				int ret = 0;
//				while((ret = is.read(fileBytes)) != -1){
//					fos.write(fileBytes);
//				}
//				fos.flush();
//				fos.close();
//			} catch(Exception e) {
//				e.printStackTrace();
//				Spark.halt(500,"WHY DOES THIS NOT WORK GOODDDDD!!!");
//			}
//
//		});
		
	}
	
	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
	
	public static void setup(){
	   ProcessBuilder processBuilder = new ProcessBuilder();
	    if (processBuilder.environment().get("MONGODB_URI") != null) {
	        System.out.println(processBuilder.environment().get("MONGODB_URI"));
	    }
	    
	    ConnectionString connString = new ConnectionString(
	    	    "mongodb://heroku_kfvlgzs0:fp6tacarv3rnvr6v7oi05cqh8g@ds135234.mlab.com:35234"
	    	);
	    MongoClientSettings settings = MongoClientSettings.builder()
	    	    .applyConnectionString(connString)
	    	    .retryWrites(true)
	    	    .build();
	    	MongoClient mongoClient = (MongoClient) MongoClients.create(settings);

	    
		CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries
				.fromProviders(PojoCodecProvider.builder()
				.register(Project.class)
				.automatic(true).build()));
		ProjectController controller = new ProjectController(new ProjectDBLayer((com.mongodb.MongoClient) mongoClient,pojoCodecRegistry));
		
		Spark.get("/ping", PingController :: ping);
		Spark.get("/getAllProjects", ProjectController :: getAllProjects);
		Spark.get("/getProject/:id", ProjectController :: getProject);
		Spark.post("/putProject", (request, response) -> {
			try {
				return ProjectController.putProject(request, response);
			} catch (RestAPIException e) {
				e.printStackTrace();
				throw e;
			}
		});
		
		Spark.internalServerError((req, res) -> {
		    res.type("application/json");
		    res.status(500);
		    return "{\"error\":\"Unknown Error\"}";
		});
		
		Spark.exception(RestAPIException.class, (exception, request, response) -> {
			response.status(exception.getStatus());
			response.body("{\"error\":\""+exception.getMessage()+"\"}");
		});
	}
	
	public static String render(Map<String, Object> model, String templatePath) {
	    return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
	}
}
