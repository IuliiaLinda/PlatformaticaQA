import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EntityExportTest extends BaseTest {
    public String exportString = "My String";
    public String exportText = "New text with 1234";
    public String exportInt = "1234";
    public String exportDec = "23.34";

    SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    public String Data = data.format(new Date());

    SimpleDateFormat dataTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public String DataTime = dataTime.format(new Date());

    public String User = "User 1";
    public String tablString = "abc";
    public String tableTex = "abc123";
    public String tableInt = "1234";
    public String tableDec = "34.56";

    private void setUp(WebDriver driver) {
        driver.get("https://ref.eteam.work");
        ProjectUtils.login(driver, "user1@tester.com", "ah1QNmgkEO");
    }

    private void createFieldForm(WebDriver driver){

        driver.findElement(By.xpath("//div[@id='menu-list-parent']//p[contains(text(), 'Export')]")).click();

        driver.findElement(By.xpath("//div/i")).click();

        driver.findElement(By.id("string")).click();
        driver.findElement(By.id("string")).clear();
        driver.findElement(By.id("string")).sendKeys(exportString);

        driver.findElement(By.id("text")).click();
        driver.findElement(By.id("text")).clear();
        driver.findElement(By.id("text")).sendKeys(exportText);

        driver.findElement(By.id("int")).click();
        driver.findElement(By.id("int")).clear();
        driver.findElement(By.id("int")).sendKeys(exportInt);

        driver.findElement(By.id("decimal")).click();
        driver.findElement(By.id("decimal")).clear();
        driver.findElement(By.id("decimal")).sendKeys(exportDec);

        driver.findElement(By.id("date")).click();
        driver.findElement(By.id("date")).clear();
        driver.findElement(By.id("date")).sendKeys(Data);

        driver.findElement(By.id("datetime")).click();
        driver.findElement(By.id("datetime")).clear();
        driver.findElement(By.id("datetime")).sendKeys(DataTime);

        driver.findElement(By.xpath("//div[@id='_field_container-user']/div/button/div/div/div")).click();
        Select selDr = new Select(driver.findElement(By.id("user")));
        selDr.selectByVisibleText(User);

    }
    private void createEmbedExp (WebDriver driver) {
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        driver.findElement(By.id("t-undefined-r-1-_line_number")).click();

        driver.findElement(By.id("t-23-r-1-string")).click();
        driver.findElement(By.id("t-23-r-1-string")).clear();
        driver.findElement(By.id("t-23-r-1-string")).sendKeys(tablString);

        driver.findElement(By.xpath("//tr[@id='row-23-1']/td[4]")).click();
        driver.findElement(By.id("t-23-r-1-text")).click();
        driver.findElement(By.id("t-23-r-1-text")).clear();
        driver.findElement(By.id("t-23-r-1-text")).sendKeys(tableTex);

        driver.findElement(By.id("t-23-r-1-int")).click();
        driver.findElement(By.id("t-23-r-1-int")).clear();
        driver.findElement(By.id("t-23-r-1-int")).sendKeys(tableInt);

        driver.findElement(By.id("t-23-r-1-decimal")).click();
        driver.findElement(By.id("t-23-r-1-decimal")).clear();
        driver.findElement(By.id("t-23-r-1-decimal")).sendKeys(tableDec);

        driver.findElement(By.id("t-23-r-1-date")).click();
        driver.findElement(By.id("t-23-r-1-date")).clear();
        driver.findElement(By.id("t-23-r-1-date")).sendKeys(Data);

        driver.findElement(By.id("t-23-r-1-datetime")).click();
        driver.findElement(By.id("t-23-r-1-datetime")).clear();
        driver.findElement(By.id("t-23-r-1-datetime")).sendKeys(DataTime);

        driver.findElement(By.id("t-23-r-1-user")).click();
        new Select(driver.findElement(By.id("t-23-r-1-user"))).selectByVisibleText(User);

        driver.findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void inputTest() {

        WebDriver driver = getDriver();
        setUp(driver);

        createFieldForm(driver);
        createEmbedExp(driver);

        driver.findElement(By.xpath("//*[@id=\"pa-all-entities-table\"]/thead/tr/th[7]/div[1]")).click();

        int size = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr")).size();

        List<WebElement> list = driver.findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[7]"));

        int number =0;
        for (int i = 0; i < size; i++) {
            if (list.get(i).getText().equals(DataTime)) {
                number = i;
            }
        }

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[2]")).getText(),
                exportString);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[3]")).getText(),
                exportText);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[4]")).getText(),
                exportInt);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[5]")).getText(),
                exportDec);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[6]")).getText(),
                Data);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[7]")).getText(),
                DataTime);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number +1) + "]/td[9]")).getText(),
                User);

        driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[" + (number + 1) + "]/td[2]/a/div")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[2]")).getText(), tablString);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[3]")).getText(), tableTex);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[4]")).getText(), tableInt);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[5]")).getText(), tableDec);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[6]")).getText(), Data);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[7]")).getText(), DataTime);
        Assert.assertEquals(driver.findElement(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr/td[9]")).getText(), User);

        driver.findElement(By.xpath("//div[@id='menu-list-parent']//p[contains(text(), 'Export')]")).click();
    }
}

