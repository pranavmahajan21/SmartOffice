package com.mw.smartoffice.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.service.MeetingService;
import com.mw.smartoffice.util.Constant;
import com.mw.smartoffice.util.CreateDialog;
import com.mw.smartoffice.util.DateFormatter;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingAddActivity extends AndhraActivity {

    public static boolean isActivityVisible = false;

    EditText subject_ET, description_ET, location_ET;

    TextView startLabel_TV, endLabel_TV, followUpLabel_TV, inviteesLabel_TV;
    TextView startDateTime_TV, endDateTime_TV;

    RelativeLayout startDateTime_RL, endDateTime_RL, invitees_RL;

    TextView user_TV;
    RadioButton yes_RB, no_RB;

    Meeting selectedMeeting;

    MyApp myApp;
    Intent previousIntent, nextIntent;

    int requestCode;

//    RequestQueue queue;

    CreateDialog createDialog;
    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    DateFormatter formatter;

    private BroadcastReceiver meetingUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progressDialog.dismiss();
        }
    };

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        previousIntent = getIntent();

        requestCode = previousIntent.getIntExtra("request_code", -1);

        if (requestCode == Constant.MEETING_EXISTING) {
//            selectedMeeting = new ArrayList<List<Meeting>>(myApp.getLoginUser().getMeetingMap().values()).get(previousIntent.getIntExtra("position", 0)).get(0);
            selectedMeeting = myApp.getTodayMeetingList().get(previousIntent.getIntExtra("position", 0));
            Toast.makeText(this, "position after : " + selectedMeeting.getMeetingId(), Toast.LENGTH_SHORT).show();
        }

        createDialog = new CreateDialog(this);
        progressDialog = createDialog.createProgressDialog("Saving Changes",
                "This may take some time", true, null);

        formatter = new DateFormatter();
//        queue = Volley.newRequestQueue(this);
    }

    public void findThings() {

        startLabel_TV = (TextView) findViewById(R.id.startLabel_TV);
        endLabel_TV = (TextView) findViewById(R.id.endLabel_TV);
        followUpLabel_TV = (TextView) findViewById(R.id.followUpLabel_TV);
        inviteesLabel_TV = (TextView) findViewById(R.id.inviteesLabel_TV);

        subject_ET = (EditText) findViewById(R.id.subject_ET);
        location_ET = (EditText) findViewById(R.id.location_ET);
        user_TV = (TextView) findViewById(R.id.user_TV);
        description_ET = (EditText) findViewById(R.id.description_ET);

        startDateTime_TV = (TextView) findViewById(R.id.startDateTime_TV);
        endDateTime_TV = (TextView) findViewById(R.id.endDateTime_TV);

        yes_RB = (RadioButton) findViewById(R.id.yes_RB);
        no_RB = (RadioButton) findViewById(R.id.no_RB);

        startDateTime_RL = (RelativeLayout) findViewById(R.id.startDateTime_RL);
        endDateTime_RL = (RelativeLayout) findViewById(R.id.endDateTime_RL);
        invitees_RL = (RelativeLayout) findViewById(R.id.invitees_RL);
    }

    private void setTypeface() {
        startLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        endLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        followUpLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        inviteesLabel_TV.setTypeface(myApp.getTypefaceBoldLato());

        subject_ET.setTypeface(myApp.getTypefaceRegularLato());
        location_ET.setTypeface(myApp.getTypefaceRegularLato());
        user_TV.setTypeface(myApp.getTypefaceRegularLato());
        description_ET.setTypeface(myApp.getTypefaceRegularLato());

        startDateTime_TV.setTypeface(myApp.getTypefaceRegularLato());
        endDateTime_TV.setTypeface(myApp.getTypefaceRegularLato());

        yes_RB.setTypeface(myApp.getTypefaceRegularLato());
        no_RB.setTypeface(myApp.getTypefaceRegularLato());
    }

    public void initView() {
        setTypeface();
        if (requestCode == Constant.MEETING_NEW) {
            super.setToolbar("Create Meeting", Constant.CHILD_BUTTON_MEETING_ADD);
            no_RB.setChecked(true);
            staticNonsense();
        } else if (requestCode == Constant.MEETING_EXISTING) {
            if (selectedMeeting.isEmptyMeeting()) {
                super.setToolbar("Create Meeting", Constant.CHILD_BUTTON_MEETING_ADD);
                startDateTime_TV.setText(formatter.formatDateToString4(selectedMeeting.getStartDate()));
            }else {
                super.setToolbar("Create Meeting", Constant.CHILD_BUTTON_MEETING_EDIT);
                yes_RB.setChecked(true);

                subject_ET.setEnabled(false);
                description_ET.setEnabled(false);
                location_ET.setEnabled(false);

                startDateTime_RL.setClickable(false);
                endDateTime_RL.setClickable(false);
                invitees_RL.setClickable(false);
                subject_ET.setText(selectedMeeting.getSubject());
                description_ET.setText(selectedMeeting.getDescription());
                location_ET.setText(selectedMeeting.getLocation());

                startDateTime_TV.setText(formatter.formatDateToString4(selectedMeeting.getStartDate()));
                endDateTime_TV.setText(formatter.formatDateToString4(selectedMeeting.getEndDate()));


                String attendees = null;

                for (int i = 0; i < selectedMeeting.getAttendees().size(); i++) {
                    User tempUser = selectedMeeting.getAttendees().get(i);
                    if (tempUser != null) {
                        if (attendees == null) {
                            attendees = tempUser.getName();
                        } else {
                            attendees = attendees + ", " + tempUser.getName();
                        }
                    }
                }
                user_TV.setText(attendees);
            }
        }

    }

    private void staticNonsense() {
        subject_ET.setText("Subject");
        description_ET.setText("Description");
        location_ET.setText("Location");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_add);


        initThings();
        findThings();
        initView();

//        staticNonsense();
    }

    public void onAddUser(View view) {
        nextIntent = new Intent(this, UserSelectActivity.class);
        nextIntent.putIntegerArrayListExtra("selectedUserIndexList", selectedUserIndexList);
        startActivityForResult(nextIntent, Constant.SELECT_USER_ACTIVITY);
    }

    private boolean validate() {
        boolean notErrorCase = true;
        if (subject_ET.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please enter some subject.", false);
            notErrorCase = false;
        } else if (startDateTime_TV.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please enter the Start Time for the Meeting.", false);
            notErrorCase = false;
        } else if (endDateTime_TV.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please enter the End Time for the Meeting.", false);
            notErrorCase = false;
        } else if (location_ET.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please enter some location.", false);
            notErrorCase = false;
        } else if (user_TV.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please add atleast 1 invitee.", false);
            notErrorCase = false;
        } else if (description_ET.getText().toString().trim().length() < 1) {
            alertDialogBuilder = createDialog.createAlertDialog(null,
                    "Please enter some description.", false);
            notErrorCase = false;
        }
        if (!notErrorCase) {
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return notErrorCase;
    }

    boolean isEditModeEnabled = false;

    public void onRightHeaderButton(View view) {
        if (requestCode == Constant.MEETING_EXISTING) {
            if (!isEditModeEnabled) {
                System.out.println("isEditModeEnabled");
                isEditModeEnabled = true;

                subject_ET.setEnabled(true);
                description_ET.setEnabled(true);
                location_ET.setEnabled(true);

                startDateTime_RL.setClickable(true);
                endDateTime_RL.setClickable(true);
                invitees_RL.setClickable(true);

                right_IB.setImageDrawable(getResources().getDrawable(R.drawable.save));
                return;
            }
        }

        if (!validate()) {
            return;
        }

        progressDialog.show();

        Meeting tempMeeting = new Meeting(null, ParseUser.getCurrentUser(), subject_ET.getText().toString(), description_ET.getText().toString(), location_ET.getText().toString(), new Date(), new Date(), new Date(), "2hours", null, null);

        List<ParseUser> aa = new ArrayList<ParseUser>();

        for (int i = 0; i < selectedUserIndexList.size(); i++) {
            aa.add(myApp.convertOToPU(myApp.getLoginUser().getUserList().get(selectedUserIndexList.get(i))));
        }

        final ParseObject meetingPO = myApp.convertMeetingOToPO(tempMeeting);
        meetingPO.addAllUnique("attendees", aa);

        meetingPO.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Toast.makeText(MeetingAddActivity.this, "Meeting created " + meetingPO.getObjectId(), Toast.LENGTH_SHORT).show();

                    JSONObject data = new JSONObject();
                    try {
                        data.put("action", "com.mw.smartoffice.NOTIFICATION");
                        data.put("type", 0);
                        data.put("alert", "New Meeting");
                        data.put("fromUserId", ParseUser.getCurrentUser().getObjectId());
//                        data.put("message", message);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }

                    ParsePush push = new ParsePush();
                    push.setChannel(myApp.getLoginUser().getUserList().get(selectedUserIndexList.get(0)).getUsername()); // Notice we use setChannels not setChannel
                    push.setData(data);
                    push.sendInBackground();

                    Intent serviceIntent = new Intent(MeetingAddActivity.this, MeetingService.class);
                    startService(serviceIntent);
                    progressDialog.dismiss();

                    progressDialog = createDialog.createProgressDialog("Updating Meetings",
                            "This may take some time", true, null);
                    progressDialog.show();
                } else {
                    // The save failed.
                    Toast.makeText(MeetingAddActivity.this, "Unable to create meeting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ArrayList<Integer> selectedUserIndexList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.SELECT_USER_ACTIVITY) {
                selectedUserIndexList = intent.getIntegerArrayListExtra("selectedUserIndexList");

                String temp = "";
                for (int i = 0; i < selectedUserIndexList.size(); i++) {
                    System.out.println(selectedUserIndexList.get(i));
                    temp = temp + myApp.getLoginUser().getUserList().get(selectedUserIndexList.get(i)).getName() + ", ";
                }
                if (temp.length() > 3) {
                    temp = temp.substring(0, temp.length() - 2);
                }
                user_TV.setText(temp);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(
                meetingUpdateReceiver,
                new IntentFilter("meeting_update_receiver"));

    }

    @Override
    protected void onPause() {
        isActivityVisible = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                meetingUpdateReceiver);
        super.onPause();
    }

    public void onPickDateTime(View view) {
        if (view.getId() == R.id.startDateTime_RL) {
            super.onPickDateTime(view, startDateTime_TV);
        } else if (view.getId() == R.id.endDateTime_RL) {
            super.onPickDateTime(view, endDateTime_TV);
        }
    }
}
