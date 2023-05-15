package com.xin.bookbackend;

import com.xin.bookbackend.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookBackendApplicationTests {
    private static WebDriver driver;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
    private SearchPage searchPage;
    private AccountPage accountPage;
    private ChangePasswordPage changePasswordPage;
    private String baseUrl;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private static Actions actions;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        actions = new Actions(driver);
        /*driver.manage().window().maximize();*/
    /*    driver.manage().window().setSize(new Dimension(1680,1050));*/
    }

    @AfterAll
    static void close() {
        driver.quit();
    }


    @BeforeEach
    void init() {
        signUpPage = new SignUpPage(driver);
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
        accountPage = new AccountPage(driver);
        changePasswordPage = new ChangePasswordPage(driver);

        baseUrl = "http://localhost:3000";
        firstname = "Xin";
        lastname = "Du";
        username = "Xin_Du";
        password = "1234";
        email = "xin.du@email.com";
    }


    @Test
    void testFunctionality() throws InterruptedException {
        driver.get(baseUrl + "/login");
        loginPage.login(username, password); //user has not registered yet
        assertEquals("Invalid Username or Password!", loginPage.errorMsg());

        driver.get(baseUrl + "/signup");
        signUpPage.signup(username, password, firstname, lastname, email);


        driver.get(baseUrl + "/signup");

        String password = "5678";
        String firstname = "ABC";
        String lastname = "DEF";
        String email = "abc.def@email.com";
        signUpPage.signup(username, password, firstname, lastname, email);
        assertEquals("The username already exists!", signUpPage.errorMsg());

        driver.get(baseUrl + "/login");
        loginPage.login(username, this.password);

        String query = "java";
        /*TimeUnit.MILLISECONDS.sleep(1500);*/
        searchPage.search(query);
      /*  TimeUnit.MILLISECONDS.sleep(1500);*/

        query = "springboot";
        searchPage.search(query);
        /*TimeUnit.MILLISECONDS.sleep(1000);*/

        WebElement firstImage = driver.findElement(By.xpath("(//img[@id='book-img'])[1]"));

        actions.clickAndHold(firstImage).build().perform();
       /* TimeUnit.MILLISECONDS.sleep(1000);*/
        actions.release(firstImage).build().perform();
        /*TimeUnit.MILLISECONDS.sleep(1000);*/

        WebElement addButton = driver.findElement(By.id("add-button"));
        actions.click(addButton).build().perform();

       /* TimeUnit.MILLISECONDS.sleep(1000);*/


        WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(), 'Search')]"));
        actions.click(searchButton);
       /* TimeUnit.MILLISECONDS.sleep(1500);*/

        firstImage = driver.findElement(By.xpath("(//img[@id='book-img'])[1]"));
        firstImage.click();

       /* TimeUnit.MILLISECONDS.sleep(1000);*/
        addButton = driver.findElement(By.id("add-button"));
        addButton.click();

      /*  TimeUnit.MILLISECONDS.sleep(1500);*/

        WebElement removeButton = driver.findElement(By.id("remove-btn"));
        removeButton.click();
    /*    TimeUnit.MILLISECONDS.sleep(1500);*/


        driver.get(baseUrl + "/account");

        String newFirstname = "_new";
        String newLastname = "_new";
        String newEmail = "xin.du1234@email.com";

        accountPage.edit(newFirstname, newLastname, newEmail);
        /*TimeUnit.MILLISECONDS.sleep(1500);*/

        driver.get(baseUrl + "/password");
        String oldPassword = "12345";
        String newPassword = "1234";

        changePasswordPage.changePassword(oldPassword, newPassword);

        oldPassword = "1234";
        newPassword = "12345";
        changePasswordPage.changePassword(oldPassword, newPassword);
        /*TimeUnit.MILLISECONDS.sleep(1000);*/


        WebElement openSettingsButton = driver.findElement(By.id("settings"));
        openSettingsButton.click();

        WebElement logoutOption = driver.findElement(By.id("logout-btn"));
        logoutOption.click();

      /*  TimeUnit.MILLISECONDS.sleep(2000);*/
        driver.get(baseUrl + "/login");
        this.password = "12345";
        loginPage.login(username, this.password);
     /*   TimeUnit.MILLISECONDS.sleep(1500);*/

    }

}
