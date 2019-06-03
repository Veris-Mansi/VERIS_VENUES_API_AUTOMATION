package Files;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import Files.PayloadDataVenue;
public class ResourceVenues {

	public static String addVenue(String token)
	{
		RestAssured.baseURI="https://sandbox.veris.in";
		Response res =given().
		headers("Content-Type","application/json").headers("Authorization","token "+token).
		body(PayloadDataVenue.addingVenue()).
		when().post("/api/v1/add-org-venue/11/").
		then().assertThat().statusCode(200).and().body("detail", equalTo("Organisation Venue successfully added.")).extract().response();
		String response = res.asString();
		JsonPath path = new JsonPath(response);
		System.out.println("Response "+response);
		String venue_id=path.getString("venue_id");
		System.out.println("venue_id "+venue_id);
		return venue_id;
	}
}
