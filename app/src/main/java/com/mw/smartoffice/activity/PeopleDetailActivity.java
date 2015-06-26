package com.mw.smartoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.User;

public class PeopleDetailActivity extends AndhraActivity {

    ImageView profilePic_IV;
    TextView name_TV;

    TextView departmentLabel_TV, designationLabel_TV, emailLabel_TV, mobileLabel_TV, officeLabel_TV;
    TextView department_TV, designation_TV, email_TV, mobile_TV, office_TV;


    MyApp myApp;
    Intent previousIntent;

    User selectedUser;

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        previousIntent = getIntent();

        selectedUser = myApp.findUserById(previousIntent.getStringExtra("people_id"));
//        selectedUser = myApp.getLoginUser().getUserList().get(previousIntent.getStringExtra("people_id"));
//        previousIntent.getIntExtra("position", 0);

    }

    private void findThings() {
        profilePic_IV = (ImageView) findViewById(R.id.profilePic_IV);
        name_TV = (TextView) findViewById(R.id.name_TV);

        departmentLabel_TV = (TextView) findViewById(R.id.departmentLabel_TV);
        designationLabel_TV = (TextView) findViewById(R.id.designationLabel_TV);
        emailLabel_TV = (TextView) findViewById(R.id.emailLabel_TV);
        mobileLabel_TV = (TextView) findViewById(R.id.mobileLabel_TV);
        officeLabel_TV = (TextView) findViewById(R.id.officeLabel_TV);

        department_TV = (TextView) findViewById(R.id.department_TV);
        designation_TV = (TextView) findViewById(R.id.designation_TV);
        email_TV = (TextView) findViewById(R.id.email_TV);
        mobile_TV = (TextView) findViewById(R.id.mobile_TV);
        office_TV = (TextView) findViewById(R.id.office_TV);
    }

    private void setTypeface() {
        name_TV.setTypeface(myApp.getTypefaceBoldLato());

        departmentLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        designationLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        emailLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        mobileLabel_TV.setTypeface(myApp.getTypefaceBoldLato());
        officeLabel_TV.setTypeface(myApp.getTypefaceBoldLato());

        department_TV.setTypeface(myApp.getTypefaceRegularLato());
        designation_TV.setTypeface(myApp.getTypefaceRegularLato());
        email_TV.setTypeface(myApp.getTypefaceRegularLato());
        mobile_TV.setTypeface(myApp.getTypefaceRegularLato());
        office_TV.setTypeface(myApp.getTypefaceRegularLato());
    }

    private void initView() {
        setTypeface();

        name_TV.setText(selectedUser.getName());
        department_TV.setText(selectedUser.getDepartment());
        designation_TV.setText(selectedUser.getDesignation());
        email_TV.setText(selectedUser.getEmail());
        mobile_TV.setText(selectedUser.getMobileNo());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_detail);
//        super.setToolbar("People", View.GONE, View.GONE, View.GONE, null);
        super.setToolbar("People", -1);

        initThings();
        findThings();
        initView();
    }


}
