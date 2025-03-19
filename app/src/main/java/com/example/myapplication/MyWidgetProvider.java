package com.example.myapplication;

import static com.example.myapplication.class_info.getFormerClass;
import static com.example.myapplication.class_info.getNextClass;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("Widget_Debug", "onReceive() called, action: " + intent.getAction());
        if ("com.example.myapplication.WIDGET_BUTTON_CLICK".equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // 更新内容
//        views.setTextViewText(R.id.widget_text, getNextClass());
//        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "font/custom_font.ttf");
//        views.setTextViewTypeface(R.id.widget_text, customFont);

        Bitmap textBitmap = createTextBitmap(context, getNextClass());
        views.setImageViewBitmap(R.id.widget_image, textBitmap);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        // 绑定点击事件
        Intent intent = new Intent(context, MyWidgetProvider.class);
        intent.setAction("com.example.myapplication.WIDGET_BUTTON_CLICK");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
//         更新小组件
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static Bitmap createTextBitmap(Context context, String text) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/kaiti.ttf");
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(typeface);
        paint.setTextSize(100);
        paint.setColor(0xFF000000);  // 黑色字体

        float textWidth = paint.measureText(text);
        float textHeight = 100;

        Bitmap bitmap = Bitmap.createBitmap((int) textWidth, (int) textHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, textHeight - paint.descent(), paint);

        return bitmap;
    }
}