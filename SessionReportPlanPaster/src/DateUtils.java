import java.io.File;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {

    // Makes a Date object associated with a particular day's patient session report
    public static LocalDate dateMaker(File file) {
        Matcher dateMatcher = Pattern.compile(Constants.DATE_REGEX).matcher(file.toString());
        int year = 0, month =0 , day = 0;
        if (dateMatcher.find()) {
            year = Integer.parseInt(dateMatcher.group(1));
            month = Integer.parseInt(dateMatcher.group(2));
            day = Integer.parseInt(dateMatcher.group(3));
        }
        return LocalDate.of(year, month, day);
    }

}
