package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateGet {
    @Test
    public void validate_get_status_code(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name",hasItems("My Workspace"),
                        "workspaces.type",hasItems("personal"),
                        "workspaces[0].name",is(equalTo("My Workspace")),
                        "workspaces.size()",equalTo(1),
                        "workspaces.name",hasItem("My Workspace"));
    }

    @Test
    public void extract_single_value_from_response(){
        Response res = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
        when().
                get("/workspaces").
        then().
                assertThat().
                statusCode(200).
                extract().response();
        //System.out.println("workspace name :"+ res.path("workspaces[0].name"));

        //JsonPath jsonPath = new JsonPath(res.asString());
        //JsonPath.from()

        //System.out.println("workspace name :"+ jsonPath.getString("workspaces[0].name"));
        //System.out.println("workspace name :"+ jsonPath.getString("workspaces[0].name"));

    }

    @Test
    public void hamcrest_assert_on_extracted_response(){
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().path("workspaces[0].name");

        assertThat(name, equalTo("My Workspace"));
    }

    @Test
    public void validate_response_body_hamcrest_learnings(){
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
                when().
                get("/workspaces").
                then().
                //               log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", containsInAnyOrder("My Workspace","My Workspace2","Team Workspace", "Velan")
                ,"workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(4),
                        //"workspaces.name", startsWith("My")
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("id", "a917575c-c531-4abd-82a8-150731ba14e3"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace"))
                        );
    }
    @Test
    public void request_response_logging(){
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
                //config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().all().
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200);
    }
}
