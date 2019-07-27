package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.common.CustomApplication;
import com.example.ogan.listofdevelopersinlagosgithub.common.NetworkUtil;
import com.example.ogan.listofdevelopersinlagosgithub.database.DeveloperDatabase;
import com.example.ogan.listofdevelopersinlagosgithub.database.InsertUserService;
import com.example.ogan.listofdevelopersinlagosgithub.network.FetchUserDataUseCase;
import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.controllers.BaseActivity;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DeveloperDetailsActivity extends BaseActivity implements DeveloperDetailViewMvc.Listener, FetchUserDataUseCase.Listener {

    private DeveloperDetailViewMvc mDeveloperDetailViewMvc;
    private String mUppercaseUsername;
    private String mUserUrl;

    public static final String USERNAME_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.USERNAME";

    public static final String URL_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.URL";

    public static final String AVATAR_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.AVATAR";
    private FetchUserDataUseCase mFetchUserDataUseCase;

    private NetworkUtil mNetworkUtil;

    private DeveloperDatabase mDeveloperDatabase;
    private String mAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeveloperDetailViewMvc = getPresentationComponent().getViewMvcFactory().getDeveloperDetailViewMvc(null);
        mDeveloperDetailViewMvc.registerListener(this);

        setContentView(mDeveloperDetailViewMvc.getRootView());

        //getting transferred intent from mainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME_KEY);
        mUppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        mUserUrl = intent.getStringExtra(URL_KEY);
        mAvatar = intent.getStringExtra(AVATAR_KEY);

        //setting toolbar title and back button
        mDeveloperDetailViewMvc.setToolbarTitle(mUppercaseUsername);
        setSupportActionBar(mDeveloperDetailViewMvc.getToolBar());
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFetchUserDataUseCase = ((CustomApplication) getApplicationContext()).getApplicationComponent().getFetchUserDataUseCase();
        mFetchUserDataUseCase.registerListener(this);

        mNetworkUtil = ((CustomApplication) getApplicationContext()).getApplicationComponent().getNetworkUtil();

        mDeveloperDatabase = ((CustomApplication) getApplicationContext()).getApplicationComponent().getDeveloperDatabase();

        loadData(username);

        //this is for getting palette from avatar so as to customise view
        //mDeveloperDetailViewMvc.customiseView(avatar, getWindow());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(String username) {
        if (mNetworkUtil.isConnected()){
            //making network call with retrofit
            mFetchUserDataUseCase.fetchUserDataAndNotify(username);

            //to load image into imageView
            mFetchUserDataUseCase.loadImageAndNotify(this, mAvatar);
        } else {
            mDeveloperDatabase.getUserDao().getUser(username).observe(this, new Observer<UserApi>() {
                @Override
                public void onChanged(@Nullable UserApi userApi) {
                    mDeveloperDetailViewMvc.hideProgressBar();

                    if (userApi != null){
                        mDeveloperDetailViewMvc.displayData(userApi);
                        mDeveloperDetailViewMvc.showCardView();
                    } else {
                        //TODO display no data text
                    }
                }
            });
        }
    }

    @Override
    public void floatingActionButtonClicked() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome lagos based java developer; " + mUppercaseUsername + ". " + mUserUrl);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share GitHub user"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFetchUserDataUseCase.unregisterListener(this);
        mDeveloperDetailViewMvc.unregisterListener(this);
    }

    @Override
    public void onUserFetched(UserApi userApi) {
        mDeveloperDetailViewMvc.hideProgressBar();
        cacheData(userApi);
        mDeveloperDetailViewMvc.displayData(userApi);
        mDeveloperDetailViewMvc.showCardView();
    }

    private void cacheData(final UserApi userApi) {
        Intent startInsertResultService = new Intent(this, InsertUserService.class);
        startInsertResultService.putExtra(InsertUserService.USER_KEY, userApi);
        startService(startInsertResultService);
    }

    @Override
    public void onUserFetchFailed() {
        mDeveloperDetailViewMvc.hideProgressBar();
        Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestFailed() {
        mDeveloperDetailViewMvc.hideProgressBar();
        Toast.makeText(getApplicationContext(),
                "An error occurred while trying to get data. Please check network connection and try again.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        mDeveloperDetailViewMvc.setProfileImage(bitmap);
        mDeveloperDetailViewMvc.customiseView(bitmap, getWindow());
    }

    @Override
    public void onImageLoadingFail(Drawable errorDrawable) {
        mDeveloperDetailViewMvc.setProfileImage(errorDrawable);
    }
}
