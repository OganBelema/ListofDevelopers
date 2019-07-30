package com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation;

import android.app.Activity;
import android.view.LayoutInflater;


import com.oganbelema.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;
import com.oganbelema.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;
import com.oganbelema.listofdevelopersinlagosgithub.viewmodel.DeveloperDetailViewModelFactory;
import com.oganbelema.listofdevelopersinlagosgithub.viewmodel.ListOfDevelopersViewModelFactory;

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

    @Provides
    RecyclerAdapter getRecyclerAdapter(ViewMvcFactory viewMvcFactory){
        return new RecyclerAdapter(mActivity, viewMvcFactory);
    }

    @Provides
    ListOfDevelopersViewModelFactory getListOfDevelopersViewModelFactory(ListOfDeveloperRepository listOfDeveloperRepository, RecyclerAdapter recyclerAdapter){
        return new ListOfDevelopersViewModelFactory(listOfDeveloperRepository, recyclerAdapter);
    }

    @Provides
    DeveloperDetailViewModelFactory getDeveloperDetailViewModelFactory(DeveloperDetailRepository developerDetailRepository){
        return new DeveloperDetailViewModelFactory(developerDetailRepository);
    }
}
