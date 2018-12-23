package com.example.ogan.listofdevelopersinlagosgithub;

import android.app.Application;

import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.CompositionRoot;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class CustomApplication extends Application {

    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
