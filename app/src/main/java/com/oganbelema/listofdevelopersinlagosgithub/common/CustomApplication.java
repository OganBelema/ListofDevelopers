package com.oganbelema.listofdevelopersinlagosgithub.common;

import android.app.Application;

import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationComponent;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationModule;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application.DaggerApplicationComponent;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application.DatabaseModule;


/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class CustomApplication extends Application {


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
