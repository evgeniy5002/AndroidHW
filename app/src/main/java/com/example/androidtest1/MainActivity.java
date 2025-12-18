package com.example.androidtest1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS = "app_prefs";
    private static final String KEY_LAUNCH_COUNT = "launch_count";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 08.12.2025
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int launchCount = prefs.getInt(KEY_LAUNCH_COUNT, 0) + 1;
        prefs.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply();

        if (launchCount == 2) {
            showRateDialog();
            prefs.edit().putInt(KEY_LAUNCH_COUNT, 0).apply();
        }
        //





        // 21.11.2025 Програмно або через суфікси отримати мовні налаштування, в залежності від обраної
        // мови розмістити на ImageButton або ImageView прапор тієї чи іншої країни.
        ImageView ivCountryFlag = findViewById(R.id.imageView_flag);
        Locale locale = Locale.getDefault();
        TextView tvLanguage = findViewById(R.id.textView2_lang);
        tvLanguage.setTextSize(20);
        tvLanguage.setText(locale.getLanguage());

        if (locale.getLanguage().equals("zh"))
            Picasso.get()
                    .load("https://flagpedia.net/data/flags/w1600/cn.png")
                    .error(R.drawable.ic_error)
                    .into(ivCountryFlag);
        else if (locale.getLanguage().equals("fi"))
            Picasso.get()
                    .load("https://yourflag.com.au/wp-content/uploads/finland-country-flag.jpg")
                    .error(R.drawable.ic_error)
                    .into(ivCountryFlag);
        else if (locale.getLanguage().equals("de"))
            Picasso.get()
                    .load("https://m.media-amazon.com/images/I/51Un9PQTYxL._AC_UF350,350_QL80_.jpg")
                    .error(R.drawable.ic_error)
                    .into(ivCountryFlag);
        //





        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_second) {
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_third) {
                Intent intent = new Intent(this, ThirdActivity.class);
                EditText ett1 = findViewById(R.id.ett1);
                intent.putExtra("fromHome", ett1.getText() + "");
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
    //



    // 08.12.2025
    private void showRateDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_rate, null);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button ok = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            ok.setOnClickListener(v -> {
                int rating = (int) ratingBar.getRating();
                dialog.dismiss();

                if (rating <= 3) {
                    showFeedbackDialog();
                } else {
                    showPlayMarketDialog();
                }
            });
        });

        dialog.show();
    }
    private void showFeedbackDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Feedback")
                .setMessage("Would you like to leave feedback?")
                .setPositiveButton("Yes", (d, w) -> showFeedbackForm())
                .setNegativeButton("No", null)
                .show();
    }
    private void showFeedbackForm() {
        EditText editText = new EditText(this);
        editText.setHint("Your feedback");

        new AlertDialog.Builder(this)
                .setTitle("Your feedback")
                .setView(editText)
                .setPositiveButton("Send", (d, w) -> {
                    String feedback = editText.getText().toString();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    private void showPlayMarketDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thank you!")
                .setMessage("Would you like to rate us on Google Play?")
                .setPositiveButton("Go", (d, w) -> openPlayMarket())
                .setNegativeButton("Later", null)
                .show();
    }
    private void openPlayMarket() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.duolingo")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.duolingo")));
        }
    }
    //
}
