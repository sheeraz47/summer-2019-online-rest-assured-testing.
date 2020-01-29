package com.automation.tests.assignments;

import com.automation.utilities.ConfigurationReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.baseURI;

public class UINamesAPITesting {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ords.uri");
    }

    @Test
    public void test1(){
        given().
                accept("application/json").
                get("charset").
                then().
                assertThat().statusCode(200).
                //log().ifError(); // if the error then its printed otherwise test pass
                        log().all(true);
    }

}
