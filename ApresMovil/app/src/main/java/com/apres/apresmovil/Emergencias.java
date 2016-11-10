package com.apres.apresmovil;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Emergencias extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + "08001227737"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, callIntent, 0);

        Intent callIntent2 = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + "42571111"));
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, callIntent, 0);

        // Get the layout for the App Widget and attach an on-click listener to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.emergencias);
        views.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
        views.setOnClickPendingIntent(R.id.actionButton2, pendingIntent2);

        // Tell the AppWidgetManager to perform an update on the current App Widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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

