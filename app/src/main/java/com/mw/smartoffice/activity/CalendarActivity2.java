package com.mw.smartoffice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mw.smartoffice.adapter.CalendarAdapter;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.util.DateFormatter;

import java.util.Calendar;
import java.util.Map;

public class CalendarActivity2 extends Activity {

    /**
     * https://github.com/nevalla/CalendarView
     * <p/>
     * This library has a bug in the refreshDays() of the adapter. It makes the the current day of week(Mon/Tue/Wed...) as the 1st date of the month.
     */

    MyApp myApp;

    GridView calendar_GV;
    TextView month_TV;

    Map<Integer, String> monthMap;

    public Calendar selectedDateCalendar;
    public CalendarAdapter calendarAdapter;

    DateFormatter formatter;
    Intent previousIntent;


    private void initThings() {
        selectedDateCalendar = Calendar.getInstance();

        previousIntent = getIntent();
        myApp = (MyApp) getApplicationContext();
        formatter = new DateFormatter();
        monthMap = myApp.getMonthMap();

        // date format is yyyy-mm-dd
        String[] dateArr = formatter.formatDateToString(formatter.formatStringToDate("2015-06-" + previousIntent.getStringExtra("date"))).split("-");
        selectedDateCalendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));

        System.out.println("DAY_OF_WEEK  : " + (int) selectedDateCalendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("DAY_OF_MONTH  : " + (int) selectedDateCalendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("YEAR  : " + (int) selectedDateCalendar.get(Calendar.YEAR));
        System.out.println("MONTH  : " + (int) selectedDateCalendar.get(Calendar.MONTH));

        System.out.println("selectedDateCalendar : " + selectedDateCalendar.toString());

        calendarAdapter = new CalendarAdapter(this, selectedDateCalendar);
    }

    private void findThings() {
        calendar_GV = (GridView) findViewById(R.id.calendar_GV);
        month_TV = (TextView) findViewById(R.id.month_TV);
    }

    private void initView() {
        month_TV.setText(monthMap.get(selectedDateCalendar.get(Calendar.MONTH)));
        if (calendarAdapter != null) {
            calendar_GV.setAdapter(calendarAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar2);

        initThings();
        findThings();
        initView();

        calendar_GV.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView date_TV = (TextView) v.findViewById(R.id.date_TV);
                if (date_TV instanceof TextView && !date_TV.getText().equals("")) {

                    Intent intent = new Intent();
                    String day = date_TV.getText().toString();
                    if (day.length() == 1) {
                        day = "0" + day;
                    }

                    // return chosen date as string format
                    String temp = android.text.format.DateFormat.format("yyyy-MM", selectedDateCalendar) + "-" + day;
                    System.out.println("temp : " + temp);

                    /** Delta2 **/
                    selectedDateCalendar.set(Calendar.DATE, Integer.parseInt(day));
//                    selectedDateCalendar.set(Calendar.MONTH, selectedDateCalendar.get(Calendar.MONTH) + 1);
                    calendarAdapter.updateBackupDateCalendar(selectedDateCalendar);
//                    adapter.refreshDays();
//                    adapter.notifyDataSetChanged();
                    calendarAdapter = new CalendarAdapter(CalendarActivity2.this, selectedDateCalendar);
                    calendar_GV.setAdapter(calendarAdapter);
                    /** Delta2 **/
                }

            }
        });
    }

    public void onNext(View view) {
        System.out.println("onNext");
        Toast.makeText(this, "onNext", Toast.LENGTH_SHORT).show();
        if (selectedDateCalendar.get(Calendar.MONTH) == selectedDateCalendar.getActualMaximum(Calendar.MONTH)) {
            selectedDateCalendar.set((selectedDateCalendar.get(Calendar.YEAR) + 1), selectedDateCalendar.getActualMinimum(Calendar.MONTH), 1);
        } else {
            selectedDateCalendar.set(Calendar.MONTH, selectedDateCalendar.get(Calendar.MONTH) + 1);
        }
        month_TV.setText(monthMap.get(selectedDateCalendar.get(Calendar.MONTH)));

        /** Why is this function called?? **/
        calendarAdapter.updateBackupDateCalendar(selectedDateCalendar);
        refreshCalendar();
    }

    public void onPrevious(View view) {
        System.out.println("onPrevious");
        Toast.makeText(this, "onPrevious", Toast.LENGTH_SHORT).show();
        if (selectedDateCalendar.get(Calendar.MONTH) == selectedDateCalendar.getActualMinimum(Calendar.MONTH)) {
            selectedDateCalendar.set((selectedDateCalendar.get(Calendar.YEAR) - 1), selectedDateCalendar.getActualMaximum(Calendar.MONTH), 1);
        } else {
            selectedDateCalendar.set(Calendar.MONTH, selectedDateCalendar.get(Calendar.MONTH) - 1);
        }
        month_TV.setText(monthMap.get(selectedDateCalendar.get(Calendar.MONTH)));
        calendarAdapter.updateBackupDateCalendar(selectedDateCalendar);
        refreshCalendar();
    }

    public void refreshCalendar() {
        calendarAdapter.refreshDays();
        calendarAdapter.notifyDataSetChanged();
    }

}
