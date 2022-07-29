package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class userSteps {

    private Properties prop;
    Response response;

    String baseURL;

   public userSteps() throws IOException {
        prop = weatherAPISteps.readPropertiesFile("src/test/resources/credentials.properties");
        baseURL = prop.getProperty("baseurl2");
    }

    @When("User send a get request to fetch the users in the page")
    public void fetchusers() {
        RestAssured.baseURI = baseURL;
        RequestSpecification httprequest = RestAssured.given();
        response = httprequest.request(Method.GET, "");

    }

    @Then("list of users should be displayed")
    public void getResponse() {
        System.out.println(response.asPrettyString());


    }

    @Then("Status code {string} should be returned")
    public void verifyStausCode(String expectedcode) {
        Assert.assertEquals(expectedcode, String.valueOf(response.getStatusCode()));
        System.out.println(response.getHeader("Content-Type"));
    }

    @When("User wants to access the weather Api")
    public void fetchweather() {
        RestAssured.baseURI = "https://api.openweathermap.org";
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.queryParam("id", "2172797").queryParam("appid", "38d090f7e84108ef08cb049aa6588c38").get("/data/2.5/weather");


    }


    @When("User send a Post request to create user of {string} and {string}")
    public void userSendAPostRequestToCreateUserOfAnd(String namevalue, String jobvalue) {
        RestAssured.baseURI = baseURL;
        RequestSpecification httprequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", namevalue);
        requestParams.put("job", jobvalue);
        httprequest.header("Content-Type", "application/json");
        httprequest.body(requestParams.toString());
        response = httprequest.request(Method.POST, "");

    }


    @Then("Response with {string} and  {string} should be returned")
    public void verifystatus(String responsetoverify, String statuslineverify) {

        String str = String.valueOf(response.getStatusCode());
        String sttausline = response.getStatusLine();
        System.out.println(str + " " + sttausline);
        Assert.assertEquals(response.getStatusLine(), statuslineverify);
        Assert.assertEquals(responsetoverify, str);


    }


    @And("response should be displayed and validated for {string}")
    public void validateresponse(String name) {
        System.out.println(response.asPrettyString());
        Assert.assertTrue(response.asString().contains(name));
    }


    @When("User send a POST request to create users from excel")
    public void createExcelUsers() throws IOException {


        RestAssured.baseURI =baseURL;
        RequestSpecification httprequest = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        LinkedHashMap<Integer, HashMap<String, String>> hm=excelutilitis();

        for(Integer i : hm.keySet()) {

           HashMap<String,String> hmap = hm.get(i);

           for(Map.Entry<String,String> e:hmap.entrySet()){

               System.out.println(e.getKey()+" "+e.getValue());
               requestParams.put(String.valueOf(e.getKey()), e.getValue());
               httprequest.header("Content-Type", "application/json");
               httprequest.body(requestParams.toString());
               response = httprequest.request(Method.POST, "/api/v1/create");

           }

        }





    }

    public static LinkedHashMap<Integer, HashMap<String, String>> excelutilitis() throws IOException {

        LinkedHashMap<Integer, HashMap<String, String>> hm1 = new LinkedHashMap<>();

        try {

            File file = new File("src/test/resources/employee.xlsx");   //creating a new file instance
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);

            XSSFSheet sh = wb.getSheetAt(0);
            XSSFRow hrow = sh.getRow(0);

            for (int i = 1; i < sh.getLastRowNum(); i++) {

                LinkedHashMap<String, String> tmap = new LinkedHashMap<>();
                //iterating over excel file
                XSSFRow row = sh.getRow(i);

                for (int j = 0; j < row.getLastCellNum(); j++) {

                    XSSFCell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    tmap.put(hrow.getCell(j).getStringCellValue(), value);

                }
                hm1.put(i, tmap);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }return hm1;
    }
}

