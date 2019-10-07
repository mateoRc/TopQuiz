package com.example.myfirstandroidapp.controller;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstandroidapp.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.xml.validation.Validator;


public class LeaderboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TreeMap<String, Integer> mMap = new TreeMap<>();
    List<String> mList = new ArrayList<String>();

    private TextView mTV_score;
    private Button mButtonName;
    private Button mButtonScore;

    private SharedPreferences mPreferences;

    private int mlastScore;
    private String mUserName;


    private int mBest1, mBest2, mBest3, mBest4, mBest5;
    private String mBestName1, mBestName2, mBestName3, mBestName4, mBestName5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        mTV_score = findViewById(R.id.activity_Leaderboard_textview_score);
        mButtonScore = findViewById(R.id.activity_Leaderboard_btn_byScore);
        mButtonName = findViewById(R.id.activity_Leaderboard_btn_byName);

        mButtonScore.setTag(1);
        mButtonName.setTag(2);

        mButtonScore.setOnClickListener(this);
        mButtonName.setOnClickListener(this);

        mPreferences = getSharedPreferences("PREFS", 0);
        mlastScore = mPreferences.getInt(MainActivity.PREF_SCORE_KEY, 0);
        mUserName = mPreferences.getString(MainActivity.PREF_USERNAME_KEY, null);

        mBest1 = mPreferences.getInt("best1", 0);
        mBest2 = mPreferences.getInt("best2", 0);
        mBest3 = mPreferences.getInt("best3", 0);
        mBest4 = mPreferences.getInt("best4", 0);
        mBest5 = mPreferences.getInt("best5", 0);

        mBestName1 = mPreferences.getString("name1", "default");
        mBestName2 = mPreferences.getString("name2", "default");
        mBestName3 = mPreferences.getString("name3", "default");
        mBestName4 = mPreferences.getString("name4", "default");
        mBestName5 = mPreferences.getString("name5", "default");

        SharedPreferences.Editor editor = mPreferences.edit();

        if (mlastScore > mBest5) {

            if (mlastScore > mBest4) {

                if (mlastScore > mBest3) {

                    if (mlastScore > mBest2) {

                        if ((mlastScore > mBest1 && !mUserName.equals(mBestName1))) {
                            mBest5 = mBest4;
                            mBest4 = mBest3;
                            mBest3 = mBest2;
                            mBest2 = mBest1;
                            mBest1 = mlastScore;

                            mBestName5 = mBestName4;
                            mBestName4 = mBestName3;
                            mBestName3 = mBestName2;
                            mBestName2 = mBestName1;
                            mBestName1 = mUserName;

                        } else if (!mUserName.equals(mBestName1)) {
                            mBest5 = mBest4;
                            mBest4 = mBest3;
                            mBest3 = mBest2;
                            mBest2 = mlastScore;

                            mBestName5 = mBestName4;
                            mBestName4 = mBestName3;
                            mBestName3 = mBestName2;
                            mBestName2 = mUserName;
                        } else {
                            mBest1 = mlastScore;
                        }
                    } else if (!mUserName.equals(mBestName2) && !mUserName.equals(mBestName1)) {
                        mBest5 = mBest4;
                        mBest4 = mBest3;
                        mBest3 = mlastScore;

                        mBestName5 = mBestName4;
                        mBestName4 = mBestName3;
                        mBestName3 = mUserName;
                    }
                } else if (!mUserName.equals(mBestName3) && !mUserName.equals(mBestName2) && !mUserName.equals(mBestName1)) {
                    mBest5 = mBest4;
                    mBest4 = mlastScore;

                    mBestName5 = mBestName4;
                    mBestName4 = mUserName;
                }
            } else if (!mUserName.equals(mBestName4) && !mUserName.equals(mBestName3) && !mUserName.equals(mBestName2) && !mUserName.equals(mBestName1)) {
                mBest5 = mlastScore;
                mBestName5 = mUserName;
            }

        }

        editor.putInt("best1", mBest1);
        editor.putInt("best2", mBest2);
        editor.putInt("best3", mBest3);
        editor.putInt("best4", mBest4);
        editor.putInt("best5", mBest5);
        editor.putString("name1", mBestName1);
        editor.putString("name2", mBestName2);
        editor.putString("name3", mBestName3);
        editor.putString("name4", mBestName4);
        editor.putString("name5", mBestName5);
        editor.apply();

        //test
        Toast.makeText(this, mBestName5 + " " + mBestName4 + " " + mBestName3 + " " + mBestName2 + " " + mBestName1, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, mBest5 + " " + mBest4 + " " + mBest3 + " " + mBest2 + " " + mBest1 , Toast.LENGTH_SHORT).show();

        if(!mBestName5.equals("default")) mMap.put(mBestName5, mBest5);
        if(!mBestName4.equals("default")) mMap.put(mBestName4, mBest4);
        if(!mBestName3.equals("default")) mMap.put(mBestName3, mBest3);
        if(!mBestName2.equals("default")) mMap.put(mBestName2, mBest2);
        if(!mBestName1.equals("default")) mMap.put(mBestName1, mBest1);

        sortNum();
        displayList();
    }

    @Override
    public void onBackPressed() {
        Intent leaderboardActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(leaderboardActivity);
        finish();
    }

    @Override
    public void onClick(View view) {
        int btnPressed = (int) view.getTag();

        if (btnPressed == 1) {
            sortNum();
            displayList();
        }
        if (btnPressed == 2) {
            sortAlph();
            displayList();
        }
    }

    private void sortNum() {
        mList.clear();
        mTV_score.setText("");
        for (Map.Entry<String, Integer> entry : mMap.entrySet()) {
            mList.add(entry.getValue() + " " + entry.getKey());

        }
        Collections.sort(mList, Collections.<String>reverseOrder());

    }

    private void sortAlph() {
        mList.clear();
        mTV_score.setText("");
        for (Map.Entry<String, Integer> entry : mMap.entrySet()) {
            mList.add(entry.getKey() + " " + entry.getValue());
        }

    }

    private void displayList() {
        for (int i = 0; i < mList.size(); i++) {
            mTV_score.append(mList.get(i) + " ");
            mTV_score.append("\n");
        }

    }
}



