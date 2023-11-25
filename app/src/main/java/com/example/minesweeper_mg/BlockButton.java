package com.example.minesweeper_mg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TableLayout;
import android.widget.Toast;

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
    private static int mines_num = 10;


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

        if(flag){ // flag 중복 방지
            flag = false;
            flags_num--;
        }
        if (!check) {
            check = true;
            setTextColor(Color.BLACK);
            setText("V");
        } else {
            check = false;
            setText("");
        }
    }
    public void breakBlock(){
        setEnabled(false);
        setText("Bk");
        check = false;

        if(mine){
            setTextColor(Color.RED);
            setText("!M!");
        }else{
            int neighbor = getNeighborMines();
            setText(Integer.toString(neighbor));
        }

    }
    public void toggleFlag() {
        flag = true;
        flags_num++;
        setText("F");
        setTextColor(Color.RED);
        check = false;
    }
    public void undoFlag(){
        flags_num--;
        flag = false;
        check = false;
    }

    public void calculate_mines(){
       mines_num--;
    }




    // Getter
    public int getNeighborMines() {
        return neighborMines;
    }

    public static int getFlags_num() {
        return flags_num;     // block 클릭 시 화면 상단에 Flag 개수 띄우기
    }

    public static int getBlocks_num() {
        return blocks_num;
    }

    public static int getMines_num(){ return mines_num;}

    // Setter
    public static void setFlags_num(int flags_num) {BlockButton.flags_num = flags_num;}

    public static void setBlocks_num(int blocks_num) {BlockButton.blocks_num = blocks_num;}

    public void setNeighborMines(int nm){
        neighborMines = nm;
    }

    public void setMine(boolean b) {
        mine = b;
    }

    //
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
