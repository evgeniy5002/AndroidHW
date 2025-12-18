package com.example.androidtest1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {
    private String TAG = "qwerty123456";
    int numQ = 0;
    private ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
//                                String text = data.getStringExtra("fromQwerty");
//                                TextView activityRes = findViewById(R.id.res_from_qwerty);
//                                activityRes.setText(text);

                            numQ = data.getIntExtra("numQ", 0);
                            Button toQwerty = findViewById(R.id.to_qwerty);
                            toQwerty.setText("To Qwerty: " + (numQ));
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i(TAG, "onCreate    " + this.toString());


        ImageView imageView = findViewById(R.id.imageView2);
        Picasso.get()
                .load("https://i.pinimg.com/736x/bf/6e/29/bf6e296386c67b027cd3d234e3c6efa4.jpg")
                .resize(220, 220)
                .centerCrop()
                .into(imageView);


        Button toQwerty = findViewById(R.id.to_qwerty);
        toQwerty.setText("To Qwerty: " + numQ);
        toQwerty.setOnClickListener((view) -> {
            Intent intent = new Intent(this, QwertyActivity.class);
            intent.putExtra("numS", numQ + 1);
            launcher.launch(intent);
            overridePendingTransition(0, 0);
        });


        Button toMinesweeper = findViewById(R.id.to_minesweeper);
        toMinesweeper.setOnClickListener((view) -> {
            Intent intent = new Intent(this, MinesweeperActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toSA = findViewById(R.id.to_sa_activity);
        toSA.setOnClickListener((view) -> {
            Intent intent = new Intent(this, SAActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toRV = findViewById(R.id.to_rv_activity);
        toRV.setOnClickListener((view) -> {
            Intent intent = new Intent(this, RVActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toDialog = findViewById(R.id.to_dialog_activity);
        toDialog.setOnClickListener((view) -> {
            Intent intent = new Intent(this, DialogActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toDiagram = findViewById(R.id.to_diagram_activity);
        toDiagram.setOnClickListener((view) -> {
            Intent intent = new Intent(this, DiagramActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toVideo = findViewById(R.id.to_video_activity);
        toVideo.setOnClickListener((view) -> {
            Intent intent = new Intent(this, VideoActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        Button toShare = findViewById(R.id.to_share_activity);
        toShare.setOnClickListener((view) -> {
            Intent intent = new Intent(this, ShareActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_third) {
                Intent intent = new Intent(this, ThirdActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Toast t = Toast.makeText(this, "DATA IS NULL BRUH", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        if (requestCode == 123 && resultCode == RESULT_OK) {
            String text = data.getStringExtra("fromQwerty");
            TextView activityRes = findViewById(R.id.res_from_qwerty);
            activityRes.setText(text);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart     " + this.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume    " + this.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause     " + this.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop      " + this.toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart   " + this.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy   " + this.toString());
    }
}
