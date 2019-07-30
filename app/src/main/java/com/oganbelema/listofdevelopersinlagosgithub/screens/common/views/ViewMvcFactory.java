package com.oganbelema.listofdevelopersinlagosgithub.screens.common.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.DeveloperDetailViewMvc;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.DeveloperDetailViewMvcImpl;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.DeveloperListItemViewMvc;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.DeveloperListItemViewMvcImpl;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.ListOfDevelopersViewMvc;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.ListOfDevelopersViewMvcImpl;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.ViewMvcImpl;


/**
 * Created by Belema Ogan on 12/24/2018.
 */

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public ListOfDevelopersViewMvc getListOfDevelopersViewMvc(@Nullable ViewGroup parent){
        return new ListOfDevelopersViewMvcImpl(mLayoutInflater, parent);
    }

    public DeveloperDetailViewMvc getDeveloperDetailViewMvc(@Nullable ViewGroup parent){
        return new DeveloperDetailViewMvcImpl(mLayoutInflater, parent);
    }

    public DeveloperListItemViewMvc getDeveloperListItemViewMvc(@Nullable ViewGroup parent){
        return new DeveloperListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public ViewMvc getViewMvc(@Nullable ViewGroup parent){
        return new ViewMvcImpl(mLayoutInflater, parent);
    }
}
