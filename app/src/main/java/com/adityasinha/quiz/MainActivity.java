package com.adityasinha.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adityasinha.quiz.controller.QuestionService;
import com.adityasinha.quiz.view.ResultsFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private final String url = "https://opentdb.com/api.php?amount=10&category=22&difficulty=easy&type=multiple";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    Log.e("rest api", "response = " + response.toString());
                    QuestionService.formQuestionList(response);
                    Log.i("rest api response","size of questions" + QuestionService.getQuestionList().size());
                },
                error -> Log.e("rest api","error = " + error.toString())
        );
        requestQueue.add(objectRequest);
    }

    public void startQuiz(View view)  {
        startActivity(new Intent(this,ContainerActivity.class));
    }

    public void showResults(View view) {
        if(QuestionService.getQuestionList() == null){
            Toast toast = Toast.makeText(this,"Please Start Quiz!",Toast.LENGTH_LONG);
            toast.show();
        }else{
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("answers",QuestionService.getUserAnswers());
            ResultsFragment resultsFragment = new ResultsFragment();
            resultsFragment.setArguments(bundle);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout,resultsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}