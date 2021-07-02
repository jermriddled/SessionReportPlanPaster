import org.sikuli.script.*;

import java.awt.*;
import java.io.IOException;

/* This program works in conjunction with Cygnet software to automate a simple but annoying task involved in
 writing our patient reports.  Each patient report has a section entitled "Plan" in which we write the specific
 patient's treatment plan and relevant information.  The information almost never changes, but since the Cygnet
 software does not have any functionality built in to automate this, we are forced to navigate to old patient reports,
 manually copy the plan, go back to Cygnet and paste it in.  This task is somewhat challenging and inconvenient for
 some of my coworkers, so I figured automation of it would be helpful.

 The program uses the Sikuli library in order to recognize a patient's name through OCR, as the text is not
 selectable; it then uses PDFBox to pull the plan details from the appropriate report; finally, it uses OCR to
 paste the plan into the specified text box. */

public class SessionReportPlanPaster {

    public static void main(String[] args) throws IOException, FindFailed, AWTException, InterruptedException {

        ImagePath.add(System.getProperty("user.dir"));
        FilesUtils.accessFiles();
        ClipboardUtils.copyToClipboard(FilesUtils.mostRecentFile());
        SikuliUtils.pastePlanInReport();

    }
}
