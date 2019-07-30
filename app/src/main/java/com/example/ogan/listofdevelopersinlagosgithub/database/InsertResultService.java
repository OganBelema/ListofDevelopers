package com.example.ogan.listofdevelopersinlagosgithub.database;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;

public class InsertResultService extends IntentService {

    public static final String RESULT_KEY = "result";

    DeveloperDatabase mDeveloperDatabase;

    public InsertResultService() {
        super("InsertResultService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDeveloperDatabase = ((CustomApplication) this.getApplication()).getApplicationComponent().getDeveloperDatabase();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            ApiResult result = intent.getParcelableExtra(RESULT_KEY);
            cacheData(result);
        }
    }

    private void cacheData(final ApiResult apiResult) {
        mDeveloperDatabase.getResultDao().insertResult(apiResult);
    }
}
