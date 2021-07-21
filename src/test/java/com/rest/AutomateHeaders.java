package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class AutomateHeaders {

    @Test
    public void multiple_headers(){
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("x-header-match","value1");
        headers.put("x-mock-match-request-headers","x-header-match");
        //Header header = new Header("x-header-match","value1");
        //Header matchHeader = new Header("x-mock-match-request-headers","x-header-match");
        //Headers headers = new Headers(header, matchHeader);
        //Headers headers = new Headers()

        given().
                baseUri("https://b865ea55-1725-4f25-9391-0a1323203110.mock.pstmn.io").
                //headers(headers).
                header("multiValueHeader","value1","value2").
                log().headers().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);



    }
}
