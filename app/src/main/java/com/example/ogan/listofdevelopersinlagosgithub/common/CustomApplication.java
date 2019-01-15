package com.example.ogan.listofdevelopersinlagosgithub.common;

import android.app.Application;

import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationComponent;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationModule;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application.DaggerApplicationComponent;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class CustomApplication extends Application {


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
