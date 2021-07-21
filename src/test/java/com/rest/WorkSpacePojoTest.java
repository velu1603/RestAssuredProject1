package com.rest;

import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WorkSpacePojoTest {
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key","PMAK-60a3f206121882005850a249-57f7d6d683b101d2c61ffac32f57a1a81e").
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

        responseSpecification = responseSpecBuilder.build();

    }
    @Test (dataProvider = "workspace")
    public void workspace_serialize_deserialize(String name, String type, String desc){
        Workspace workspace = new Workspace(name,type,desc);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot deserializeWorkSpaceRoot = given().
                body(workspaceRoot).
        when().
                post("/workspaces").
        then().spec(responseSpecification).
                extract().
                response().
                as(WorkspaceRoot.class);

        assertThat(deserializeWorkSpaceRoot.getWorkspace().getName(),equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializeWorkSpaceRoot.getWorkspace().getId(),matchesPattern("^[a-z0-9-]{36}$"));
     }

     @DataProvider(name = "workspace")
     public Object[][]  getWorkSpace(){
        return new Object[][]{
                {"myWorkspaceP","personal","description"},
                {"myWorkspaceT","team","description"}
        };
    }
}