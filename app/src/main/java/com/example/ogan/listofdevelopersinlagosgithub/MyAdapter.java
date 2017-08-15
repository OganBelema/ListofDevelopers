package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ogan on 6/14/17.
 */

public class MyAdapter extends ArrayAdapter<Item> {

    public MyAdapter(Context context, ArrayList<Item> githubUsers) {
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
        String username = getItem(position).getLogin();
        String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        textView.setText(uppercaseUsername);

        String avatar = getItem(position).getAvatarUrl();

        CircleImageView circleImageView = (CircleImageView) githubUserList.findViewById(R.id.profile_pic);
        Picasso.with(getContext()).load(avatar).into(circleImageView);

        return githubUserList;
    }

}
