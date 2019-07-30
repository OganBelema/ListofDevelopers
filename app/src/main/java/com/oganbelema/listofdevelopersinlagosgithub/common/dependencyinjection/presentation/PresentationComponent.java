package com.oganbelema.listofdevelopersinlagosgithub.common.dependencyinjection.presentation;

import android.view.LayoutInflater;


import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;
import com.oganbelema.listofdevelopersinlagosgithub.viewmodel.DeveloperDetailViewModelFactory;
import com.oganbelema.listofdevelopersinlagosgithub.viewmodel.ListOfDevelopersViewModelFactory;

import dagger.Subcomponent;

/**
 * Created by Belema Ogan on 1/14/2019.
 */

@Subcomponent(modules = {PresentationModule.class})
public interface PresentationComponent {

    public LayoutInflater getLayoutInflater();

    public ViewMvcFactory getViewMvcFactory();

    public ListOfDevelopersViewModelFactory getListOfDevelopersViewModelFactory();

    public DeveloperDetailViewModelFactory getDeveloperDetailViewModelFactory();
}
