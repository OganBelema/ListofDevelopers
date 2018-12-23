package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GithubApi;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;
import com.example.ogan.listofdevelopersinlagosgithub.common.Constants;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeveloperDetailsActivity extends BaseActivity implements DeveloperDetailViewMvc.Listener {

    private DeveloperDetailViewMvc mDeveloperDetailViewMvc;
    private String mUppercaseUsername;
    private String mUserUrl;

    public static final String USERNAME_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.USERNAME";

    public static final String URL_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.URL";

    public static final String AVATAR_KEY =
            "com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.AVATAR";
    private GithubApi mGithubApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeveloperDetailViewMvc = new DeveloperDetailViewMvcImpl(LayoutInflater.from(this), null);
        mDeveloperDetailViewMvc.registerListener(this);

        setContentView(mDeveloperDetailViewMvc.getRootView());

        //getting transferred intent from mainActivity
        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME_KEY);
        mUppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        mUserUrl = intent.getStringExtra(URL_KEY);
        String avatar = intent.getStringExtra(AVATAR_KEY);

        //setting toolbar title and back button
        mDeveloperDetailViewMvc.setToolbarTitle(mUppercaseUsername);
        setSupportActionBar(mDeveloperDetailViewMvc.getToolBar());
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mGithubApi = getCompositionRoot().getGithubApi();

        //making network call with retrofit
        loadData(username);

        //to load image into imageView
        mDeveloperDetailViewMvc.setProfileImage(avatar);


        //this is for getting palette from avatar so as to customise view
        mDeveloperDetailViewMvc.customiseView(avatar, getWindow());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(String username) {

        mGithubApi.getUserData(username).enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(@NonNull Call<UserApi> call, @NonNull Response<UserApi> response) {

                mDeveloperDetailViewMvc.hideProgressBar();

                UserApi userResult = response.body();

                if (userResult != null) {
                    mDeveloperDetailViewMvc.displayData(userResult, mUserUrl);
                    mDeveloperDetailViewMvc.showCardView();

                } else {

                    mDeveloperDetailViewMvc.hideProgressBar();
                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<UserApi> call, @NonNull Throwable t) {

                mDeveloperDetailViewMvc.hideProgressBar();
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again.", Toast.LENGTH_SHORT).show();


            }
        });
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
        mDeveloperDetailViewMvc.unregisterListener(this);
    }
}
