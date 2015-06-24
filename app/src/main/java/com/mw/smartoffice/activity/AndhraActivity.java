package com.mw.smartoffice.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.util.Constant;

import java.util.Calendar;

public class AndhraActivity extends ActionBarActivity {

    MyApp myApp;

    Toolbar toolbar;

    //    ImageView back_IV;
    TextView titleHeader_TV;

    LinearLayout right_LL;
//    LinearLayout calendar_LL;

    /*ImageButton search_IB, filter_IB, add_IB;

    RelativeLayout search_RL;
    EditText search_ET;*/

    TextView dateHeader_TV;
    ImageButton right_IB;

    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        myApp = (MyApp) getApplicationContext();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    public void setToolbar(String title, int rightButton1Visibility, int rightButton2Visibility, int rightButton3Visibility, Drawable rightButton3Drawable) {
//        toolbar = (Toolbar) findViewById(R.id.actionBar_T);
//        setSupportActionBar(toolbar);
//
//        back_IV = (ImageView) toolbar.findViewById(R.id.back_IV);
//        titleHeader_TV = (TextView) toolbar.findViewById(R.id.titleHeader_TV);
//
//        search_IB = (ImageButton) toolbar.findViewById(R.id.search_IB);
//        filter_IB = (ImageButton) toolbar.findViewById(R.id.filter_IB);
//        add_IB = (ImageButton) toolbar.findViewById(R.id.add_IB);
//
//        search_RL = (RelativeLayout) toolbar.findViewById(R.id.search_RL);
//        search_ET = (EditText) toolbar.findViewById(R.id.search_ET);
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(search_ET, InputMethodManager.SHOW_IMPLICIT);
//
//        search_IB.setVisibility(rightButton1Visibility);
//        filter_IB.setVisibility(rightButton2Visibility);
//        add_IB.setVisibility(rightButton3Visibility);
//
//        if(rightButton3Drawable != null){
//            add_IB.setImageDrawable(rightButton3Drawable);
//        }
//
//        titleHeader_TV.setText(title);
//    }

    public void setToolbar(String title, int child) {
        toolbar = (Toolbar) findViewById(R.id.actionBar_T);
        setSupportActionBar(toolbar);

        titleHeader_TV = (TextView) toolbar.findViewById(R.id.titleHeader_TV);

        right_LL = (LinearLayout) toolbar.findViewById(R.id.right_LL);

        switch (child) {
            case Constant.CHILD_CALENDAR:
                right_LL.addView(inflater.inflate(R.layout.child_calendar,
                        null, false));
                dateHeader_TV = (TextView) right_LL.findViewById(R.id.dateHeader_TV);
                dateHeader_TV.setTypeface(myApp.getTypefaceRegularLato());
                break;
            case Constant.CHILD_BUTTON_MEETING_ADD:
                inflater.inflate(R.layout.child_button,
                        right_LL, true);
//                right_LL.addView(inflater.inflate(R.layout.child_button,
//                        null, false));
                right_IB = (ImageButton) right_LL.findViewById(R.id.right_IB);
                right_IB.setImageDrawable(getResources().getDrawable(R.drawable.save));
                break;
            case Constant.CHILD_BUTTON_MEETING_EDIT:
                inflater.inflate(R.layout.child_button,
                        right_LL, true);
                right_IB = (ImageButton) right_LL.findViewById(R.id.right_IB);
                right_IB.setImageDrawable(getResources().getDrawable(R.drawable.edit));
                break;
            case Constant.CHILD_BUTTON_SELECT_USER:
                right_LL.addView(inflater.inflate(R.layout.child_button,
                        null, false));
                right_IB = (ImageButton) right_LL.findViewById(R.id.right_IB);
                right_IB.setImageDrawable(getResources().getDrawable(R.drawable.done));
                break;
            default:
                break;
        }

        titleHeader_TV.setTypeface(myApp.getTypefaceBoldLato());
        titleHeader_TV.setText(title);
    }

    public void onBack(View view) {
        finish();
    }

    boolean a = false;

//    public void onRight1(View view) {
//        search_RL.setVisibility(View.VISIBLE);
//
//        titleHeader_TV.setVisibility(View.GONE);
//        back_IV.setVisibility(View.GONE);
//        search_IB.setVisibility(View.GONE);
//        filter_IB.setVisibility(View.GONE);
//        add_IB.setVisibility(View.GONE);
//
//        search_ET.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(search_ET, InputMethodManager.SHOW_IMPLICIT);
//
//    }

    public void onRight2(View view) {

    }

    public void onRight3(View view) {

    }

//    public void onClearText(View view) {
//        search_ET.setText("");
//
//        search_RL.setVisibility(View.GONE);
//
//        titleHeader_TV.setVisibility(View.VISIBLE);
//        back_IV.setVisibility(View.VISIBLE);
//        search_IB.setVisibility(View.VISIBLE);
//        filter_IB.setVisibility(View.VISIBLE);
//        add_IB.setVisibility(View.VISIBLE);
//    }

    int noOfTimesDateCalled = 0;
    int noOfTimesTimeCalled = 0;

    String dateTimeString;

    public void onPickDateTime(final View view, final TextView textView) {
        final TimePickerDialog tPicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view2, int hourOfDay,
                                          int minute) {
                        if (noOfTimesTimeCalled % 2 == 0) {
                            // System.out.println(hourOfDay + ":" + minute);

                            String timeString = "";

                            if (hourOfDay < 10) {
                                timeString = "0" + hourOfDay;
                            } else {
                                timeString = "" + hourOfDay;
                            }
                            if (minute < 10) {
                                timeString = timeString + ":0" + minute;
                            } else {
                                timeString = timeString + ":" + minute;
                            }

                            dateTimeString = dateTimeString + ", " + timeString;
                            textView.setText(dateTimeString);
//							view.setTag(dateTimeString);
//							getDateTimeString(view, timeString, false);
                        }
                        noOfTimesTimeCalled++;
                    }
                }, 12, 00, true);
        tPicker.setCancelable(false);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dPicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    /**
                     * http://stackoverflow.com/questions/19836210/method-called
                     * -twice-in-datepicker
                     **/
                    @Override
                    public void onDateSet(DatePicker view2, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (noOfTimesDateCalled % 2 == 0) {
                            // System.out.println(dayOfMonth + "-"
                            // + (monthOfYear + 1) + "-" + year);

                            String dateString = "";
                            if (dayOfMonth < 10) {
                                dateString = "0" + dayOfMonth;
                            } else {
                                dateString = "" + dayOfMonth;
                            }

                            if (monthOfYear < 10) {
                                dateString = dateString + "-0" + (monthOfYear + 1);
                            } else {
                                dateString = dateString + "-" + (monthOfYear + 1);
                            }
                            dateString = dateString + "-" + year;

                            dateTimeString = dateString;
//							getDateTimeString(view, dateString, true);
                            tPicker.show();
                        }
                        noOfTimesDateCalled++;
                    }
                }, mYear, mMonth, mDay);
        dPicker.setCancelable(false);
        dPicker.show();
    }

}
