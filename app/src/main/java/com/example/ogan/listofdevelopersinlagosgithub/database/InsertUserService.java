package com.example.ogan.listofdevelopersinlagosgithub.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class InsertUserService extends Service {

    public static final String USER_KEY = "user";

    DeveloperDatabase mDeveloperDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mDeveloperDatabase = ((CustomApplication) getApplication()).getApplicationComponent().getDeveloperDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        UserApi userApi = intent.getParcelableExtra(USER_KEY);

        cacheData(userApi);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void cacheData(final UserApi userApi) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
               mDeveloperDatabase.getUserDao().insertUser(userApi);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }
}
