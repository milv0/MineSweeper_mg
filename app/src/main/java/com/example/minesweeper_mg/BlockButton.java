package com.example.minesweeper_mg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TableLayout;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BlockButton extends AppCompatButton {
    private int x, y;
    private boolean mine;
    private int neighborMines;
    private boolean flag;
    private boolean check;

    // Static fields

    private static int flags_num;
    private static int blocks_num;
    public static int getFlags_num() {
        return flags_num;     // block 클릭 시 화면 상단에 Flag 개수 띄우기
    }

    public static int getBlocks_num() {
        return blocks_num;
    }

    public BlockButton(Context context, int x, int y) {
        super(context);

        setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));

        blocks_num++;

        this.x = x;
        this.y = y;
        mine = false;
        flag = false;
        check = false;
        neighborMines = 0;
    }

    public void checkBlock(){
        if (!check) {
            check = true;
//            flags_num++;
            setText("V");
            if(mine){
                setTextColor(Color.RED);
                setText("!M!");
            }
        } else {
            check = false;
//            flags_num--;
            setText("");
            if(mine){
                setTextColor(Color.RED);
                setText("!M!");
            }
        }
    }
    public void breakBlock(){
        setEnabled(false);
        setText("Bk");
        check = false;

    }
    public void toggleFlag() {
        if (!flag) {
            flag = true;
            flags_num++;
            setText("F");
            setTextColor(Color.RED);
            setEnabled(false);
            check = false;
        } else {
            flag = false;
            flags_num--;
            setText("");
            setEnabled(false);
            check = false;

        }
    }

    public void setMine(boolean b) {
        mine = b;
    }
    public void showAllMines(){
        setText("!M!");
    }

    public boolean blockChecked() {
        return check;
    }
    public boolean isFlagged(){
        return flag;
    }
    public  boolean isMine(){
        return mine;
    }



}
