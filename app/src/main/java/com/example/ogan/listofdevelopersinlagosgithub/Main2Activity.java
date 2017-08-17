package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.GetUser;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    TextView txtUrl;
    TextView txtFullName;
    TextView repo;
    CircleImageView imgAvatar;
    FloatingActionButton button;
    GetUser getUser;
    private ProgressBar progressBar;
    CardView cardView;
    CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;

    private static final String URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        progressBar = (ProgressBar) findViewById(R.id.pb_detail);
        txtUrl = (TextView) findViewById(R.id.user_url);
        imgAvatar = (CircleImageView) findViewById(R.id.user_pic);
        button = (FloatingActionButton) findViewById(R.id.fabBtn);
        txtFullName = (TextView) findViewById(R.id.user_name);
        repo = (TextView) findViewById(R.id.repo_number);
        cardView = (CardView) findViewById(R.id.course_card);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        getUser = retrofit.create(GetUser.class);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        final String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        final String userUrl = intent.getStringExtra("url");
        String avatar = intent.getStringExtra("avatar");

        //setTitle(uppercaseUsername);
        toolbar.setTitle(uppercaseUsername);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Call<UserApi> call = getUser.getUserData(username);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                System.out.println("error" + response.errorBody());
                System.out.println("code" + response.code());


                UserApi userResult = response.body();

                if (userResult != null) {
                    String fullName = userResult.getName();
                    if(fullName != null){
                        txtFullName.setText(fullName);
                    }
                    String repoNumber = userResult.getPublicRepos().toString();
                    repo.setText(repoNumber + " repositories");
                    progressBar.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.VISIBLE);

                } else {

                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again.", Toast.LENGTH_SHORT).show();

                System.out.println("t" + t.getMessage());

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome lagos based java developer; " + uppercaseUsername+ ". " + userUrl );
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share GitHub user"));
            }
        });

        txtUrl.setText(userUrl);

        Picasso.with(getApplicationContext()).load(avatar).into(imgAvatar);

        Picasso.with(getApplicationContext()).load(avatar)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        if (swatch == null) {
                                            return;
                                        }

                                        button.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
                                        toolbar.setBackgroundColor(swatch.getRgb());
                                        toolbar.setTitleTextColor(swatch.getTitleTextColor());

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                getWindow().setStatusBarColor(swatch.getRgb());
                                            }

                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }


}
