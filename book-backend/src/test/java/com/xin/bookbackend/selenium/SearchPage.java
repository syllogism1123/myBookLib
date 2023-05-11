package com.xin.bookbackend.selenium;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    @FindBy(id = "search-book")
    private WebElement searchField;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void search(String query) {
        this.searchField.sendKeys(query + Keys.ENTER);

    }

}
