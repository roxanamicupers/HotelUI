package ui.automation.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class HotelSupport {

    public static String getDateInCorrectFormat(long days) {
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = Date.from(Instant.now().plus(Duration.ofDays(days)));

        return customFormat.format(date);
    }

    public static int getRandomInt() {
        return new Random().nextInt(30);
    }
}
