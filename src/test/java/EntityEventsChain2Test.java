import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import java.util.List;

public class EntityEventsChain2Test extends BaseTest {

    @Test
    public void createSaveAssertNewRecord() throws InterruptedException {
        final int f1Value = 1;
        final String[] expectedValuesF1ToF10 = {"1", "1", "2", "3", "5", "8", "13", "21", "34", "55"};

        By view = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
        By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        createNewEntityEventsChain2Record(f1Value);
        clickOnDropDownMenuChoice(view);
        shortSleep();

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);

        clickOnDropDownMenuChoice(delete);
    }

    @Test
    public void replaceF1WithZeroAssertAllZeros() throws InterruptedException {
        final int f1OldValue = 1;
        final int f1NewValue = 0;
        final String[] expectedValuesF1ToF10 = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};

        By view = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
        By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        createNewEntityEventsChain2Record(f1OldValue);
        replaceF1withNewValue(f1NewValue);
        clickOnDropDownMenuChoice(view);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);

        clickOnDropDownMenuChoice(delete);
    }

    @Test
    public void assertValuesOnViewMode() throws InterruptedException {
        final int f1Value = 5;
        final String[] expectedValuesF1ToF10 = {"5", "5", "10", "15", "25", "40", "65", "105", "170", "275"};

        By view = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
        By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        createNewEntityEventsChain2Record(f1Value);
        clickOnDropDownMenuChoice(view);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);

        clickOnDropDownMenuChoice(delete);
    }

    @Test
    public void assertValuesOnEditMode() throws InterruptedException {
        final int f1Value = 3;
        final String[] expectedValuesF1ToF10 = {"3", "3", "6", "9", "15", "24", "39", "63", "102", "165"};

        By edit = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");
        By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        createNewEntityEventsChain2Record(f1Value);
        clickOnDropDownMenuChoice(edit);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);

        clickOnDropDownMenuChoice(delete);
    }

    @Test
    public void replaceWithInvalidValuesAssertError() throws InterruptedException {
        final int f1Value = 415;
        final int elementInLastRecord = 5;
        final String text = "We scream for ice cream!";

        By saveButton = By.cssSelector("#pa-entity-form-save-btn");
        By edit = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");
        By delete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        WebDriver driver = getDriver();

        createNewEntityEventsChain2Record(f1Value);
        clickOnDropDownMenuChoice(edit);
        inputCharSequences(driver);
        shortSleep();
        driver.findElement(saveButton).click();

        assertErrorForInvalidValues();

        assertRecordNotCreated(elementInLastRecord, text);

        clickOnDropDownMenuChoice(delete);
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(2000);
    }

    private void shortSleep() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void createNewEntityEventsChain2Record(int f1Value) throws InterruptedException {
        final String url = "https://ref.eteam.work";
        final String login = "user1@tester.com";
        final String password = "ah1QNmgkEO";

        By entityEventsChain2Tab = By.cssSelector("#menu-list-parent>ul>li>a[href*='id=62");
        By createNewRecord = By.cssSelector("div.card-icon>i");
        By f1 = By.cssSelector("#f1");
        By saveButton = By.cssSelector("#pa-entity-form-save-btn");

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        ProjectUtils.login(driver, login, password);

        driver.findElement(entityEventsChain2Tab).click();
        driver.findElement(createNewRecord).click();
        driver.findElement(f1).sendKeys(String.valueOf(f1Value));
        sleep();
        driver.findElement(saveButton).click();
        shortSleep();
    }

    public void replaceF1withNewValue(int f1NewValue) throws InterruptedException {
        By f1 = By.cssSelector("#f1");
        By saveButton = By.cssSelector("#pa-entity-form-save-btn");
        By edit = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");

        WebDriver driver = getDriver();

        clickOnDropDownMenuChoice(edit);
        driver.findElement(f1).click();
        driver.findElement(f1).clear();
        driver.findElement(f1).sendKeys(String.valueOf(f1NewValue));
        sleep();
        driver.findElement(saveButton).click();
        shortSleep();
    }

    private void clickOnDropDownMenuChoice(By dropDownMenuChoice) throws InterruptedException {
        final String url = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        shortSleep();

        List<WebElement> dropDownMenus = driver.findElements(By.cssSelector("div.dropdown.pull-left>button>i"));
        dropDownMenus.get(dropDownMenus.size() - 1).click();
        shortSleep();
        driver.findElement(dropDownMenuChoice).click();
        shortSleep();
    }

    private void assertValuesPopulatedAsExpected(String[] expectedValuesF1ToF10) {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();

        List<WebElement> actualValues = driver.findElements(By.cssSelector(".pa-view-field"));

        for(int i = 0; i < actualValues.size(); i ++) {
            Assert.assertEquals(actualValues.get(i).getText(), expectedValuesF1ToF10[i]);
        }
    }

    private void assertErrorForInvalidValues() {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();

        By errorMessage = By.cssSelector("#pa-error");

        Assert.assertEquals(driver.findElement(errorMessage).getText(), "Error saving entity");
    }

    private void assertRecordNotCreated(int element, String text) {
        final String url = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

        By lastRecord = By.cssSelector("tbody>tr:last-child>td>a>div");

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();

        List<WebElement> valuesForLastRecord = driver.findElements(lastRecord);
        Assert.assertNotEquals(valuesForLastRecord.get(element).getText(), text);
    }

    private void inputCharSequences(WebDriver driver) throws InterruptedException {
        By f1 = By.xpath("//input[@id='f1']");
        By f2 = By.xpath("//input[@id='f2']");
        By f3 = By.cssSelector("#f3");
        By f4 = By.cssSelector("#f4");
        By f5 = By.cssSelector("#f5");
        By f6 = By.cssSelector("#f6");
        By f7 = By.cssSelector("#f7");
        By f8 = By.cssSelector("#f8");
        By f9 = By.cssSelector("#f9");
        By f10 = By.cssSelector("#f10");

        shortSleep();
        driver.findElement(f1).click();
        driver.findElement(f1).clear();
        driver.findElement(f1).sendKeys("I scream for ice cream!");
        shortSleep();

        driver.findElement(f2).click();
        driver.findElement(f2).clear();
        driver.findElement(f2).sendKeys("I scream for ice cream!");
        shortSleep();


        driver.findElement(f3).click();
        driver.findElement(f3).clear();
        driver.findElement(f3).sendKeys("You scream for ice cream!");
        shortSleep();

        driver.findElement(f4).click();
        driver.findElement(f4).clear();
        driver.findElement(f4).sendKeys("You scream for ice cream!");
        shortSleep();

        driver.findElement(f5).click();
        driver.findElement(f5).clear();
        driver.findElement(f5).sendKeys("We scream for ice cream!");
        sleep();

        driver.findElement(f6).click();
        driver.findElement(f6).clear();
        driver.findElement(f6).sendKeys("We scream for ice cream!");
        shortSleep();

        driver.findElement(f7).click();
        driver.findElement(f7).clear();
        driver.findElement(f7).sendKeys("We scream for ice cream!");
        shortSleep();

        driver.findElement(f8).click();
        driver.findElement(f8).clear();
        driver.findElement(f8).sendKeys("Everybody love ice cream!");
        sleep();

        driver.findElement(f9).click();
        driver.findElement(f9).clear();
        driver.findElement(f9).sendKeys("Everybody love ice cream!");
        shortSleep();

        driver.findElement(f10).click();
        driver.findElement(f10).clear();
        driver.findElement(f10).sendKeys("Everybody love ice cream!");
        shortSleep();
    }
}
