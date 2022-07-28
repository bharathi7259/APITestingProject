package stepdefinitions;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class weatherAPISteps {


    static Response response;
    static String baseURL;
    static Properties prop;

    public weatherAPISteps() throws IOException {
        prop = readPropertiesFile("src/test/resources/credentials.properties");
        baseURL = prop.getProperty("baseurl");
    }


    @When("User sends a get request to {string} to fetch the weather of {string}")
    public void fetchtheweather(String serviceURL, String param) throws IOException {
        RestAssured.baseURI = baseURL;
        String str = prop.getProperty(serviceURL);
        String pathurl = str.replaceAll("<Param>", param);
        RequestSpecification httprequest = given();
        response = httprequest.request(Method.GET, pathurl);
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }


    @Then("Response with success status code  of value {int} should be returned")
    public void response(int code) {
        int statuscode = response.getStatusCode();
        Assert.assertEquals(code, statuscode);
    }


    @And("response should have value {string} at path {string}")
    public void validateValue(String value, String path) {
        JsonPath js = response.jsonPath();
        String actualValue = js.get(path).toString();
        System.out.print(actualValue + " ");
        Assert.assertEquals(value, actualValue);


    }



    @And("print all the headers")
    public  void printheaders() {

        Headers allHeaders = response.headers();
        // Iterate over all the Headers
        for (Header header : allHeaders)
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());



    }
}