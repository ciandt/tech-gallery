package ui.steps;

import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class SearchSteps {

    private WebDriver driver;

    @Before
    public void before() {
        // download chromedriver at https://chromedriver.storage.googleapis.com/2.35/chromedriver_linux64.zip
        // unzip ${user.home}/tools/chromedriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + "/tools/chromedriver");
        driver = new ChromeDriver();
        driver.navigate().to("https://tech-gallery-sandbox.appspot.com");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS  );
        driver.manage().window().maximize();
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("^I am logged in as '(.*?)'$")
    public void loggedIn(String username) {
        String winHandleBefore = driver.getWindowHandle();
        WebElement loginBtn = driver.findElement(By.className("btn"));
        loginBtn.click();
        // switch login popup
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        String subWindowHandler = null;
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler);

        // enter user and password
        WebElement emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys("marcus.lacerda@gmail.com");
        WebElement nextBtn = driver.findElement(By.className("CwaK9"));
        nextBtn.click();

        // wait next page loader
        waitForSeconds(3);

        WebElement passwordInput = driver.findElement(By.name("password"));

        passwordInput.sendKeys("XXXXX");
        WebElement nextBtn2 = driver.findElement(By.xpath("//content[@class='CwaK9']"));
        nextBtn2.click();

        // retorna a janela principal
        driver.switchTo().window(winHandleBefore);
    }

    @When("^I enter search term '(.*?)'$")
    public void searchFor(String searchTerm) {
        System.out.println(searchTerm);
        WebElement searchField = driver.findElement(By.xpath("//input[@type='text']"));
        searchField.sendKeys(searchTerm);
    }

    @And("^press the enter key$")
    public void clickSearchButton() {
        WebElement searchForm = driver.findElement(By.id("app-search"));
        searchForm.submit();
    }

    @Then("^I should see at least 1 card for '(.*?)'$")
    public void assertSingleResult(String searchResult) {
        waitForSeconds(3);
        WebElement results = driver.findElement(By.className("card"));
        assertFalse(results.getText().contains(searchResult + " may refer to:"));
        assertTrue(results.getText().startsWith(searchResult));
    }

    private void waitForSeconds(int segs) {
        try {
            Thread.sleep(segs * 1000);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

}
