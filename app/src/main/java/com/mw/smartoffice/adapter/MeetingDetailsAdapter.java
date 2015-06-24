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
public class MeetingDetailsAdapter extends BaseAdapter {

    private static final int VIEW1 = 0;
    private static final int VIEW2 = 1;
    private static final int VIEW_MAX_COUNT = VIEW2 + 1;

    MyApp myApp;
    Context context;

    LayoutInflater inflater;

    List<Meeting> meetingList;

    Map<String, Integer> colorMap;

    public MeetingDetailsAdapter(Context context, List<Meeting> meetingList) {
        super();
        this.context = context;
        this.meetingList = meetingList;
        System.out.println("meetingList size:  " + this.meetingList.size());
        myApp = (MyApp) context.getApplicationContext();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorMap = myApp.getColorMap();
    }

//    public void swapData(List<Appointment> appointmentList) {
//        this.appointmentList = appointmentList;
//        this.tempAppointmentList = new ArrayList<Appointment>();
//        this.tempAppointmentList.addAll(appointmentList);
//    }

    static class ViewHolder {
        /* Common */
        protected ImageView senderInitials_TV;
        protected TextView nameOrganiser_TV;
        protected TextView startDateTime_TV;

        /* Detailed view */
        protected TextView createdOnLabel_TV;

        protected TextView createdOn_TV;
        protected TextView email_TV;
        protected TextView attendees_TV;
        protected TextView duration_TV;
        protected TextView location_TV;
        protected TextView description_TV;

    }

    @Override
    public int getViewTypeCount() {
        return VIEW_MAX_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (meetingList.get(position).isChecked()) {
            return VIEW2;
        } else {
            return VIEW1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        int type = getItemViewType(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            switch (type) {
                case VIEW1:
                    convertView = inflater.inflate(R.layout.element_meeting_details_concise,
                            parent, false);
                    break;
                case VIEW2:
                    convertView = inflater.inflate(R.layout.element_meeting_details_detailed,
                            parent, false);
                    viewHolder.createdOnLabel_TV = (TextView) convertView
                            .findViewById(R.id.createdOnLabel_TV);

                    viewHolder.createdOn_TV = (TextView) convertView
                            .findViewById(R.id.createdOn_TV);
                    viewHolder.email_TV = (TextView) convertView
                            .findViewById(R.id.email_TV);
                    viewHolder.attendees_TV = (TextView) convertView
                            .findViewById(R.id.attendees_TV);
                    viewHolder.duration_TV = (TextView) convertView
                            .findViewById(R.id.duration_TV);
                    viewHolder.location_TV = (TextView) convertView
                            .findViewById(R.id.location_TV);
                    viewHolder.description_TV = (TextView) convertView
                            .findViewById(R.id.description_TV);

                    viewHolder.createdOn_TV.setTypeface(myApp.getTypefaceRegularLato());
                    viewHolder.email_TV.setTypeface(myApp.getTypefaceRegularLato());
                    viewHolder.attendees_TV.setTypeface(myApp.getTypefaceRegularLato());
                    viewHolder.duration_TV.setTypeface(myApp.getTypefaceRegularLato());
                    viewHolder.location_TV.setTypeface(myApp.getTypefaceRegularLato());
                    viewHolder.description_TV.setTypeface(myApp.getTypefaceRegularLato());
                    break;
                default:
                    break;
            }


            viewHolder.senderInitials_TV = (ImageView) convertView
                    .findViewById(R.id.senderInitials_IV);
            viewHolder.nameOrganiser_TV = (TextView) convertView
                    .findViewById(R.id.name_TV);
            viewHolder.startDateTime_TV = (TextView) convertView
                    .findViewById(R.id.startDateTime_TV);

            viewHolder.nameOrganiser_TV
                    .setTypeface(myApp.getTypefaceBoldLato());
            viewHolder.startDateTime_TV.setTypeface(myApp.getTypefaceRegularLato());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Meeting tempMeeting = meetingList.get(position);

        User meetingOwnerUser = myApp.findUserById(tempMeeting.getOwnerUserId());

        switch (type) {
            case VIEW2:

//                viewHolder.createdOnLabel_TV.setText();
                if (meetingOwnerUser != null) {
                    viewHolder.email_TV.setText(meetingOwnerUser.getEmail());
                }
                viewHolder.createdOn_TV.setText(tempMeeting.getCreatedOn().toString());

                String nameOfAttendees = null;
                List<String> tempUserIdList = new ArrayList<String>(tempMeeting.getUserIdList());

                for (int i = 0; i < myApp.getLoginUser().getUserList().size(); i++) {
                    for (int j = 0; j < tempUserIdList.size(); j++) {

                        if (myApp.getLoginUser().getUserList().get(i).getUserId().equalsIgnoreCase(tempUserIdList.get(j))) {
                            if (nameOfAttendees == null) {
                                nameOfAttendees = myApp.getLoginUser().getUserList().get(i).getName();
                            } else {
                                nameOfAttendees = nameOfAttendees + ", " + myApp.getLoginUser().getUserList().get(i).getName();
                            }
                            tempUserIdList.remove(j);
                            break;
                        }
                    }
                }


                viewHolder.attendees_TV.setText(nameOfAttendees);
//                viewHolder.duration_TV.setText(tempMeeting.getDuration());
                //TODO: put if check
                viewHolder.location_TV.setText(tempMeeting.getLocation());
                viewHolder.description_TV.setText(tempMeeting.getDescription());
                break;
            default:
                break;
        }


        if (meetingOwnerUser != null) {
            CharacterDrawable drawable = new CharacterDrawable(meetingOwnerUser.getName().charAt(0), colorMap.get(meetingOwnerUser.getName().charAt(0) + ""));
            viewHolder.senderInitials_TV.setImageDrawable(drawable);
            viewHolder.nameOrganiser_TV.setText(meetingOwnerUser.getName());
        }
        viewHolder.startDateTime_TV.setText(tempMeeting.getStartDate().toString());

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
