package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.util.Properties;

public class userSteps {

    private Properties prop;
    Response response;

    String baseURL;

    public userSteps() throws IOException
    {
        prop = weatherAPISteps.readPropertiesFile("src/test/resources/credentials.properties");
        baseURL = prop.getProperty("baseurl2");
    }

    @When("User send a get request to fetch the users in the page")
    public void fetchusers() {
        RestAssured.baseURI=baseURL;
        RequestSpecification httprequest=RestAssured.given();
         response=httprequest.request(Method.GET,"");

    }

    @Then("list of users should be displayed")
    public void getResponse() {
        System.out.println(response.asPrettyString());


    }

    @Then("Status code {string} should be returned")
    public void verifyStausCode(String expectedcode) {
        Assert.assertEquals(expectedcode,String.valueOf(response.getStatusCode()));
        System.out.println(response.getHeader("Content-Type"));
}

    @When("User wants to access the weather Api")
    public void fetchweather() {
        RestAssured.baseURI="https://api.openweathermap.org";
        RequestSpecification httpRequest = RestAssured.given();
       response = httpRequest.queryParam("id","2172797").queryParam("appid","38d090f7e84108ef08cb049aa6588c38").get("/data/2.5/weather");


    }


    @When("User send a Post request to create user of {string} and {string}")
    public void userSendAPostRequestToCreateUserOfAnd(String namevalue, String jobvalue) {
        RestAssured.baseURI=baseURL;
        RequestSpecification httprequest=RestAssured.given();

        JSONObject requestParams = new JSONObject();
         requestParams.put("name",namevalue );
        requestParams.put("job", jobvalue);
        httprequest.header("Content-Type","application/json");
        httprequest.body(requestParams.toString());
        response=httprequest.request(Method.POST,"");

    }



    @Then("Response with {string} and  {string} should be returned")
    public void verifystatus(String responsetoverify,String statuslineverify) {

        String str=String.valueOf(response.getStatusCode());
        String sttausline=response.getStatusLine();
        System.out.println(str+" "+sttausline);
        Assert.assertEquals(response.getStatusLine(),statuslineverify);
        Assert.assertEquals(responsetoverify,str);


    }


    @And("response should be displayed and validate for {string}")
    public void validateresponse(String name) {
        System.out.println(response.asPrettyString());
        Assert.assertTrue(response.asString().contains(name));
    }


}
