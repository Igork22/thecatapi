package com.thecat;

import org.junit.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TesteApi {

    @Test
    public void cadastro(){

        String url = "https://api.thecatapi.com/v1/user/passwordlesssignup";
        String corpo = "{ \"email\": \"igorohricht95@gmail.com\", \"appDescription\": \"teste the cat api\" } ";

        Response response = given().contentType("application/json").body(corpo)
            .when().post(url);

        response.then().body("message", containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno => " + response.body().asString());
    }

    @Test
    public void votacaoLoveIt(){

        String url = "https://api.thecatapi.com/v1/votes/";

        Response response = given().contentType("application/json").body("{\"image_id\": \"d64P0lTUk\", \"value\": \"true\", \"sub_id\": \"demo-fc76b1\"}")  
            .when().post(url);

        response.then().body("message", containsString("SUCCESS")).statusCode(200);

        System.out.println("Retorno => " + response.body().asString());
        String id = response.jsonPath().getString("id");
        System.out.println("ID => " + id);
    }
    
}
