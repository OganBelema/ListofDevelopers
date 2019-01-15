package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Module
public class PresentationModule {

    private final Activity mActivity;

    public PresentationModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivity);
    }

    @Provides
    ViewMvcFactory getViewMvcFactory(LayoutInflater inflater){
        return new ViewMvcFactory(inflater);
    }
}
