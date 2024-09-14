package com.github.olegushak.FTT.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    public static String parseTime(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date startDate;
        try {
            startDate = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("hh:mm a");
        return newFormat.format(startDate);
    }

    public static String parseDuration(int minutes){
        Duration durationInMinutes = Duration.ofMinutes(minutes);
        return String.format("%dh %02d",durationInMinutes.toHoursPart(),durationInMinutes.toMinutesPart());
    }


}
