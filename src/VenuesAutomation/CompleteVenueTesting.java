package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

//import Files.PayLoadData;
import Files.ResourceVenues;
import Files.ResourcesPortalLogin;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CompleteVenueTesting {

	String token="";
	String invalid_token="19fbca94eb937121ee1446d164b851b9d13f04a";
	String venue_id="";
	String grp_id="50";
	String roles="6";
	
	@Test(priority=0,groups="portalLogin")
	public void portalLogin()
	{
		token=ResourcesPortalLogin.portalLogin();
		System.out.println(token);
	}
	
	@Test(priority=1,groups="addVenue",dependsOnGroups="portalLogin")
	public void addVenue()
	{
		venue_id=ResourceVenues.addVenue(token);
	}
	
	@Test(priority=2,groups="addVenue",dependsOnGroups="portalLogin")
	public void invalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		when().post("/api/v1/add-org-venue/6000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=3,groups="addVenue",dependsOnGroups="portalLogin")
	public void invalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+invalid_token).
		when().post("/api/v1/add-org-venue/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=4,groups="MapAdminWithVenue",dependsOnGroups="addVenue")
	public void mapinvalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		when().post("/api/v1/map-venue-admin/2599/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=5,groups="MapAdminWithVenue",dependsOnGroups="addVenue")
	public void mapinvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-venue-admin/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=6,groups="MapAdminWithVenue",dependsOnGroups="addVenue")
	public void mapinvalidVenue()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		formParam("venue", "222").
		headers("Authorization","token "+token).
		when().post("/api/v1/map-venue-admin/6/").
		then().assertThat().statusCode(404).and().body("detail", equalTo("Not found.")).extract().response();
	}
	@Test(priority=7,groups="MapAdminWithVenue",dependsOnGroups="addVenue")
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
	@Test(priority=8,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
	public void grpinvalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().headers("Authorization","token "+token).
		when().post("/api/v1/map-group-venue-perm/6000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=9,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
	public void grpinvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-group-venue-perm/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=10,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
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
	@Test(priority=11,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
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
	@Test(priority=12,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
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
	@Test(priority=13,groups="Permissions",dependsOnGroups="MapAdminWithVenue")
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
	@Test(priority=14,groups="EnableInviteforGrpmembers",dependsOnGroups="Permissions")
	public void inviteinvalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().formParam("groups", grp_id).
				headers("Authorization","token "+token).
		when().post("/api/v1/map-user-group-venue-perm/60000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=15,groups="EnableInviteforGrpmembers",dependsOnGroups="Permissions")
	public void inviteinvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().formParam("groups", grp_id).headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-user-group-venue-perm/6/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=16,groups="EnableInviteforGrpmembers",dependsOnGroups="Permissions")
	public void inviteinvalidGroup()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("groups", "1234").
				headers("Authorization","token "+token).when().post("/api/v1/map-user-group-venue-perm/6/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);

	}
	@Test(priority=17,groups="EnableInviteforGrpmembers",dependsOnGroups="Permissions")
	public void enableInviteGroup()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("groups", grp_id).
				headers("Authorization","token "+token).when().post("/api/v1/map-user-group-venue-perm/6/").
		then().assertThat().statusCode(200).and().body("detail", equalTo("Mapping sucessfully Done.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);

	}

}
