package com.mw.smartoffice.util;

/**
 * Created by pranav on 2/6/15.
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateFormatter {

    public String formatDateToString(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = formatter.format(date);
        System.out.println(">><<><><><" + dateStr);
        return dateStr;
    }

    public String formatDateToString2(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");

        String dateStr = formatter.format(date);
        System.out.println(">><<><><><" + dateStr);
        return dateStr;
    }

    public String formatDateToString3(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

        String dateStr = formatter.format(date);
        System.out.println(">><<><><><" + dateStr);
        return dateStr;
    }

    public String formatDateToString4(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm");

        String dateStr = formatter.format(date);
        System.out.println(">><<><><><" + dateStr);
        return dateStr;
    }

    public Date formatStringToDate(String dateString) {
        System.out.println("1212  :  " + dateString);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println(">><<><><><" + dateStr);
        return date;
    }

    public Date formatStringToDate2(String dateString) {
        System.out.println("1212  :  " + dateString);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(">><<><><><" + dateStr);
        return date;
    }
}
