package com.mw.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.CharacterDrawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pranav on 11/5/15.
 */
public class MeetingAdapter extends BaseAdapter {

    MyApp myApp;
    Context context;

    LayoutInflater inflater;

    Map<String, List<Meeting>> meetingMap;

    Map<String, Integer> colorMap;

    public MeetingAdapter(Context context, Map<String, List<Meeting>> meetingMap) {
        super();
        this.context = context;
        this.meetingMap = meetingMap;
        System.out.println("map size:  " + this.meetingMap.size());
        myApp = (MyApp) context.getApplicationContext();

        colorMap = myApp.getColorMap();
    }

//    public void swapData(List<Appointment> appointmentList) {
//        this.appointmentList = appointmentList;
//        this.tempAppointmentList = new ArrayList<Appointment>();
//        this.tempAppointmentList.addAll(appointmentList);
//    }

    static class ViewHolder {
        protected ImageView senderInitials_IV;
        protected TextView nameOrganiser_TV;
        protected TextView subject_TV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.element_meeting_list,
                    parent, false);

            viewHolder.senderInitials_IV = (ImageView) convertView
                    .findViewById(R.id.senderInitials_IV);
            viewHolder.nameOrganiser_TV = (TextView) convertView
                    .findViewById(R.id.nameOrganiser_TV);
            viewHolder.subject_TV = (TextView) convertView
                    .findViewById(R.id.subject_TV);

//            viewHolder.senderInitials_TV.setTypeface(myApp.getTypefaceBoldSans());
            viewHolder.nameOrganiser_TV
                    .setTypeface(myApp.getTypefaceBoldLato());
            viewHolder.subject_TV.setTypeface(myApp.getTypefaceRegularLato());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        List<String> aa = new ArrayList<String>(appointmentList.keySet());
        Meeting tempMeeting = meetingMap.get(new ArrayList<String>(meetingMap.keySet()).get(position)).get(0);
        User meetingOwnerUser = myApp.findUserById(tempMeeting.getOwnerUserId());

        viewHolder.nameOrganiser_TV.setText("Pranav Mahajan");
        viewHolder.subject_TV.setText(tempMeeting.getSubject());

        CharacterDrawable drawable = new CharacterDrawable(meetingOwnerUser.getName().charAt(0), colorMap.get(meetingOwnerUser.getName().charAt(0) + ""));
        viewHolder.senderInitials_IV.setImageDrawable(drawable);

        return convertView;
    }

    @Override
    public int getCount() {
        return meetingMap.size();
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
