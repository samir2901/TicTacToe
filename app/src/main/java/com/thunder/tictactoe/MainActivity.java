package com.thunder.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount, player1Score, player2Score;
    private TextView textViewPlayer1, textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.player1_score);
        textViewPlayer2 = findViewById(R.id.player2_score);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.reset_btn);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        updateScoreText();
        resetBoard();
    }


    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if(player1Turn){
            ((Button) v).setText("X");
        }else{
            ((Button) v).setText("O");
        }
        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount==9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }

    private void player1Wins() {
        player1Score++;
        Toast.makeText(this,"Player 1 Wins!",Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void player2Wins() {
        player2Score++;
        Toast.makeText(this,"Player 2 Wins!",Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void updateScoreText() {
        textViewPlayer1.setText("Player 1: "+ player1Score);
        textViewPlayer2.setText("Player 2: "+ player2Score);
    }



    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++){
            if((field[0][i].equals(field[1][i])) && (field[0][i].equals(field[2][i])) && !field[0][i].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++){
            if((field[i][0].equals(field[i][1])) && (field[i][0].equals(field[i][2])) && !field[i][0].equals("")){
                return true;
            }
        }
        if((field[0][0].equals(field[1][1])) && (field[0][0].equals(field[2][2])) && !field[0][0].equals("")){
            return true;
        }
        if((field[0][2].equals(field[1][1])) && (field[0][2].equals(field[2][0])) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

}
