package com.xin.bookbackend.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class ChangePasswordPage {
    @FindBy(id = "oldPassword")
    private WebElement oldPasswordField;

    @FindBy(id = "newPassword")
    private WebElement newPasswordField;

    @FindBy(id = "save-button")
    private WebElement saveButton;

    public ChangePasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void changePassword(String oldPassword, String newPassword) throws InterruptedException {
        this.oldPasswordField.sendKeys(oldPassword);
        TimeUnit.MILLISECONDS.sleep(1000);
        this.newPasswordField.sendKeys(newPassword);
        TimeUnit.MILLISECONDS.sleep(1000);
        this.saveButton.click();
        TimeUnit.MILLISECONDS.sleep(1500);
    }
}
