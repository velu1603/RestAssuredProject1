package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JacksonAPI_JSONArray {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://676b9a6b-e02c-422c-b110-5b591c137747.mock.pstmn.io").
             //   addHeader("x-mock-match-request-body", "true").
        //        setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
        //                .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }

//    @Test
//    public void serialize_list_to_json_array_using_jackson() throws JsonProcessingException {
//        HashMap<String, String> obj5001 = new HashMap<String, String>();
//        obj5001.put("id", "5001");
//        obj5001.put("type", "None");
//
//        HashMap<String, String> obj5002 = new HashMap<String, String>();
//        obj5002.put("id", "5002");
//        obj5002.put("type", "Glazed");
//
//        List<HashMap<String, String>> jsonList = new ArrayList<HashMap<String, String>>();
//        jsonList.add(obj5001);
//        jsonList.add(obj5002);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonListStr = objectMapper.writeValueAsString(jsonList);
//
//        given().
//                body(jsonListStr).
//        when().
//                post("/post").
//        then().spec(customResponseSpecification).
//                assertThat().
//                body("msg", equalTo("Success"));
//    }
//
//    @Test
//    public void serialize_json_array_using_jackson() throws JsonProcessingException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        ArrayNode arrayNodeList = objectMapper.createArrayNode();
//
//        ObjectNode obj5001Node = objectMapper.createObjectNode();
//        obj5001Node.put("id", "5001");
//        obj5001Node.put("type", "None");
//
//        ObjectNode obj5002Node = objectMapper.createObjectNode();
//        obj5002Node.put("id", "5002");
//        obj5002Node.put("type", "Glazed");
//
//        arrayNodeList.add(obj5001Node);
//        arrayNodeList.add(obj5002Node);
//
//        String jsonListStr = objectMapper.writeValueAsString(arrayNodeList);
//
//        given().
//                body(jsonListStr).
//                when().
//                post("/post").
//                then().spec(customResponseSpecification).
//                assertThat().
//                body("msg", equalTo("Success"));
//    }
    @Test
    public void simple_pogo() throws JsonProcessingException {
       // SimplePojo simplePojo = new SimplePojo("value1","value2");
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");

        SimplePojo deSerilizedPojo = given().
                body(simplePojo).
        when().
                post("/postSimpleJson").
        then().
                spec(customResponseSpecification).
                assertThat().
//                body("key1",equalTo(simplePojo.getKey1()),
//                        "key2",equalTo(simplePojo.getKey2()));
                extract().
                response().
                as(SimplePojo.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String deserializedPojoStr = objectMapper.writeValueAsString(deSerilizedPojo);
        String simplePojoStr = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(deserializedPojoStr),equalTo(objectMapper.readTree(simplePojoStr)));

    }
}
