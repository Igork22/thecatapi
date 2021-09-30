package com.thecat;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TesteApi extends MassaDeDados{


    @BeforeClass
    public static void urlbase(){
        RestAssured.baseURI = "\"https://api.thecatapi.com/v1/";
    }

    @Test
    public void cadastro(){



        Response response = given().contentType("application/json").body(corpoCadastro)
            .when().post(urlCadastro);

        response.then().statusCode(200).body("message", containsString("SUCCESS"));

        System.out.println("Retorno => " + response.body().asString());
    }

    @Test
    public void votacao(){
        
        Response response = 
            given().contentType("application/json")
            .body(corpoVotacao)  
            .when().post("votes/");
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

        Response response = 
                given()
                .contentType("application/json")
                .header("x-api-key", "3866186a-cc73-4735-9f9c-c71b6eeab364")
                .pathParam("vote_id", voteid)
                .when().delete("votes/{vote_id}");

        System.out.println("Retorno => " + response.body().asString());
        response.then().statusCode(200).body("message", containsString("SUCCESS"));
        
    }

    @Test
    public void favorito(){

        Response response = 
                given()
                .contentType("application/json")
                .header("x-api-key", "3866186a-cc73-4735-9f9c-c71b6eeab364")
                .body(corpoFavorito)
                .when()
                .post(urlFavorito);

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
        String url = "favourites/{favourite_id}";

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
