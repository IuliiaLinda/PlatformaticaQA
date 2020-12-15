import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.awt.*;

public class EditBoardEditTest extends BaseTest {
    @Test
    public void editBoard() throws InterruptedException {
        WebDriver driver = getDriver();
        driver.get("https://ref.eteam.work/index.php?action=login");
        //log in
        ProjectUtils.login(driver,"user3@tester.com", "SXGJhNd1aM");
        WebElement board = driver.findElement(By.xpath("//p[contains(text(),'Board')]"));
        ProjectUtils.click(driver, board);

        // create new values on board and save
        driver.findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        driver.findElement(By.xpath("//textarea[@id='text']")).sendKeys("my test");
        driver.findElement(By.xpath("//input[@id='int']")).sendKeys(String.valueOf(20));
        driver.findElement(By.xpath("//input[@id='decimal']")).sendKeys(String.valueOf(22.5));

        /*WebElement calendar1 = driver.findElement(By.xpath("//input[@id='date']"));
        calendar1.clear();
        driver.findElement(By.xpath("//input[@id='date']")).sendKeys("15/12/2020");
        WebElement calendar2 = driver.findElement(By.xpath("//input[@id='datetime']"));
        calendar2.clear();
        driver.findElement(By.xpath("//input[@id='datetime']")).sendKeys("20/12/2020 12:40:00");*/
        //driver.findElement(By.xpath("//input[@id='datetime']")).sendKeys(Keys.ESCAPE);
        //Thread.sleep(2000);
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));
        Thread.sleep(3000);

        //edit existing values
        driver.findElement(By.xpath("//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text()='dashboard']")).click();
        driver.findElement(By.xpath("//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']//i[text()='list']")).click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/button[1]")).click();
        //driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/ul[1]/li[2]/a[1]")).click();
        ProjectUtils.click(driver, driver.findElement(By.xpath("//tbody/tr[1]/td[10]/div[1]/ul[1]/li[2]/a[1]")));
        driver.findElement(By.xpath("//div[@class='filter-option-inner-inner'][text()='Pending']")).click();
        driver.findElement(By.xpath("//span[contains(text(),'On track')]")).click();
        WebElement text = driver.findElement(By.xpath("//textarea[@id='text']"));
        text.clear();
        driver.findElement(By.xpath("//textarea[@id='text']")).sendKeys("my test changed");
        Thread.sleep(2000);
        ProjectUtils.click(driver, driver.findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")));

        // validate edition
        String result = driver.findElement(By.xpath("//tbody/tr[1]/td[3]/a[1]/div[1]")).getText();
        Assert.assertEquals(result,"my test changed");









    }
}
