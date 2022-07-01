package day1;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bsh.org.objectweb.asm.Type;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class restday2 {
	@DataProvider(name="testdata")
	public Object[][] data()
	{
		Object[][] studentsdata = new Object[1][8];
		studentsdata[0][0]= "0";
		studentsdata[0][1]= "surya";
		studentsdata[0][2]= "surya";
		studentsdata[0][3]= "deep";
		studentsdata[0][4]= "surya@gmail.com";
		studentsdata[0][5]= "surya";
		studentsdata[0][6]= "9879879870";
		studentsdata[0][7]= "1";
		
		return studentsdata;
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = true,dataProvider="testdata")
	public void testcase1(String id,String uname,String fname,String lname,String email,String pass,String ph,String ustat)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		
		JSONObject obj =new JSONObject();
		
		obj.put("id",id);
		obj.put("username",uname);
		obj.put("firstName",fname);
		obj.put("lastName",lname);
		obj.put("email",email);
		obj.put("password",pass);
		obj.put("phone",ph);
		obj.put("userstatus",ustat);
		
		
		given().
			contentType(ContentType.JSON).
		body(obj.toJSONString()).
		 	when().post("/user").andReturn().
		 then().statusCode(200).log().all();
	}
	
		//Get user data 
		@Test(enabled=true,dependsOnMethods="testcase1")
		public void getUser(){
			
			RestAssured.baseURI="https://petstore.swagger.io/v2"; 
			given().get("/user/surya").
			then()
			.statusCode(200).log().all();
		}
		
		
		@Test(enabled=true,dataProvider="testdata",dependsOnMethods="testcase1")
		public void LoginUser(String id,String uname,String fname,String lname,String email,String pass,String ph,String ustat){
	       RestAssured.baseURI="https://petstore.swagger.io/v2"; 
			
	       
		given().queryParam("username", uname)
		.queryParam("password", pass).
		when()
		  .get("/user/login").
		then()
		   .statusCode(200)
		   .log().all();			
		}
		
		//Update the user using the put method
		@SuppressWarnings("unchecked")
		@Test(enabled=true,dependsOnMethods="LoginUser",dataProvider="testdata")
		public void UpdateUser(String id,String uname,String fname,String lname,String email,String pass,String ph,String ustat){
			 RestAssured.baseURI="https://petstore.swagger.io/v2"; 
			 
			 JSONObject obj=new JSONObject();
				
			 	obj.put("id",id);
				obj.put("username",uname);
				obj.put("firstName",fname);
				obj.put("lastName",lname);
				obj.put("email","suryadeep@gmail.com");
				obj.put("password",pass);
				obj.put("phone",ph);
				obj.put("userstatus",ustat);
				
				given().contentType(ContentType.JSON)
				   .body(obj.toJSONString())
				.when()
				   .put("/user/surya")
				   .then()
				   .statusCode(200).log().all();
		}
		
		//delete the user
		@Test(enabled=true,dependsOnMethods="UpdateUser")
		public void deleteUser(){
			 RestAssured.baseURI="https://petstore.swagger.io/v2"; 
			 given().delete("/user/surya").then().statusCode(200).log().all();
		}
		
}
