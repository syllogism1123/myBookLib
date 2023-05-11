package com.xin.bookbackend.selenium;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement loginButton;

    @FindBy(id = "error-msg")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

    }


    public void login(String username, String password) throws InterruptedException {
        this.usernameField.sendKeys(username);
        TimeUnit.MILLISECONDS.sleep(500);
        this.passwordField.sendKeys(password);
        TimeUnit.MILLISECONDS.sleep(500);
        this.loginButton.click();
        TimeUnit.SECONDS.sleep(1);
    }

    public String errorMsg() {
        return errorMessage.getText();
    }

}

