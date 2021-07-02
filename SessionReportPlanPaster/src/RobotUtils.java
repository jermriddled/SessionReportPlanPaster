import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotUtils {

    public static void pasteText() throws AWTException {
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
    }

    public static void copyTextFromLastReport() throws AWTException {
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_A);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_C);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_C);
    }

    public static void closePDF() throws AWTException, InterruptedException {
        Robot r = new Robot();
        Thread.sleep(2000);
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_F4);
        r.keyRelease(KeyEvent.VK_ALT);
        r.keyRelease(KeyEvent.VK_F4);
    }

    /* This is to override Cygnet scrolling back up for no apparent reason after scrolling the session
     window down */
    public static void scrollDownAndOverride() throws AWTException, InterruptedException {
        Robot r = new Robot();
        for (int i = 0; i< 10; i++) r.mouseWheel(1);
        Thread.sleep(500);
        for (int i = 0; i< 10; i++) r.mouseWheel(1);
        Thread.sleep(500);
        for (int i = 0; i< 10; i++) r.mouseWheel(1);
        Thread.sleep(500);
    }
}
