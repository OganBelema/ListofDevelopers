package com.oganbelema.listofdevelopersinlagosgithub.screens.common.views;

import android.content.Context;
import android.view.View;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public abstract class BaseViewMvc implements ViewMvc {

    private View mRootView;

    protected void setRootView(View rootView) {
        mRootView = rootView;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    protected View findViewById(int id){
        return getRootView().findViewById(id);
    }

    protected Context getContext(){
        return getRootView().getContext();
    }
}
