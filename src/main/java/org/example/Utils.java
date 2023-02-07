package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.function.Function;

public class Utils {

  public static void waitForElementToBeDisplayed(By by) {
    waitUntil(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public static void waitForElementToBeDisplayed(WebElement element) {
    waitUntil(ExpectedConditions.visibilityOf(element));
  }

  public static void waitForInvisibilityOfElementLocated(By by) {
    waitUntil(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  public static <V> V waitUntil(Function<? super WebDriver, V> isTrue) {
    FluentWait<WebDriver> wait = new FluentWait<>(DriverFactory.getDriver());
    wait.withTimeout(Duration.ofSeconds(20));
    wait.pollingEvery(Duration.ofSeconds(1));
    return wait.until(isTrue);
  }
}
