package com.thecat;

import org.junit.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TesteApi {

    String voteid;
    String favouriteid;

    @Test
    public void cadastro(){

        String url = "https://api.thecatapi.com/v1/user/passwordlesssignup";
        String corpo = "{ \"email\": \"valknut422@gmail.com\", \"appDescription\": \"teste the cat api\" } ";

        Response response = given().contentType("application/json").body(corpo)
            .when().post(url);

        response.then().statusCode(200).body("message", containsString("SUCCESS"));

        System.out.println("Retorno => " + response.body().asString());
    }

    @Test
    public void votacao(){

        String url = "https://api.thecatapi.com/v1/votes/";

        Response response = given().contentType("application/json").body("{\"image_id\": \"d64P0lTUk\", \"value\": \"true\", \"sub_id\": \"demo-fc76b1\"}")  
            .when().post(url);

        response.then().statusCode(200).body("message", containsString("SUCCESS"));

        System.out.println("Retorno => " + response.body().asString());
        String id = response.jsonPath().getString("id");
        voteid = id;
        System.out.println("ID => " + id);
    }
    
    @Test
    public void deletaVotacao(){
        votacao();
        deletaVoto();
    }

    private void deletaVoto() {
        String url = "https://api.thecatapi.com/v1/votes/{vote_id}";

        Response response = 
                given()
                .contentType("application/json")
                .header("x-api-key", "3866186a-cc73-4735-9f9c-c71b6eeab364")
                .pathParam("vote_id", voteid)
                .when().delete(url);

        System.out.println("Retorno => " + response.body().asString());
        response.then().statusCode(200).body("message", containsString("SUCCESS"));
        
    }

    @Test
    public void favorito(){
        String url = ("https://api.thecatapi.com/v1/favourites");
        String corpo = ("{ \"image_id\": \"z3-yEohk9\", \"sub_id\": \"demo-fc76b1\"}");

        Response response = 
                given()
                .contentType("application/json")
                .header("x-api-key", "3866186a-cc73-4735-9f9c-c71b6eeab364")
                .body(corpo)
                .when()
                .post(url);

        String id = response.jsonPath().getString("id");
        favouriteid = id;

        System.out.println("Retorno => " + response.body().asString());
        response.then().statusCode(200).body("message", containsString("SUCCESS"));
    }

    @Test
    public void adicionaDeletaFavorito(){
        favorito();
        deletaFavorito();
    }

    private void deletaFavorito() {
        String url = "https://api.thecatapi.com/v1/favourites/{favourite_id}";

        Response response = 
                given()
                .contentType("application/json")
                .header("x-api-key", "3866186a-cc73-4735-9f9c-c71b6eeab364")
                .pathParam("favourite_id", favouriteid)
                .when().delete(url);

        System.out.println("Retorno => " + response.body().asString());
        response.then().statusCode(200).body("message", containsString("SUCCESS"));
    }

}
