package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import Files.*;
import org.testng.annotations.Test;

//import Files.ResourcesLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class portalLogin {

	String token="";
	String invalid_token="291b12850a914d0dba55bd5aa62b16d3769797";
	@Test(groups="portalLogin")
	public void portalLogin()
	{
		token=ResourcesPortalLogin.portalLogin();
		System.out.println(token);
	}
	@Test(groups="portalLogin")
	public void invalidUserportalLogin()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().urlEncodingEnabled(true)
	            .param("username", "1111")
	            .param("password", "ttpl@12345").
	            when().post("/api/v1/portal-login/").then().
	            assertThat().statusCode(200).and().body("valid", equalTo(0)).extract().response();
		String response=res.asString();
		System.out.println("Response "+response);
	}
}
