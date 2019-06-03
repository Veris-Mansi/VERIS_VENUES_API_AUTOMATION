package VenuesAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;
import Files.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EnableInviteforGrpmembers {

	String token=ResourcesPortalLogin.portalLogin();
	
	String invalid_token="85e0ca6032acc5da7a6bd9682ee9fa11026dab5";
	String grp_id="56";
	
	@Test(priority=1,groups="EnableInviteforGrpmembers")
	public void inviteinvalidOrganization()

	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().formParam("groups", grp_id).
				headers("Authorization","token "+token).
		when().post("/api/v1/map-user-group-venue-perm/60000/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
	}
	@Test(priority=2,groups="EnableInviteforGrpmembers")
	public void inviteinvalidToken()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().formParam("groups", grp_id).headers("Authorization","token "+invalid_token).
		when().post("/api/v1/map-user-group-venue-perm/11/").
		then().assertThat().statusCode(401).and().body("detail", equalTo("Invalid token.")).extract().response();
	}
	@Test(priority=3,groups="EnableInviteforGrpmembers")
	public void inviteinvalidGroup()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("groups", "1234").
				headers("Authorization","token "+token).when().post("/api/v1/map-user-group-venue-perm/11/").
		then().assertThat().statusCode(403).and().body("detail", equalTo("You do not have permission to perform this action.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);

	}
	@Test(priority=4,groups="EnableInviteforGrpmembers")
	public void enableInviteGroup()
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
				formParam("groups", grp_id).
				headers("Authorization","token "+token).when().post("/api/v1/map-user-group-venue-perm/11/").
		then().assertThat().statusCode(200).and().body("detail", equalTo("Mapping sucessfully Done.")).extract().response();
		String response = res.asString();
		System.out.println("Response is "+response);

	}
	

}
