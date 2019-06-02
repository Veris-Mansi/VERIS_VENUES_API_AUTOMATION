package Files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResourcesPortalLogin {

	public static String portalLogin()
	{

		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().urlEncodingEnabled(true)
	            .param("username", "mansi.sahu@veris.in")
	            .param("password", "ttpl@12345").
	            when().post("/api/v1/portal-login/").then().
	            assertThat().statusCode(200).and().body("valid", equalTo(1)).extract().response();
		String response=res.asString();
		JsonPath path = new JsonPath(response);
		String token=path.get("token");
		return token;
	}
}
