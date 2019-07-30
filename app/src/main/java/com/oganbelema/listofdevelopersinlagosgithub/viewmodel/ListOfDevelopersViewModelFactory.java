package com.oganbelema.listofdevelopersinlagosgithub.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.oganbelema.listofdevelopersinlagosgithub.repository.ListOfDeveloperRepository;
import com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews.RecyclerAdapter;

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
