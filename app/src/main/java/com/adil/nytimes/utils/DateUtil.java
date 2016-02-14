package com.adil.nytimes.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by adil on 2/13/16.
 */
public class DateUtil {

    private static final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private static final DateFormat urlDateFormatter = new SimpleDateFormat("yyyyMMdd", Locale.US);

    public static String getDateAsString(Date date){
        return dateFormatter.format(date);
    }

    public static Date getStringAsDate(String date) throws ParseException {
        return dateFormatter.parse(date);
    }

    public static String getUrlFormattedDate(Date date){
        return urlDateFormatter.format(date);
    }

    public static Date getUrlFormatStringAsDate(String date) throws ParseException{
        return urlDateFormatter.parse(date);
    }

}
