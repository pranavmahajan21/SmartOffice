package com.mw.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.util.DateFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pranav on 1/6/15.
 */
public class CalendarAdapter extends BaseAdapter {

    static final int FIRST_DAY_OF_WEEK = 0; // Sunday = 0, Monday = 1

    private Context context;

    private Calendar todayDateCalendar;
    private Calendar backupDateCalendar;
    private Calendar selectedDateCalendar;
    private ArrayList<String> items;

    // references to our items
    public String[] days;

    public CalendarAdapter(Context context, Calendar selectedDateCalendar) {
        /** Delta1 **/
        todayDateCalendar = Calendar.getInstance();
        String[] dateArr = new DateFormatter().formatDateToString(new Date()).split("-");
        todayDateCalendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));
        /** Delta1 **/

        this.context = context;
        backupDateCalendar = selectedDateCalendar;
//        backupDateCalendar = Calendar.getInstance();
        this.selectedDateCalendar = (Calendar) selectedDateCalendar.clone();
        backupDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
//        backupDateCalendar.set(this.selectedDateCalendar.get(Calendar.YEAR),this.selectedDateCalendar.get(Calendar.MONTH), 1);
        System.out.println("backupDateCalendar : " + backupDateCalendar.toString());
        items = new ArrayList<String>();
        refreshDays();
    }

    public void updateBackupDateCalendar(Calendar selectedDateCalendar) {
        backupDateCalendar = selectedDateCalendar;

        System.out.println("backupDateCalendar : " + backupDateCalendar.toString());
    }

//    public void setItems(ArrayList<String> items) {
//        for (int i = 0; i != items.size(); i++) {
//            if (items.get(i).length() == 1) {
//                items.set(i, "0" + items.get(i));
//            }
//        }
//        this.items = items;
//    }

    public int getCount() {
        return days.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new view for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        TextView date_TV;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.element_calendar, null);

        }
        date_TV = (TextView) view.findViewById(R.id.date_TV);

        // disable empty days from the beginning
        if (days[position].equals("")) {
            date_TV.setClickable(false);
            date_TV.setFocusable(false);
            date_TV.setBackgroundResource(0);
        } else {
            // mark current day as focused
            if (backupDateCalendar.get(Calendar.YEAR) == selectedDateCalendar.get(Calendar.YEAR) && backupDateCalendar.get(Calendar.MONTH) == selectedDateCalendar.get(Calendar.MONTH) && days[position].equals("" + selectedDateCalendar.get(Calendar.DAY_OF_MONTH))) {
//                v.setBackgroundResource(R.drawable.item_background_focused);
                date_TV.setBackgroundResource(R.drawable.custom_bg_solid_white_radius);
                date_TV.setTextColor(context.getResources().getColor(R.color.bg_calendar));
            }
            /** Delta1 **/
            else if (backupDateCalendar.get(Calendar.YEAR) == todayDateCalendar.get(Calendar.YEAR) && backupDateCalendar.get(Calendar.MONTH) == todayDateCalendar.get(Calendar.MONTH) && days[position].equals("" + todayDateCalendar.get(Calendar.DAY_OF_MONTH))) {
//                v.setBackgroundResource(R.drawable.item_background_focused);
                date_TV.setBackgroundResource(R.drawable.custom_bg_stroke_white_radius);
                date_TV.setTextColor(context.getResources().getColor(android.R.color.white));
            }
            /** Delta1 **/
            else {
//                view.setBackgroundResource(R.drawable.list_item_background);
                date_TV.setBackgroundResource(0);
                date_TV.setTextColor(context.getResources().getColor(android.R.color.white));
            }
        }
        date_TV.setText(days[position]);

        // create date string for comparison
//        String date = days[position];
//
//        if (date.length() == 1) {
//            date = "0" + date;
//        }
//        String monthStr = "" + (backupDateCalendar.get(Calendar.MONTH) + 1);
//        if (monthStr.length() == 1) {
//            monthStr = "0" + monthStr;
//        }

        // show icon if date is not empty and it exists in the items array
//        ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
//        if (date.length() > 0 && items != null && items.contains(date)) {
//            iw.setVisibility(View.VISIBLE);
//        } else {
//            iw.setVisibility(View.INVISIBLE);
//        }
        return view;
    }

    /**
     * Called when we change the month & once from the constructor *
     */
    public void refreshDays() {
        /**
         * This function populates String days[] with blank values & proper dates.
         * Therefore this func  tion should be called when we change the month.
         * **/

        /**
         * Calendar.SUNDAY
         * Sunday -1, Monday -2 ...... Saturday -7
         * Calendar.JANUARY
         * January-0 .... December- 11
         * **/

        // clear items
        items.clear();
        System.out.println(backupDateCalendar.toString());


        int lastDay = backupDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("lastDay  : " + lastDay);
        int firstDay = (int) backupDateCalendar.get(Calendar.DAY_OF_WEEK);
//        int firstDay = (int) backupDateCalendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("firstDayAdapter  : " + firstDay);


        // figure size of the array
        if (firstDay == 1) {
            days = new String[lastDay + (FIRST_DAY_OF_WEEK * 6)];
        } else {
            days = new String[lastDay + firstDay - (FIRST_DAY_OF_WEEK + 1)];
        }
        /** Size of array will be 31-Sunday, 32-Monday, ....., 37-Saturday **/

        int j = FIRST_DAY_OF_WEEK;

//        populate empty days before first real day
        if (firstDay > 1) {
            for (j = 0; j < firstDay - FIRST_DAY_OF_WEEK; j++) {
                days[j] = "";
            }
        } else {
            for (j = 0; j < FIRST_DAY_OF_WEEK * 6; j++) {
                days[j] = "";
            }
            j = FIRST_DAY_OF_WEEK * 6 + 1; // sunday => 1, monday => 7
        }

        // populate days
        int dayNumber = 1;
        for (int i = j - 1; i < days.length; i++) {
            days[i] = "" + dayNumber;
            dayNumber++;
        }
    }
}