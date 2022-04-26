import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;

import java.io.IOException;
import java.util.ArrayList;

public class SalesForceLogin {

    WebDriver driver;
    DataDriven dataDriven = new DataDriven();
    ExtentReports reports;

    @BeforeTest
    public void configReport(){
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

        reporter.config().setDocumentTitle("Salesforce Test Report");
        reporter.config().setReportName("Login Page Test");

        reports = new ExtentReports();
        reports.attachReporter(reporter);

    }

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","D:\\Programming Related Software\\Selenium\\chorome driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://login.salesforce.com/?locale=eu");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test
    public void titleTest(){
        reports.createTest("Title Test");
        driver.getTitle();
        reports.flush();
    }

    @Test
    public void loginTest() throws IOException {
        ExtentTest test = reports.createTest("Login Test");
        ArrayList<String> data = dataDriven.getData("SalesForceLogin");
        driver.findElement(By.id("username")).sendKeys(data.get(1));
        driver.findElement(By.id("password")).sendKeys(data.get(2));
        driver.findElement(By.id("Login")).click();
        String text = driver.findElement(By.cssSelector("#error")).getText();
        if (text == "Please check your username and password. If you still can't log in, contact your Salesforce administrator"){
            test.fail(text);
        }
        reports.flush();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }



}
