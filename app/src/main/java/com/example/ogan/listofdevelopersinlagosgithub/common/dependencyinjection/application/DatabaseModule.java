package com.example.ogan.listofdevelopersinlagosgithub.common.dependencyinjection.application;

import android.content.Context;

import androidx.room.Room;

import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private final Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    DeveloperDatabase getDeveloperDatabase(){
        return Room.databaseBuilder(context, DeveloperDatabase.class, "developer.db")
                .build();
    }
}
