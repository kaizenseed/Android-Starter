package com.example.androidstarter.utils;

import org.threeten.bp.Duration;

/**
 * Created by samvedana on 23/12/17.
 */

public class DateTimeHelper {
    public static String getStringDuration(long mins) {
        Duration d = Duration.ofMinutes(mins);
        return getStringDuration(d);
    }
    public static String getStringDuration(Duration d){
        StringBuilder str = new StringBuilder();

        long days = d.toDays();
        if (days > 0) {
            str.append(days).append("d ");
            d = d.minusDays(days);
        }

        long hours = d.toHours();
        if (hours > 0) {
            str.append(hours)
                    .append("h ");
            d = d.minusHours(hours);
        }

        long minutes = d.toMinutes();
        str.append(minutes)
                .append("m");

        return str.toString();
    }
}
