import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static String getCurrentTime() {
        return LocalDateTime.now().format(formatter);
    }

    public static String beautifyMili(long timeInMillis) {
        if (timeInMillis < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        long seconds = timeInMillis / 1000;
        long milliseconds = timeInMillis % 1000;
        return String.format("%d.%02d" + "s", seconds, milliseconds);
    }
}
