package com.example.martyna.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class MediaWidget extends AppWidgetProvider  {

    RemoteViews views;
    private static boolean status = false;

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        views = new RemoteViews(context.getPackageName(), R.layout.media_widget);

        //open website
        if(intent.getAction().equals("OPEN_WEB")){
            appWidgetManager.updateAppWidget(new ComponentName(context, MediaWidget.class), views);
        }

        //toggle image
        if(intent.getAction().equals("CHANGE_PICTURE")){
            if(status){
                views.setImageViewResource(R.id.image, R.drawable.skynight);
                status = false;
            }else{
                views.setImageViewResource(R.id.image, R.drawable.fjord);
                status = true;
            }
            views.setOnClickPendingIntent(R.id.image_button, MediaWidget.buildPendingIntentImage(context));

            appWidgetManager.updateAppWidget(new ComponentName(context, MediaWidget.class), views);
        }

    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.media_widget);

        //open webpage on click
        Intent intentWeb = new Intent(Intent.ACTION_VIEW);
        intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentWeb.setData(Uri.parse("http://www.pja.edu.pl/"));
        PendingIntent pendingWeb = PendingIntent.getActivity(context, 0, intentWeb, 0);
        views.setOnClickPendingIntent(R.id.web_button, pendingWeb);

        //change image on click
        views.setOnClickPendingIntent(R.id.image_button, buildPendingIntentImage(context));


        // Instruct the widget manager to update the widget
        appWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, MediaWidget.class), views);

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

    public static PendingIntent buildPendingIntentImage(Context context) {
        Intent intent = new Intent();
        intent.setAction("CHANGE_PICTURE");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}





