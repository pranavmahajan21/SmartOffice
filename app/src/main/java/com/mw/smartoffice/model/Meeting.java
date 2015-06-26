package com.mw.smartoffice.model;

import com.google.gson.annotations.SerializedName;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pranav on 11/5/15.
 */
public class Meeting implements Serializable {

    @SerializedName("meeting_id")
    String meetingId;

    @SerializedName("owner_user_id")
    String ownerUserId;

    String subject, description, location;

//    @SerializedName("created_on")
//    String createdOn;

    Date startDate, endDate, createdOn;

    @SerializedName("parent_meeting_id")
    String parentMeetingId;

    @SerializedName("userList")
    List<String> userIdList;

    List<User> attendees = new ArrayList<User>();

    /**
     * Used on MeetingDetails Page
     */
    boolean isChecked;

    boolean isEmptyMeeting;

    ParseUser createdBy;

    public Meeting(String meetingId, ParseUser createdBy, String subject, String description, String location, Date createdOn, Date startDate, Date endDate, String duration, String parentMeetingId, List<User> attendees) {
        this.meetingId = meetingId;
        this.createdBy = createdBy;
        this.subject = subject;
        this.description = description;
        this.location = location;

        this.createdOn = createdOn;
        this.startDate = startDate;
        this.endDate = endDate;

        this.parentMeetingId = parentMeetingId;

        this.attendees = attendees;

        this.isEmptyMeeting = false;

        userIdList = new ArrayList<String>();
        userIdList.add("user1");
        userIdList.add("user2");
        userIdList.add("user3");
        userIdList.add("user4");
    }

    public Meeting(Date startDate,boolean isEmptyMeeting){
        this.startDate = startDate;
        this.isEmptyMeeting = true;
    }

//    public void setDates() {
//        this.createdOn = new Date();
//        this.startDate = new Date();
//        this.endDate = new Date();
//    }


    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public String getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(String createdOn) {
//        this.createdOn = createdOn;
//    }
//
//    public String getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    public String getDuration() {
//        return duration;
//    }
//
//    public void setDuration(String duration) {
//        this.duration = duration;
//    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getParentMeetingId() {
        return parentMeetingId;
    }

    public void setParentMeetingId(String parentMeetingId) {
        this.parentMeetingId = parentMeetingId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public ParseUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ParseUser createdBy) {
        this.createdBy = createdBy;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public boolean isEmptyMeeting() {
        return isEmptyMeeting;
    }

    public void setEmptyMeeting(boolean isEmptyMeeting) {
        this.isEmptyMeeting = isEmptyMeeting;
    }

}
