package com.example.myfirstandroidapp.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstandroidapp.R;
import com.example.myfirstandroidapp.model.User;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int GAME_ACTIVITY_REQUEST_CODE = 123;

    private SharedPreferences mPreferences;
    public static final String PREF_SCORE_KEY = "PREF_SCORE";
    public static final String PREF_USERNAME_KEY = "PREF_USERNAME_KEY";

    public static final String BUNDLE_USER_NAME = "BUNDLE_USER_NAME";
    public static final String BUNDLE_USER_SCORE = "BUNDLE_USER_SCORE";

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mLeaderboardBtn;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear preferences
        //getSharedPreferences("PREFS", 0).edit().clear().commit(); getPreferences(MODE_PRIVATE).edit().clear().commit();

        System.out.println("MainActivity::onCreate()");

        mUser = new User();

        //izmjena --> getPref(MODE_PRIVATE) --> getSharedPref
        mPreferences = getSharedPreferences("PREFS", 0);

        mGreetingText = findViewById(R.id.activity_main_TextView);
        mNameInput = findViewById(R.id.activity_main_UserInput);
        mPlayButton = findViewById(R.id.activity_main_Btn_Play);
        mLeaderboardBtn = findViewById(R.id.activity_main_Btn_Leaderboard);

        mPlayButton.setTag(1);
        mLeaderboardBtn.setTag(2);

        mPlayButton.setOnClickListener(this);
        mLeaderboardBtn.setOnClickListener(this);

        mLeaderboardBtn.setVisibility(View.GONE);
        mLeaderboardBtn.setEnabled(false);

        mPlayButton.setEnabled(false);

        greetings();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPlayButton.setEnabled(charSequence.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View view){
        int btnPressed = (int) view.getTag();
        //Play!
        if (btnPressed == 1) {
            mUser.setmFirstName(mNameInput.getText().toString());

            //Spremi ime u preferences, key->s
            /*
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(PREF_USERNAME_KEY, mUser.getmFirstName());
            editor.apply();*/


            Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
            startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
        }
        //Leaderboard
        else if (btnPressed == 2) {

            /*mPreferences = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(PREF_SCORE_KEY, mUser.getmScore());
            editor.putString(PREF_USERNAME_KEY, mUser.getmFirstName());
            editor.apply();*/

            Intent leaderboardActivity = new Intent(getApplicationContext(), LeaderboardActivity.class);
            //leaderboardActivity.putExtra(BUNDLE_USER_NAME, mUser.getmFirstName());
            //leaderboardActivity.putExtra(BUNDLE_USER_SCORE, mUser.getmScore());
            startActivity(leaderboardActivity);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Spremi vrijednost iz Intent-a u score varijablu
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mUser.setmScore(score);
            String username = mUser.getmFirstName();

            Toast.makeText(this, "Score [" + score + "] AND name [" + username + "] saved into SharedPrefs! ", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(PREF_SCORE_KEY, score);
            editor.putString(PREF_USERNAME_KEY, username);
            editor.apply();

        }

        greetings();
    }

    private void greetings() {

        String userName = mPreferences.getString(PREF_USERNAME_KEY, null);

        if (userName != null){
            int userScore = mPreferences.getInt(PREF_SCORE_KEY, 0);
            mUser.setmScore(userScore);
            mUser.setmFirstName(userName);

            //Toast.makeText(this, "Score has been counted!", Toast.LENGTH_SHORT).show();

            //Postavljanje leaderboard buttona - VISIBLE i enabled
            mLeaderboardBtn.setVisibility(View.VISIBLE);
            mLeaderboardBtn.setEnabled(true);

            String greetingString = "Welcome back, " + userName +
                    "\nYour last score was " + userScore;

            mGreetingText.setText(greetingString);
            mNameInput.setText(userName);
            //Postavljanje kursora na kraj imena
            mNameInput.setSelection(userName.length());
            mPlayButton.setEnabled(true);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }
}
