package com.adityasinha.quiz.model;

import android.util.Log;

import com.google.gson.Gson;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest extends TestCase {

    @Test
    public void testConstructor() {
        String question = "What does &quot;LCD&quot; stand for?";
        String type = "";
        String difficulty = "";
        String category = "";
        String correct_answer = "";
        List<String> incorrect_answers = new ArrayList<>();
        Question questionObject = new Question(category,type,difficulty,question,correct_answer,incorrect_answers);
        System.out.println(questionObject.getQuestion());
    }


    public void testUnescape() {
        String jsonString = "{\n" +
                "            \"category\": \"Science: Computers\",\n" +
                "            \"type\": \"multiple\",\n" +
                "            \"difficulty\": \"medium\",\n" +
                "            \"question\": \"What does &quot;LCD&quot; stand for?\",\n" +
                "            \"correct_answer\": \"Liquid Crystal Display\",\n" +
                "            \"incorrect_answers\": [\n" +
                "                \"Language Control Design\",\n" +
                "                \"Last Common Difference\",\n" +
                "                \"Long Continuous Design\"\n" +
                "            ]\n" +
                "        }";
        Question question = new Gson().fromJson(jsonString,Question.class);
        question.parseToText();
        System.out.println(question.getQuestion());
    }
}