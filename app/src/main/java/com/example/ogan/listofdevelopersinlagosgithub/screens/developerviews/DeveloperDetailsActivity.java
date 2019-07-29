package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.controllers.BaseActivity;
import com.example.ogan.listofdevelopersinlagosgithub.viewmodel.DeveloperDetailViewModel;

public class DeveloperDetailsActivity extends BaseActivity implements DeveloperDetailViewMvc.Listener {

    private DeveloperDetailViewMvc mDeveloperDetailViewMvc;
    private String mUsername;
    private String mUppercaseUsername;
    private String mUserUrl;
    private String mAvatar;

    public static final String USERNAME_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.USERNAME";

    public static final String URL_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.URL";

    public static final String AVATAR_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.AVATAR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        offloadExtras();

        mDeveloperDetailViewMvc = getPresentationComponent().getViewMvcFactory().getDeveloperDetailViewMvc(null);
        mDeveloperDetailViewMvc.registerListener(this);

        setContentView(mDeveloperDetailViewMvc.getRootView());

        setupToolbar();

        DeveloperDetailViewModel developerDetailViewModel = ViewModelProviders.of(this,
                getPresentationComponent().getDeveloperDetailViewModelFactory())
                .get(DeveloperDetailViewModel.class);

        developerDetailViewModel.loadData(mUsername, mAvatar);

        developerDetailViewModel.loading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean loading) {
                if (loading != null){
                    if (!loading){
                        mDeveloperDetailViewMvc.hideProgressBar();
                    }
                }
            }
        });

        developerDetailViewModel.getUserApi().observe(this, new Observer<UserApi>() {
            @Override
            public void onChanged(@Nullable UserApi userApi) {
                if (userApi != null){
                    mDeveloperDetailViewMvc.displayData(userApi);
                    mDeveloperDetailViewMvc.showCardView();
                }
            }
        });

        developerDetailViewModel.getNoData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean noData) {
                if (noData != null){
                    if (noData){
                            mDeveloperDetailViewMvc.hideCardView();
                        mDeveloperDetailViewMvc.showMessage("No data");
                    }
                }
            }
        });

        developerDetailViewModel.getUserFetchingError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean userFetchingError) {
                if (userFetchingError != null){
                    if (userFetchingError){
                        mDeveloperDetailViewMvc.showMessage("Error loading data");
                    }
                }
            }
        });

        developerDetailViewModel.getRequestFailed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean requestFailed) {
                if (requestFailed != null){
                    if (requestFailed){
                        mDeveloperDetailViewMvc.showMessage("An error occurred while trying to get data. Please check network connection and try again.");
                    }
                }
            }
        });

        developerDetailViewModel.getBitmap().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                if (bitmap != null){
                    mDeveloperDetailViewMvc.setProfileImage(bitmap);
                    mDeveloperDetailViewMvc.customiseView(bitmap, getWindow());
                }
            }
        });

        developerDetailViewModel.getErrorDrawable().observe(this, new Observer<Drawable>() {
            @Override
            public void onChanged(@Nullable Drawable errorDrawable) {
                if (errorDrawable != null){
                    mDeveloperDetailViewMvc.setProfileImage(errorDrawable);
                }
            }
        });

        //this is for getting palette from avatar so as to customise view
        //mDeveloperDetailViewMvc.customiseView(avatar, getWindow());
    }

    private void setupToolbar() {
        //setting toolbar title and back button
        mDeveloperDetailViewMvc.setToolbarTitle(mUppercaseUsername);
        setSupportActionBar(mDeveloperDetailViewMvc.getToolBar());
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void offloadExtras() {
        //getting transferred intent from mainActivity
        Intent intent = getIntent();
        mUsername = intent.getStringExtra(USERNAME_KEY);
        mUppercaseUsername = mUsername.substring(0, 1).toUpperCase() + mUsername.substring(1);
        mUserUrl = intent.getStringExtra(URL_KEY);
        mAvatar = intent.getStringExtra(AVATAR_KEY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        mDeveloperDetailViewMvc.unregisterListener(this);
        super.onDestroy();
    }

}
