package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection;

import android.app.Activity;
import android.view.LayoutInflater;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GithubApi;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity mActivity;

    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public GithubApi getGithubApi() {
        return mCompositionRoot.getGithubApi();
    }

    public LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivity);
    }


}
