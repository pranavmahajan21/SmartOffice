package com.mw.smartoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mw.smartoffice.adapter.MeetingDetailsAdapter;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class MeetingDetailsActivity extends AndhraActivity {

    MyApp myApp;

    TextView title_TV;

    List<Meeting> meetingList;
    MeetingDetailsAdapter adapter;
    ListView meetingDetail_LV;

    Intent previousIntent, nextIntent;

    private void initThings() {
        myApp = (MyApp) getApplicationContext();
        previousIntent = getIntent();

//        previousIntent.getIntExtra("position", 0);

        meetingList = new ArrayList<List<Meeting>>(myApp.getLoginUser().getMeetingMap().values()).get(previousIntent.getIntExtra("position", 0));

        if (meetingList != null && meetingList.size() > 0) {
            adapter = new MeetingDetailsAdapter(this, meetingList);
        }
    }

    private void findThings() {
        title_TV = (TextView) findViewById(R.id.title_TV);
        meetingDetail_LV = (ListView) findViewById(R.id.meetingDetail_LV);
    }

    private void initView() {
        title_TV.setText(meetingList.get(0).getSubject());
        title_TV.setTypeface(myApp.getTypefaceBoldLato());

        if (adapter != null) {
            meetingDetail_LV.setAdapter(adapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
//        super.setToolbar("Meeting Details", View.VISIBLE, View.GONE, View.VISIBLE, null);
        super.setToolbar("Meeting Details",-1);
                initThings();
        findThings();
        initView();

        meetingDetail_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                meetingList.get(position).setChecked(!meetingList.get(position).isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        /**
         * Put this function in parent class maybe
         **/
        intent.putExtra("request_code", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRight3(View view) {
        nextIntent = new Intent(this, MeetingAddActivity.class);
        startActivityForResult(nextIntent, Constant.MEETING_FOLLOW);
    }

    public void onExpand(View view) {
    }
}
