package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kuaidi extends AppCompatActivity {
    private final List<String> mData = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kuaidi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton Home_button = findViewById(R.id.Home_button);
        Home_button.setOnClickListener(view -> Home());
        ImageButton card_button = findViewById(R.id.id_card);
        card_button.setOnClickListener(view -> openIdBarcode());

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSmsInPhone();
        MyAdapter mAdapter = new MyAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }
    private boolean judge(String strBody) {
        final List<String> keywords = Arrays.asList("驿收发");
        for (String kw : keywords) {
            if (strBody.contains(kw)) {
                return true;
            }
        }
        return false;
    }
    public String getPackageId(String strBody) {
        String regex = "[0-9A-Z]+-[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strBody);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "未找到取货码";
        }
    }
    public void getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";
        // StringBuilder 与 String 不同的是 StringBuilder 能够被多次修改，而不产生新的使用对象
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            // parse 将字符串转化为 uri 对象
            String[] projection = new String[] {"body", "date", };
            Cursor cur = getContentResolver().query(uri, projection, null,
                    null, "date desc");
            assert cur != null;
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            while (cur.moveToNext()) {
                StringBuilder smsBuilder = new StringBuilder();
                String strBody = cur.getString(index_Body);
                long longDate = cur.getLong(index_Date);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date(longDate);
                String strDate = dateFormat.format(d);
                if (judge(strBody)) {
                    String packageId = getPackageId(strBody);
                    smsBuilder.append("取货码：");
                    smsBuilder.append(packageId).append("\n日期：");
                    smsBuilder.append(strDate);
                }
                if (smsBuilder.length() != 0) {
                    mData.add(smsBuilder.toString());
                }
            }
            if (!cur.isClosed()) {
                cur.close();
            }
        } catch (SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", Objects.requireNonNull(ex.getMessage()));
        }
    }
    public void Home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openIdBarcode() {
        String path = "taobao://m.tb.cn/h.gwwsUs3";
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(path);
        intent.setData(uri);
        startActivity(intent);
    }
}
