package com.example.ogan.listofdevelopersinlagosgithub.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class InsertResultService extends Service {

    public static final String RESULT_KEY = "result";

    DeveloperDatabase mDeveloperDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        mDeveloperDatabase = ((CustomApplication) getApplication()).getApplicationComponent().getDeveloperDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ApiResult result = intent.getParcelableExtra(RESULT_KEY);

        cacheData(result);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void cacheData(final ApiResult apiResult) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                mDeveloperDatabase.getResultDao().insertResult(apiResult);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe();
    }
}
