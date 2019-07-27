package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;

public class ListOfDevelopersViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    private final RecyclerAdapter recyclerAdapter;

    public ListOfDevelopersViewModelFactory(Context context, RecyclerAdapter recyclerAdapter) {
        this.context = context;
        this.recyclerAdapter = recyclerAdapter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListOfDevelopersViewModel(context, recyclerAdapter);
    }
}
