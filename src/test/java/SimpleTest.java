import org.example.DriverFactory;
import org.example.pages.Customers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

public class SimpleTest {

  @Test(description = "Search by email")
  public void test1() throws InterruptedException {
    String filter = "office@alabaster.com";
    List<Map<String,String>> customers= Customers
        .navigateToPage()
        .setFilterType("Email")
        .setSearchFilter(filter)
        .getCustomers();
    Assert.assertEquals(customers.size(), 1, "Incorrect number of customers found");
    Assert.assertEquals(customers.get(0).get("Email"), filter, "Found email is incorrect");
  }

  @Test(description = "Search by name. Case sensitive", priority = 1)
  public void test2() throws InterruptedException {
    String filter = "Postimex";
    List<Map<String,String>> customers= Customers
            .navigateToPage()
            .setCaseSensitiveCheckbox()
            .setSearchFilter(filter)
            .getCustomers();
    Assert.assertEquals(customers.size(), 1, "Incorrect number of customers found");
    Assert.assertEquals(customers.get(0).get("Name"), filter, "Found email is incorrect");
  }

  @Test(description = "Search by City. Case sensitive. Negative", priority = 2)
  public void test3() throws InterruptedException {
    String filter = "melbourne";
    List<Map<String,String>> customers= Customers
            .navigateToPage()
            .setFilterType("City")
            .setCaseSensitiveCheckbox()
            .setSearchFilter(filter)
            .getCustomers();
    Assert.assertEquals(customers.size(), 0, "Incorrect number of customers found");
  }


  @Test(description = "Search by nonexistent id", priority = 3)
  public void test4() throws InterruptedException {
    String filter = "4";
    List<Map<String,String>> customers= Customers
            .navigateToPage()
            .setFilterType("Id")
            .setSearchFilter(filter)
            .getCustomers();
    Assert.assertEquals(customers.size(), 0, "Incorrect number of customers found");
  }

  @Test(description = "Clear filters", priority = 3)
  public void test6() throws InterruptedException {
    String filter = "Unexistent value";
    Customers customersPage = Customers
            .navigateToPage()
            .setSearchFilter(filter);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(customersPage.getNumberOfCustomers(), 0, "Incorrect number of customers found");
    customersPage.clearFilters();
    softAssert.assertEquals(customersPage.getNumberOfCustomers(), 3, "Incorrect number of customers found");
    softAssert.assertAll();
  }

  @AfterSuite
  public void tearDown() {
    DriverFactory.closeAllDrivers();
  }
}
