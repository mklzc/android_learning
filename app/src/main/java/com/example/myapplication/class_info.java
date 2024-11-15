package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class class_info extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageButton homeButton = findViewById(R.id.Home_button);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(class_info.this, MainActivity.class);
            startActivity(intent);
        });
        TextView nextClass = findViewById(R.id.next_class);
        nextClass.setText(getNextClass());

        Button ucloudButton = findViewById(R.id.ucloud);
        ucloudButton.setOnClickListener(view -> {
            final String TARGET_URL = "https://ucloud.bupt.edu.cn/uclass/index.html#/student/homePage";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TARGET_URL));
            startActivity(browserIntent);
        });

        Button ktp = findViewById(R.id.ketangpai);
        ktp.setOnClickListener(view -> {
            final String TARGET_URL = "https://www.ketangpai.com/#/main";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TARGET_URL));
            startActivity(browserIntent);
        });
    }

    static class Course {
        int dayOfWeek; // 1 = Monday, 7 = Sunday
        String time;
        List<Integer> weeks;
        String location;
        String courseName; // 新增变量用于存储课程名称

        Course(String courseName, int dayOfWeek, String time, List<Integer> weeks, String location) {
            this.courseName = courseName; // 初始化课程名称
            this.dayOfWeek = dayOfWeek;
            this.time = time;
            this.weeks = weeks;
            this.location = location;
        }
    }

    static Map<String, Course> timetable = new HashMap<>();

    static {
        timetable.put("Python与人工智能", new Course("Python与人工智能", 1, "19:20-20:00", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S509"));
        timetable.put("军事理论", new Course("军事理论", 3, "08:00-09:35", Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "N104"));
        timetable.put("高阶综合英语", new Course("高阶综合英语", 3, "09:50-11:25", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S1-309"));
        timetable.put("大学生心理健康1", new Course("大学生心理健康1", 4, "16:35-18:10", Arrays.asList(3, 4, 5), "S210"));
        timetable.put("线性代数", new Course("线性代数", 4, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S210"));
        timetable.put("创新创业实践课", new Course("创新创业实践课", 1, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "N315"));
        timetable.put("计算导论与程序设计 (星期二)", new Course("计算导论与程序设计", 2, "08:00-09:35", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S302"));
        timetable.put("计算导论与程序设计 (星期四)", new Course("计算导论与程序设计", 4, "08:00-09:35", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S404"));
        timetable.put("信息通信概论 A", new Course("信息通信概论 A", 1, "08:00-09:35", Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18), "S207"));
        timetable.put("形势与政策1", new Course("形势与政策1", 1, "08:00-09:35", Arrays.asList(8, 9, 10), "N403"));
        timetable.put("习近平新时代中国特色社会主义思想概论", new Course("习近平新时代中国特色社会主义思想概论", 2, "09:50-12:15", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "N116"));
        timetable.put("数学分析(上)(星期二)", new Course("数学分析(上)", 2, "15:40-18:10", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S208"));
        timetable.put("数学分析(上)(星期五)", new Course("数学分析(上)", 5, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S205"));
        timetable.put("思想道德与法治", new Course("思想道德与法治", 4, "09:50-12:15", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S404"));
    }

    public static int calculateCurrentWeek() {
        LocalDate startDate = LocalDate.of(2024, 8, 26);
        LocalDate today = LocalDate.now();
        int deltaDays = (int) (today.toEpochDay() - startDate.toEpochDay());
        return deltaDays / 7 + 1;
    }

    public static String getNextClass() {
        int currentWeek = calculateCurrentWeek();
        LocalDateTime now = LocalDateTime.now();
        int currentDayOfWeek = now.getDayOfWeek().getValue(); // Monday = 1, Sunday = 7
        LocalTime currentTime = now.toLocalTime();

        List<Map.Entry<String, Course>> todayClasses = new ArrayList<>();

        for (Map.Entry<String, Course> entry : timetable.entrySet()) {
            Course course = entry.getValue();
            if (course.dayOfWeek == currentDayOfWeek && course.weeks.contains(currentWeek)) {
                String startTimeString = course.time.split("-")[0];
                LocalTime startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("HH:mm"));
                if (currentTime.isBefore(startTime)) {
                    todayClasses.add(entry);
                }
            }
        }

        todayClasses.sort(Comparator.comparing(e -> LocalTime.parse(e.getValue().time.split("-")[0], DateTimeFormatter.ofPattern("HH:mm"))));

        if (!todayClasses.isEmpty()) {
            Map.Entry<String, Course> nextClassEntry = todayClasses.get(0);
            Course nextClass = nextClassEntry.getValue();
            return String.format("下一节课是%s\n时间: %s\n地点: %s", nextClass.courseName, nextClass.time, nextClass.location);
        } else {
            return "今天没有更多课程";
        }
    }

}
