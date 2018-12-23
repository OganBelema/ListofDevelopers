package com.example.ogan.listofdevelopersinlagosgithub.screens.common;

import android.support.v7.app.AppCompatActivity;

import com.example.ogan.listofdevelopersinlagosgithub.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.CompositionRoot;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class BaseActivity extends AppCompatActivity {

    protected CompositionRoot getCompositionRoot(){
        return ((CustomApplication) getApplication()).getCompositionRoot();
    }
}
