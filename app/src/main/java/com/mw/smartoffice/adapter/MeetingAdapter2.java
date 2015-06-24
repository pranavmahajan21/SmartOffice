package com.mw.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pranav on 11/5/15.
 */
public class MeetingAdapter2 extends BaseAdapter {

    MyApp myApp;
    Context context;

    DateFormatter formatter;
    LayoutInflater inflater;

    Map<String, List<Meeting>> meetingMap;
    List<Meeting> meetingList;

    Map<String, Integer> colorMap;

    public MeetingAdapter2(Context context, Map<String, List<Meeting>> meetingMap, List<Meeting> meetingList) {
        super();
        this.context = context;
        this.meetingMap = meetingMap;
        this.meetingList = meetingList;
        System.out.println("map size:  " + this.meetingMap.size());
        myApp = (MyApp) context.getApplicationContext();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new DateFormatter();

        colorMap = myApp.getColorMap();
    }

//    public void swapData(List<Appointment> appointmentList) {
//        this.appointmentList = appointmentList;
//        this.tempAppointmentList = new ArrayList<Appointment>();
//        this.tempAppointmentList.addAll(appointmentList);
//    }

    static class ViewHolder {
        protected TextView startDateTime_TV;
        protected TextView subject_TV;
        protected TextView attendees_TV;
        protected TextView noMeeting_TV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.element_meeting_list2,
                    parent, false);

            viewHolder.startDateTime_TV = (TextView) convertView
                    .findViewById(R.id.startDateTime_TV);
            viewHolder.subject_TV = (TextView) convertView
                    .findViewById(R.id.subject_TV);
            viewHolder.attendees_TV = (TextView) convertView
                    .findViewById(R.id.attendees_TV);
            viewHolder.noMeeting_TV = (TextView) convertView
                    .findViewById(R.id.noMeeting_TV);

            viewHolder.startDateTime_TV.setTypeface(myApp.getTypefaceBoldLato());
            viewHolder.subject_TV.setTypeface(myApp.getTypefaceBoldLato());
            viewHolder.attendees_TV.setTypeface(myApp.getTypefaceRegularLato());
            viewHolder.noMeeting_TV.setTypeface(myApp.getTypefaceRegularLato());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        List<String> aa = new ArrayList<String>(appointmentList.keySet());
//        Meeting tempMeeting = meetingMap.get(new ArrayList<String>(meetingMap.keySet()).get(position)).get(0);
        Meeting tempMeeting = meetingList.get(position);
//        User meetingOwnerUser = myApp.findUserById(tempMeeting.getOwnerUserId());
        if (tempMeeting.isEmptyMeeting()) {
            viewHolder.noMeeting_TV.setVisibility(View.VISIBLE);
            viewHolder.subject_TV.setVisibility(View.GONE);
            viewHolder.attendees_TV.setVisibility(View.GONE);

            viewHolder.startDateTime_TV.setText(formatter.formatDateToString3(tempMeeting.getStartDate()));
        } else {
            viewHolder.noMeeting_TV.setVisibility(View.GONE);
            viewHolder.subject_TV.setVisibility(View.VISIBLE);
            viewHolder.attendees_TV.setVisibility(View.VISIBLE);

            viewHolder.startDateTime_TV.setText(formatter.formatDateToString3(tempMeeting.getStartDate()));
            viewHolder.subject_TV.setText(tempMeeting.getSubject());
            String attendees = null;

//        for (int i = 0; i < tempMeeting.getUserIdList().size(); i++) {
//            User tempUser = myApp.findUserById(tempMeeting.getUserIdList().get(i));
//            if (tempUser != null) {
//                if (attendees == null) {
//                    attendees = tempUser.getName();
//                } else {
//                    attendees = attendees + ", " + tempUser.getName();
//                }
//            }
//        }

            for (int i = 0; i < tempMeeting.getAttendees().size(); i++) {
                User tempUser = tempMeeting.getAttendees().get(i);
                if (tempUser != null) {
                    if (attendees == null) {
                        attendees = tempUser.getName();
                    } else {
                        attendees = attendees + ", " + tempUser.getName();
                    }
                }
            }

            viewHolder.attendees_TV.setText(attendees);
        }



        return convertView;
    }

    @Override
    public int getCount() {
        return meetingList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        appointmentList.clear();
//        if (charText.length() == 0) {
//            appointmentList.addAll(tempAppointmentList);
//        } else {
//            for (Appointment tempAppointment : tempAppointmentList) {
//                if (tempAppointment.getNameOfTheClientOfficial()
//                        .toLowerCase(Locale.getDefault()).contains(charText)
//                        || tempAppointment.getPurposeOfMeeting()
//                        .toLowerCase(Locale.getDefault())
//                        .contains(charText)) {
//                    appointmentList.add(tempAppointment);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}
