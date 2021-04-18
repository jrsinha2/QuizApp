package com.adityasinha.quiz.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adityasinha.quiz.R;
import com.adityasinha.quiz.controller.QuestionService;
import com.adityasinha.quiz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {

    private static final String TAG = "Logging";
    private int score;
    private TextView tvCorrect;
    private TextView tvTotal;
    private Button finishBtn;
    private Button checkAnswersBtn;
    ArrayList<String> userAnswers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void computeScore(List<String> answers) {
        List<Question> questions = QuestionService.getQuestionList();
        int i = 0;
        score = 0;
        for(String answer : answers){
            if(answer.equals(questions.get(i).getCorrect_answer())){
                score++;
            }
            i++;
        }
        Log.d(TAG, "computeScore: score = " + score);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        Bundle received = getArguments();
        if(received!=null){
            userAnswers = received.getStringArrayList("answers");
            Toast toast  = Toast.makeText(this.getActivity(),userAnswers.toString(),Toast.LENGTH_LONG);
            toast.show();
            computeScore(userAnswers);
            tvCorrect.setText(String.valueOf(score));
            tvTotal.setText(String.valueOf(userAnswers.size()));
        } else {
            Toast toast = Toast.makeText(getActivity(),"Please appear in quiz first!",Toast.LENGTH_LONG);
            toast.show();
            getActivity().finish();

        }
        finishBtn.setOnClickListener(v -> getActivity().finish());
        checkAnswersBtn.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("answers",userAnswers);
            ShowAnswers showAnswers = new ShowAnswers();
            showAnswers.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout,showAnswers)
                    .addToBackStack(null)
                    .commit();
        });

    }

    private void initialize(View view) {
        tvCorrect = view.findViewById(R.id.correct);
        tvTotal = view.findViewById(R.id.total);
        checkAnswersBtn = view.findViewById(R.id.checkAnswersBtn);
        finishBtn = view.findViewById(R.id.finishBtn);
    }

    public int getScore() {
        return score;
    }
}