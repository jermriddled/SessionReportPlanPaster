import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SikuliUtils {

    private static boolean isHD;

    // Scans the Cygnet GUI for patient name and returns it
    public static String patientNameOCR() throws FindFailed, InterruptedException {

        Region region = new Region(0, 0, 1920, 1080);

/*         First attempt scans for the contemporary style GUI because it's more common;
         if it fails, scan for the old style GUI */
        try {
            return getNameFromHDViaOCR(region);
        } catch (FindFailed | AWTException | IOException | UnsupportedFlavorException e) {
            e.printStackTrace();
            return getNameFromILFViaOCR(region);
        }

    }

    // Scans the GUI for the "Plan" section in the report and pastes into it the copied report on the clipboard
    public static void pastePlanInReport() throws FindFailed, AWTException, InterruptedException {

        findPlanFieldAndClick();
        RobotUtils.pasteText();
    }

    // Finds the correct field into which to paste the Plan
    private static void findPlanFieldAndClick() throws InterruptedException, AWTException, FindFailed {

        Region region = new Region(0,0, 1920,1080);
        Pattern planField;
        Pattern siteMapField;

        // Pick proper pattern for the GUI that's in use
        if (isHD()) planField = new Pattern(ClassLoader.getSystemResource("hd-plan-window-big.png"));
        else {
            planField = new Pattern(ClassLoader.getSystemResource("ilf-plan-window-big.png"));

            // For old GUI, scrolls the report window down to reveal the "Plan" section so that OCR can recognize it
            siteMapField = new Pattern(ClassLoader.getSystemResource("site-map-snippet.png"));
            region.find(siteMapField);
            region.click();

        /* This is to override Cygnet scrolling back up for no apparent reason after scrolling the session
             window down */
            RobotUtils.scrollDownAndOverride();
        }

        region.find(planField);
        region.click();

    }

    private static String getNameFromHDViaOCR(Region region) throws FindFailed, IOException, InterruptedException,
            UnsupportedFlavorException, AWTException {

        Pattern clientIDTextField = new Pattern(ClassLoader.getSystemResource("hd-name-snippet-blank.png"));
        region.find(clientIDTextField);
        String name = region.getLastMatch().text();
        if (name.contains("\n")) {
            String[] stringArr = name.split("\n");
            name = stringArr[1];
        }

        // Lets the pastePlanInReport method know what style the GUI is
        setIsHD();

        /* Last resort if OCR of name field inexplicably fails (which it only does with particular names and only on
         the contemporary GUI) */
        if (!FilesUtils.checkDirectoryExists(FilesUtils.makeFile(name))) {
            return getNameFromPDF(region);

        } else return name;

    }

    private static String getNameFromILFViaOCR(Region region) throws FindFailed {

        Pattern clientIDTextField = new Pattern(ClassLoader.getSystemResource("ilf-name-with-logo-snippet.png"));
        region.find(clientIDTextField);
        String name = region.getLastMatch().text();
        String[] stringArr = name.split(":");
        return stringArr[1].split("CYGNET @")[0];

    }

    // Backup measure to get name from PDF if OCR fails (last resort because it takes longer and interrupts workflow)
    private static String getNameFromPDF(Region region) throws FindFailed, InterruptedException, AWTException,
            IOException, UnsupportedFlavorException {

        Pattern lastReportButton = new Pattern(ClassLoader.getSystemResource("last-report-snippet.png"));
        region.find(lastReportButton);
        region.click();
        Thread.sleep(4000);
        RobotUtils.copyTextFromLastReport();
        String reportText = ClipboardUtils.retrieveStringFromClipboard();
        Thread.sleep(2000);
        int timeout = 0;

        // In case computer was slow to open last report, wait and try again, but not forever
        while (!StringUtils.reportTextWasCopied(reportText) && timeout < 3) {
            Thread.sleep(4000);
            RobotUtils.copyTextFromLastReport();
            reportText = ClipboardUtils.retrieveStringFromClipboard();
            timeout++;
        }

        // To avoid accidentally closing Cygnet
        if (timeout >= 3) System.exit(0);

        RobotUtils.closePDF();
        return reportText.split("\n")[0];

    }

    public static void setIsHD() {
        isHD = true;
    }

    public static boolean isHD() {
        return isHD;
    }
}
