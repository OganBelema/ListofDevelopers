package com.example.ogan.listofdevelopersinlagosgithub;

import android.view.View;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public interface DeveloperListItemViewMvc {

    public interface Listener {
        void onDevelopClicked(Item item);
    }

    View getUserView();

    void bindDeveloper(Item item);

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

}
