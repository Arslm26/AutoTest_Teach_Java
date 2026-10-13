package testRun;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideTestPart2 {

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:8080");
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        Configuration.timeout = 30000;
    }

    @AfterEach
    void tearDown() {
        WebDriverRunner.closeWebDriver();
    }

    @Test
    void test1() {

        // Переход в админку
        $("a.btn-outline[href='/admin']").click();

        // Ввод логина и пароля
        $("#username").shouldBe(visible).setValue("admin");
        $("#password").shouldBe(visible).setValue("secret123");
        $("button.primary").click();


        // Ввод данных товара
        $("#n-name").shouldBe(visible).setValue("Товар2");
        $("#n-price").shouldBe(visible).setValue("60");
        $("#add-btn").click();

        // Возврат на витрину
        $("a[href='/']").click();

        // Проверка карточки товара
        $(".product-card[data-name='Товар2'][data-price='60']").shouldBe(visible);

    }

    @Test
    void test2() {
        // Клик по кнопке добавления (используем сложный селектор как в оригинале)
        $("button[data-action='add-to-cart'][data-name='Товар2']").click();

        // Открытие корзины
        $("#open-cart-btn").click();

        // Ждем появления модального окна корзины
        $("#cartModal").shouldBe(visible);

        // Проверка наличия товара внутри корзины
        $(".cart-item").shouldHave(text("Товар2"));
    }

    @Test
    void test3() {

        // Переход в админку
        $("a.btn-outline[href='/admin']").click();

        // Попытка входа с неверными данными
        $("#username").setValue("Noadmin");
        $("#password").setValue("secret1233");
        $("button.primary").click();

        // Ожидаем сообщение об ошибке
        $(".alert.alert-danger").shouldBe(visible)
                .shouldHave(text("Неверные учетные данные пользователя"));
    }

    @Test
    void test4() {
        // Добавляем два товара
        $("button[data-action='add-to-cart'][data-name='Товар1']").click();
        $("button[data-action='add-to-cart'][data-name='Стакан']").click();

        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cartModal").shouldBe(visible);
        $(".cart-item").should(not(empty));

        // Обновление страницы
        Selenide.refresh();

        // Ждем загрузки страницы
        $("body").shouldBe(exist);

        // Пытаемся открыть корзину снова
        $("#open-cart-btn").click();
        $("#cartModal").shouldBe(visible);

        // Ассерт: корзина должна быть ПУСТОЙ после refresh

        $$(".cart-item").shouldHave(size(0));
    }

    @Test
    void test5() {

        //Добавим Товар "Товар1 7 раз
        for (int i = 0; i < 7; i++) {
            $("button[data-action='add-to-cart'][data-name='Товар1']").click();

            Selenide.sleep(200);
        }

        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cartModal").shouldBe(visible);

        //Клик на оформление заказа
        $("#makeOrder").click();

        //JS Alert
        try {
            String alertText = Selenide.switchTo().alert().getText();
            System.out.println("Заказ принят в обработку: " + alertText);
        } catch (Exception e) {
            throw new AssertionError("JS Alert не появился.", e);
        }
    }
}



