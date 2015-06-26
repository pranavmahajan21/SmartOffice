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

import java.util.ArrayList;
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

    List<Meeting> allMeetingList;
    List<Meeting> todayMeetingList = new ArrayList<Meeting>();

    private void initDates(Calendar calendar) {
        d1 = calendar.getTime();
        d1 = formatter.formatStringToDate(formatter.formatDateToString(d1));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        d2 = calendar.getTime();
        d2 = formatter.formatStringToDate(formatter.formatDateToString(d2));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        System.out.println("Date range  : " + d1 + "   " + d2);
    }

    private void initTodayMeetingList() {
        System.out.println("Meeting Size : " + allMeetingList.size());
        for (int i = 0; i < allMeetingList.size(); i++) {
            System.out.println("Meeting Dates : " + allMeetingList.get(i).getStartDate());
        }
        int x = binary_search(allMeetingList, 0, allMeetingList.size() - 1);

        if (x != -1) {
            int low = x - 1;
            int high = x + 1;

            while (low >= 0 && allMeetingList.get(low).getStartDate().compareTo(d1) > 0 && d2.compareTo(allMeetingList.get(low).getStartDate()) > 0) {
                low--;
            }
            while (high < allMeetingList.size() && allMeetingList.get(high).getStartDate().compareTo(d1) > 0 && d2.compareTo(allMeetingList.get(high).getStartDate()) > 0) {
                high++;
            }

            Toast.makeText(this, "x : " + x + "low : " + low + "high : " + high, Toast.LENGTH_LONG).show();
            System.out.println("x : " + x + "low : " + low + "high : " + high);

            /** We have to exclude the lowest & highest point. Therefore we
             * start from low+1 & end before high **/
            for (int i = low + 1; i < high; i++) {
                todayMeetingList.add(allMeetingList.get(i));
            }

            if(todayMeetingList.size() <1){
                Toast.makeText(this, "No meetings scheduled for today", Toast.LENGTH_SHORT).show();
            }

            Date officeStartTime = formatter.formatStringToDate2(formatter.formatDateToString(new Date()) + Constant.OFFICE_START_TIME);
            Date officeEndTime = formatter.formatStringToDate2(formatter.formatDateToString(new Date()) + Constant.OFFICE_END_TIME);

            if ((todayMeetingList.get(0).getStartDate().getTime() - officeStartTime.getTime()) / (60 * 1000) > 15) {
                todayMeetingList.add(0, new Meeting(officeStartTime, true));
            }
            if ((officeEndTime.getTime() - todayMeetingList.get(0).getStartDate().getTime()) / (60 * 1000) > 15) {
                todayMeetingList.add(todayMeetingList.size(), new Meeting(officeEndTime, true));
            }

            addEmptyMeetings();
        }
    }

    int binary_search(List<Meeting> meetingList, int min, int max) {
        if (max < min)
            return -1;
        else {
            int imid = (min + max) / 2;

            if (d1.compareTo(meetingList.get(imid).getStartDate()) > 0)
                // key is in lower subset
                return binary_search(meetingList, imid + 1, max);
            else if (meetingList.get(imid).getStartDate().compareTo(d2) > 0)
                // key is in upper subset
                return binary_search(meetingList, min, imid - 1);
            else
                // key has been found
                return imid;
        }
    }

    private void addEmptyMeetings() {
        for (int i = 0; i < todayMeetingList.size() - 1; i++) {
//                        Date d1 = formatter.formatStringToDate2("2015-06-23 13:55:00");
//                        Date d2 = formatter.formatStringToDate2("2015-06-23 14:53:00");

            Date d1 = todayMeetingList.get(i).getStartDate();
            Date d2 = todayMeetingList.get(i + 1).getStartDate();

            long diff = d2.getTime() - d1.getTime();
            long diffMinutes = diff / (60 * 1000);
            long noOfInsertions = Math.abs(diffMinutes / (Constant.MINUTES_EXTRA / 2)) - 1;
            System.out.println("#$ noOfInsertions : " + noOfInsertions);

            long t = d1.getTime();
            Date dateAfterAdding = null;
            int multiplier = 0;
            while (noOfInsertions >= 1) {
//                System.out.println("ifif " + noOfInsertions);
                if (noOfInsertions == 2 || noOfInsertions == 1) {
                }

                long delta1 = (Constant.MINUTES_EXTRA / 4) * Constant.ONE_MINUTE_IN_MILLIS;
                long delta2 = (Constant.MINUTES_EXTRA / 2) * multiplier * Constant.ONE_MINUTE_IN_MILLIS;
                dateAfterAdding = new Date(t + delta1 + delta2);
                System.out.println("dateAfterAdding " + dateAfterAdding);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateAfterAdding);
                int minutes = calendar.get(Calendar.MINUTE);

                // eg. 12:48

                if (minutes < 8) {
                    calendar.set(Calendar.MINUTE, 15);
                    calendar.set(Calendar.SECOND, 0);
                } else if (minutes < 23) {
                    calendar.set(Calendar.MINUTE, 30);
                    calendar.set(Calendar.SECOND, 0);
                } else if (minutes < 38) {
                    calendar.set(Calendar.MINUTE, 45);
                    calendar.set(Calendar.SECOND, 0);
                } else if (minutes < 53) {
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
                } else {
                    calendar.set(Calendar.MINUTE, 15);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
                }

                dateAfterAdding = calendar.getTime();
                System.out.println("dateAfterAdding " + dateAfterAdding);
                todayMeetingList.add(i++ + 1, new Meeting(dateAfterAdding, true));
                multiplier++;
                noOfInsertions--;
            }//while
        }//for
        myApp.setTodayMeetingList(todayMeetingList);
    }

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        meetingMap = myApp.getLoginUser().getMeetingMap();

        allMeetingList = myApp.getLoginUser().getMeetingList();

        /** Calendar stuff **/
        selectedDateCalendar = Calendar.getInstance();
        monthMap = myApp.getMonthMap();

        formatter = new DateFormatter();
        dateComparator = new DateComparatorIgnoringTime();

        // date format is yyyy-mm-dd
        String[] dateArr = formatter.formatDateToString(new Date()).split("-");
        selectedDateCalendar.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]));

        initDates(selectedDateCalendar);

        calendarAdapter = new CalendarAdapter(this, selectedDateCalendar);
        /** Calendar stuff **/


//        int x = binary_search(allMeetingList, 0, allMeetingList.size() - 1);
//        int low = x - 1;
//        int high = x + 1;
//
//        while (low >= 0 && allMeetingList.get(low).getStartDate().compareTo(d1) > 0 && d2.compareTo(allMeetingList.get(low).getStartDate()) > 0) {
//            low--;
//        }
//        while (high < allMeetingList.size() && allMeetingList.get(high).getStartDate().compareTo(d1) > 0 && d2.compareTo(allMeetingList.get(high).getStartDate()) > 0) {
//            high++;
//        }
//
//        /** We have to exclude the lowest & highest point. Therefore we
//         * start from low+1 & end before high **/
//        for (int i = low + 1; i < high; i++) {
//            todayMeetingList.add(allMeetingList.get(i));
//        }
        initTodayMeetingList();

        /** Add empty meetings **/
//        for (int i = 0; i < todayMeetingList.size() - 1; i++) {
////                        Date d1 = formatter.formatStringToDate2("2015-06-23 13:55:00");
////                        Date d2 = formatter.formatStringToDate2("2015-06-23 14:53:00");
//
//            Date d1 = todayMeetingList.get(i).getStartDate();
//            Date d2 = todayMeetingList.get(i + 1).getStartDate();
//
//            long diff = d2.getTime() - d1.getTime();
//            long diffMinutes = diff / (60 * 1000);
//            long noOfInsertions = Math.abs(diffMinutes / (Constant.MINUTES_EXTRA / 2));
//            System.out.println("#$ noOfInsertions : " + noOfInsertions);
//            long t = d1.getTime();
//            Date dateAfterAdding = null;
//            int multiplier = 0;
//            while (noOfInsertions >= 1) {
//                System.out.println("ifif " + noOfInsertions);
//                long delta1 = (Constant.MINUTES_EXTRA / 4) * Constant.ONE_MINUTE_IN_MILLIS;
//                long delta2 = (Constant.MINUTES_EXTRA / 2) * multiplier * Constant.ONE_MINUTE_IN_MILLIS;
//                dateAfterAdding = new Date(t + delta1 + delta2);
//                System.out.println("dateAfterAdding " + dateAfterAdding);
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(dateAfterAdding);
//                int minutes = calendar.get(Calendar.MINUTE);
//
//                if (minutes < 8) {
//                    calendar.set(Calendar.MINUTE, 15);
//                    calendar.set(Calendar.SECOND, 0);
//                } else if (minutes < 23) {
//                    calendar.set(Calendar.MINUTE, 30);
//                    calendar.set(Calendar.SECOND, 0);
//                } else if (minutes < 38) {
//                    calendar.set(Calendar.MINUTE, 45);
//                    calendar.set(Calendar.SECOND, 0);
//                } else if (minutes < 53) {
//                    calendar.set(Calendar.MINUTE, 0);
//                    calendar.set(Calendar.SECOND, 0);
//                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
//                }
//                dateAfterAdding = calendar.getTime();
//                System.out.println("dateAfterAdding " + dateAfterAdding);
//                todayMeetingList.add(i++ + 1, new Meeting(dateAfterAdding, true));
//                multiplier++;
//                noOfInsertions--;
//            }//while
//
//        }//for


        if (todayMeetingList != null && todayMeetingList.size() > 0) {
            adapter = new MeetingAdapter2(this, meetingMap, todayMeetingList);
        }

    }

    Date d1, d2;


    private void findThings() {
        meeting_LV = (ListView) findViewById(R.id.meeting_LV);

        calendar_RL = (RelativeLayout) findViewById(R.id.calendar_RL);
        calendar_GV = (GridView) findViewById(R.id.calendar_GV);
        month_TV = (TextView) findViewById(R.id.month_TV);
    }

    private void initView() {
//        if (adapter != null) {
        meeting_LV.setAdapter(adapter);
//        }

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

//                Toast.makeText(MeetingListActivity2.this, "position before : " + position, Toast.LENGTH_SHORT).show();

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

                    todayMeetingList = new ArrayList<Meeting>();
                    adapter = null;
                    initDates(selectedDateCalendar);
                    initTodayMeetingList();
                    if (todayMeetingList != null && todayMeetingList.size() > 0) {
                        adapter = new MeetingAdapter2(MeetingListActivity2.this, meetingMap, todayMeetingList);
                    }
//                    if (adapter != null) {
                    meeting_LV.setAdapter(adapter);
//                    }

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
