package com.mw.smartoffice.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mw.smartoffice.activity.MeetingAddActivity;
import com.mw.smartoffice.activity.MenuActivity;
import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.Constant;
import com.mw.smartoffice.util.DateFormatter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingService extends IntentService {

    MyApp myApp;
    Gson gson;

    List<Meeting> meetingList = new ArrayList<Meeting>();

    public MeetingService() {
        super("MeetingService");
    }


    DateFormatter formatter;

    @Override
    protected void onHandleIntent(Intent intent) {
        myApp = (MyApp) getApplicationContext();
        gson = new Gson();
        formatter = new DateFormatter();


        if (intent != null) {
            ParseQuery<ParseObject> query1;
            ParseQuery<ParseObject> query2;

            Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));
            query1 = ParseQuery.getQuery("Meeting");
            query1.whereEqualTo("attendees", ParseUser.getCurrentUser());

            query2 = ParseQuery.getQuery("Meeting");
            query2.whereEqualTo("createdBy", ParseUser.getCurrentUser());

            List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
            queries.add(query1);
            queries.add(query2);

            final List<Meeting> tempMeetingList = new ArrayList<Meeting>();

            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);

            mainQuery.include("attendees");
            mainQuery.orderByAscending("startDate");
            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> meetingList, ParseException e) {
                    Toast.makeText(MeetingService.this, "Meetings done" + meetingList.size(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < meetingList.size(); i++) {
                        tempMeetingList.add(myApp.convertMeetingPOToO(meetingList.get(i)));
                    }

//                    for (int i = 0; i < tempMeetingList.size() - 1; i++) {
////                        Date d1 = formatter.formatStringToDate2("2015-06-23 13:55:00");
////                        Date d2 = formatter.formatStringToDate2("2015-06-23 14:53:00");
//
//                        Date d1 = tempMeetingList.get(i).getStartDate();
//                        Date d2 = tempMeetingList.get(i + 1).getStartDate();
//
//                        long diff = d2.getTime() - d1.getTime();
//                        long diffMinutes = diff / (60 * 1000);
//                        long noOfInsertions = Math.abs(diffMinutes / (Constant.MINUTES_EXTRA / 2));
//                        System.out.println("#$ noOfInsertions : " + noOfInsertions);
//                        long t = d1.getTime();
//                        Date dateAfterAdding = null;
//                        int multiplier = 0;
//                        while (noOfInsertions >= 1) {
//                            System.out.println("ifif " + noOfInsertions);
//                            long delta1 = (Constant.MINUTES_EXTRA / 4) * Constant.ONE_MINUTE_IN_MILLIS;
//                            long delta2 = (Constant.MINUTES_EXTRA / 2) * multiplier * Constant.ONE_MINUTE_IN_MILLIS;
//                            dateAfterAdding = new Date(t + delta1 + delta2);
//                            System.out.println("dateAfterAdding " + dateAfterAdding);
//
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.setTime(dateAfterAdding);
//                            int minutes = calendar.get(Calendar.MINUTE);
//
//                            if (minutes < 8) {
//                                calendar.set(Calendar.MINUTE, 15);
//                                calendar.set(Calendar.SECOND, 0);
//                            } else if (minutes < 23) {
//                                calendar.set(Calendar.MINUTE, 30);
//                                calendar.set(Calendar.SECOND, 0);
//                            } else if (minutes < 38) {
//                                calendar.set(Calendar.MINUTE, 45);
//                                calendar.set(Calendar.SECOND, 0);
//                            } else if (minutes < 53) {
//                                calendar.set(Calendar.MINUTE, 0);
//                                calendar.set(Calendar.SECOND, 0);
//                                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 1);
//                            }
//                            dateAfterAdding = calendar.getTime();
//                            System.out.println("dateAfterAdding " + dateAfterAdding);
//                            tempMeetingList.add(i++ + 1, new Meeting(dateAfterAdding, true));
//                            multiplier++;
//                            noOfInsertions--;
//                        }//while
//
//                    }//for

                    System.out.println("tempMeetingList.size()" + tempMeetingList.size());
                    Toast.makeText(MeetingService.this, "tempMeetingList.size()" + tempMeetingList.size(), Toast.LENGTH_SHORT).show();


                    myApp.getLoginUser().setMeetingList(tempMeetingList);
                    onRequestComplete();
                }
            });

        }
    }

    private void onRequestComplete() {
        if (MenuActivity.isActivityVisible) {
            Intent nextIntent = new Intent("app_data");
            LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
        }else if (MeetingAddActivity.isActivityVisible) {
            Intent nextIntent = new Intent("meeting_update_receiver");
            LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
        }
    }
}
