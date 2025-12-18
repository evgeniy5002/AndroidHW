package com.example.androidtest1;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// 18.11.2025 Натискання на кнопку виводить у тост
// текст вашої улюбленої пісніпо одному куплету/приспіву
public class VideoActivity extends AppCompatActivity {
    private TextView textView;
    private VideoView videoView;
    private Button btnPlayVideo;

    private int subtitleIndex = 0;
    private final Handler handler = new Handler();
    private Subtitles subtitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.video_view);
        textView = findViewById(R.id.textView);
        btnPlayVideo = findViewById(R.id.play_video_btn);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.program_in_c;
        videoView.setVideoURI(Uri.parse(videoPath));

        new Thread(() -> {
            try {
                subtitles = SRTParser.parse(this, "program_in_c_subs.srt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        btnPlayVideo.setOnClickListener(view -> {
            String hexColor = getRandomHexColor();
            btnPlayVideo.setText(hexColor);
            btnPlayVideo.setBackgroundColor(Color.parseColor(hexColor));

            try {
                SRTParser.parse(this, "program_in_c_subs.srt");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            videoView.start();
            updateSubtitles();
        });
    }

    public void updateSubtitles() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < subtitles.size(); i++) {
                    if (subtitles.get(i).isTimeInside(videoView.getCurrentPosition())) {
                        String text = subtitles.get(i).getText();
                        runOnUiThread(() -> textView.setText(text));
                    }
                }
            }
        }, 0, 20);
    }

    private String getRandomHexColor() {
        return String.format("#%06X", (int) (Math.random() * 0xFFFFFF));
    }
}

class SRTParser {
    public static Subtitles parse(Context context, String filePath) {
        Subtitles subtitles = new Subtitles();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filePath)))) {
            long startMilliseconds;
            long endMilliseconds;
            String text;

            String line;
            while ((line = reader.readLine()) != null) {
                line = reader.readLine();
                String startTime = line.substring(0, line.indexOf("-->")).strip();
                String endTime = line.substring(line.indexOf("-->") + 3).strip();
                startMilliseconds = parseTime(startTime);
                endMilliseconds = parseTime(endTime);
                text = reader.readLine();
                reader.readLine(); // skip empty line

                subtitles.add(new Subtitle(startMilliseconds, endMilliseconds, text));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage() + "\n");
        }

        return subtitles;
    }

    public static long parseTime(String srtTime) {
        short hours = Short.parseShort(srtTime.substring(0, 2));
        short minutes = Short.parseShort(srtTime.substring(3, 5));
        short seconds = Short.parseShort(srtTime.substring(6, 8));
        short millseconds = Short.parseShort(srtTime.substring(9));
        return (long) (hours * 3600000 + minutes * 60000 + seconds * 1000 + millseconds);
    }
}


class Subtitle {
    private final long startMilliseconds;
    private final long endMilliseconds;
    private final String text;

    public Subtitle(long startMilliseconds, long endMilliseconds, String text) {
        this.startMilliseconds = startMilliseconds;
        this.endMilliseconds = endMilliseconds;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isTimeInside(long currentMilliseconds) {
        return currentMilliseconds >= startMilliseconds && currentMilliseconds <= endMilliseconds;
    }

    @Override
    public String toString() {
        return "Start: " + this.startMilliseconds + "\n" + "End:   " + this.endMilliseconds + "\n" + "Text:  " + this.text;
    }
}


class Subtitles {
    private ArrayList<Subtitle> subtitles;

    public Subtitles() {
        subtitles = new ArrayList<Subtitle>();
    }

    public void add(Subtitle subtitle) {
        subtitles.add(subtitle);
    }

    public Subtitle get(int i) {
        return subtitles.get(i);
    }

    public int size() {
        return subtitles.size();
    }
}