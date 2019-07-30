package com.oganbelema.listofdevelopersinlagosgithub.screens.common.views;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public abstract class BaseObservableViewMvc <ListenerType> extends BaseViewMvc
        implements ObservableViewMvc<ListenerType> {

    private Set<ListenerType> mListeners = new HashSet<>();

    @Override
    public void registerListener(ListenerType listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(ListenerType listener) {
        mListeners.remove(listener);
    }

    protected Set<ListenerType> getListeners(){
        //an example of defensive programming, protects the set from being modified
        return Collections.unmodifiableSet(mListeners);
    }
}
