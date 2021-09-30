package com.thecat;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TesteApi extends MassaDeDados {

    @BeforeClass
    public static void urlbase() {
        RestAssured.baseURI = "https://api.thecatapi.com/v1/";
    }

    @Test
    public void cadastro() {

        Response response = given().contentType("application/json").body(corpoCadastro).when().post(urlCadastro);
        validacao(response);
    }

    @Test
    public void votacao() {

        Response response = given().contentType("application/json").body(corpoVotacao).when().post("votes/");
        response.then().statusCode(200).body("message", containsString("SUCCESS"));
        validacao(response);        
        String id = response.jsonPath().getString("id");
        voteid = id;
        System.out.println("ID => " + id);
    }

    @Test
    public void deletaVotacao() {
        votacao();
        deletaVoto();
    }

    private void deletaVoto() {

        Response response = given().contentType("application/json")
                .header("x-api-key", apiKey).pathParam("vote_id", voteid).when()
                .delete("votes/{vote_id}");
        validacao(response);
    }

    @Test
    public void favorito() {

        Response response = given().contentType("application/json")
                .header("x-api-key", apiKey).body(corpoFavorito).when()
                .post(urlFavorito);

        String id = response.jsonPath().getString("id");
        favouriteid = id;

        validacao(response);
    }

    @Test
    public void adicionaDeletaFavorito() {
        favorito();
        deletaFavorito();
    }

    private void deletaFavorito() {

        Response response = given().contentType("application/json")
                .header("x-api-key", apiKey).pathParam("favourite_id", favouriteid)
                .when().delete(corpoDeletaFavorito);

        validacao(response);
    }

    public void validacao(Response response) {

        response.then().statusCode(200).body("message", containsString("SUCCESS"));
        System.out.println("Retorno da API => " + response.body().asString());
        System.out.println("------------------------------------------------------");
    }

}
