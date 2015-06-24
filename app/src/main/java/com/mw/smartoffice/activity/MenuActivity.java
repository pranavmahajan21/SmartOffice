package com.mw.smartoffice.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mw.smartoffice.service.MeetingService;
import com.mw.smartoffice.service.UserService;
import com.mw.smartoffice.util.CreateDialog;
import com.parse.ParseUser;
import com.parse.PushService;

import java.util.Set;

public class MenuActivity extends ActionBarActivity {

    public static int X = 0;
    public static boolean isActivityVisible = false;

    Intent previousIntent, nextIntent;

    CreateDialog createDialog;
    ProgressDialog progressDialog;

    private BroadcastReceiver appDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            X--;
            System.out.println("appDataReceiver  : " + X);
            if (X == 0) {
                progressDialog.dismiss();
            }
        }
    };

    private void initThings() {
        previousIntent = getIntent();
        createDialog = new CreateDialog(this);

    }

    private void findThings() {

    }

    private void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initThings();
        findThings();
        initView();

        if (previousIntent.hasExtra("is_first_time_login")
                && previousIntent.getBooleanExtra("is_first_time_login", true)) {
            loadAppData();
        } else {
//            fetchPreferences();
        }
    }

    private void loadAppData() {
        progressDialog = createDialog.createProgressDialog("Account Setup",
                "This may take some time", true, null);
        progressDialog.show();

        Intent intent = new Intent(this, MeetingService.class);

        X++;
        startService(intent);

        intent = new Intent(this, UserService.class);
        X++;
        startService(intent);

    }


    public void onMeeting(View view) {
        nextIntent = new Intent(this, MeetingListActivity2.class);
        startActivity(nextIntent);
    }

    public void onPeople(View view) {
        nextIntent = new Intent(this, PeopleListActivity.class);
        startActivity(nextIntent);
    }

    public void onMessage(View view) {
//        nextIntent = new Intent(this, MeetingListActivity.class);
//        startActivity(nextIntent);
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
    }

    public void onLogout(View view) {
        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show();
        Set<String> setOfAllSubscriptions = PushService.getSubscriptions(this);
//        System.out.println(">>>>>>> Channels before clearing - "
//                + setOfAllSubscriptions.toString());
        for (String subscription : setOfAllSubscriptions) {
//            System.out.println(">>>>>>> MainActivity::onLogOut() - "
//                    + setOfAllSubscription);
            PushService.unsubscribe(this, subscription);
        }
//        setOfAllSubscriptions = PushService.getSubscriptions(this);
//        System.out.println(">>>>>>> Channels after cleared - "
//                + setOfAllSubscriptions.toString());
        ParseUser.logOut();
        finish();
    }

    public void onCalendar(View view) {
        nextIntent = new Intent(this, CalendarActivity2.class);
        nextIntent.putExtra("date", ((EditText) findViewById(R.id.temp_ET)).getText().toString());
//        nextIntent.putExtra("date", new DateFormatter().formatDateToString(new Date()));
        startActivity(nextIntent);


    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
        LocalBroadcastManager.getInstance(this).registerReceiver(
                appDataReceiver, new IntentFilter("app_data"));
    }

    @Override
    protected void onPause() {
        isActivityVisible = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                appDataReceiver);
        super.onPause();
    }
}
