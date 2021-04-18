package com.adityasinha.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;

import com.adityasinha.quiz.controller.QuestionService;
import com.adityasinha.quiz.view.QuestionsFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        createFragment(new QuestionsFragment());
    }

    private void createFragment(Fragment fragment) {

        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}