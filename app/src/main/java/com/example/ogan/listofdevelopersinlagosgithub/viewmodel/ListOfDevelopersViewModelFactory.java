package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ogan.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;
import com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;

public class ListOfDevelopersViewModelFactory implements ViewModelProvider.Factory {

    private final ListOfDeveloperRepository listOfDeveloperRepository;

    private final RecyclerAdapter recyclerAdapter;

    public ListOfDevelopersViewModelFactory(ListOfDeveloperRepository listOfDeveloperRepository, RecyclerAdapter recyclerAdapter) {
        this.listOfDeveloperRepository = listOfDeveloperRepository;
        this.recyclerAdapter = recyclerAdapter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListOfDevelopersViewModel(listOfDeveloperRepository, recyclerAdapter);
    }
}
