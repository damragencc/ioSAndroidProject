package utils;

public class OS {
    public static String OS = "iOS";

    public static boolean isAndroid() {

        return "Android".equalsIgnoreCase(OS);
    }


    public static boolean isIOS() {
        return "iOS".equalsIgnoreCase(OS);
    }
}