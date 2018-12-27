package com.example.ogan.listofdevelopersinlagosgithub.screens.common;

/**
 * Created by Belema Ogan on 12/24/2018.
 */

public interface Observable<ListenerType> {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
