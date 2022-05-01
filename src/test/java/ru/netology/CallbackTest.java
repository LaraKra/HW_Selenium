package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://0.0.0.0:7777/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;

    }

    @Test
    public void shouldSendCorrectForm() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Анна Игнатьева");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("+79993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldSendCorrectFormWithDoubleName() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Анна-Мария Петрова");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("+79993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldDontSendFormWithMiddleName() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Мария Ивановна Петрова");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("+79993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldDontSendCorrectFormWithoutNameAndSurname() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("+79993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }
    @Test
    public void shouldDontSendCorrectFormWithoutTelNumb() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Мария Авдеева");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }
    @Test
    public void shouldDontSendFormWithWrongName() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Olga Ivanova");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("+79993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }
    @Test
    public void shouldDontSendFormWithWrongTel() {
        driver.findElement(By.cssSelector("[type = 'text']")).sendKeys("Мария Авдеева");
        driver.findElement(By.cssSelector("[type = 'tel']")).sendKeys("89993321232");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }
}