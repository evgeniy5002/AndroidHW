package com.example.androidtest1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {

    private static final String PREFS = "app_prefs";
    private static final String KEY_LAUNCH_COUNT = "launch_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        int launchCount = prefs.getInt(KEY_LAUNCH_COUNT, 0) + 1;
        prefs.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply();

        if (launchCount == 2) {
            showRateDialog();
        }
    }

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
        String packageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

}

