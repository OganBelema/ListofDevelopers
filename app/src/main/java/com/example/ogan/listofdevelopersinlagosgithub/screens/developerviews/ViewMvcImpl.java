package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ogan.listofdevelopersinlagosgithub.R;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.BaseViewMvc;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.ViewMvc;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class ViewMvcImpl extends BaseViewMvc implements ViewMvc {

    public ViewMvcImpl(LayoutInflater inflater, ViewGroup parent){
        setRootView(inflater.inflate(R.layout.pagination_loading_layout, parent, false));
    }

}
