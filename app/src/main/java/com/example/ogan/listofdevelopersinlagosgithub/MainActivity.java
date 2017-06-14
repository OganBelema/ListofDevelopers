package com.example.ogan.listofdevelopersinlagosgithub;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://api.github.com/search/users?q=location:lagos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            MyAdapter adapter = new MyAdapter(getApplicationContext(),new ArrayList<GithubUser>());
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
            Log.e("I was touched", "here");
        }
    }
}
