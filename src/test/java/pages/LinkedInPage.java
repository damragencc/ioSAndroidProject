package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.OS;
import static utils.Driver.getCurrentDriver;

public class LinkedInPage extends BasePage {

    public LinkedInPage() {

        super(getCurrentDriver());
    }


    public void cliquerAnaSayfa() {
        By anaSayfaButon = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@text='Ana Sayfa']") ://Anroid
                AppiumBy.accessibilityId("12000");//ios
        getCurrentDriver().findElement(anaSayfaButon).click();
    }


    public void cliquerAg() {
        By agButon = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@text='Profesyonel Ağım']") :
                AppiumBy.iOSClassChain("**/XCUIElementTypeImage[`name == \"system-icons_people_medium_24x24\"`]");
        getCurrentDriver().findElement(agButon).click();
    }


    public void cliquerIsIlanlari() {
        By isIlanlariButon = OS.isAndroid() ?
                AppiumBy.id("com.linkedin.android:id/tab_jobs") :
                AppiumBy.iOSNsPredicateString("name == \"system-icons_job_medium_24x24\"");
        getCurrentDriver().findElement(isIlanlariButon).click();
    }


    public void cliquerBildirimler() {
        By bildirimButon = OS.isAndroid() ?
                AppiumBy.xpath("//android.widget.TextView[@text='Bildirimler']") :
                AppiumBy.accessibilityId("5500");
        getCurrentDriver().findElement(bildirimButon).click();
    }


    public void aramaYap(String aramaKelimesi) throws InterruptedException {
        By aramaAlani = OS.isAndroid() ?
                AppiumBy.id("com.linkedin.android:id/search_bar_text") :
                AppiumBy.accessibilityId("5501_placeholder");
        getCurrentDriver().findElement(aramaAlani).click();
        Thread.sleep(2000);
        By aramaMetinAlani = OS.isAndroid() ?
                AppiumBy.id("com.linkedin.android:id/search_bar_edit_text") :
                AppiumBy.accessibilityId("5418");
        getCurrentDriver().findElement(aramaMetinAlani).sendKeys(aramaKelimesi,Keys.ENTER);

    }
}

