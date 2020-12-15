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
        final String url = "https://ref.eteam.work";
        final String login = "user1@tester.com";
        final String password = "ah1QNmgkEO";
        final int f1Value = 1;
        final String[] expectedValuesF1ToF10 = {"1", "1", "2", "3", "5", "8", "13", "21", "34", "55"};

        By entityEventsChain2Tab = By.cssSelector("#menu-list-parent>ul>li>a[href*='id=62");
        By createNewRecord = By.cssSelector("div.card-icon>i");
        By f1 = By.cssSelector("#f1");
        By saveButton = By.cssSelector("#pa-entity-form-save-btn");
        By dropDownMenuView = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='view']");
        By dropDownMenuEdit = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='edit']");
        By dropDownMenuDelete = By.cssSelector("ul.dropdown-menu.dropdown-menu-right.show>li>a[href*='delete']");

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        ProjectUtils.login(driver, login, password);

        driver.findElement(entityEventsChain2Tab).click();
        driver.findElement(createNewRecord).click();
        driver.findElement(f1).sendKeys(String.valueOf(f1Value));
        sleep();
        driver.findElement(saveButton).click();
        clickOnDropDownMenuChoice(dropDownMenuView);

        assertValuesPopulatedAsExpected(expectedValuesF1ToF10);
    }

    private void clickOnDropDownMenuChoice(By dropDownMenuChoice) {
        final String url = "https://ref.eteam.work/index.php?action=action_list&entity_id=62";

        WebDriver driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();

        List<WebElement> dropDownMenus = driver.findElements(By.cssSelector("div.dropdown.pull-left>button>i"));
        dropDownMenus.get(dropDownMenus.size() - 1).click();

        driver.findElement(dropDownMenuChoice).click();
    }

    private void assertValuesPopulatedAsExpected(String[] expectedValuesF1ToF10) {
        WebDriver driver = getDriver();
        driver.manage().window().maximize();

        List<WebElement> actualValues = driver.findElements(By.cssSelector(".pa-view-field"));

        for(int i = 0; i < actualValues.size(); i ++) {
            System.out.println(actualValues.get(i).getText() + "|" + expectedValuesF1ToF10[i]);
                Assert.assertEquals(actualValues.get(i).getText(), expectedValuesF1ToF10[i]);
        }

    }

    private void sleep() throws InterruptedException {
        Thread.sleep(2000);

    }




}
