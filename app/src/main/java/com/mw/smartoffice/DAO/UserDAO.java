package com.mw.smartoffice.DAO;

/**
 * Created by pranav on 20/6/15.
 */

import android.content.Context;

import com.mw.smartoffice.activity.R;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserDAO {

    ParseQuery<ParseUser> query;

    public UserDAO(Context context) {
        super();

        Parse.initialize(context, context.getResources().getString(R.string.parse_app_id), context.getResources().getString(R.string.parse_client_key));
        query = ParseUser.getQuery();
    }

    public ParseUser loginUser(String userName, String password) {
        try {
            ParseUser.logIn(userName, password);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ParseUser.getCurrentUser();
    }

    public List<ParseUser> getAllUsers() {
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> bookList, ParseException e) {

            }
        });

        System.out.println("get all users");
        List<ParseUser> userList = null;
        try {
            userList = query.orderByAscending("name").find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public ParseUser getUserById(String userId) {
        System.out.println(">>>>>> get user by id");
        ParseUser contactUser = null;
        query.whereEqualTo("objectId", userId);
        try {
            contactUser = query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return contactUser;
    }
}