package com.example.minesweeper_mg;

import android.content.Context;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BlockButton extends AppCompatButton {
    private int x, y;
    private boolean mine;
    private boolean flag;
    private int neighborMines;

    // Static fields
    private static int flags;
    private static int blocks;

    public BlockButton(Context context, int x, int y) {
        super(context);

        setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));

        blocks++;

        this.x = x;
        this.y = y;
        mine = false;
        flag = false;
        neighborMines = 0;
    }

    public void toggleFlag(){

    }
}
