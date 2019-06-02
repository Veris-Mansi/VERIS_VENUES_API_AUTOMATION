package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;
import Files.ResourceVenues;
import Files.ResourcesPortalLogin;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateVenue {
	static String token=ResourcesPortalLogin.portalLogin();
	String invalid_token="19fbca94eb937121ee1446d164b851b9d13f04a";
	String venue_id="";
	
	@Test(groups="addVenue")
	public void addVenue()
	{
		venue_id=ResourceVenues.addVenue(token);
	}
	
	@Test(groups="addVenue")
	public void invalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		when().post("/api/v1/add-org-venue/6000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(groups="addVenue")
	public void invalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+invalid_token).
		when().post("/api/v1/add-org-venue/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
}
