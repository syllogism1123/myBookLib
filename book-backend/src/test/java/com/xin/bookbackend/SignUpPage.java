package com.xin.bookbackend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignUpPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "inputFirstName")

    private WebElement firstnameField;

    @FindBy(id = "inputLastName")
    private WebElement lastnameField;

    @FindBy(id = "inputEmail")
    private WebElement emailField;

    @FindBy(id = "submit-button")
    private WebElement signupButton;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    void signup(String username, String password, String firstname, String lastname, String email) throws InterruptedException {
        this.usernameField.sendKeys(username);
        TimeUnit.MILLISECONDS.sleep(500);
        this.passwordField.sendKeys(password);
        TimeUnit.MILLISECONDS.sleep(500);
        this.firstnameField.sendKeys(firstname);
        TimeUnit.MILLISECONDS.sleep(500);
        this.lastnameField.sendKeys(lastname);
        TimeUnit.MILLISECONDS.sleep(500);
        this.emailField.sendKeys(email);
        TimeUnit.MILLISECONDS.sleep(500);
        this.signupButton.click();
        TimeUnit.MILLISECONDS.sleep(1500);
    }


}
