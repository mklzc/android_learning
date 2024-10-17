package com.example.myapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private List<String> mData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSmsInPhone();
        MyAdapter mAdapter = new MyAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }
    private boolean judge(String strBody) {
        final List<String> keywords = Arrays.asList("驿收发", "快递");
        for (String kw : keywords) {
            if (strBody.contains(kw)) {
                return true;
            }
        }
        return false;
    }
    public void getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";

        // StringBuilder 与 String 不同的是 StringBuilder 能够被多次修改，而不产生新的使用对象
        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            // parse 将字符串转化为 uri 对象
            String[] projection = new String[] { "_id", "address", "person",
                    "body", "date", };
            Cursor cur = getContentResolver().query(uri, projection, null,
                    null, "date desc");
            assert cur != null;
            int index_Address = cur.getColumnIndex("address");
            int index_Person = cur.getColumnIndex("person");
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            while (cur.moveToNext()) {
                StringBuilder smsBuilder = new StringBuilder();
                String strAddress = cur.getString(index_Address);
                int intPerson = cur.getInt(index_Person);
                String strBody = cur.getString(index_Body);
                long longDate = cur.getLong(index_Date);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d = new Date(longDate);
                String strDate = dateFormat.format(d);
                if (judge(strBody)) {
                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress).append(", ");
                    smsBuilder.append(intPerson).append(", ");
                    smsBuilder.append(strBody).append(", ");
                    smsBuilder.append(strDate).append(", ");
                    smsBuilder.append(" ]");
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
}