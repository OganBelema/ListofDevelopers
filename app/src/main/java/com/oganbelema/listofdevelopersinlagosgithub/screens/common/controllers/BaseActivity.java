package com.oganbelema.listofdevelopersinlagosgithub.screens.common.controllers;


import androidx.appcompat.app.AppCompatActivity;

import com.oganbelema.listofdevelopersinlagosgithub.common.CustomApplication;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.application.ApplicationComponent;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationComponent;
import com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation.PresentationModule;


/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private PresentationComponent mPresentationComponent;

    protected PresentationComponent getPresentationComponent(){

        if (mPresentationComponent == null){
            return mPresentationComponent = getApplicationComponent()
                    .newPresentationComponent(new PresentationModule(this));
        }

        return mPresentationComponent;
    }

    private ApplicationComponent getApplicationComponent() {
        return ((CustomApplication) (getApplication())).getApplicationComponent();
    }
}
