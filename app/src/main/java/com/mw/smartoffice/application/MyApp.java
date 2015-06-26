package com.mw.smartoffice.application;

import android.app.Application;
import android.graphics.Typeface;

import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pranav on 12/5/15.
 */
public class MyApp extends Application {

    User loginUser;

//    List<User> userList;
//    List<Meeting> meetingList;

    Map<String, Integer> colorMap;
    Map<Integer, String> monthMap;

    Typeface typefaceRegularLato;
    Typeface typefaceBoldLato;

    List<Meeting> todayMeetingList;

    private void initThings() {
//        userList = new ArrayList<User>();
//        meetingList = new ArrayList<Meeting>();

        String[] alphabets = getResources().getStringArray(R.array.alphabets);
        int[] hexCodes = getResources().getIntArray(R.array.hex_codes);
        colorMap = new HashMap<String, Integer>();
        for (int i = 0; i < alphabets.length; i++) {
            colorMap.put(alphabets[i], hexCodes[i]);
        }

        int[] monthCodes = getResources().getIntArray(R.array.month_codes);
        String[] months = getResources().getStringArray(R.array.months);
        monthMap = new HashMap<Integer, String>();
        for (int i = 0; i < monthCodes.length; i++) {
            monthMap.put(monthCodes[i], months[i]);
        }

//        typefaceRegularSans = Typeface.createFromAsset(getAssets(),
//                "fonts/SourceSansPro-Regular.ttf");
//        typefaceBoldSans = Typeface.createFromAsset(getAssets(),
//                "fonts/SourceSansPro-Semibold.ttf");

        typefaceRegularLato = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Regular.ttf");
        typefaceBoldLato = Typeface.createFromAsset(getAssets(),
                "fonts/Lato-Bold.ttf");
    }

    private void staticNonsense() {
     }

    @Override
    public void onCreate() {
        super.onCreate();
        initThings();
        staticNonsense();

        Parse.initialize(this, getResources().getString(R.string.parse_app_id),
                getResources().getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

//        ParseACL defaultACL = new ParseACL();
//
//        // If you would like all objects to be private by default, remove this
//        // line.
//        defaultACL.setPublicReadAccess(true);
//
//        ParseACL.setDefaultACL(defaultACL, true);
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public Map<String, Integer> getColorMap() {
        return colorMap;
    }

    public void setColorMap(Map<String, Integer> colorMap) {
        this.colorMap = colorMap;
    }

    public String getStringFromJson(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Typeface getTypefaceRegularLato() {
        return typefaceRegularLato;
    }

    public void setTypefaceRegularLato(Typeface typefaceRegularLato) {
        this.typefaceRegularLato = typefaceRegularLato;
    }

    public Typeface getTypefaceBoldLato() {
        return typefaceBoldLato;
    }

    public void setTypefaceBoldLato(Typeface typefaceBoldLato) {
        this.typefaceBoldLato = typefaceBoldLato;
    }

    public Map<Integer, String> getMonthMap() {
        return monthMap;
    }

    public void setMonthMap(Map<Integer, String> monthMap) {
        this.monthMap = monthMap;
    }

    public User findUserById(String userId) {
        for (int i = 0; i < loginUser.getUserList().size(); i++) {
            if (loginUser.getUserList().get(i).getUserId().equalsIgnoreCase(userId)) {
                return loginUser.getUserList().get(i);
            }
        }
        return null;
    }

    public Meeting convertMeetingPOToO(ParseObject meetingPO) {
        List<ParseUser> tempPUList = (ArrayList<ParseUser>) meetingPO.get("attendees");

        List<User> attendees = new ArrayList<User>();
        if (tempPUList != null) {
            for (int i = 0; i < tempPUList.size(); i++) {
                attendees.add(convertPUToO(tempPUList.get(i)));
            }
        }

        Meeting m = new Meeting(meetingPO.getObjectId(), meetingPO.getParseUser("createdBy"), meetingPO.getString("subject"), meetingPO.getString("description"), meetingPO.getString("location"), meetingPO.getCreatedAt(), meetingPO.getDate("startDate"), meetingPO.getDate("endDate"), null, null, attendees);
        System.out.println("check!@  : " + m.getAttendees().size());
        return m;
    }

    public ParseObject convertMeetingOToPO(Meeting meeting) {
        ParseObject parseObject = new ParseObject("Meeting");
        parseObject.put("createdBy", ParseUser.getCurrentUser());
        parseObject.put("subject", meeting.getSubject());
        parseObject.put("description", meeting.getDescription());
        parseObject.put("location", meeting.getLocation());
        parseObject.put("startDate", meeting.getStartDate());
        parseObject.put("endDate", meeting.getEndDate());
//        parseObject.put("startDate",);
//        parseObject.put("startDate",);
        return parseObject;
    }

    public User convertPUToO(ParseUser parseUser) {
        User u = new User(parseUser.getObjectId(), parseUser.getString("name"),parseUser.getUsername(), parseUser.getString("email"), parseUser.getString("designation"), null, parseUser.getString("mobileNo"), parseUser.getString("officeNo"));
        return u;
    }

    public ParseUser convertOToPU(User user) {
        ParseUser parseUser = new ParseUser();
        parseUser.setObjectId(user.getUserId());
//        parseUser.s
//        User u = new User(parseUser.getObjectId(), parseUser.getString("name"), parseUser.getString("email"), parseUser.getString("designation"), null, parseUser.getString("mobileNo"), parseUser.getString("officeNo"));
        return parseUser;
    }

    public List<Meeting> getTodayMeetingList() {
        return todayMeetingList;
    }

    public void setTodayMeetingList(List<Meeting> todayMeetingList) {
        this.todayMeetingList = todayMeetingList;
    }
}
