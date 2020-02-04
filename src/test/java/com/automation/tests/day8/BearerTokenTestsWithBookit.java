package com.automation.tests.day8;


import org.junit.jupiter.api.BeforeAll;

import com.automation.utilities.ConfigurationReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class BearerTokenTestsWithBookit {

    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("bookit.qa1");
    }

//    lets get list of all rooms and verify that status cose is 200
    // /api/rooms

    @Test
    @DisplayName("get list of all rooms")
    public void test1(){

        Response response = given().
                                    header("Authorization", getToken()).
                            when().
                                    get("/api/rooms").prettyPeek();

        response.then().assertThat().statusCode(200).body("name", is("harvard"));

    }
    /**
     * Method that generates access token
     * @return bearer token
     */

        public String getToken(){
        Response response = given().
                                queryParam("email", ConfigurationReader.getProperty("team.leader.email")).
                                queryParam("password", ConfigurationReader.getProperty("team.leader.password")).
                            when().
                                get("/sign").prettyPeek();
        return response.jsonPath().getString("accessToken");



    }
}
