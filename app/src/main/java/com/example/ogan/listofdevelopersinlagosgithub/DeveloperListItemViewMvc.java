package com.example.ogan.listofdevelopersinlagosgithub;

import android.view.View;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public interface DeveloperListItemViewMvc extends ObservableViewMvc<DeveloperListItemViewMvc.Listener>{

    public interface Listener {
        void onDevelopClicked(Item item);
    }

    void bindDeveloper(Item item);

}
