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
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        final String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
        final String userUrl = intent.getStringExtra("url");
        String avatar = intent.getStringExtra("avatar");

        setTitle(uppercaseUsername);

        txtName = (TextView) findViewById(R.id.github_username);
        txtUrl = (TextView) findViewById(R.id.user_url);
        imgAvatar = (CircleImageView) findViewById(R.id.user_pic);
        button = (Button) findViewById(R.id.share);
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

        txtName.setText(uppercaseUsername);
        txtUrl.setText(userUrl);

        Picasso.with(getApplicationContext()).load(avatar).into(imgAvatar);



    }


}
