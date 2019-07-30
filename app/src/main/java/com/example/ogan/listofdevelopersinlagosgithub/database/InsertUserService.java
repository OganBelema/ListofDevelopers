package com.example.ogan.listofdevelopersinlagosgithub.database;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;

public class InsertUserService extends IntentService {

    public static final String USER_KEY = "user";

    DeveloperDatabase mDeveloperDatabase;

    public InsertUserService() {
        super("InsertUserService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDeveloperDatabase = ((CustomApplication) getApplication()).getApplicationComponent().getDeveloperDatabase();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            UserApi userApi = intent.getParcelableExtra(USER_KEY);
            cacheData(userApi);
        }
    }

    private void cacheData(final UserApi userApi) {
        mDeveloperDatabase.getUserDao().insertUser(userApi);
    }
}
