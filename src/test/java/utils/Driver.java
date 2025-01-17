package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import java.net.URL;
import java.time.Duration;

public class Driver {
    private Driver() {
    }

    public static AppiumDriver Android;
    public static AppiumDriver iOS;

    public static AppiumDriver getAndroidDriver() {
        if (Android == null) {
            try {
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setAutomationName("UiAutomator2")
                        .setPlatformVersion("11.0")
                        .setDeviceName("emulator-5554")
                        .setUdid("emulator-5554")
                        .setAppPackage("com.linkedin.android")
                        .setAppActivity("com.linkedin.android.authenticator.LaunchActivity")
                        .setNoReset(true)
                        .setAutoGrantPermissions(true);

                Android = new AppiumDriver(new URL("http://127.0.0.1:4723"), options);
                Android.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            } catch (Exception e) {
                System.err.println("Android driver oluşturma hatası: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return Android;
    }

    public static AppiumDriver getIOSDriver() {
        if (iOS == null) {
            try {
                XCUITestOptions options = new XCUITestOptions()
                        .setPlatformName("iOS")
                        .setAutomationName("XCUITest")
                        .setPlatformVersion("16.7.10")
                        .setDeviceName("Damra iPhone’u")
                        .setUdid("67eabf5127c7b0384912007f02ffe655f7de75a3")
                        .setBundleId("com.linkedin.LinkedIn")
                        .setNoReset(true)
                        .setAutoAcceptAlerts(true);

                iOS = new AppiumDriver(new URL("http://127.0.0.1:4723"), options);
                iOS.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            } catch (Exception e) {
                System.err.println("iOS driver oluşturma hatası: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return iOS;
    }

    public static WebDriver getCurrentDriver() {
        try {
            if ("Android".equals(OS.OS)) {
                return Android;
            } else if ("iOS".equals(OS.OS)) {
                return iOS;
            } else {
                throw new IllegalStateException("Desteklenmeyen işletim sistemi: " + OS.OS);
            }
        } catch (Exception e) {
            System.err.println("getCurrentDriver hatası: " + e.getMessage());
            return null;
        }
    }

    public static void closeDriver() {
        try {
            if (iOS != null) {
                iOS.quit();
                iOS = null;
            }
        } catch (Exception e) {
            System.err.println("iOS driver kapatma hatası: " + e.getMessage());
        }

        try {
            if (Android != null) {
                Android.quit();
                Android = null;
            }
        } catch (Exception e) {
            System.err.println("Android driver kapatma hatası: " + e.getMessage());
        }
    }
}