import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFUtils {

    private static boolean previousReportIsILFOrEEG;

    // Scans a previous patient report in PDF form, extracts the "plan" section and returns it
    public static String returnReportPlan(File file) throws IOException {

        String page = getTextFromPDF(file);
        return getPlanText(getMatcher(page));

    }

    private static String getTextFromPDF(File file) throws IOException {

        if (file != null) {
            return new PDFTextStripper().getText(PDDocument.load(file)).trim();
        }
        else return null;

    }

    private static Matcher getMatcher(String page) {

        String pattern;

        // Check report type for formatting purposes
        if (page.startsWith("Client Name/ID:")) previousReportIsILFOrEEG = true;
        if (previousReportIsILFOrEEG) pattern = Constants.REPORT_PLAN_REGEX_ILF;
        else pattern = Constants.REPORT_PLAN_REGEX_HD;
        return Pattern.compile(pattern).matcher(page);

    }

    private static String getPlanText(Matcher match) {

        if (match.find()) {

            // Format and return
            if (previousReportIsILFOrEEG) return match.group(1).trim()
                    .replaceAll("  ", " ").replaceAll("\r\n", "");
            else return match.group(1).trim().replaceAll("\r\n", " ")
                    .replaceAll("\\*1Hz α", "");
        }
        else return null;

    }

}
