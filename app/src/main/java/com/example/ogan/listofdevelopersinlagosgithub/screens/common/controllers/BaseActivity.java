package com.example.ogan.listofdevelopersinlagosgithub.screens.common.controllers;

import android.support.v7.app.AppCompatActivity;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationComponent;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.NetworkModule;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationComponent;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationModule;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private PresentationComponent mPresentationComponent;

    protected PresentationComponent getPresentationComponent(){

        if (mPresentationComponent == null){
            return mPresentationComponent = getApplicationComponent()
                    .newPresentationComponent(new PresentationModule(this),
                            new NetworkModule());
        }

        return mPresentationComponent;
    }

    private ApplicationComponent getApplicationComponent() {
        return ((CustomApplication) (getApplication())).getApplicationComponent();
    }
}
