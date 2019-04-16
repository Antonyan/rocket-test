package core.modules.web.models;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebBrowser {

    public static WebDriver initialize(String browser) {
         {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
                case "ie":
                    WebDriverManager.iedriver().setup();
                    return new InternetExplorerDriver();
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    return new EdgeDriver();
                case "safari":
                    return new SafariDriver();
                default:
                    return null;
            }
        }
    }
}
