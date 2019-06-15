package ui.automation.pages;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Page extends PageFactory {
    private WebDriver driver;
    private static final String CONFIG_FILE = "ui.conf";
    private long timeoutTreshold;
    private String url;
    protected Logger LOGGER = Logger.getLogger("Page logger");

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        Config config = ConfigFactory.load(CONFIG_FILE);
        this.url = config.getString("uiUrl");
        this.timeoutTreshold = config.getLong("timeout");

    }

    public long getTimeoutTreshold() {
        return timeoutTreshold;
    }

    public void setTimeoutTreshold(long timeoutTreshold) {
        this.timeoutTreshold = timeoutTreshold;
    }

    public void load() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get(url);
        } catch (NullPointerException exception) {
            throw new RuntimeException("Please provide the app URL in the configuration file");
        }
    }

    public List<WebElement> findElements(By way) {
        return driver.findElements(way);
    }

    public void closeBrowser() {
        driver.quit();
    }

    public void waitForVisibility(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
