package com.mw.smartoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mw.smartoffice.adapter.PeopleAdapter;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.User;

import java.util.ArrayList;
import java.util.List;

public class PeopleListActivity extends AndhraActivity {

    /**
     * Category ListView
     * http://javatechig.com/android/listview-with-section-header-in-android
     * *
     */

    MyApp myApp;

    List<User> peopleList;
    PeopleAdapter adapter;
    ListView people_LV;

    Intent previousIntent;

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        previousIntent = getIntent();

        if (myApp.getLoginUser().getUserList() != null) {
            peopleList = new ArrayList<User>(myApp.getLoginUser().getUserList());
        } else {
            peopleList = new ArrayList<User>();
        }

        for (int i = 0; i < peopleList.size(); i++) {
            System.out.println("peopleList.size()  :  " + peopleList.size());
            System.out.println("peopleList.getUserId()  :  " + peopleList.get(i).getUserId());
            if (i == 0) {
                peopleList.add(i, new User(peopleList.get(i).getName().charAt(0) + "", true));
                i++;
            }

            if (!(peopleList.get(i).getName().charAt(0) + "").equalsIgnoreCase(peopleList.get(i - 1).getName().charAt(0) + "")) {
                peopleList.add(i, new User(peopleList.get(i).getName().charAt(0) + "", true));
                i++;
            }
        }


        if (peopleList != null && peopleList.size() > 0) {
            adapter = new PeopleAdapter(this, peopleList);
        }
    }

    private void findThings() {
        people_LV = (ListView) findViewById(R.id.people_LV);
    }

    private void initView() {
        if (adapter != null) {
            people_LV.setAdapter(adapter);
        }
    }

    Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_list);
//        super.setToolbar("People", View.VISIBLE, View.GONE, View.GONE, null);
        super.setToolbar("People", -1);

        initThings();
        findThings();
        initView();

        people_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nextIntent = new Intent(PeopleListActivity.this, PeopleDetailActivity.class);
                System.out.println("check1  : " + peopleList.get(position).getUserId());
                nextIntent.putExtra("people_id", peopleList.get(position).getUserId());
                startActivity(nextIntent);
            }
        });
    }
}
