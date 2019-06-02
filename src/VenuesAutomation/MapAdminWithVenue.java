package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import Files.PayloadDataVenue;
import Files.ResourcesPortalLogin;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MapAdminWithVenue {

	String token=ResourcesPortalLogin.portalLogin();;
	String invalid_token="19fbca94eb937121ee1446d164b851b9d13f04a";
	String venue_id="27";
	
	@Test(priority=1,groups="MapAdminWithVenue")
	public void mapinvalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("venue", venue_id).
				headers("Authorization","token "+token).
		when().post("/api/v1/map-venue-admin/2599/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=2,groups="MapAdminWithVenue")
	public void mapinvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("venue" ,venue_id).
				headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-venue-admin/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=3,groups="MapAdminWithVenue")
	public void mapinvalidVenue()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		formParam("venue", "222").
		headers("Authorization","token "+token).
		when().post("/api/v1/map-venue-admin/6/").
		then().assertThat().statusCode(404).and().body("detail", equalTo("Not found.")).extract().response();
	}
	@Test(priority=4,groups="MapAdminWithVenue")
	public void MappingVenue()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().urlEncodingEnabled(true).
		formParam("venue", venue_id).
		headers("Authorization","token "+token).
		when().post("/api/v1/map-venue-admin/6/").
		then().assertThat().statusCode(200).and().body("detail", equalTo("Venue with admin role to Successfully mapped.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);
	}
}
