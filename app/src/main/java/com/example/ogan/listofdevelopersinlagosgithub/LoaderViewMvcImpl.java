package com.example.ogan.listofdevelopersinlagosgithub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Belema Ogan on 12/23/2018.
 */

public class LoaderViewMvcImpl implements LoaderViewMvc{

    private final View mLoaderView;

    public LoaderViewMvcImpl(LayoutInflater inflater, ViewGroup parent){
        mLoaderView = inflater.inflate(R.layout.pagination_loading_layout, parent, false);
    }

    @Override
    public View getLoaderView() {
        return mLoaderView;
    }
}
