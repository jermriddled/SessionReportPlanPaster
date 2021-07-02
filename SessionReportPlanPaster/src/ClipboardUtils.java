import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class ClipboardUtils {

    // Copies patient's report plan to clipboard
    public static void copyToClipboard(File file) throws IOException {

        if (file != null) {
            String string = PDFUtils.returnReportPlan(file);
            StringSelection selection = new StringSelection(string);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);

        }

    }

    // Used only for getNameFromPDF() in SikuliUtils
    public static String retrieveStringFromClipboard() throws IOException, UnsupportedFlavorException, InterruptedException {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        Thread.sleep(1000);
        return (String) clipboard.getData(DataFlavor.stringFlavor);

    }

}
