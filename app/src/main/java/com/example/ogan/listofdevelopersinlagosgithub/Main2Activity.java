package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {

    TextView txtName;
    TextView txtUrl;
    CircleImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        String userUrl = intent.getStringExtra("url");
        String avatar = intent.getStringExtra("avatar");

        txtName = (TextView) findViewById(R.id.github_username);
        txtUrl = (TextView) findViewById(R.id.user_url);
        imgAvatar = (CircleImageView) findViewById(R.id.user_pic);

        txtName.setText(uppercaseUsername);
        txtUrl.setText(userUrl);

        Picasso.with(getApplicationContext()).load(avatar).into(imgAvatar);



    }


}
