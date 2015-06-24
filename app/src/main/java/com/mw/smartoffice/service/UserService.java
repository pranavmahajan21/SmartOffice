package com.mw.smartoffice.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mw.smartoffice.DAO.UserDAO;
import com.mw.smartoffice.activity.MenuActivity;
import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.Meeting;
import com.mw.smartoffice.model.User;
import com.mw.smartoffice.util.Constant;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserService extends IntentService {

    MyApp myApp;
    Gson gson;

//    UserDAO userDAO = new UserDAO(this);

    public UserService() {
        super("UserService");
    }

//    public UserService(String name) {
//        super(name);
//    }

    @Override
    protected void onHandleIntent(Intent intent) {
        myApp = (MyApp) getApplicationContext();
        gson = new Gson();

        if (intent != null) {
//            try {
//
//                String url = Constant.BASE_URL + Constant.USERS_DATA;
//                System.out.println("URL : " + url);
//
//                JSONObject params = new JSONObject();
//
//                System.out.println(params.toString());
//
//                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                        Request.Method.GET, url, null,
//                        new Response.Listener<JSONArray>() {
//
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                System.out.println(">>>>Account Response => "
//                                        + response.toString());
//
//                                ProcessResponseAsynTask asynTask = new ProcessResponseAsynTask();
//                                asynTask.execute(response);
//                            }
//                        }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out
//                                .println("ERROR  : " + error.getMessage());
//                        Toast.makeText(UserService.this,
//                                "Error while fetching Users",
//                                Toast.LENGTH_LONG).show();
//                        error.printStackTrace();
//                        onRequestComplete();
//                    }
//                });
//
//                RetryPolicy policy = new DefaultRetryPolicy(30000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//                jsonArrayRequest.setRetryPolicy(policy);
//                Volley.newRequestQueue(this).add(jsonArrayRequest);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            ParseQuery<ParseUser> query;

            Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));
            query = ParseUser.getQuery();

            final List<User> tempUserList = new ArrayList<User>();

            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> userList, ParseException e) {
                    Toast.makeText(UserService.this, "done", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < userList.size(); i++) {
                        tempUserList.add(myApp.convertPUToO(userList.get(i)));
                    }

                    myApp.getLoginUser().setUserList(tempUserList);
                    onRequestComplete();
                }
            });
        }
    }

    private class ProcessResponseAsynTask extends
            AsyncTask<JSONArray, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(JSONArray... params) {
            JSONArray responseJsonArray = params[0];

            Type listType = (Type) new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> tempUserList = (List<User>) gson
                    .fromJson(responseJsonArray.toString(), listType);

            myApp.getLoginUser().setUserList(tempUserList);
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject aa) {
            super.onPostExecute(aa);

            onRequestComplete();
        }
    }

    private void onRequestComplete() {
        if (MenuActivity.isActivityVisible) {
            Intent nextIntent = new Intent("app_data");
            LocalBroadcastManager.getInstance(this).sendBroadcast(nextIntent);
        }
    }
}
