package api;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javax.json.JsonArray;
import javax.json.stream.JsonGenerator;

import org.bson.types.ObjectId;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import db.ProjectDBLayer;
import model.Project;
import model.RestAPIException;
import spark.HaltException;
import spark.Request;
import spark.Response;
import spark.Spark;

/*
 * getAllProjects -> no paramters
 * getDescription and skills for each projects for a particular id -> 
 */
public class ProjectController {
	
	static ProjectDBLayer dbLayer;
	public static final String PROJECTID = "id";
	public static final String NAME = "name";
	public static final String NAMES = "projectNames";
	public static final String PROJECT = "project";
	public static final String SKILLS = "skills";
	public static final String YEARTO = "yearTo";
	public static final String YEARFROM = "yearFrom";
	public static final String DESCRIPTION = "description";

	public ProjectController(ProjectDBLayer dbLayer) {
		this.dbLayer = dbLayer;
	}

	// works
	public static String getProjectNamesAndIds(Request request, Response response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			map.put(NAMES, dbLayer.getAllProjectNames());
		} catch (Exception e){
			e.printStackTrace();
		}
		response.header("Content-Type", "application/json");
		
		return getJSONString(map);
	}

	// works
	public static String getAllProjects(Request request, Response response) {
		ArrayList<Project> projects = null;
		try{
			response.header("Content-Type", "application/json");
			projects = dbLayer.getAllProjects();
		} catch(Exception e){
			e.printStackTrace();
		}
		return getJSONString(projects);
	}
	
	// object mapper doesnt work
	public static String getProject(Request request, Response response) throws Exception {
		String id = request.params(PROJECTID);

		if (isEmpty(id)) {
			System.out.println("id is null");
			Spark.halt(500, "id is empty");
		}
		String projectJson = getJSONString(dbLayer.getProject(id));
		return projectJson;
	}

	public static String putProject(Request request, Response response) throws RestAPIException  {
		// read request
		StringBuffer jb = new StringBuffer();

		try (BufferedReader reader = request.raw().getReader();) {
			String line = null;
			while ((line = reader.readLine()) != null)
				jb.append(line);

		} catch (Exception e) {
			throw new RestAPIException(500,"Cannot read response body");
		}
		
		// get everything from JSON body
		JSONObject jsonObject =null;
		String projectName = null;
		String description =null ;
		JSONArray  array = null;
		int yearTo = 0,yearFrom = 0;

		try{
			jsonObject = new JSONObject(jb.toString());
			projectName = (String) jsonObject.get(NAME);
			description = (String) jsonObject.get(DESCRIPTION);
			array = (JSONArray) jsonObject.get(SKILLS);
			yearFrom= (int) jsonObject.get(YEARFROM);
			yearTo= (int) jsonObject.get(YEARTO);
		} catch(JSONException e){
			throw new RestAPIException(400,e.getMessage());
		} 
		
		if(isEmpty(projectName)){
			throw new RestAPIException(400,"project name cannot be empty");
		}
		
		if(isEmpty(description)){
			throw new RestAPIException(400,"description cannot be empty");
		}
		
		if(yearFrom < 1900 || yearTo < 1900){
			throw new RestAPIException(400,"Bud you were not alive then");
		}
		
		if(array != null && array.length() < 1){
			throw new RestAPIException(400,"Skills should not be empty");
		}
		
		ArrayList<String> skills = jsonArrayToArrayList(array);
		// update database
		Project project = dbLayer.putProject(new Project(projectName, skills, description,yearFrom,yearTo, null ));
		return JSONObject.valueToString(project);

	}

	private static String getJSONString(Object object) {
		try {
			if(object instanceof Collection){
				System.out.println(object);
				JSONArray collection = new JSONArray((Collection) object);
				return collection.toString();
			}
			JSONObject json = new JSONObject(object);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Spark.halt(500, "Issue with json processing");
		}
		return null;
	}
	
	private static ArrayList<String> jsonArrayToArrayList(JSONArray jArray){
		ArrayList<String> listdata = new ArrayList<String>();     
		if (jArray != null) { 
		   for (int i=0;i<jArray.length();i++){ 
		    listdata.add(jArray.getString(i));
		   } 
		}
		return listdata;
	}
	
	public static void main(String[] args) {
		System.out.println("test");
		String test="{\t\"name\" : \"postName\",\t\"description\" : \"postDescription\",\t\"skills\" : [\"ps1\",\"ps2\"]\t}" ;
		
//		System.out.println(gson.toJson(gson.toJson(jb.toString().replace("\"", ""))));
		JSONObject jsonObject = new JSONObject(test);
	    
		ArrayList<String> skills = new ArrayList<String>();
		ArrayList<Project> projects = new ArrayList<Project>();
		//variable
		skills.add("ps1");
		skills.add("ps2");
		
		projects.add(new Project("test",skills,"test",2020,2020,new ObjectId("5e61dbfba652118982eccf7e")));
//		
//		System.out.println(getJSONString(new Project("testProject",skills,"testDescription",2020,2020,new ObjectId("5e61dbfba652118982eccf7e"))));
//		System.out.println((new ObjectId("5e61dbfba652118982eccf7e")).toString());
//		String projectName = (String) jsonObject.get(NAME);
//		String description = (String) jsonObject.get(DESCRIPTION);
		JSONArray array = new JSONArray(skills);
		JSONArray pArray = new JSONArray(projects);
		System.out.println(array.toString());
		
		System.out.println(jsonObject.get("not in there"));
		System.out.println(skills instanceof Collection);
		System.out.println(projects instanceof Collection);
	}
	
	private static  boolean isEmpty(String s){
		if(s == null || s.equals(""))
			return true;
		return false;
	}

}
