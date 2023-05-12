package com.xin.bookbackend;

import com.xin.bookbackend.selenium.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        actions = new Actions(driver);
        /*driver.manage().window().maximize();*/
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
    void testLoginSignupLogoutAndRedirection() throws InterruptedException {
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
        TimeUnit.SECONDS.sleep(1);
        searchPage.search(query);
        TimeUnit.SECONDS.sleep(2);

        query = "springboot";
        searchPage.search(query);
        TimeUnit.SECONDS.sleep(2);

        WebElement firstImage = driver.findElement(By.xpath("(//img[@id='book-img'])[1]"));

        actions.clickAndHold(firstImage).build().perform();
        TimeUnit.SECONDS.sleep(2);
        actions.release(firstImage).build().perform();
        TimeUnit.SECONDS.sleep(2);



        TimeUnit.SECONDS.sleep(1);

        driver.get(baseUrl + "/account");

        String newFirstname = "_new";
        String newLastname = "_new";
        String newEmail = "xin.du1234@email.com";

        accountPage.edit(newFirstname, newLastname, newEmail);


        driver.get(baseUrl + "/password");
        String oldPassword = "12345";
        String newPassword = "1234";

        changePasswordPage.changePassword(oldPassword, newPassword);

        oldPassword = "1234";
        newPassword = "12345";
        changePasswordPage.changePassword(oldPassword, newPassword);
        TimeUnit.SECONDS.sleep(1);
    }

}
