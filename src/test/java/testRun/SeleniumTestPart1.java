package testRun;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTestPart1 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("http://localhost:8080");
    }


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void TestPart1() {
        // Переход в админку
        WebElement adminLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-outline[href='/admin']")));
        adminLink.click();

        // Ввод логина и пароля
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#username")));
        usernameField.sendKeys("admin");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#password")));
        passwordField.sendKeys("secret123");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.primary")));
        loginBtn.click();

        // Ввод данных товара
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#n-name")));
        nameField.sendKeys("Товар1");
        WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#n-price")));
        priceField.sendKeys("55");
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#add-btn")));
        addBtn.click();

        // Возврат на витрину
        WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/']")));
        homeLink.click();

        // Ожидание появления карточки товара
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.product-card[data-name='Товар1'][data-price='55']")));

        String name = card.getAttribute("data-name");
        String price = card.getAttribute("data-price");

        assertAll(
                () -> assertEquals("Товар1", name),
                () -> assertEquals("55", price)
        );
    }

    @Test
    void TestPart2() {
        // Нажатие на кнопку «В корзину»
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-action='add-to-cart'][data-name='Товар1']")));
        addToCartBtn.click();

        // Нажатие на кнопку открытия корзины
        WebElement openCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#open-cart-btn")));
        openCartBtn.click();

        // Проверка наличия товара в корзине
        WebElement cartItemName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-card[data-price='55'] h4")));

        assertTrue(cartItemName.getText().contains("Товар1"), "Не найдено название 'Товар1' в корзине");
    }

    @Test
    void TestPart3() {
        // Переход в админку
        WebElement adminLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-outline[href='/admin']")));
        adminLink.click();

        // Попытка входа с неверными данными
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#username")));
        usernameField.sendKeys("Noadmin");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#password")));
        passwordField.sendKeys("secret1233");
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.primary")));
        loginBtn.click();

        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.alert.alert-danger")));
        assertTrue(errorMsg.getText().contains("Неверные учетные данные пользователя"), "Нет сообщения об ошибке");
    }

    @Test
    void TestPart4() {
        // Нажатие на кнопку «В корзину» на Товар1
        WebElement addToCartBtn0 = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-action='add-to-cart'][data-name='Товар1']")));
        addToCartBtn0.click();

        // Нажатие на кнопку «В корзину» на Стакан
        WebElement addToCartBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-action='add-to-cart'][data-name='Стакан']")));
        addToCartBtn1.click();

        WebElement openCartBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#open-cart-btn")));
        openCartBtn1.click();

        // Обновление страницы
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Ищем кнопку открытия корзины заново (после refresh старые элементы невалидны)
        WebElement openCartBtnAfterRefresh = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("#open-cart-btn")));
        openCartBtnAfterRefresh.click();
        System.out.println("Кнопка открытия корзины нажата после refresh.");

        // Ждём повторного появления модального окна корзины
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartModal")));
        System.out.println("Модальное окно корзины открыто после refresh.");

        // Проверка товаров в корзине
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart-item"));

        System.out.println("Найдено элементов .cart-item: " + cartItems.size());
        cartItems.forEach(item -> System.out.println("- Текст элемента: " + item.getText()));

        // Ассерт: корзина должна быть ПУСТОЙ после refresh
        assertEquals(0, cartItems.size(),
                "Корзина должна быть пустой после обновления страницы. Найдено товаров: " + cartItems.size());

        System.out.println("ТЕСТ УСПЕШНО ПРОЙДЕН: корзина корректно пуста после refresh.");
    }
}
