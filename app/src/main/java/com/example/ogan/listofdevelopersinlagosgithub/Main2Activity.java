package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.GetData;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.GetUser;
import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    FrameLayout frameLayout;

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
        frameLayout = (FrameLayout) findViewById(R.id.rootLayout);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        getUser = retrofit.create(GetUser.class);




        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        final String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        final String userUrl = intent.getStringExtra("url");
        String avatar = intent.getStringExtra("avatar");

        setTitle(uppercaseUsername);



        Call<UserApi> call = getUser.getUserData(username);
        call.enqueue(new Callback<UserApi>() {
            @Override
            public void onResponse(Call<UserApi> call, Response<UserApi> response) {
                System.out.println("error" + response.errorBody());
                System.out.println("code" + response.code());

                progressBar.setVisibility(View.INVISIBLE);
                UserApi userResult = response.body();

                if (userResult != null) {
                    String fullName = userResult.getName();
                    txtFullName.setText(fullName);
                    String repoNumber = userResult.getPublicRepos().toString();
                    repo.setText(repoNumber);
                    cardView.setVisibility(View.VISIBLE);

                } else {

                }

            }

            @Override
            public void onFailure(Call<UserApi> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "An error occurred while trying to get data. Please check network connection and try again.", Toast.LENGTH_SHORT).show();
                /* Snackbar.make(frameLayout, "An error occurred while trying to get data.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent1);
                    }
                }).show(); */

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



    }


}
