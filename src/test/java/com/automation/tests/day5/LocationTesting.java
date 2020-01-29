package com.automation.tests.day5;

import com.automation.pojo.Location;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class LocationTesting {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    /*
    #Task
    Create POJO for Location:
    public class Location{
    }
    Write 2 tests:
	#1 get single POJO of Location class
	#2 get collection of POJO’s.
	Same thing like we did with Job class
	* follow java naming conventions!
     */


    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations");

        JsonPath jsonPath = response.jsonPath();
        //this is deserialization
        // from JSON to POJO (java object)
        Location location = jsonPath.getObject("items[0]", Location.class);//Job.class type of POJO that you gonna create from JSON

        System.out.println(location);

        System.out.println("location id: "+location.getLocationId());

    }

    @Test
    @DisplayName("Convert JSON into collection of POJO's")// collection mean all jobs in the json its a list
    public void test3(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations");

        JsonPath jsonPath = response.jsonPath();
        List<Location> locations = jsonPath.getList("items", Location.class); // if we can get all the data we need to add Job in list

        for(Location location: locations){
            System.out.println(location.getCountryId());

        }

    }



}
