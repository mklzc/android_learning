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

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class class_info extends AppCompatActivity {
    boolean isNextClass = true;
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
        TextView tvClass = findViewById(R.id.next_class);
        tvClass.setText(getNextClass());

        Button btFormerClass = findViewById(R.id.btformer_class);
        btFormerClass.setOnClickListener(view -> {
            if (isNextClass) {
                btFormerClass.setText("下一节课");
                tvClass.setText(getFormerClass());
                isNextClass = false;
            }
            else {
                btFormerClass.setText("上一节课");
                tvClass.setText(getNextClass());
                isNextClass = true;
            }
        });

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

        Button xinXiMenHu = findViewById(R.id.xinXiMenHu);
        xinXiMenHu.setOnClickListener(view -> {
            final String TARGET_URL = "http://my.bupt.edu.cn";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TARGET_URL));
            startActivity(browserIntent);
        });
    }

    public static class Course {
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
//    大一上课表
//    static {
//        timetable.put("Python与人工智能", new Course("Python与人工智能", 1, "19:20-20:00", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S509"));
//        timetable.put("军事理论", new Course("军事理论", 3, "08:00-09:35", Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "N104"));
//        timetable.put("高阶综合英语", new Course("高阶综合英语", 3, "09:50-11:25", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S1-309"));
//        timetable.put("大学生心理健康1", new Course("大学生心理健康1", 4, "16:35-18:10", Arrays.asList(3, 4, 5), "S210"));
//        timetable.put("线性代数", new Course("线性代数", 4, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S210"));
//        timetable.put("创新创业实践课", new Course("创新创业实践课", 1, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "N315"));
//        timetable.put("计算导论与程序设计 (星期二)", new Course("计算导论与程序设计", 2, "08:00-09:35", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S302"));
//        timetable.put("计算导论与程序设计 (星期四)", new Course("计算导论与程序设计", 4, "08:00-09:35", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S404"));
//        timetable.put("信息通信概论 A", new Course("信息通信概论 A", 1, "08:00-09:35", Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18), "S207"));
//        timetable.put("形势与政策1", new Course("形势与政策1", 1, "08:00-09:35", Arrays.asList(8, 9, 10), "N403"));
//        timetable.put("习近平新时代中国特色社会主义思想概论", new Course("习近平新时代中国特色社会主义思想概论", 2, "09:50-12:15", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "N116"));
//        timetable.put("数学分析(上)(星期二)", new Course("数学分析(上)", 2, "15:40-18:10", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S208"));
//        timetable.put("数学分析(上)(星期五)", new Course("数学分析(上)", 5, "13:00-15:30", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), "S205"));
//        timetable.put("思想道德与法治", new Course("思想道德与法治", 4, "09:50-12:15", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S404"));
//    }
//  大一下课表
    static {
        timetable.put("国家安全教育（下）", new Course("国家安全教育（下）", 1, "08:00-09:35", Arrays.asList(4, 5, 6, 7), "N108"));
        timetable.put("数学分析(下)1", new Course("数学分析(下)", 1, "09:50-12:15", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S104"));
        timetable.put("中国近现代史纲要", new Course("中国近现代史纲要", 1, "13:00-15:30", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14), "N308"));
        timetable.put("大学物理D2", new Course("大学物理D", 2, "08:00-09:35", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S216"));
        timetable.put("形势与政策2", new Course("形势与政策2", 2, "13:00-14:35", Arrays.asList(8, 9, 10), "N513"));
        timetable.put("设计思维", new Course("设计思维", 2, "16:35-18:10", Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16), "S502"));
        timetable.put("微信小程序开发入门（双创）", new Course("微信小程序开发入门（双创）", 2, "19:20-20:55", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), "S1-110"));
        timetable.put("中华人民共和国史", new Course("中华人民共和国史", 3, "08:00-09:35", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), "N119"));
        timetable.put("科技胜任力英语", new Course("科技胜任力英语", 3, "09:50-11:25", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S1-413"));
        timetable.put("人工智能引论 A", new Course("人工智能引论 A", 4, "08:00-09:35", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "N110"));
        timetable.put("大学物理D4", new Course("大学物理D", 4, "09:50-11:25", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S216"));
        timetable.put("数学分析(下)4", new Course("数学分析(下)", 4, "13:00-15:30", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S106"));
        timetable.put("离散数学(上)", new Course("离散数学(上)", 4, "16:35-18:10", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S205"));
        timetable.put("电路与电子学基础", new Course("电路与电子学基础", 5, "08:00-09:35", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "S302"));
        timetable.put("项目式课程阶段1（计算导论与程序设计课程设计）", new Course("项目式课程阶段1（计算导论与程序设计课程设计）", 5, "09:50-12:15", Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14), "N319"));
        timetable.put("体育基础", new Course("体育基础", 5, "14:45-16:25", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), "体育场"));
    }



    public static int calculateCurrentWeek() {
//        LocalDate startDate = LocalDate.of(2024, 8, 26);
        LocalDate startDate = LocalDate.of(2025, 2, 24);
        LocalDate today = LocalDate.now();
        int deltaDays = (int) (today.toEpochDay() - startDate.toEpochDay());
        return deltaDays / 7 + 1;
    }
    public String getFormerClass() {
        int currentWeek = calculateCurrentWeek();
        LocalDateTime now = LocalDateTime.now();
        int currentDayOfWeek = now.getDayOfWeek().getValue(); // Monday = 1, Sunday = 7
        LocalTime currentTime = now.toLocalTime();
        List<Map.Entry<String, Course>> classes = new ArrayList<>();
        for (Map.Entry<String, Course> entry : timetable.entrySet()) {
            Course course = entry.getValue();
            if (course.dayOfWeek == currentDayOfWeek && course.weeks.contains(currentWeek)) {
                String startTimeString = course.time.split("-")[0];
                LocalTime startTime = LocalTime.parse(startTimeString, DateTimeFormatter.ofPattern("HH:mm"));
                if (startTime.isBefore(currentTime)) {
                    classes.add(entry);
                }
            }
        }
        classes.sort(Comparator.comparing(e -> LocalTime.parse(e.getValue().time.split("-")[0], DateTimeFormatter.ofPattern("HH:mm"))));
        Collections.reverse(classes);
        if (!classes.isEmpty()) {
            Map.Entry<String, Course> formerClassEntry = classes.get(0);
            Course formerClass = formerClassEntry.getValue();
            return String.format("上一节课是%s\n时间：%s\n地点：%s", formerClass.courseName, formerClass.time, formerClass.location);
        }
        else {
            return "这是今天的第一节课";
        }
    }
    //
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
