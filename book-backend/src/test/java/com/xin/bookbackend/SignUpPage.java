package com.xin.bookbackend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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


    void signup(String username, String password, String firstname, String lastname, String email) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.firstnameField.sendKeys(firstname);
        this.lastnameField.sendKeys(lastname);
        this.emailField.sendKeys(email);
        this.signupButton.click();
    }


}
