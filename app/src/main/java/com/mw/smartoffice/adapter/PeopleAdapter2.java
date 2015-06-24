package com.mw.smartoffice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mw.smartoffice.activity.R;
import com.mw.smartoffice.application.MyApp;
import com.mw.smartoffice.model.User;

import java.util.List;

/**
 * Created by pranav on 12/6/15.
 */
public class PeopleAdapter2 extends BaseAdapter {

    MyApp myApp;
    Context context;

    LayoutInflater inflater;

    List<User> userList;

    public PeopleAdapter2(Context context, List<User> userList) {
        super();
        this.context = context;
        this.userList = userList;
        myApp = (MyApp) context.getApplicationContext();
    }

    static class ViewHolder {
        protected TextView name_TV;
        protected ImageView tick_IV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.element_people_select, parent,
                    false);

            viewHolder.name_TV = (TextView) convertView
                    .findViewById(R.id.name_TV);
            viewHolder.tick_IV = (ImageView) convertView
                    .findViewById(R.id.tick_IV);

            viewHolder.name_TV.setTypeface(myApp.getTypefaceRegularLato());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User tempUser = userList.get(position);
        viewHolder.name_TV.setText(tempUser.getName());

            if (tempUser.isChecked()) {
                viewHolder.tick_IV.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tick_IV.setVisibility(View.GONE);
            }
        return convertView;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}