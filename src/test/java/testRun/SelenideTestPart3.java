package testRun;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebElementCondition;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;




public class SelenideTestPart3 {


    private static String generatedProductId;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "secret123";


    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:8080/");
    }

    @AfterEach
    void tearDown() {
        if (generatedProductId != null) {
            try {
                RestAssured.given()
                        .baseUri(BASE_URL)
                        .delete("/api/products/" + generatedProductId)
                        .then()
                        .statusCode(200);
                System.out.println("Тестовый товар удален. ID: " + generatedProductId);
            } catch (Exception e) {
                System.err.println("Не удалось удалить тестовый товар (возможно, он уже удален): " + e.getMessage());
            } finally {
                generatedProductId = null;
            }
        }

        Selenide.closeWebDriver();
    }

    @Test
    void selenideTestPart1() {
        //Добавим Товар "СтаканЭ 3 раз не превышая сумму 300 рублей
        for (int i = 0; i < 3; i++) {
            $("button[data-action='add-to-cart'][data-name='Товар1']").click();

            Selenide.sleep(200);
        }
        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cartModal").shouldBe(visible);

        // Получаем общую стоимость из элемента корзины
        var totalPriceI = $("#total-price");
        totalPriceI.shouldBe(visible);

        // Извлекаем число из текста
        String rawText = totalPriceI.getText();
        int totalPrice = Integer.parseInt(rawText.replaceAll("\\D+", ""));

        // общая стоимость не должна превышать 300 рублей
        assertTrue(totalPrice <= 300,
                "Общая стоимость превышает 300 рублей! Найдено: " + totalPrice + ", текст элемента: " + rawText);

        System.out.println("Проверка пройдена: общая стоимость = " + totalPrice + " руб. (≤ 300)");

        // Клик на оформление заказа
        $("#makeOrder").shouldBe(clickable).click();
        Selenide.sleep(2000);

        // Уведомление "Зака принят в обработку!"
        $("#toast-container .toast")
                .shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку!"));
    }

    @Test
    void selenideTestPart2() {
        // Добавление товаров
        $("button[data-action='add-to-cart'][data-name='Товар1']").shouldBe(clickable).click();
        $("button[data-action='add-to-cart'][data-name='Стакан']").shouldBe(clickable).click();

        // Переход в корзину (селектор подставить по верстке)
        $("#open-cart-btn").click();
        $("#cartModal").shouldBe(visible);

        // Получение цен и расчет
        int price1 = Integer.parseInt($("#cart-item-71 div[style*='width:70px']").getText().replaceAll("\\D+", ""));
        int price2 = Integer.parseInt($("#cart-item-68 div[style*='width:70px']").getText().replaceAll("\\D+", ""));
        int expectedTotal = 55 + 25;

        // Получение общей суммы
        int actualTotal = Integer.parseInt($("#total-price").getText().replaceAll("\\D+", ""));

        assertEquals(expectedTotal, actualTotal, "Общая цена в корзине рассчитана верно");
        System.out.println("Сумма в корзине рассчитана правильно: " + actualTotal + " ₽");
    }

    @Test
    void selenideTestPart3() {

        // Переход в админку
        $("a.btn-outline[href='/admin']").click();

        // Ввод логина и пароля
        $("#username").shouldBe(visible).setValue("admin");
        $("#password").shouldBe(visible).setValue("secret123");
        $("button.primary").click();

        //Создание товара Товар3, Цена 10 руб. и нажать на кнопку Создать
        $("#n-name").setValue("Товар3");
        $("#n-price").setValue("10");
        $("#add-btn").click();
        Selenide.sleep(2000);

        // Проверка, что товар успешно добавлен
        $("#toast-container .toast").shouldHave(text("Товар успешно добавлен!"));
        System.out.println("Тест пройден: товар успешно добавлен");
    }

    @Test
    void selenideTestPart4() {

        // Переход в админку
        $("a.btn-outline[href='/admin']").click();

        // Ввод логина и пароля
        $("#username").shouldBe(visible).setValue("admin");
        $("#password").shouldBe(visible).setValue("secret123");
        $("button.primary").click();

        // Смена Товар2 на Товар4
        $("#nm-75").setValue("Товар4");
        $$("[data-action='update'][data-id='75']").first().click();
        Selenide.sleep(3000);

        //Возврат на главную страницу
        $("a[href='/']").shouldBe(clickable).click();

        // Убеждаемся, что Товар4 есть
        $("#card-75").shouldHave(text("Товар4"));
        System.out.println("Проверка пройдена: в карточке товара с data-id='75' установлено название «Товар4»");

    }
}
