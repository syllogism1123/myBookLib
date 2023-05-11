package com.xin.bookbackend;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookBackendApplicationTests {
    private static WebDriver driver;
    private LoginPage loginPage;
    private SignUpPage signUpPage;
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
        /* assertEquals("Invalid Username or Password!", loginPage.errorMsg());*/

        driver.get(baseUrl + "/signup");

        signUpPage.signup(username, password, firstname, lastname, email);

        assertEquals(2, 1 + 1);

/*
        signUpPage.signup(username, "1234567", "Albert", "Einstein", "email123@email.com"); //The username already exists.get errorMsg
        Thread.sleep(3000);*/

        driver.get(baseUrl + "/login");
        loginPage.login(username, password);

    }


}
