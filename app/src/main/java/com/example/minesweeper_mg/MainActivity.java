package com.example.minesweeper_mg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 테이블 레이아웃 9x9
        table = (TableLayout) findViewById(R.id.tableLayout);

        Button[][] buttons = new Button[9][9];
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            table.addView(tableRow);

            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new Button(this);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);
            }
        }
    }

    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(), newActivity.class);
        startActivity(intent);
    }


}