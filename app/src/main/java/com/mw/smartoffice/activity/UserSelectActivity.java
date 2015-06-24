package com.mw.smartoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.smartoffice.adapter.PeopleAdapter2;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.Constant;

import java.util.ArrayList;
import java.util.List;


public class UserSelectActivity extends AndhraActivity {

    MyApp myApp;

    ListView user_LV;
    TextView errorMsg_TV;

    Intent previousIntent;

    List<User> userList;
    PeopleAdapter2 adapter;

    ArrayList<Integer> selectedUserIndexList;

    SparseBooleanArray checkedUsersArray;

    public void initThings() {
        myApp = (MyApp) getApplicationContext();
        previousIntent = getIntent();

        userList = myApp.getLoginUser().getUserList();

        checkedUsersArray = new SparseBooleanArray();

        /** Fetch the index of already selected users **/
        selectedUserIndexList = previousIntent.getIntegerArrayListExtra("selectedUserIndexList");
        if (selectedUserIndexList == null) {
            selectedUserIndexList = new ArrayList<Integer>();
        }

        if (userList != null) {
            for (int i = 0; i < selectedUserIndexList.size(); i++) {
                userList.get(selectedUserIndexList.get(i)).setChecked(true);
            }
            adapter = new PeopleAdapter2(this, userList);
        }
    }

    private void findViews() {
        user_LV = (ListView) findViewById(R.id.user_LV);
        errorMsg_TV = (TextView) findViewById(R.id.errorMsg_TV);
    }

    private void setTypeface() {
//        errorMsg_TV.setTypeface(appController.getTypefaceOsRegular());
    }

    public void initViews() {
        setTypeface();

        if (adapter != null) {
            user_LV.setAdapter(adapter);
        } else {
            errorMsg_TV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
//        super.setToolbar("Select Users", View.GONE, View.GONE, View.VISIBLE, getResources().getDrawable(R.drawable.done));
        super.setToolbar("Select Users", Constant.CHILD_BUTTON_SELECT_USER);

        initThings();
        findViews();
        initViews();

        /** Check already selected users in the ListView **/
        for (int i = 0; i < selectedUserIndexList.size(); i++) {
            user_LV.setItemChecked(selectedUserIndexList.get(i), true);
        }

        user_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkedUsersArray = user_LV.getCheckedItemPositions();
                for (int i = 0; i < checkedUsersArray.size(); i++) {
                    int key = checkedUsersArray.keyAt(i);
                    userList.get(key).setChecked(checkedUsersArray.valueAt(i));
                    System.out.println(">>>>key  : " + key + "    value>>>> " + checkedUsersArray.valueAt(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onRightHeaderButton(View view) {

        selectedUserIndexList = new ArrayList<Integer>();

        checkedUsersArray = user_LV.getCheckedItemPositions();
        for (int i = 0; i < checkedUsersArray.size(); i++) {
            if (checkedUsersArray.valueAt(i) == true) {
                selectedUserIndexList.add(checkedUsersArray.keyAt(i));
                userList.get(checkedUsersArray.keyAt(i)).setChecked(false);
            }
        }
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra("selectedUserIndexList", selectedUserIndexList);

        setResult(RESULT_OK, intent);
        finish();
    }
}