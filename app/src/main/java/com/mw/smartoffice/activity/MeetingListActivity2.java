package com.mw.smartoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mw.smartoffice.adapter.CalendarAdapter;
import com.mw.smartoffice.adapter.MeetingAdapter2;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.util.Constant;
import com.mw.smartoffice.util.DateComparatorIgnoringTime;
import com.mw.smartoffice.util.DateFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MeetingListActivity2 extends AndhraActivity {

    MyApp myApp;

    Map<String, List<Meeting>> meetingMap;
    MeetingAdapter2 adapter;
    ListView meeting_LV;

    RelativeLayout calendar_RL;

    Intent nextIntent;

    /**
     * Calendar stuff *
     */
    GridView calendar_GV;
    TextView month_TV;

    Map<Integer, String> monthMap;

    public Calendar selectedDateCalendar;
    public CalendarAdapter calendarAdapter;

    DateFormatter formatter;
    DateComparatorIgnoringTime dateComparator;

    /**
     * Calendar stuff *
     */

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        meetingMap = myApp.getLoginUser().getMeetingMap();

        if (meetingMap != null && meetingMap.size() > 0) {
            adapter = new MeetingAdapter2(this, meetingMap, myApp.getLoginUser().getMeetingList());
        }

        /** Calendar stuff **/
        selectedDateCalendar = Calendar.getInstance();
        monthMap = myApp.getMonthMap();

        formatter = new DateFormatter();
        dateComparator = new DateComparatorIgnoringTime();

        // date format is yyyy-mm-dd
        String[] dateArr = formatter.formatDateToString(new Date()).split("-");
        selectedDateCalendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));

        calendarAdapter = new CalendarAdapter(this, selectedDateCalendar);
        /** Calendar stuff **/
    }

    private void findThings() {
        meeting_LV = (ListView) findViewById(R.id.meeting_LV);

        calendar_RL = (RelativeLayout) findViewById(R.id.calendar_RL);
        calendar_GV = (GridView) findViewById(R.id.calendar_GV);
        month_TV = (TextView) findViewById(R.id.month_TV);
    }

    private void initView() {
        if (adapter != null) {
            meeting_LV.setAdapter(adapter);
        }

        dateHeader_TV.setText("Today");

        month_TV.setText(monthMap.get(selectedDateCalendar.get(Calendar.MONTH)));
        if (calendarAdapter != null) {
            calendar_GV.setAdapter(calendarAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
//        super.setToolbar("Meetings List", View.VISIBLE, View.VISIBLE, View.VISIBLE, null);
        super.setToolbar("Meetings", Constant.CHILD_CALENDAR);

        initThings();
        findThings();
        initView();

        meeting_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                nextIntent = new Intent(MeetingListActivity.this, MeetingDetailsActivity.class);
//                nextIntent.putExtra("position", position);
//                startActivity(nextIntent);

                Toast.makeText(MeetingListActivity2.this, "position before : " + position, Toast.LENGTH_SHORT).show();

                nextIntent = new Intent(MeetingListActivity2.this, MeetingAddActivity.class);
                nextIntent.putExtra("position", position);
                startActivityForResult(nextIntent, Constant.MEETING_EXISTING);
            }
        });

        calendar_GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView date_TV = (TextView) v.findViewById(R.id.date_TV);
                if (date_TV instanceof TextView && !date_TV.getText().equals("")) {

//                    Intent intent = new Intent();
                    String day = date_TV.getText().toString();
                    if (day.length() == 1) {
                        day = "0" + day;
                    }

                    // return chosen date as string format
                    String temp = android.text.format.DateFormat.format("yyyy-MM", selectedDateCalendar) + "-" + day;
                    System.out.println("temp : " + temp);

                    /** Delta2 **/
                    selectedDateCalendar.set(Calendar.DATE, Integer.parseInt(day));

                    Date tempDate = selectedDateCalendar.getTime();
                    int x = dateComparator.compare(tempDate, new Date());
                    if (x == 0) {
                        dateHeader_TV.setText("Today");
                    } else if (x == 1) {
                        dateHeader_TV.setText("Tomorrow");
                    } else {
                        dateHeader_TV.setText(formatter.formatDateToString2(selectedDateCalendar.getTime()));
                    }

//                    selectedDateCalendar.set(Calendar.MONTH, selectedDateCalendar.get(Calendar.MONTH) + 1);
                    calendarAdapter.updateBackupDateCalendar(selectedDateCalendar);
//                    adapter.refreshDays();
//                    adapter.notifyDataSetChanged();
                    System.out.println("fu1  : " + selectedDateCalendar);
                    calendarAdapter = new CalendarAdapter(MeetingListActivity2.this, selectedDateCalendar);
                    System.out.println("fu2  : " + selectedDateCalendar);
                    calendar_GV.setAdapter(calendarAdapter);
                    /** Delta2 **/

                    System.out.println("fu3  : " + selectedDateCalendar);

                    isCalendarVisible = false;
                    calendar_RL.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        /**
         * Put this function in parent class maybe
         **/
        intent.putExtra("request_code", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    boolean isCalendarVisible = false;

    public void onShowCalendar(View view) {
        isCalendarVisible = !isCalendarVisible;
        if (isCalendarVisible) {
            calendar_RL.setVisibility(View.VISIBLE);
        } else {
            calendar_RL.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRight3(View view) {
        nextIntent = new Intent(this, MeetingAddActivity.class);
        startActivityForResult(nextIntent, Constant.MEETING_NEW);
    }

    public void onAddMeeting(View view) {
        nextIntent = new Intent(this, MeetingAddActivity.class);
        startActivityForResult(nextIntent, Constant.MEETING_NEW);
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
