package com.adityasinha.quiz.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.adityasinha.quiz.R;
import com.adityasinha.quiz.controller.QuestionService;
import com.adityasinha.quiz.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowAnswers extends Fragment {


    private ListView listView;
    public ShowAnswers() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_show_answers, container, false);
        initialize(view);
        Question[] questions = new Question[10];
        List<Question> questionList = QuestionService.getQuestionList();
        for(int i =0;i<10;i++){
            questions[i] = questionList.get(i);
        }
        ArrayAdapter<Question> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.simple_list_items, questions);
        listView.setAdapter(arrayAdapter);
        return view;
    }


    private void initialize(View view) {
        listView = view.findViewById(R.id.list);
    }


}