package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.ogan.listofdevelopersinlagosgithub.repository.DeveloperDetailRepository;

public class DeveloperDetailViewModelFactory implements ViewModelProvider.Factory {

    private final DeveloperDetailRepository developerDetailRepository;

    public DeveloperDetailViewModelFactory(DeveloperDetailRepository developerDetailRepository) {
        this.developerDetailRepository = developerDetailRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DeveloperDetailViewModel(developerDetailRepository);
    }
}
