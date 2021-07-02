public class StringUtils {

    public static boolean reportTextWasCopied(String text) {

        return (text.contains("Session")  && text.contains("Site") && text.contains("Frequency"));

    }

}
