package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import com.example.ogan.listofdevelopersinlagosgithub.network.items.Item;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ObservableViewMvc;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public interface DeveloperListItemViewMvc extends ObservableViewMvc<DeveloperListItemViewMvc.Listener> {

    public interface Listener {
        void onDevelopClicked(Item item);
    }

    void bindDeveloper(Item item);

}
