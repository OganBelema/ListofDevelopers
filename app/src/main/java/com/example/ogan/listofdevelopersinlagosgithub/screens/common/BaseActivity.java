package com.example.ogan.listofdevelopersinlagosgithub.screens.common;

import android.support.v7.app.AppCompatActivity;

import com.example.ogan.listofdevelopersinlagosgithub.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.CompositionRoot;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.ControllerCompositionRoot;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){

        if (mControllerCompositionRoot == null){
           mControllerCompositionRoot = new ControllerCompositionRoot(
                   ((CustomApplication) getApplication()).getCompositionRoot(), this);
        }

        return  mControllerCompositionRoot;
    }
}
