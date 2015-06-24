package com.mw.smartoffice.util;

/**
 * Created by pranav on 19/5/15.
 */
public class Constant {

    public static String BASE_URL = "http://192.168.1.39:8081/AndhraApp/api";
    public static String MEETINGS_DATA = "/meeting/get_meetings";

    public static String TEMP_MEETING_CRAP = "?userId=79db3abe-036b-11e5-a2ea-b8ac6fa5bad5";
    public static String MEETING_CREATE = "/meeting/create_meeting";
    public static String USERS_DATA = "/user/get_users";

    public static final int SELECT_USER_ACTIVITY = 1000;

    public static final int MEETING_NEW = 1001;
    public static final int MEETING_EXISTING = 1002;
    public static final int MEETING_FOLLOW = 1003;

    public static final int CHILD_CALENDAR = 1010;
    public static final int CHILD_BUTTON_MEETING_ADD = 2000;
    public static final int CHILD_BUTTON_MEETING_EDIT = 2001;
    public static final int CHILD_BUTTON_SELECT_USER = 1101;

    public static final int MINUTES_EXTRA = 30;

    public static final long ONE_MINUTE_IN_MILLIS=60000;
}
