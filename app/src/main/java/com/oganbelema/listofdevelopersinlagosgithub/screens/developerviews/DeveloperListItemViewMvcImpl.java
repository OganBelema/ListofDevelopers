package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oganbelema.listofdevelopersinlagosgithub.R;
import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.BaseObservableViewMvc;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class DeveloperListItemViewMvcImpl extends
        BaseObservableViewMvc<DeveloperListItemViewMvc.Listener> implements DeveloperListItemViewMvc {

    private final CircleImageView mUserImageView;
    private final TextView mUsernameTextView;
    private Item mItem;

    public DeveloperListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.home_page, parent, false));

        mUserImageView = (CircleImageView) findViewById(R.id.profile_pic);
        mUsernameTextView = (TextView) findViewById(R.id.user_name);

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Listener listener: getListeners()){
                    listener.onDevelopClicked(mItem);
                }
            }
        });



    }

    @Override
    public void bindDeveloper(Item item) {
        mItem = item;
        String username = item.getLogin();
        if (username != null){
            String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
            mUsernameTextView.setText(uppercaseUsername);
        }
        Picasso.get().load(item.getAvatarUrl()).into(mUserImageView);
    }

}
