package ui.automation;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserFactory {
    private static String gridURL;

    public static WebDriver configureBrowser() {
        Config uiTestConfig = ConfigFactory.load(System.getProperty("ui_test_config", "ui.conf"));
        String env = System.getProperty("env", uiTestConfig.getString("env"));
        if(env == null) {
            env = "";
        }
        gridURL = System.getProperty("gridURL", uiTestConfig.getString("gridURL"));
        if(gridURL == null) {
            gridURL = "http://localhost:4444/wd/hub";
        }
        MutableCapabilities desiredCapabilities;
        switch (env) {
            case "chrome":
                desiredCapabilities = new ChromeOptions();
                break;
            case "firefox":
                desiredCapabilities = new FirefoxOptions();
                break;
            case "safari":
                desiredCapabilities = new SafariOptions();
                break;
            default:
                desiredCapabilities = new ChromeOptions();
        }

        try {
            return new RemoteWebDriver(new URL(gridURL), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
