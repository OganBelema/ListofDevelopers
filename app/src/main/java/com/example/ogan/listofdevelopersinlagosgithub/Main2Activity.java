package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity {

    private final String URL = "https://api.github.com/search/users?q=language:java+location:lagos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        GithubAsyncTask task = new GithubAsyncTask();
        task.execute();

    }

    private class GithubAsyncTask extends AsyncTask<String, Void, ArrayList<GithubUser>> {

        @Override
        protected ArrayList<GithubUser> doInBackground(String... String) {
            ArrayList<GithubUser> result = Util.fetchGithubUserData(URL);
            return result;
        }


        @Override
        protected void onPostExecute(ArrayList<GithubUser> data) {
            Intent intent = getIntent();
            int value = intent.getIntExtra("position", 0);
            CircleImageView circleImageView = (CircleImageView) findViewById(R.id.user_pic);
            circleImageView.setImageBitmap(data.get(value).getAvatar());

            TextView user_name = (TextView) findViewById(R.id.github_username);
            final String UppercaseUsername = data.get(value).getName().substring(0,1).toUpperCase() + data.get(value).getName().substring(1);
            user_name.setText(UppercaseUsername);

            final String user_url = data.get(value).getUser_url();

            final TextView url = (TextView) findViewById(R.id.user_url);
            url.setText(data.get(value).getUser_url());
            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri userUrl = Uri.parse(url.getText().toString());
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,userUrl);
                    startActivity(webIntent);
                }
            });

            Button share = (Button) findViewById(R.id.share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer " + UppercaseUsername+ ", " + user_url + ".");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "send"));
                }
            });

        }
    }
}
