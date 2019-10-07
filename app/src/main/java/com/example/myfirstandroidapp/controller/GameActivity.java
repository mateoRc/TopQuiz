package com.example.myfirstandroidapp.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.model.Question;
import com.example.myfirstandroidapp.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private QuestionBank mQuestionBank;
    private Question mQuestion;

    private TextView mQuestionText;
    private Button mAnswerBtn1;
    private Button mAnswerBtn2;
    private Button mAnswerBtn3;
    private Button mAnswerBtn4;

    private int mNumberOfQuestions;
    private int mScore;

    private int best1, best2, best3;

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        System.out.println("GameActivity::onCreate()");

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        mQuestionText = findViewById(R.id.activity_game_question_text);
        mAnswerBtn1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswerBtn2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswerBtn3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswerBtn4 = findViewById(R.id.activity_game_answer4_btn);

        mAnswerBtn1.setTag(0);
        mAnswerBtn2.setTag(1);
        mAnswerBtn3.setTag(2);
        mAnswerBtn4.setTag(3);

        mQuestionBank = this.generateQuestions();
        mQuestion = mQuestionBank.getQuestion();
        displayQuestion(mQuestion);

        mAnswerBtn1.setOnClickListener(this);
        mAnswerBtn2.setOnClickListener(this);
        mAnswerBtn3.setOnClickListener(this);
        mAnswerBtn4.setOnClickListener(this);

    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who created Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "John Travolta"),
                0);

        Question question2 = new Question("What is the name of the network of computers from which the Internet has emerged?",
                Arrays.asList("Meganet",
                        "Istranet",
                        "Filenet",
                        "Arpanet"),
                3);

        Question question3 = new Question("What does USB stand for in the computer world?",
                Arrays.asList("Use Your Brain",
                        "Until Something Better",
                        "Universal Serial Bus",
                        "Universal Serial Button"),
                2);

        Question question4 = new Question("What does \"FTP\" stand for in the computer and internet world?",
                Arrays.asList("For The People",
                        "File Transfer Protocol",
                        "Find The Program",
                        "First To Post"),
                1);

        Question question5 = new Question("When was The IEEE (Institute of Electrical and Electronics Engineers founded?",
                Arrays.asList("1963",
                        "1953",
                        "1973",
                        "1983"),
                0);
        Question question6 = new Question("OS computer abbreviation usually means ?",
                Arrays.asList("Order of Significance",
                        "Operating System",
                        "Open Software",
                        "Optical Sensor"),
                1);

        Question question7 = new Question(".MOV extension refers usually to what kind of file?",
                Arrays.asList("Image file",
                        "Audio file",
                        "Animation/movie file",
                        "MS Office document"),
                2);

        Question question8 = new Question(".MOV extension refers usually to what kind of file?",
                Arrays.asList("Charles Babbage",
                        "George Boole",
                        "Jeff Bezos",
                        "Alan Turing"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4));
    }

    //Postavljanje teksta za TextView, odgovori na 4 buttona
    private void displayQuestion(final Question question) {
        mQuestionText.setText(question.getmQuestion());
        mAnswerBtn1.setText(question.getmChoiceList().get(0));
        mAnswerBtn2.setText(question.getmChoiceList().get(1));
        mAnswerBtn3.setText(question.getmChoiceList().get(2));
        mAnswerBtn4.setText(question.getmChoiceList().get(3));
    }

    @Override
    public void onClick(View view) {
        int userAnswer = (int) view.getTag();

        if (userAnswer == mQuestion.getmAnswerIndex()) {
            Toast.makeText(this,  "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;

            //Test
            //Toast.makeText(this, "Score:" + mScore, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "numberOfQuestions:" + mNumberOfQuestions, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                if (--mNumberOfQuestions == 0) {
                    // Kraj igre
                    endGame();
                } else {
                    mQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT = 2 sec

    }

    public void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Slanje rezultata u MainActivity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GameActivity::onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }
}
