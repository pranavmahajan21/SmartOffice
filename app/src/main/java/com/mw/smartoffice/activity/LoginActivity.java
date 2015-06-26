package com.mw.smartoffice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mw.smartoffice.DAO.UserDAO;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.CreateDialog;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

public class LoginActivity extends ActionBarActivity {

    MyApp myApp;

    EditText username_ET;

    Intent nextIntent;

    UserDAO userDAO;

    CreateDialog createDialog;
    ProgressDialog progressDialog;

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        userDAO = new UserDAO(this);
        createDialog = new CreateDialog(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initThings();

        username_ET = (EditText) findViewById(R.id.username_ET);
        username_ET.setText("8499950005");
    }

    int x = 1;

    public void onLogin(View view) {
        progressDialog = createDialog.createProgressDialog("LogIn",
                "This may take some time" + x, true, null);
        progressDialog.show();

        x++;

        ParseUser loginPU = userDAO.loginUser("user" + username_ET.getText().toString(), "qqqq");


        if (loginPU != null) {
//            PushService.subscribe(this, ParseUser
//                    .getCurrentUser().getUsername(), HandlePushActivity.class);

            // Save the current Installation to Parse.
            ParseInstallation.getCurrentInstallation().saveInBackground();

            ParsePush.subscribeInBackground(ParseUser
                    .getCurrentUser().getUsername(), new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                        Toast.makeText(LoginActivity.this, "successfully subscribed to the broadcast channel.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("com.parse.push", "failed to subscribe for push", e);
                        Toast.makeText(LoginActivity.this, "failed to subscribe for push", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            User loginUser = myApp.convertPUToO(loginPU);
            myApp.setLoginUser(loginUser);

            nextIntent = new Intent(this, MenuActivity.class);
//        nextIntent = new Intent(this, MeetingAddActivity.class);
            nextIntent.putExtra("is_first_time_login", true);
//            progressDialog.dismiss();
            startActivity(nextIntent);
        } else {
//            progressDialog.dismiss();
            System.out.println("else");
        }
    }
}
