package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;


import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ObservableViewMvc;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public interface DeveloperListItemViewMvc extends ObservableViewMvc<DeveloperListItemViewMvc.Listener> {

    interface Listener {
        void onDevelopClicked(Item item);
    }

    void bindDeveloper(Item item);

}
