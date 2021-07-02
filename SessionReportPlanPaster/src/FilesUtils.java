import org.sikuli.script.FindFailed;

import java.io.File;
import java.time.LocalDate;

public class FilesUtils {

    // All of a patient's session reports
    private static File[] files;

    // Accesses a patient's folder and adds their report PDFs to files array
    public static void accessFiles() throws FindFailed, InterruptedException {

        String name = SikuliUtils.patientNameOCR().trim();
        File directory = makeFile(name);
        files = directory.listFiles((dir, name1) -> name1.toLowerCase().endsWith(".pdf"));

    }

    // Returns most recent patient session report as long as it was not created today
    public static File mostRecentFile() {

        LocalDate today = LocalDate.now();
        if (files != null) {
            int size = files.length;
            for (int i = size - 1; i > size - 8; i--) {
                LocalDate thisDate = DateUtils.dateMaker(files[i]);
                if (!thisDate.equals(today)) {
                    return files[i];
                }
            }
        }
        return null;

    }

    public static boolean checkDirectoryExists(File file) {
        return file.length() > 0;
    }

    public static File makeFile(String name) {
        File directory = new File(Constants.LIVE_FOLDER + "\\" + name);
        if (!checkDirectoryExists(directory)) directory = new File
                (Constants.LIVE_FOLDER_ALTERNATIVE_1 + "\\" + name);
        if (!checkDirectoryExists(directory)) directory = new File
                (Constants.LIVE_FOLDER_ALTERNATIVE_2 + "\\" + name);
        return directory;
    }

}
