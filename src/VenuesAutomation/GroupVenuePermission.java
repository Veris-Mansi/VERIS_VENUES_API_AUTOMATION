package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import Files.ResourcesPortalLogin;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GroupVenuePermission {

	String token=ResourcesPortalLogin.portalLogin();;
	String invalid_token="19fbca94eb937121ee1446d164b851b9d13f04a";
	String venue_id="27";
	String grp_id="50";
	String roles="6";
	@Test(priority=1,groups="GrpVenuePermission")
	public void invalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().headers("Authorization","token "+token).
		when().post("/api/v1/map-group-venue-perm/6000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=2,groups="GrpVenuePermission")
	public void invalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=4,groups="GrpVenuePermission")
	public void invalidVenue()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().formParam("roles",roles).
		formParam("venues", "222").
		formParam("groups",grp_id).
		headers("Authorization","token "+token).
		when().post("/api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=5,groups="GrpVenuePermission")
	public void invalidGroup()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		formParam("venues", venue_id).
		formParam("roles",roles).
		formParam("groups","888").
		headers("Authorization","token "+token).
		when().post("/api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=6,groups="GrpVenuePermission")
	public void invalidRoles()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		formParam("venues", venue_id).
		formParam("roles","10").
		formParam("groups",grp_id).
		headers("Authorization","token "+token).
		when().post("/api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=7,groups="GrpVenuePermission")
	public void MapAdminandVenue()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().urlEncodingEnabled(true).
		formParam("roles",roles).
		formParam("venues", venue_id).
		formParam("groups",grp_id).
		headers("Authorization","token "+token).
		when().post("api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(200).and().body("detail", equalTo("Mapping sucessfully Done.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);
	}
}
