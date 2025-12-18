package com.example.androidtest1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class QwertyActivity extends AppCompatActivity {
    private String TAG = "qwerty123456";
    int numS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qwerty);

        Log.i(TAG, "onCreate    " + this.toString());


        Button finishBtn = findViewById(R.id.finish_btn);
        finishBtn.setOnClickListener((view) -> {
            Intent intent = new Intent();
            EditText edtext = findViewById(R.id.result_for_second_activity);
            intent.putExtra("fromQwerty", edtext.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });


        Button toSecond = findViewById(R.id.to_second);
        Intent pInvent = getIntent();
        numS = pInvent.getIntExtra("numS", 0);
        toSecond.setText("To Second: " + (numS));

        toSecond.setOnClickListener((view) -> {
            Intent intent = new Intent();
            intent.putExtra("numQ", numS + 1);
            setResult(RESULT_OK, intent);
            finish();
        });
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
