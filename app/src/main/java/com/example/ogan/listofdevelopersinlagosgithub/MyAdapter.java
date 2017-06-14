package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ogan on 6/14/17.
 */

public class MyAdapter extends ArrayAdapter<GithubUser> {

    public MyAdapter(Context context, ArrayList<GithubUser> githubUsers) {
        super(context,0, githubUsers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View githubUserList = convertView;
        if(githubUserList == null){
            githubUserList = LayoutInflater.from(getContext()).inflate(R.layout.home_page, parent, false);
        }

        TextView textView = (TextView) githubUserList.findViewById(R.id.user_name);
        textView.setText(getItem(position).getName());

        return githubUserList;
    }
}
