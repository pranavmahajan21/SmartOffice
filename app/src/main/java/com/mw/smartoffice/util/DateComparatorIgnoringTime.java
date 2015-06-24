package com.mw.smartoffice.util;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by pranav on 18/6/15.
 */
public class DateComparatorIgnoringTime implements Comparator<Date> {
    public int compare(Date d1, Date d2) {
        if (d1.getYear() != d2.getYear())
            return d1.getYear() - d2.getYear();
        if (d1.getMonth() != d2.getMonth())
            return d1.getMonth() - d2.getMonth();
        return d1.getDate() - d2.getDate();
    }
}