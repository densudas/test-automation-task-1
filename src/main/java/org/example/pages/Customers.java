package org.example.pages;

import org.example.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.Utils.waitForElementToBeDisplayed;

public class Customers {

  private static final String URL = "file:///"
          + new File("src/test/resources/index.html")
          .getAbsolutePath().replace("\\","/");

  @FindBy(css = "[ng-app='searchApp'] input#search-input")
  WebElement searchInput;

  @FindBy(css = "[ng-app='searchApp'] table")
  WebElement table;

  @FindBy(css = "[ng-app='searchApp'] input#match-case")
  WebElement matchCaseCheckbox;

  @FindBy(css = "[ng-app='searchApp'] select#search-column")
  WebElement filterTypeDropdown;

  public Customers() {
    PageFactory.initElements(DriverFactory.getDriver(), this);
  }

  public static Customers navigateToPage() {
    DriverFactory.getDriver().get(URL);
    waitForElementToBeDisplayed(By.cssSelector("[ng-app='searchApp']"));
    return new Customers();
  }

  public Customers setSearchFilter(final String text) throws InterruptedException {
    searchInput.sendKeys(text);
    return this;
  }

  public List<Map<String, String>> getCustomers() throws InterruptedException {
    waitForElementToBeDisplayed(table);
    Thread.sleep(500);
    List<Map<String, String>> result = new ArrayList<>();
    List<String> header =  table.findElements(By.cssSelector("thead tr th")).stream().map(WebElement::getText).toList();
    List<WebElement> rowElements = table.findElements(By.cssSelector("tbody tr"));
    for (WebElement rowElement : rowElements){
      Map<String, String> row = new HashMap<>();
      for (int i = 0; i < header.size(); i++) {
       WebElement rowElementCell = rowElement.findElements(By.cssSelector("td")).get(i);
       if (rowElementCell.findElements(By.cssSelector("a")).size() == 0) {
         row.put(header.get(i),rowElementCell.getText());
       } else {
         row.put(header.get(i),rowElementCell.findElement(By.cssSelector("a")).getText());
       }
      }
      result.add(row);
    }
    return result;
  }

  public Customers setFilterType(final String filterType) {
    Select select = new Select(filterTypeDropdown);
    select.selectByValue(filterType);
    return this;
  }

  public Customers setCaseSensitiveCheckbox() {
    matchCaseCheckbox.click();
    return this;
  }

  public Customers clearFilters() throws InterruptedException {
    Thread.sleep(500);
    DriverFactory.getDriver().findElement(By.cssSelector("[ng-app='searchApp'] button#clear-button")).click();
    return this;
  }

  public int getNumberOfCustomers() throws InterruptedException {
    Thread.sleep(500);
    return table.findElements(By.cssSelector("tbody tr")).size();
  }
}
