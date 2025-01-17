package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.Driver;
import utils.OS;

public class Hooks {
    private Scenario scenario;

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
        OS.OS = ConfigReader.getProperty("platformName", "iOS");
    }

    @Given("LinkedIn uygulamasi acik durumda")
    public void linkedInUygulamasiAcikDurumda() {
        try {
            if (OS.isIOS()) {
                if (Driver.iOS == null) {
                    Driver.iOS = Driver.getIOSDriver();
                }
            } else if (OS.isAndroid()) {
                if (Driver.Android == null) {
                    Driver.Android = Driver.getAndroidDriver();
                }
            }
        } catch (Exception e) {
            System.out.println("Driver başlatma hatası: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            WebDriver driver = Driver.getCurrentDriver();
            if (driver != null && scenario.isFailed()) {
                if (driver instanceof TakesScreenshot) {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "hata-ekran-goruntusu");
                }
            }
        } catch (Exception e) {
            System.out.println("Teardown hatası: " + e.getMessage());
        } finally {
            try {
                Driver.closeDriver();
            } catch (Exception e) {
                System.out.println("Driver kapatma hatası: " + e.getMessage());
            }
        }
    }
}