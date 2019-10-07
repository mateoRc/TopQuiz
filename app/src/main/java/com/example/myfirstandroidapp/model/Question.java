package com.example.myfirstandroidapp.model;

import java.util.List;

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;


    public Question(String question, List<String> choiceList, int answerIndex) {
        this.mQuestion = question;
        this.mChoiceList = choiceList;
        this.mAnswerIndex = answerIndex;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String question) {
        this.mQuestion = question;
    }

    public List<String> getmChoiceList() {
        return mChoiceList;
    }

    public void setmChoiceList(List<String> choiceList) {
        if (choiceList == null) {
            throw new IllegalArgumentException("No elements in the list");
        }

        this.mChoiceList = choiceList;
    }

    public int getmAnswerIndex() {

        return mAnswerIndex;
    }

    public void setmAnswerIndex(int answerIndex) {
        if (answerIndex < 0 || answerIndex >= mChoiceList.size()) {
            throw new IllegalArgumentException("Answer index out of bounds");
        }

        this.mAnswerIndex = answerIndex;
    }
}
