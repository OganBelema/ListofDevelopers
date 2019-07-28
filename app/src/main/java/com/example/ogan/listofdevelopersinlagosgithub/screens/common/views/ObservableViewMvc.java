package com.example.ogan.listofdevelopersinlagosgithub.screens.common.views;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public interface ObservableViewMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}