import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

public class EntityFieldsOpsTest extends BaseTest {

    @Test
    public void newRecord() {
        WebDriver driver = getDriver();

        //driver get
        driver.get("https://ref.eteam.work");

        ProjectUtils.login(driver,"user1@tester.com", "ah1QNmgkEO");

        WebElement tab = driver.findElement(By.xpath("//p[contains(text(),'Fields Ops')]"));
        tab.click();

        WebElement createNewFolder = driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        createNewFolder.click();

        WebElement checkbox = driver.findElement(By.xpath("//div[@class='d-flex']//span"));
        checkbox.click();

        Select dropdownMenu = new Select(driver.findElement(By.xpath("//select[@name='entity_form_data[dropdown]']")));
        dropdownMenu.selectByValue("Done");

        Select dropdownReference = new Select(driver.findElement(By.xpath("//select[@name='entity_form_data[reference]']")));
        dropdownReference.selectByValue("1");

        WebElement checkBoxFirst = driver.findElement(By.xpath("//label[contains(text(),'First reference')]"));
        checkBoxFirst.click();

        Select dropdownWithReference = new Select(driver.findElement(By.id("reference_with_filter")));
        dropdownWithReference.selectByValue("3");

        WebElement embeddedBtn = driver.findElement(By.xpath("//button[contains(text(),'+')]"));
        embeddedBtn.click();

        WebElement saveBtn = driver.findElement(By.id("pa-entity-form-save-btn"));
        ProjectUtils.click(driver,saveBtn);

        WebElement drpDown = driver.findElement(By.xpath("//div[contains(text(),'Done')]"));
        drpDown.isDisplayed();

        try {
            WebElement pageTitle = driver.findElement(By.className("card-title"));
            Assert.assertEquals(pageTitle.getText(), "Fields Ops",
                    "Redirection works incorrectly");
        } catch (TimeoutException e) {
            Assert.fail("Redirection works incorrectly");
        }
    }
}
