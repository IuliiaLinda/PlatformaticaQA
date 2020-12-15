import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EntityDefaultTest extends BaseTest {

   private void login(WebDriver driver){

    WebDriverWait wait = new WebDriverWait(getDriver(), 1);
    driver.get("https://ref.eteam.work");

    ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
  }

  private void accessToEntityDefaultScreen(WebDriver driver) throws InterruptedException {

    WebElement tab = driver.findElement(By.xpath("//a[@href='#menu-list-parent']/i"));
    tab.click();

    WebElement entityOpenList= driver.findElement(By.xpath("//div[@id='menu-list-parent']"));
    entityOpenList.click();

    Thread.sleep(300);
    WebElement entityTab = driver.findElement(By.xpath("//a[@href='index.php?action=action_list&entity_id=7']"));

    Thread.sleep(100);
    entityTab.click();
  }
  private void createDefaultEntity(WebDriver driver){

    WebElement newRecord = driver.findElement(By.xpath("//i[text()='create_new_folder']"));
    newRecord.click();
  }

  public void entityCreation() throws InterruptedException {

    WebDriver driver = getDriver();
    login(driver);
    accessToEntityDefaultScreen(driver);
    createDefaultEntity(driver);
  }



  public boolean isInt(String str){

    try
    {
      Integer.parseInt(str);
      return  true;
    }
    catch (NumberFormatException e)
    {
      return false;
    }
  }

  private boolean isDouble(String str) {

    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  public boolean isValidDateFormat(String dateStr) {

    DateFormat sdf = new SimpleDateFormat(dateStr);
    sdf.setLenient(false);
    try {
      sdf.parse(dateStr);
    } catch (ParseException e) {
      return false;
    }
    return true;
  }

  private boolean isTitleFound(String title) {

    List<WebElement> titlesWe = getDriver().findElements(By.xpath("//tr/td[2]"));
    for (WebElement we : titlesWe) {
      if (we.getText().equals(title)) {
        return true;
      }
    }
    return false;
  }

  private void clickOnLastElementInTable() {

    List<WebElement> titlesWe = getDriver().findElements(By.xpath("//tr/td[2]"));
    titlesWe.get(titlesWe.size()-1).click();
  }

  private void goToLastPage(WebDriver driver){

    WebDriverWait wait = new WebDriverWait(getDriver(), 1);
    // boolean titleFound = false;
    int rowsPerPage;
    int numOfAllRecords = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By
            .xpath("//span[@class='pagination-info']"))).getText().split(" ")[5]);
    WebElement rowsPerPageWe = driver.findElement(By.cssSelector("span.page-size"));
    if (rowsPerPageWe.isDisplayed()) {
      rowsPerPage = Integer.parseInt(rowsPerPageWe.getText());
    } else {
      rowsPerPage = 26;
    }
    if (numOfAllRecords > rowsPerPage) {
      boolean firstRound = true;
      while (firstRound) {
        if (getDriver().findElements(By.xpath("//a[@href='javascript:void(0)']")).size()<=3) {
          break;
        }
        List<WebElement> pagination = driver.findElements(By.cssSelector("a.page-link"));
        pagination.get(pagination.size() -2).click();
        WebElement paginationLastIndexWe = driver.findElements(By.cssSelector("a.page-link")).get(pagination.size() -2);
        boolean paginationLastIndexActive =
                paginationLastIndexWe.getCssValue("color").equals("rgba(255, 255, 255, 1)");
        if ( paginationLastIndexActive) {
          firstRound = false;
        }
      }
    }

    clickOnLastElementInTable();
  }

  @Test
  public void validateEntityDefaultValuesCreation() throws InterruptedException {

    WebDriver driver = getDriver();
    entityCreation();

    Assert.assertEquals(driver.findElement(By.xpath("//div[@id='_field_container-string']//span//input")).getAttribute("value")
            ,"DEFAULT STRING VALUE");

    String attributeTextValueToCompare= driver.findElement(By.xpath("//div[@id='_field_container-text']//p//span")).getText();
    Assert.assertEquals(attributeTextValueToCompare,"DEFAULT TEXT VALUE");

    boolean isInt = isInt(driver.findElement(By.xpath("//div[@id='_field_container-int']//span//input")).getAttribute("value"));
    Assert.assertTrue(isInt);

    boolean isDouble = isDouble(driver.findElement(By.xpath("//div[@id='_field_container-int']//span//input")).getAttribute("value"));
    Assert.assertTrue(isDouble);


    boolean isValidDate = isValidDateFormat(driver.findElement(By.xpath("//div[@id='_field_container-date']//input")).getAttribute("value"));
    Assert.assertTrue(isValidDate);

    boolean isValidDateTime = isValidDateFormat(driver.findElement(By.xpath("//div[@id='_field_container-datetime']//input")).getAttribute("value"));
    Assert.assertTrue(isValidDateTime);


    String userName= driver.findElement(By.xpath("//button[@data-id='user']")).getAttribute("title").toUpperCase();
    String userNameExpected= driver.findElement(By.xpath("//button[@data-id='user']//div//div//div[@class='filter-option-inner-inner']")).getText().toUpperCase();
    Assert.assertEquals(userName,userNameExpected);

    WebElement saveBtn = driver.findElement(By.xpath("//button[.='Save']"));

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", saveBtn);
    Assert.assertTrue(saveBtn.isDisplayed());

    driver.findElement(By.xpath("//table[@id='table-11']//button[@data-table_id='11']")).click();


    Assert.assertEquals(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[3]")).getText()
            ,"Default String");

    Assert.assertEquals(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[4]")).getText()
            ,"Default text");

    Assert.assertTrue(isInt(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[5]")).getText()));


    Assert.assertTrue(isDouble(driver.findElement(By.xpath("//table[@id='table-11']//tbody//tr[2]//td[6]")).getText()));

    WebElement saveButton = driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
    ProjectUtils.click(driver, saveButton);

    Thread.sleep(300);
  }

 @Test
 public void validateEntityDefaultValuesRecord() throws InterruptedException {

   WebDriver driver = getDriver();
   login(driver);
   accessToEntityDefaultScreen(driver);
   goToLastPage(driver);

   List<WebElement> objectValidation = driver.findElements(By.xpath("//span[@class='pa-view-field']"));

   Assert.assertEquals((objectValidation.get(0).getText()),"DEFAULT STRING VALUE");

   Assert.assertEquals((objectValidation.get(1).getText()),"DEFAULT TEXT VALUE");

   boolean isInt = isInt(objectValidation.get(2).getText());
   Assert.assertTrue(isInt);

   boolean isDouble = isDouble(objectValidation.get(3).getText());
   Assert.assertTrue(isDouble);


   boolean isValidDate = isValidDateFormat(objectValidation.get(3).getText());
   Assert.assertTrue(isValidDate);

   boolean isValidDateTime = isValidDateFormat(objectValidation.get(5).getText());
   Assert.assertTrue(isValidDateTime);


   String userName= "User 1 Demo".toUpperCase();
   String userNameExpected= driver.findElement(By.xpath("//label[text()='User']/following-sibling::p")).getText().toUpperCase();
   Assert.assertEquals(userName,userNameExpected);


   List<WebElement> allCells = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']//tr//td"));


   Assert.assertEquals(allCells.get(1).getText()
           ,"Default String");

   Assert.assertEquals(allCells.get(2).getText()
           ,"Default text");

   Assert.assertTrue(isInt(allCells.get(3).getText()));

   Assert.assertTrue(isDouble(allCells.get(4).getText()));
 }

}
