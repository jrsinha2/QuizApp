package com.adityasinha.quiz.controller;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.adityasinha.quiz.model.Question;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    private static List<Question> questionList;
    private static int COUNTER = 0;
    private static List<Integer> randomSequence;
    private static ArrayList<String> userAnswers;

    public static int getCOUNTER() {
        return COUNTER;
    }

    public static void nextCounter(){
        COUNTER++;
    }

    public static List<Question> getQuestionList() {
        return questionList;
    }

    public static List<Integer> getRandomSequence() {
        return randomSequence;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void formQuestionList(JSONObject response) {
        questionList = new ArrayList<>();
        randomSequence = new ArrayList<>();
        for(int i = 0;i<10;i++){
            randomSequence.add((int) Math.floor(Math.random()*(4) + 0));
        }
        try {
            JSONArray results = (JSONArray) response.get("results");
            for(int i = 0;i<results.length();i++){
                JSONObject result = (JSONObject) results.get(i);
                Question question = new Gson().fromJson(result.toString(), Question.class);
                question.parseToText();
                questionList.add(question);
                Log.i("questions",question.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void setUserAnswers(ArrayList<String> userAnswers) {
        QuestionService.userAnswers = userAnswers;
    }

    public static ArrayList<String> getUserAnswers() {
        return userAnswers;
    }

}
