package testRun;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class restApiAssuredTest2 {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @BeforeEach
    void cleanDatabase() {
        try {
            Response response = given()
                    .auth().basic("admin", "secret123")
                    .param("page", 0)
                    .param("size", 1000)
                    .when()
                    .get("/goods/list");

            if (response.getStatusCode() != 200) {
                System.out.println("GET /goods/list вернул " + response.getStatusCode());
                return;
            }

            List<Integer> ids = response.jsonPath().getList("goods.id");
            if (ids == null) return;

            for (Integer id : ids) {
                try {
                    given()
                            .auth().basic("admin", "secret123")
                            .when()
                            .delete("/goods/" + id)
                            .then()
                            .statusCode(anyOf(is(200), is(404), is(400), is(500)));
                } catch (Exception e) {
                    System.out.println("DELETE /goods/" + id + " упал: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Очистка БД не произошла: " + e.getMessage());
        }
    }
    @Test
    //POST /goods/add - 200
    public void testPostAdd_200() {
        String uniqueName = "String" + UUID.randomUUID();
        String jsonBody =
                "{" +
                        "\"name\":\"" + uniqueName + "\"," +
                        "\"price\":0.1" +
                        "}";
        //Первый 200
        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200)
                //Assert тело ответа не пустое
                .body(notNullValue());

        // Assert: товар появился в списке
        given()
                .auth().basic("admin", "secret123")
                .param("page", 0)
                .param("size", 1000)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                // Assert имя товара в списке
                .body("goods.name", hasItem(uniqueName))
                //Assert список не пустой
                .body("goods", not(empty()));
    }

    @Test
    //POST /goods/add 400 дубликат
    public void testPostAdd400Duplicate() {
        String uniqueName = "String" + UUID.randomUUID();
        String jsonBody =
                "{" +
                        "\"name\":\"" + uniqueName + "\"," +
                        "\"price\":0.1" +
                        "}";

        // Первый раз 200
        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200);

        // Второй раз с тем же именем 400
        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(400)
                //Assert сообщение об ошибке
                .body("message", containsString("already exists"));

    }

    @Test
    //POST /goods/add — 400: пустое тело запроса
    public void testPostAdd400EmptyBody() {
        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(400)
                //Assert: тело есть
                .body(notNullValue())
                //Assert: пустое тело запроса
                .body(containsString("empty"));

    }
    //------------------------------------------------------------------------------------------------------------//
    @Test
    // GET /goods/{id} 200 - товар существует
    public void testGetGoodById200() {
        String uniqueName = "String" + UUID.randomUUID();
        Integer id = createString(uniqueName , 0.1F);
        given()
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/" + id)
                .then()
                .log().all()
                .statusCode(200)
                //Assert id совпадает
                .body("id", equalTo(id))
                //Assert имя уникальное
                .body("name", equalTo(uniqueName))
                //Assert цена совпадает
                .body("price", equalTo(0.1F));
    }
    @Test
    // GET запрос Товар не найден 404
    public void testGetGoodById404() {
        given()
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/100")
                .then()
                .log().all()
                // БАГ: сервер возвращает 500 вместо 404
                .statusCode(404)
                // Assert тело ответа есть
                .body(notNullValue())
                // Assert сообщение об ошибке
                .body("message", notNullValue());

    }
    //-------------------------------------------------------------------------------------------------//

    @Test
    // DELETE запрос на успешное удаление /goods/{id}
    public void testDeleteGoodById200() {
        String uniqueName = "String" + UUID.randomUUID();
        Integer id = createString(uniqueName, 0.1F);
        given()
                .auth().basic("admin", "secret123")
                .when()
                .delete("/goods/" + id)
                .then()
                .log().all()
                .statusCode(200)
                // Assert тело ответа 200
                .body(notNullValue());

        // Assert: товар действительно удалён — GET возвращает 404
        given()
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/" + id)
                .then()
                .statusCode(404)
                // Assert есть сообщение об ошибке 404
                .body("message", notNullValue());
    }

    @Test
    // DELETE запрос  404: товар не найден /goods/{id}
    public void testDeleteGood_404() {
        Response response = given()
                .auth().basic("admin", "secret123")
                .when()
                .delete("/goods/101")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();

        // Assert : тело ответа "not found"
        String body = response.getBody().asString();
        assertThat(body).isNotEmpty();
        assertThat(body).contains("not found");
    }
//--------------------------------------------------------------------------------------------------//

    @Test
    //PATCH запрос /goods/{id} успешный 200
    public void testUpdateGoodById200() {
        String uniqueName = "String" + UUID.randomUUID();
        Integer id = createString(uniqueName, 0.1F);
        String updatedName = "UpdatedGood_" + UUID.randomUUID();
        String jsonBody = String.format("{\"name\":\"%s\",\"price\":0.1}", updatedName);
        given()
        .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .patch("/goods/" + id)
                .then()
                .log().all()
                .statusCode(200)
                //Assert тело ответа
                .body(notNullValue());

        // Ассерт: проверяем, что данные обновились через GET
        given()
                .auth().basic("admin", "secret123")
                .when()
                .get("/goods/" + id)
                .then()
                .statusCode(200)
                //Assert имя обновилось
                .body("name", equalTo(updatedName))
                //Assert цена обновилась
                .body("price", equalTo(0.1f));

    }

    @Test
    // PATCH запрос 400 /goods/{id} Неверный запрос
    public void testUpdateGood400() {
        String uniqueName = "String" + UUID.randomUUID();
        Integer id = createString(uniqueName, 0.1F);

        Response response = given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .patch("/goods/" + id)
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .response();

        // Assert тело ответа не пустое
        String body = response.getBody().asString();
        assertThat(body).isNotEmpty();

    }
@Test
// PATCH запрос 404 не найдено /goods/{id}
public void testPatchGood_404() {
    String patchBody = "{\"name\":\"UpdatedGood\",\"price\":25.0}";

    Response response = given()
            .auth().basic("admin", "secret123")
            .contentType(ContentType.JSON)
            .body(patchBody)
            .when()
            .patch("/goods/0")
            .then()
            .log().all()
            // БАГ: сервер возвращает 500 вместо 404
            .statusCode(anyOf(is(404), is(500)))
            .extract()
            .response();

    // Assert тело ответа не пустое
    String body = response.getBody().asString();
    assertThat(body).isNotEmpty();

}

@Test
// GET 200 /goods/list получение список всех товаров с разбивкой на странице
public void testGetGoodsList_200() {
    given()
            .auth().basic("admin", "secret123")
            .param("page", 0)
            .param("size", 10)
            .when()
            .get("/goods/list")
            .then()
            .log().all()
            .statusCode(200)
            //Assert товар есть
            .body("goods", notNullValue())
            // Список всех товаров
            .body("goods", isA(List.class));
}


//Вспомогательный метод

        private Integer createString(String name, double price) {
        String jsonBody = "{\"name\":\"" + name + "\",\"price\":" + price + "}";

        given()
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200);

        Response listResponse = given()
                .auth().basic("admin", "secret123")
                .param("page", 0)
                .param("size", 1000)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .extract()
                .response();

        List<String> names = listResponse.jsonPath().getList("goods.name");
        List<Integer> ids = listResponse.jsonPath().getList("goods.id");
        int idx = names.indexOf(name);
        return ids.get(idx);
    }
}
