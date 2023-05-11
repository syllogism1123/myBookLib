package com.xin.bookbackend;


import com.xin.bookbackend.selenium.AccountPage;
import com.xin.bookbackend.selenium.LoginPage;
import com.xin.bookbackend.selenium.SearchPage;
import com.xin.bookbackend.selenium.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    private String baseUrl;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;


    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
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

        baseUrl = "http://localhost:3000";
        firstname = "Xin";
        lastname = "Du";
        username = "xin_du";
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
        assertEquals("The username already exists!", signUpPage.errorMsg());

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);
        driver.get(baseUrl + "/search");

        String query = "java";
        searchPage.search(query);
        TimeUnit.SECONDS.sleep(2);
        query = "springboot";
        searchPage.search(query);
        TimeUnit.SECONDS.sleep(2);

        driver.get(baseUrl + "/account");

        String newFirstname = "_new";
        String newLastname = "_new";
        String newEmail = "xin.du1234@email.com";

        accountPage.edit(newFirstname,newLastname,newEmail);
        TimeUnit.SECONDS.sleep(2);
    }


}
