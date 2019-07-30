package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.oganbelema.listofdevelopersinlagosgithub.R;
import com.oganbelema.listofdevelopersinlagosgithub.common.Constants;
import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.controllers.BaseActivity;
import com.oganbelema.listofdevelopersinlagosgithub.viewmodel.DeveloperDetailViewModel;

public class DeveloperDetailsActivity extends BaseActivity implements DeveloperDetailViewMvc.Listener {

    private DeveloperDetailViewMvc mDeveloperDetailViewMvc;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    private String mUsername;
    private String mUppercaseUsername;
    private String mUserUrl;
    private String mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeveloperDetailViewMvc = getPresentationComponent().getViewMvcFactory().getDeveloperDetailViewMvc(null);
        mDeveloperDetailViewMvc.registerListener(this);

        setContentView(mDeveloperDetailViewMvc.getRootView());

        offloadExtras();

        saveExtrasInSharedPreference();

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
                        mDeveloperDetailViewMvc.showMessage(getString(R.string.no_data));
                    }
                }
            }
        });

        developerDetailViewModel.getUserFetchingError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean userFetchingError) {
                if (userFetchingError != null){
                    if (userFetchingError){
                        mDeveloperDetailViewMvc.showMessage(getString(R.string.data_loading_error));
                    }
                }
            }
        });

        developerDetailViewModel.getRequestFailed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean requestFailed) {
                if (requestFailed != null){
                    if (requestFailed){
                        mDeveloperDetailViewMvc.showMessage(getString(R.string.network_error));
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
        setUpAd();
    }

    private void setUpAd() {
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("/6499/example/interstitial");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                shareDeveloper();
            }
        });
    }

    private void saveExtrasInSharedPreference() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PEREFERENCE_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putString(Constants.USERNAME_KEY, mUsername);
        sharedPreferenceEditor.putString(Constants.URL_KEY, mUserUrl);
        sharedPreferenceEditor.putString(Constants.AVATAR_KEY, mAvatar);
        sharedPreferenceEditor.apply();
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
        mUsername = intent.getStringExtra(Constants.USERNAME_KEY);
        mUppercaseUsername = mUsername.substring(0, 1).toUpperCase() + mUsername.substring(1);
        mUserUrl = intent.getStringExtra(Constants.URL_KEY);
        mAvatar = intent.getStringExtra(Constants.AVATAR_KEY);
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
        if (mPublisherInterstitialAd.isLoaded()) {
            mPublisherInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            shareDeveloper();
        }
    }

    private void shareDeveloper() {
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
