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
import com.mw.smartoffice.util.CharacterDrawable;

import java.util.List;
import java.util.Map;

/**
 * Created by pranav on 11/5/15.
 */
public class PeopleAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private static final int VIEW_MAX_COUNT = 2;

    MyApp myApp;
    Context context;

    LayoutInflater inflater;

    List<User> userList;

    Map<String, Integer> colorMap;

    public PeopleAdapter(Context context, List<User> userList) {
        super();
        this.context = context;
        this.userList = userList;
        myApp = (MyApp) context.getApplicationContext();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorMap = myApp.getColorMap();
    }


    static class ViewHolder {
        /* Header */
        protected TextView alphabet_TV;

        /* Row */
        protected ImageView senderInitials_IV;
        protected TextView name_TV;
        protected TextView email_TV;


    }

    @Override
    public int getViewTypeCount() {
        return VIEW_MAX_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return userList.get(position).isUserRajnikant() ? TYPE_HEADER : TYPE_ITEM;

//        if (peopleList.get(position).isChecked()) {
//            return TYPE_HEADER;
//        } else {
//            return TYPE_ITEM;
//        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        int type = getItemViewType(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            switch (type) {
                case TYPE_HEADER:
                    convertView = inflater.inflate(R.layout.element_people_section_header,
                            parent, false);
                    viewHolder.alphabet_TV = (TextView) convertView
                            .findViewById(R.id.alphabet_TV);
                    break;
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.element_people,
                            parent, false);
                    viewHolder.senderInitials_IV = (ImageView) convertView
                            .findViewById(R.id.senderInitials_IV);
                    viewHolder.name_TV = (TextView) convertView
                            .findViewById(R.id.name_TV);
                    viewHolder.email_TV = (TextView) convertView
                            .findViewById(R.id.email_TV);
                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User tempUser = userList.get(position);

        switch (type) {
            case TYPE_HEADER:
                viewHolder.alphabet_TV.setText(tempUser.getName().charAt(0) + "");
                convertView.setOnClickListener(null);
                convertView.setOnLongClickListener(null);
                convertView.setLongClickable(false);
                break;
            case TYPE_ITEM:
                viewHolder.name_TV.setText(tempUser.getName());
                viewHolder.email_TV.setText(tempUser.getEmail());
                CharacterDrawable drawable = new CharacterDrawable(tempUser.getName().charAt(0), colorMap.get(tempUser.getName().charAt(0) + ""));
                viewHolder.senderInitials_IV.setImageDrawable(drawable);
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
//        return null;
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
