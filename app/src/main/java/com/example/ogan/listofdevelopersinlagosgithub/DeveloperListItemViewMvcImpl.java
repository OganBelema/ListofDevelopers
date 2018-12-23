package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class DeveloperListItemViewMvcImpl implements DeveloperListItemViewMvc {


    private final View mUserView;
    private final CircleImageView mUserImageView;
    private final TextView mUsernameTextView;
    private List<Listener> mListeners = new ArrayList<>();
    private Item mItem;

    public DeveloperListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {

        mUserView = inflater.inflate(R.layout.home_page, parent, false);

        mUserImageView = (CircleImageView) mUserView.findViewById(R.id.profile_pic);
        mUsernameTextView = (TextView) mUserView.findViewById(R.id.user_name);

        mUserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener: mListeners){
                    listener.onDevelopClicked(mItem);
                }
            }
        });



    }


    @Override
    public View getRootView() {
        return mUserView;
    }

    @Override
    public void bindDeveloper(Item item) {
        mItem = item;

        String username = item.getLogin();
        String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        mUsernameTextView.setText(uppercaseUsername);
        Picasso.with(getContext()).load(item.getAvatarUrl()).into(mUserImageView);
    }

    private Context getContext() {
        return getRootView().getContext();
    }

    @Override
    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }
}
