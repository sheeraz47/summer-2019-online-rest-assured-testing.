package com.automation.tests.day5;

import com.automation.pojo.Job;
import com.automation.pojo.Location;
import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


import static io.restassured.RestAssured.baseURI;

public class POJOTesting {


    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Get job info from JSON and convert it into POJO")
    public void test1(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        //this is deserialization
        // from JSON to POJO (java object)
        Job job = jsonPath.getObject("items[0]", Job.class);//Job.class type of POJO that you gonna create from JSON

        System.out.println(job);

        System.out.println("Job id: "+job.getJobId());

    }

    @Test
    @DisplayName("Convert from POJO to JSOON")
    public void test2(){
        Job sdet = new Job("SDET", "Software Development Engineer in Test", 5000, 20000);
        Gson gson = new Gson();
        gson.toJson(sdet); // convert POJO to JSON : this is called serialization
        System.out.println(sdet);
        System.out.println(gson.toJson(sdet));
    }

    @Test
    @DisplayName("Convert JSON into collection of POJO's")// collection mean all jobs in the json its a list
    public void test3(){
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/jobs");

        JsonPath jsonPath = response.jsonPath();
        List<Job> jobs = jsonPath.getList("items", Job.class); // if we can get all the data we need to add Job in list

        for(Job job: jobs){
            System.out.println(job.getJob_title());// we can print only title
        }
        for(Job job: jobs){
            System.out.println(job);
        }
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
    @DisplayName("Convert from JSON to Location POJO")
    public void test4() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/locations/{location_id}", 2500);
        Location location = response.jsonPath().getObject("", Location.class);

        System.out.println(location);


        Response response2 = given().
                accept(ContentType.JSON).
                when().
                get("/locations");

        List<Location> locations = response2.jsonPath().getList("items", Location.class);

        for(Location l: locations){
            System.out.println(l);
        }
    }
}
