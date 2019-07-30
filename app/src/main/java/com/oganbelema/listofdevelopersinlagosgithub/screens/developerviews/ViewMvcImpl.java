package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oganbelema.listofdevelopersinlagosgithub.R;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.BaseViewMvc;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ViewMvc;


/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class ViewMvcImpl extends BaseViewMvc implements ViewMvc {

    public ViewMvcImpl(LayoutInflater inflater, ViewGroup parent){
        setRootView(inflater.inflate(R.layout.pagination_loading_layout, parent, false));
    }

}
