package com.example.minesweeper_mg;

import static com.example.minesweeper_mg.BlockButton.getFlags_num;
import static com.example.minesweeper_mg.BlockButton.getBlocks_num;
import static com.example.minesweeper_mg.BlockButton.getMines_num;
import static com.example.minesweeper_mg.BlockButton.setFlags_num;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView flags_num;
    private TextView all_mines_num;
    private BlockButton[][] buttons;
    private long startTime; // 실행 시간
    private TextView runTime; // 실행 시간
    private Handler handler; // 실행 시간

    private Button simpleBreakButton;
    private Button simpleFlagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runTime = (TextView)findViewById(R.id.runTimeText);
        handler = new Handler();
        startTime = System.currentTimeMillis();
        handler.postDelayed(calculateTime, 1000);

        all_mines_num = (TextView)findViewById(R.id.mine_num_text);
        all_mines_num.setText("Mines : " + getMines_num());

        flags_num = (TextView)findViewById(R.id.flags_num);

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
                        flags_num.setText("Flags : " + getFlags_num()); // Block 클릭 시 Flag 개수 BlockButon.java에서 가져오기
                    }
                });
            }
        }

        // 지뢰 세팅
        setMines(buttons);
        calculateMineCounts(buttons);

        // 테스트 위해  버튼 두개 분리
        simpleBreakButton = (Button)findViewById(R.id.simple_break);
        simpleFlagButton = (Button)findViewById(R.id.simple_flag);
        simpleBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                breakSelectedBlock();
                blocks_num.setText("Blocks : "+ getBlocks_num());
            }
        });
        simpleFlagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagSelectedBlock();
                TextView textView = (TextView) findViewById(R.id.flags_num);
                textView.setText("Flags : " + getFlags_num());
            }
        });

        // Mine 정답 확인 / Reset Toggle 버튼
        ToggleButton showAllMines = (ToggleButton)findViewById(R.id.show_mine);
        showAllMines.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    Toast.makeText(MainActivity.this, "Restart!", Toast.LENGTH_SHORT).show();
                    restartApp();
                    // game Over 시 답 보이게 해서 필요 없어짐..
//                    for (int i = 0; i < 9; i++) {
//                        for (int j = 0; j < 9; j++) {
//                            buttons[i][j].performClick();
//                        }
//                    }
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            breakSelectedBlock();
//                        }
//                    }, 200);
                } else {
                    Toast.makeText(MainActivity.this, "Restart!", Toast.LENGTH_SHORT).show();
                    restartApp();

                }
            }
        });

        blocks_num.setText("Blocks : " + getBlocks_num()); // Blocks 총 개수

        // Break / Flag Toggle 버튼
        ToggleButton break_button = (ToggleButton)findViewById(R.id.break_button);
        break_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((ToggleButton) view).isChecked()) {
                    breakSelectedBlock();
                    blocks_num.setText("Blocks : "+ getBlocks_num());
                } else {
                    flagSelectedBlock();
                    flags_num.setText("Flags : " + getFlags_num()); // Block 클릭 시 Flag 개수 BlockButon.java에서 가져오기
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

    private void calculateMineCounts(BlockButton[][ ] buttons) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int[][] mineCounts = new int[9][9];
                if (buttons[row][col].isMine()) {
                    mineCounts[row][col] = -1;
                    buttons[row][col].setNeighborMines(mineCounts[row][col]);
                    continue;
                }
                int count = 0;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < 9 && j >= 0 && j < 9 && buttons[i][j].isMine()) {
                            count++;
                        }
                    }
                }
                mineCounts[row][col] = count;
                buttons[row][col].setNeighborMines(mineCounts[row][col]);
            }
        }
    }

    // 선택한 블럭 Break
    private void breakSelectedBlock() {

        boolean gameOver = false;

        for (int i = 0; i < table.getChildCount(); i++) {
            View rowView = table.getChildAt(i);
            if (rowView instanceof TableRow) {
                TableRow tableRow = (TableRow) rowView;
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    View blockView = tableRow.getChildAt(j);
                    if (blockView instanceof BlockButton) {
                        BlockButton selectedBlock = (BlockButton) blockView;
                        if (selectedBlock.blockChecked()) {
                            selectedBlock.breakBlock(); // BlockButton.java -> breakBlock()
                            if(selectedBlock.isMine()){
                                gameOver = true;   // Mine 인 블럭 Break 할 때 game Over
//                                Toast.makeText(this, "Game Over...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }
        if (gameOver) {
            // 게임이 종료되었을 때 모든 블럭 선택 후 열기
            for (int i = 0; i < table.getChildCount(); i++) {
                View rowView = table.getChildAt(i);
                if (rowView instanceof TableRow) {
                    TableRow tableRow = (TableRow) rowView;
                    for (int j = 0; j < tableRow.getChildCount(); j++) {
                        View blockView = tableRow.getChildAt(j);
                        if (blockView instanceof BlockButton) {
                            BlockButton selectedBlock = (BlockButton) blockView;
                            selectedBlock.setCheck(true);  // 모든 블럭을 선택 상태로 변경
                        }
                    }
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    breakSelectedBlock();
                }
            }, 200);
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
                            if(selectedBlock.isMine()){
                                selectedBlock.calculate_mines(); // Flag 선택한 블럭이 Mine일 때 Mine 총 개수 줄이기
                                all_mines_num.setText("Mines : " + getMines_num());

                                if(getMines_num() == 0){  // Mine에 모두 10개 Flag 선택 시 종료 Toast
                                    Toast.makeText(this, "Great!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // App 재시작 및 Mine 재배치
    private void restartApp() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        BlockButton.setBlocks_num(0);
        BlockButton.setFlags_num(0);
    }

    // 시간 측정
    private Runnable calculateTime = new Runnable() {
        @Override
        public void run() {
            // 현재 시간과 시작 시간 간의 차이를 계산
            long currentTime = System.currentTimeMillis();
            long executionTime = (currentTime - startTime) / 1000;

            // TextView 업데이트
            runTime.setText("Time : " + executionTime);

            // 1초마다 실행
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(calculateTime);
    }
}
