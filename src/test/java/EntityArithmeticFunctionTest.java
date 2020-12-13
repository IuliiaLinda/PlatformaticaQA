import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.util.List;

public class EntityArithmeticFunctionTest extends BaseTest {

    public void preconditions(WebDriver driver) {

        driver.get("https://ref.eteam.work/");

        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");

        WebElement tabArithmeticFunction =
                driver.findElement(By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Arithmetic Function ']"));
        tabArithmeticFunction.click();
    }

    public void recordDelete(WebDriver driver) throws InterruptedException {

        WebElement tabArithmeticFunction =
                driver.findElement(By.xpath("//div[@id = 'menu-list-parent']//p[text() = ' Arithmetic Function ']"));
        tabArithmeticFunction.click();

        Thread.sleep(3000);

        List <WebElement> allRecords = driver.findElements(By.xpath("//tr[@data-index]"));
        int length = allRecords.size();

        WebElement lastRecord = driver.findElement(By.xpath("//tr[@data-index][" + length +"]//td[8]"));
        lastRecord.click();

        WebElement deleteRecord = driver.findElement(By.xpath("//ul[@x-placement='bottom-end']//a[text()='delete']"));
        deleteRecord.click();
    }

    @Test
    public void tc001() throws InterruptedException {

        WebDriver driver = getDriver();
        preconditions(driver);

        WebElement createNewRecord = driver.findElement(By.xpath("//div[@class='card-icon']"));
        createNewRecord.click();

        final int f1 = 12;
        final int f2 = 6;

        WebElement inputF1 = driver.findElement(By.id("f1"));
        inputF1.sendKeys(String.valueOf(f1));

        WebElement inputF2 = driver.findElement(By.id("f2"));
        inputF2.sendKeys(String.valueOf(f2));
        Thread.sleep(1000);

        WebElement sum = driver.findElement(By.id("sum"));
        Assert.assertTrue(String.valueOf(f1 + f2).equals(sum.getAttribute("value")));

        WebElement sub = driver.findElement(By.id("sub"));
        Assert.assertTrue(String.valueOf(f1 - f2).equals(sub.getAttribute("value")));

        WebElement mul = driver.findElement(By.id("mul"));
        Assert.assertTrue(String.valueOf(f1 * f2).equals(mul.getAttribute("value")));

        WebElement div = driver.findElement(By.id("div"));
        Assert.assertTrue(String.valueOf(f1 / f2).equals(div.getAttribute("value")));

        WebElement buttonSave = driver.findElement(By.id("pa-entity-form-save-btn"));
        buttonSave.click();
    }

    @Test
    public void tc002 () throws InterruptedException {

        WebDriver driver = getDriver();
        preconditions(driver);

        List <WebElement> allRecords = driver.findElements(By.xpath("//tr[@data-index]"));
        int length = allRecords.size();

        WebElement lastRecord = driver.findElement(By.xpath("//tr[@data-index][" + length +"]//td[8]"));
        lastRecord.click();

        WebElement viewMode = driver.findElement(By.xpath("//ul[@x-placement='bottom-end']//a[text()='view']"));
        viewMode.click();

        final int f1 = 12;
        final int f2 = 6;

        WebElement sum = driver.findElement(By.xpath("//div[3]//span"));
        Assert.assertTrue(String.valueOf(f1 + f2).equals(sum.getText()));

        WebElement sub = driver.findElement(By.xpath("//div[4]//span"));
        Assert.assertTrue(String.valueOf(f1 - f2).equals(sub.getText()));

        WebElement mul = driver.findElement(By.xpath("//div[5]//span"));
        Assert.assertTrue(String.valueOf(f1 * f2).equals(mul.getText()));

        WebElement div = driver.findElement(By.xpath("//div[6]//span"));
        Assert.assertTrue(String.valueOf(f1 / f2).equals(div.getText()));

        //recordDelete(driver);

        Thread.sleep(3000);
    }
}
