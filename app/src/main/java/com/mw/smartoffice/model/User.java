package com.mw.smartoffice.model;

import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pranav on 11/5/15.
 */
public class User implements Serializable {

    @SerializedName("user_id")
    String userId;
    String name;
    String username;
    String email;
    String designation;
    String department;

    String mobileNo;
    String officeNo;

    /**
     * Used in Header ListView *
     */
    boolean isUserRajnikant;

    List<User> userList = new ArrayList<User>();
    List<Meeting> meetingList;

    Map<String, List<Meeting>> meetingMap;

    boolean isChecked;

    public User(String name, boolean isUserRajnikant) {
        this.name = name;
        this.isUserRajnikant = isUserRajnikant;
    }

    public User(String userId, String name, String username, String email, String designation, String department, String mobileNo, String officeNo) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.designation = designation;
        this.department = department;
        this.mobileNo = mobileNo;
        this.officeNo = officeNo;

        meetingMap = new HashMap<String, List<Meeting>>();
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList = meetingList;

        for (int i = 0; i < this.meetingList.size(); i++) {
            Meeting tempMeeting = this.meetingList.get(i);
            if (tempMeeting.getParentMeetingId() != null) {
                System.out.println("Follow-up meeting  " + i);
                /** Follow-up meeting **/
                if (meetingMap.containsKey(tempMeeting.getParentMeetingId())) {
                    List<Meeting> childMeetingList = meetingMap.get(tempMeeting.getParentMeetingId());
                    childMeetingList.add(tempMeeting);
                    meetingMap.put(tempMeeting.getParentMeetingId(), childMeetingList);
                } else {
                    List<Meeting> childMeetingList = new ArrayList<Meeting>();
                    childMeetingList.add(tempMeeting);
                    meetingMap.put(tempMeeting.getParentMeetingId(), childMeetingList);
                }
            } else {
                System.out.println("First meeting  " + i);
                /** First meeting **/
                if (meetingMap.containsKey(tempMeeting.getParentMeetingId())) {
                    List<Meeting> childMeetingList = meetingMap.get(tempMeeting.getParentMeetingId());
                    childMeetingList.add(tempMeeting);
                    meetingMap.put(tempMeeting.getMeetingId(), childMeetingList);
                } else {
                    List<Meeting> childMeetingList = new ArrayList<Meeting>();
                    childMeetingList.add(tempMeeting);
                    meetingMap.put(tempMeeting.getMeetingId(), childMeetingList);
                }
            }
        }
        System.out.println("meetingMap  :  " + meetingMap.size());
    }

    public Map<String, List<Meeting>> getMeetingMap() {
        return meetingMap;
    }

    public void setMeetingMap(Map<String, List<Meeting>> meetingMap) {
        this.meetingMap = meetingMap;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean isUserRajnikant() {
        return isUserRajnikant;
    }

    public void setUserRajnikant(boolean isUserRajnikant) {
        this.isUserRajnikant = isUserRajnikant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
