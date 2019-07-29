package com.example.ogan.listofdevelopersinlagosgithub;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.ogan.listofdevelopersinlagosgithub.common.Constants;
import com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews.DeveloperDetailsActivity;
import com.squareup.picasso.Picasso;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PEREFERENCE_ID, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.USERNAME_KEY, "");
        String userUrl = sharedPreferences.getString(Constants.URL_KEY, "");
        String avatar = sharedPreferences.getString(Constants.AVATAR_KEY, "");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.dev_appwidget_text, username);
        Picasso.get().load(avatar)
                .error(R.drawable.octocat)
                .into(views, R.id.dev_appwidget_imageView, appWidgetManager
                .getAppWidgetIds(new ComponentName(context, AppWidget.class)));

        setClickListener(context, views, username, userUrl, avatar);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void setClickListener(Context context, RemoteViews views, String username, String userUrl, String avatar) {
        Intent intent = new Intent(context, DeveloperDetailsActivity.class);
        intent.putExtra(Constants.USERNAME_KEY, username);
        intent.putExtra(Constants.URL_KEY, userUrl);
        intent.putExtra(Constants.AVATAR_KEY, avatar);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, 0 );
        views.setOnClickPendingIntent(R.id.dev_appwidget_imageView, pendingIntent);
        views.setOnClickPendingIntent(R.id.dev_appwidget_text, pendingIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

