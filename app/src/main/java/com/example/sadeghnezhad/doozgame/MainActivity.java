package com.example.sadeghnezhad.doozgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int white_code = 0;
    public static final int red_code = 1;
    public static final int not_played = 2;
    public static final int NO_WINNER = -1;
    int winner = NO_WINNER;

    int[] gameState = {not_played, not_played, not_played,
            not_played,not_played, not_played,
            not_played, not_played, not_played};
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int active_player =red_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_ground);
    }

    public void dropIn(View view){
        int tag = Integer.parseInt((String) view.getTag());
        if (winner !=NO_WINNER || gameState[tag] != not_played){
            return;
        }
        ImageView img = (ImageView) view;
        img.setTranslationY(-2000f);
        if(active_player == red_code) {
            img.setImageResource(R.drawable.red);
            img.animate().translationY(0f).setDuration(500);
            gameState[tag] = red_code;
            active_player = white_code;;
        }
        else if(active_player == white_code) {
            img.setImageResource(R.drawable.white);
            img.animate().translationY(0f).setDuration(500);
            gameState[tag] = white_code;
            active_player = red_code;
        }
        String winnerOne ;
        winner =checkWinner();

        if(winner  != NO_WINNER || filled()){
            Toast.makeText(this, "winner : "+winner, Toast.LENGTH_SHORT).show();
        }
    }

    public int checkWinner(){
        for(int[] positions : winningPositions){
            if(gameState[positions[0]] == gameState[positions[1]] && gameState[positions[2]] == gameState[positions[1]]
                    &&gameState[positions[0]] != not_played){
                return gameState[positions[0]];
            }
        }
        return NO_WINNER;
    }

    public boolean filled(){
        for (int i=0; i<gameState.length ;i++){
            if (gameState[i] == not_played)
                return false;
        }
        return true;
    }

    public void reset(){
        active_player = red_code;
        winner = NO_WINNER;
        for (int i=0; i<gameState.length ;i++){
            gameState[i] = not_played;}

        LinearLayout pglayout=(LinearLayout) findViewById(R.id.pglayout);
        for(int i=0 ; i<pglayout.getChildCount() ; i++){
            LinearLayout row = (pglayout.getChildAt(i) instanceof LinearLayout) ?
                    (LinearLayout) pglayout.getChildAt(i): null;
            if(row == null) return;
            for (int j=0; j< row.getChildCount(); j++){
                ImageView iv = (row.getChildAt(j) instanceof ImageView) ?
                        (ImageView) row.getChildAt(j) : null;
                if (row == null)return;
                iv.setImageResource(0);
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem resetItem = menu.add("RESET");
        resetItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        resetItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                reset();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
