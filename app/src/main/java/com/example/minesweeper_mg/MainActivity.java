package com.example.minesweeper_mg;

import static com.example.minesweeper_mg.BlockButton.getFlags_num;
import static com.example.minesweeper_mg.BlockButton.getBlocks_num;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TableLayout table;
    private  TextView blocks_num;
    private BlockButton[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mine 다 보이기

        // 테이블 레이아웃 9x9
        table = (TableLayout) findViewById(R.id.tableLayout);
        blocks_num = (TextView) findViewById(R.id.blocks_num);

        buttons = new BlockButton[9][9];
        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            table.addView(tableRow);

            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BlockButton) view).checkBlock();
//                        blocks_num.setText("Blocks : "+ getBlocks_num());
                    }
                });
            }
        }

        setMines(buttons);

        blocks_num.setText("Blocks : " + getBlocks_num()); // Blocks 총 개수

        ToggleButton break_button = (ToggleButton)findViewById(R.id.break_button);
        break_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    breakSelectedBlock();
                    blocks_num.setText("Blocks : "+ getBlocks_num());
                } else {
                    flagSelectedBlock();
//                    ((BlockButton) view).toggleFlag();
                    TextView textView = (TextView) findViewById(R.id.flags_num); // Block 클릭 시 # 보이기
                    textView.setText("Flags : " + getFlags_num()); // Block 클릭 시 Flag 개수 BlockButon.java에서 가져오기
                }
            }
        });

    }

    // 지뢰 10개 랜덤 설정
    private void setMines(BlockButton[][ ] buttons){
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < 10) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (!buttons[row][col].isMine()) {
                buttons[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }

    // 선택한 블럭 Break
    private void breakSelectedBlock() {
        for (int i = 0; i < table.getChildCount(); i++) {
            View rowView = table.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow tableRow = (TableRow) rowView;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View blockView = tableRow.getChildAt(j);
                    if (blockView instanceof BlockButton) {
                        BlockButton selectedBlock = (BlockButton) blockView;
                        if (selectedBlock.blockChecked()) {
                            selectedBlock.breakBlock();
                        }
                    }
                }
            }
        }
    }

    // 선택한 블럭 Flag
    private void flagSelectedBlock() {
        for (int i = 0; i < table.getChildCount(); i++) {
            View rowView = table.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow tableRow = (TableRow) rowView;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View blockView = tableRow.getChildAt(j);
                    if (blockView instanceof BlockButton) {
                        BlockButton selectedBlock = (BlockButton) blockView;
                        if (selectedBlock.blockChecked()) {
                            selectedBlock.toggleFlag();
                        }
                    }
                }
            }
        }
    }

}