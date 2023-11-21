package com.example.minesweeper_mg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class newActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

    }

    public void onClick(View v){
        finish();

    }
    public void onClick2(View v){
        Intent intent = new Intent(getApplicationContext(), newActivity2.class);
        startActivity(intent);
    }
    public void onClick3(View v){
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings");

        startActivity(intent);
    }
}