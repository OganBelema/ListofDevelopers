package com.example.ogan.listofdevelopersinlagosgithub.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

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
