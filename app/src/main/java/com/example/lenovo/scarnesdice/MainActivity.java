package com.example.lenovo.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    int userScore=0,userScoreFinal=0,computerScore=0,computerScoreFinal=0,roll=0,FinalScore=100;
    Button rollbtn,holdbtn,resetbtn;
    TextView textViewUser,textViewComp,diceScore;
    ImageView image;

    boolean userturn=true,compturn=false;
    Handler timerHandler = new Handler();
    Toast toast;
    CharSequence winner;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollbtn=(Button) findViewById(R.id.rollBtn);
        holdbtn=(Button) findViewById(R.id.holdBtn);
        resetbtn=(Button) findViewById(R.id.resetBtn);
        textViewUser = (TextView) findViewById(R.id.textUserScore);
        textViewComp = (TextView) findViewById(R.id.textCompScore);
        image = (ImageView) findViewById(R.id.imageView);
        diceScore=(TextView) findViewById(R.id.DiceS);
        rollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RollOnClick","Roll Click Entered..");

                RollDice();
                if(roll!=1){
                    userScore+=roll;
                }
                else {
                    userScore=0;
                    holdDice();
                    ComputerPlays();
                }
            }
        });

        holdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HoldOnClick","Roll Click Entered..");

                holdDice();
//                ComputerPlays();

                //rollbtn.setClickable();
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDice();
            }
        });

    }


    private void resetDice() {
        computerScore=0;
        computerScoreFinal=0;
        userScoreFinal=0;
        userScore=0;
        roll=0;

        textViewUser.setText("0");
        textViewComp.setText("0");
        image.setImageResource(R.drawable.dice1);
        DiceScoreUpdate();
        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);

    }


    private void holdDice() {
        Log.i("HoldDice","Roll Entered..");

        {
            userScoreFinal+=userScore;
            // String userScoreFinalString= String.valueOf(userScoreFinal);
            textViewUser.setText(String.valueOf(userScoreFinal));
            userScore=0;
        }
        Log.i("HoldDice","Roll Entered..");
        if(userScoreFinal>=FinalScore){
            //Winner();
            Toast.makeText(getApplicationContext(),"Congratulations You Won!",Toast.LENGTH_LONG).show();
            rollbtn.setEnabled(false);
            holdbtn.setEnabled(false);
        }
        else
            ComputerPlays();
    }
    void Winner(){
        if(userScoreFinal>=FinalScore){
            winner="User Wins!!";
            toast=Toast.makeText(getApplicationContext(),"User Wins",Toast.LENGTH_LONG);
            toast.show();
//         rollbtn.setEnabled(false);
            //       holdbtn.setEnabled(false);

        }
        if(computerScoreFinal>=FinalScore){

            winner="Computer Wins!!";
            toast.makeText(getApplicationContext(),"Comp Wins!",Toast.LENGTH_LONG);
            toast.show();
            //     rollbtn.setEnabled(false);
            //   holdbtn.setEnabled(false);


        }

    }
    private void RollDice() {
        Log.i("RollDice","Roll Entered..");

        //image.setImageResource(R.drawable.xxx);

        int no = r.nextInt(6) + 1;

        if(no==1) {
            //image = (ImageView) findViewById(R.id.imageView);
            image.setImageResource(R.drawable.dice1);
        }
        if(no==2) {
            image.setImageResource(R.drawable.dice2);
        }
        if(no==3) {
            image.setImageResource(R.drawable.dice3);
        }
        if(no==4) {
            image.setImageResource(R.drawable.dice4);
        }
        if(no==5) {
            image.setImageResource(R.drawable.dice5);
        }
        if(no==6) {
            image.setImageResource(R.drawable.dice6);
        }
        roll=no;
        DiceScoreUpdate();
        Log.i("RollDice","Roll Finished..");

    }


    void DiceScoreUpdate(){
        diceScore.setText(String.valueOf(roll));

    }

    int randomNo(){
        Random ran= new Random();
        int rand=ran.nextInt(100);
        return rand;
    }

/*
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            ComputerPlays();

            timerHandler.postDelayed(this, 500);
        }
    };
*/

    Runnable runnablefun=new Runnable() {
        @Override
        public void run() {
            RollDice();

            if (roll != 1) {
                computerScore += roll;
                if(r.nextBoolean())
                    timerHandler.postDelayed(this,500);
                else {

                    computerScoreFinal += computerScore;
                    textViewComp.setText(String.valueOf(computerScoreFinal));
                    computerScore = 0;

                    if(computerScoreFinal>=FinalScore){
                        // Winner();
                        Toast.makeText(getApplicationContext(),"Computer Wins!",Toast.LENGTH_LONG).show();
                        rollbtn.setEnabled(false);
                        holdbtn.setEnabled(false);
                    }
                    else
                    {
                        rollbtn.setEnabled(true);
                        holdbtn.setEnabled(true);
                    }

                }
            } else {
                computerScore = 0;
                compturn = false;

                computerScoreFinal += computerScore;
                textViewComp.setText(String.valueOf(computerScoreFinal));
                computerScore = 0;
                rollbtn.setEnabled(true);
                holdbtn.setEnabled(true);


            }


        }
    };


    void ComputerPlays() {
        //rollbtn.setClickable(false);
        //holdbtn.setClickable(false);
        // boolean tempScoreReset = false;
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        compturn = true;


        timerHandler.postDelayed(runnablefun, 500);

        //mytexts.setText(myarray[count]);
        //   RollDice();
/*        do{
        RollDice();
                if (roll != 1) {
                    computerScore += roll;
                } else {
                    computerScore = 0;
                    compturn = false;
                }
                randno=randomNo();
                //randno = r.nextInt(100);
        } while (randno >= 50 && compturn);

                computerScoreFinal += computerScore;
                textViewComp.setText(String.valueOf(computerScoreFinal));
                computerScore = 0;
                rollbtn.setEnabled(true);
                holdbtn.setEnabled(true);

        // rollbtn.setClickable(true);
        // holdbtn.setClickable(true);
*/

            /*
            if (userturn) {
                Random r = new Random();
                userScore = r.nextInt(6) + 1;

                if (userScore == 1){
                    if(tempScoreReset){
                        userScoreFinal-=userScore;
                        tempScoreReset=false;
                    }
                    userScore=0;
                    userturn=false;
                    compturn=true;
                }
                else{
                    userScoreFinal += userScore;
                    tempScoreReset = true;
                }


            }
            */

        //if(compturn)
        {

            //  userScoreFinal+=userScore;
            // String userScoreFinalString= String.valueOf(userScoreFinal);
            // textViewComp.setText(String.valueOf(computerScoreFinal));
            //computerScore=0;


                /*
                if (computerScore == 1){
                    if(tempScoreReset){
                        computerScoreFinal-=computerScore;
                        tempScoreReset=false;
                    }
                    computerScore=0;
                    compturn=false;
                }
                else{
                    userScoreFinal += userScore;
                    tempScoreReset = true;
                }
                */
        }


    }
}