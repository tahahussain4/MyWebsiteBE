package api;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.skyscreamer.jsonassert.JSONAssert;

import com.google.gson.Gson;

import db.ProjectDBLayer;
import model.Project;
import spark.HaltException;
import spark.Request;
import spark.Response;

public class ProjectControllerTest {
	ProjectController controller ;
	ProjectDBLayer dbLayer;
	Request request;
	Response response;
	HttpServletRequest requestraw ;
	
	public static final String PROJECTID = "id";
	public static final String NAME = "name";
	public static final String NAMES = "projectNames";
	public static final String PROJECT = "project";
	public static final String SKILLS = "skills";
	public static final String YEARTO = "yearTo";
	public static final String YEARFROM = "yearFrom";
	public static final String DESCRIPTION = "description";
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	
	@Before
	public void before(){
		dbLayer = mock(ProjectDBLayer.class);
		controller = new ProjectController(dbLayer);
		request = mock(Request.class);
		response = mock(Response.class);
		requestraw = mock(HttpServletRequest.class);
	//form test get request 
		
	//form test get All names request
	//form test post request
		
	}
	
	@Test
	public void projectDbLayerIsCalledWithCorrectProjectObject() throws Exception {
		//variable
		String name = "putName";
		String description = "putDescription";
		int yearFrom = 2020;
		int yearTo = 2020;

		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		
		String arrayString = getStringVersionOfArray(skills);

		response.getClass();
		BufferedReader reader = mock(BufferedReader.class);
		
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
		
		verify(dbLayer,atLeast(1)).putProject(new Project(name,skills,description,yearFrom,yearTo,null));
		
	}
	
	@Test()
	public void ifNameIsEmptyError() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("project name cannot be empty");
	    
		//variable
		String name = "";
		String description = "putDescription";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	@Test()
	public void ifSkillIsEmptyError() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("Skills should not be empty");
	    
		//variable
		String name = "test";
		String description = "putDescription";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	@Test()
	public void ifYearFromIsLessThan1900Error() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("Bud you were not alive then");
	    
		//variable
		String name = "test";
		String description = "putDescription";
		int yearFrom = 1700;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	@Test()
	public void ifYearToIsLessThan1900Error() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("Bud you were not alive then");
	    
		//variable
		String name = "test";
		String description = "putDescription";
		int yearFrom = 2100;
		int yearTo = 1700;
		ArrayList<String> skills = new ArrayList<String>();
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	@Test()
	public void ifYearToAndYearFromIsLessThan1900Error() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("Bud you were not alive then");
	    
		//variable
		String name = "test";
		String description = "putDescription";
		int yearFrom = 1899;
		int yearTo = 1899;
		ArrayList<String> skills = new ArrayList<String>();
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	
	
	@Test()
	public void ifDescriptionIsEmptyError() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("description cannot be empty");
	    
		//variable
		String name = "test";
		String description = "";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenReturn(reader);
		when(reader.readLine()).thenReturn(
			"{"
		,"	\"name\":\""+name+"\","
		,"	\"description\": \""+description+"\","
		,"	\"skills\" : "+arrayString+","
		,"	\"yearFrom\" : "+yearFrom+","
		,"	\"yearTo\" : "+yearTo+","
		,"}"
		,null);
		
		controller.putProject(request, response);
	}
	
	@Test()
	public void ifReaderThrowsAnIOExceptionAStandardExceptionIsThrown() throws Exception {
		expectedException.expect(Exception.class);
	    expectedException.expectMessage("Cannot read response body");
		//variable
		String name = "test";
		String description = "";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		String arrayString = getStringVersionOfArray(skills);
		
		BufferedReader reader = mock(BufferedReader.class);
		when(request.raw()).thenReturn(requestraw);
		when(requestraw.getReader()).thenThrow(new IOException());

		controller.putProject(request, response);
	}
	
	//============================================================== get tests ==============================================================
	@Test()
	public void getReturnsProjectAsJsonWhenIdIsPassedInRequest() throws Exception {
	    
	    String testId = "5e61dbfba652118982eccf7e";
	    String projectName = "pName";
		String description = "pDesc";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		
		
		when(request.params(PROJECTID)).thenReturn(testId);
		when(dbLayer.getProject(testId)).thenReturn(new Project(projectName,skills,description,yearFrom,yearTo,new ObjectId(testId)));
		
		HashMap<String,Object> actualMap = new HashMap<>();
		actualMap.put(NAME, projectName);
		actualMap.put(DESCRIPTION,description);
		actualMap.put(YEARFROM, yearFrom);
		actualMap.put(YEARTO,yearTo);
		actualMap.put(SKILLS, skills);
		ObjectId obj = new ObjectId(testId);
		actualMap.put(PROJECTID,obj);
		
		JSONObject actualJson = new JSONObject(actualMap);
		System.out.println(actualJson.toString());
		System.out.println(controller.getProject(request, response));
		
		JSONAssert.assertEquals(controller.getProject(request, response), actualJson, true);
//		String returnJson = controller.getProject(request, response);
	}
	
	@Test()
	public void getThrowsExceptionWhenIdIsNull() throws Exception {
		expectedException.expect(HaltException.class);
	    String testId = "";
	    controller.getProject(request, response);
	}
	
	//============================================================== get all projects ==============================================================
	
	@Test()
	public void jsonIsReturnedFromRetreivedProjects() throws Exception {
	    //props1
	    String testId = "5e61dbfba652118982eccf7e";
	    String projectName = "pName";
		String description = "pDesc";
		int yearFrom = 2020;
		int yearTo = 2020;
		ArrayList<String> skills = new ArrayList<String>();
		skills.add("ps1");
		skills.add("ps2");
		
	    //props2
	    String testId2 = "5e61dbfba652118982eccf7e";
	    String projectName2 = "pName2";
		String description2 = "pDesc2";
		int yearFrom2 = 2021;
		int yearTo2 = 2021;
		ArrayList<String> skills2 = new ArrayList<String>();
		skills2.add("ps12");
		skills2.add("ps22");
		
		ArrayList<Project> testProjects = new ArrayList<Project>();
		testProjects.add(new Project(projectName,skills,description,yearFrom,yearTo,new ObjectId(testId)));
		testProjects.add(new Project(projectName2,skills2,description2,yearFrom2,yearTo2,new ObjectId(testId2)));
		
		
		HashMap<String,Object> actualMap = new HashMap<>();
		actualMap.put(NAME, projectName);
		actualMap.put(DESCRIPTION,description);
		actualMap.put(YEARFROM, yearFrom);
		actualMap.put(YEARTO,yearTo);
		actualMap.put(SKILLS, skills);
		ObjectId obj = new ObjectId(testId);
		actualMap.put(PROJECTID,obj);
		JSONObject actualJson = new JSONObject(actualMap);
		
		HashMap<String,Object> actualMap2 = new HashMap<>();
		actualMap2.put(NAME, projectName2);
		actualMap2.put(DESCRIPTION,description2);
		actualMap2.put(YEARFROM, yearFrom2);
		actualMap2.put(YEARTO,yearTo2);
		actualMap2.put(SKILLS, skills2);
		ObjectId obj2 = new ObjectId(testId2);
		actualMap2.put(PROJECTID,obj2);
		JSONObject actualJson2 = new JSONObject(actualMap2);
		
		JSONArray array = new JSONArray();
		array.put(actualJson);
		array.put(actualJson2);
		
		when(dbLayer.getAllProjects()).thenReturn(testProjects);

		JSONAssert.assertEquals(controller.getAllProjects(request, response), array, true);
	}
	
	@Test()
	public void emptyResponseReturnEmptyJson() throws Exception {

		JSONArray array = new JSONArray();
		
		when(dbLayer.getAllProjects()).thenReturn(new ArrayList<Project>());

		JSONAssert.assertEquals(controller.getAllProjects(request, response), array, true);
	}
	
	private static String getStringVersionOfArray(ArrayList<String> stringArray){
		String array = "[";
		for(int index=0;index < stringArray.size(); index++){
			array += "\"";
			array += stringArray.get(index);
			array += "\"";
			if(array.length() - 1> index){
				array+= ",";
			}
		}
		array+= "]";
		return array;
	}

}
