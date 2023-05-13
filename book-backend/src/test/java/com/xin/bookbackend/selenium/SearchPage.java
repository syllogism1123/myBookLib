package com.xin.bookbackend.selenium;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SearchPage {
    @FindBy(id = "search-book")
    private WebElement searchField;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void search(String query) throws InterruptedException {
        this.searchField.sendKeys(query + Keys.ENTER);
    }

}
