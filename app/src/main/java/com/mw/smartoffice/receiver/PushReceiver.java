package com.mw.smartoffice.receiver;

/**
 * Created by pranav on 22/6/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.mw.smartoffice.application.MyApp;

import org.json.JSONException;
import org.json.JSONObject;

public class PushReceiver extends BroadcastReceiver {
    Intent nextIntent;
    MyApp myApp;

    SharedPreferences sharedPreferences;
    Editor editor;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        myApp = (MyApp) context.getApplicationContext();

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        editor = sharedPreferences.edit();

        Bundle extras = intent.getExtras();
        String message = extras != null ? extras.getString("com.parse.Data")
                : "";
        System.out.println(">>>>><<<<<<" + message);
        JSONObject jsonObject;
//        try {
//            jsonObject = new JSONObject(message);
//            // Toast.makeText(context,
//            // "Notif received - " + jsonObject.getInt("type"),
//            // Toast.LENGTH_LONG).show();
//            nextIntent = new Intent(context, JustADialogActivity.class);
//            nextIntent.putExtra("type", jsonObject.getInt("type"));
//            nextIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // if(a new chat message is received)
//            if (jsonObject.getInt("type") == 2) {
//                // if (the user is already chatting with someone)
//                if (myApp.getChatPerson() != null) {
//                    // if(the chatting person is same as the one who sent the
//                    // new message)
//                    if (jsonObject.getString("fromUserId").equals(
//                            myApp.getChatPerson().getObjectId())) {
//                        // refresh chat list
//                        Intent nextIntent = new Intent("new_message");
//                        // add data
//                        nextIntent.putExtra("message",
//                                jsonObject.getString("message"));
//                        nextIntent.putExtra("fromUserId",
//                                jsonObject.getString("fromUserId"));
//                        LocalBroadcastManager.getInstance(context)
//                                .sendBroadcast(nextIntent);
//                    }
//                } else {
//                    // update preferences for contacts page, to display the
//                    // number of unread messages
//
//                    if (sharedPreferences.contains(jsonObject
//                            .getString("fromUserId"))) {
//                        editor.putInt(
//                                jsonObject.getString("fromUserId"),
//                                sharedPreferences.getInt(
//                                        jsonObject.getString("fromUserId"), 0) + 1);
//                    } else {
//                        editor.putInt(jsonObject.getString("fromUserId"), 1);
//                    }
//                    editor.commit();
//
//                    // If contacts page is already open, use intent to refresh
//                    // the page.
//                    Intent nextIntent = new Intent(
//                            "unread_messages_from_various_users_count");
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(
//                            nextIntent);
//
//                }
//            } else {
//                // handle new email & message in next activity because they have
//                // to show an alert & that is possible from an Activity only
//                context.startActivity(nextIntent);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}