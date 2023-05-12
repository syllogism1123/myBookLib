package com.xin.bookbackend.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class AccountPage {
    @FindBy(id = "inputFirstName")

    private WebElement firstnameField;

    @FindBy(id = "inputLastName")
    private WebElement lastnameField;

    @FindBy(id = "inputEmail")
    private WebElement emailField;

    @FindBy(id = "submit-button")
    private WebElement saveButton;

    @FindBy(id = "cancel-button")
    private WebElement cancelButton;

    public AccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void edit(String firstname, String lastname, String email) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        this.firstnameField.sendKeys(firstname);
        TimeUnit.MILLISECONDS.sleep(500);
        this.lastnameField.sendKeys(lastname);
        TimeUnit.MILLISECONDS.sleep(500);
        this.emailField.sendKeys(email);
        TimeUnit.MILLISECONDS.sleep(500);
        this.cancelButton.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        this.saveButton.click();

    }
}
