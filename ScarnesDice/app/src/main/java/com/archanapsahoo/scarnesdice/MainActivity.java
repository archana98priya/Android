package com.archanapsahoo.scarnesdice;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int playerScore=0, compScore=0, player=0, comp=0;
    int currentDiceValue=1;
   // private boolean isPlayerTurn = true;

    private Button holdb,resetb,rollb;
    private ImageView diceImage;
    private TextView scoreText;
    private String scorePlayer = "Your Score : ";
    private String scoreComp = "Computer Score : ";
   // private String scoreTurn = "Turn Score : ";
    int images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = (TextView) findViewById(R.id.textView);
        diceImage = (ImageView) findViewById(R.id.imageView2);
        rollb = (Button) findViewById(R.id.button);
        holdb = (Button) findViewById(R.id.button2);
        resetb = (Button) findViewById(R.id.button3);



    }


    public void roll(View view){

        currentDiceValue = new Random().nextInt(6) +1;
        diceImage.setImageResource(images[(int) currentDiceValue - 1]);


        if(currentDiceValue ==1)
        {
            scoreText.setText(scorePlayer + playerScore + " " + scoreComp + compScore + "\n" );
            player = 0;
            computerTurn();
        }
        else
        {
            player += currentDiceValue;
            scoreText.setText(scorePlayer + playerScore + " " + scoreComp + compScore + "\n");

            if ((playerScore + player) >= 100)
                stopGame(0);

        }


    }



    private void reset(View view)
    {
        player = 0;
        playerScore = 0;
        comp = 0;
        compScore = 0;
        scoreText.setText(scorePlayer + "0" + " " + scoreComp + "0" + "\n");
        rollb.setEnabled(true);
        holdb.setEnabled(false);
    }

    // Plays computer's turn
    private void computerTurn() {

        rollb.setEnabled(false);
        holdb.setEnabled(false);
        resetb.setEnabled(false);

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                currentDiceValue = new Random().nextInt(6) +1;
                diceImage.setImageResource(images[(int) currentDiceValue - 1]);

                if (currentDiceValue == 1) {
                    scoreText.setText(scorePlayer + playerScore + " " + scoreComp + compScore + "\n" );
                    comp = 0;
                    userChance();
                    return;

                } else {
                    comp += currentDiceValue;
                    if ((compScore + comp) >= 100) {
                        stopGame(1);
                        return;
                    }

                    scoreText.setText(scorePlayer + playerScore + " " + scoreComp + compScore + "\n" );
                    if (comp > 20) {
                        compScore += comp;
                        comp = 0;
                        userChance();
                        return;
                    } else
                        computerTurn();
                }
            }
        }.start();
    }
    private void stopGame(int winner) {
        if (winner == 0)
            scoreText.setText("YOU WON!!!!!");
        else
            scoreText.setText("COMPUTER WON!!!!");
        rollb.setEnabled(false);
        holdb.setEnabled(false);
    }

    // Enable all the buttons
    private void userChance() {
        rollb.setEnabled(true);
        holdb.setEnabled(true);
        resetb.setEnabled(true);
    }

    public void hold(View view)
    {

        playerScore += player;
        player = 0;
        computerTurn();


    }



}
