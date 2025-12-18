package com.example.androidtest1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class ThirdActivity extends AppCompatActivity {
    int count = 0;
    private String TAG = "qwerty123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Log.i(TAG, this.toString());

        TextView tv1 = findViewById(R.id.tv1);
        Intent parentIntent = getIntent();
        if (parentIntent != null) {
            tv1.setText(parentIntent.getStringExtra("fromHome"));

        } else {
            Toast t = Toast.makeText(this, "parentIntent is NUll", Toast.LENGTH_SHORT);
            t.show();
        }

        TextView countView = findViewById(R.id.count);
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count", 0);
            countView.setText(count + "");
        }

        Log.i(TAG, "onCreate");
        Button but1 = findViewById(R.id.but1);
        but1.setOnClickListener((view) -> {
            count++;
            countView.setText("Count: " + count);
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (item.getItemId() == R.id.nav_second) {
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
