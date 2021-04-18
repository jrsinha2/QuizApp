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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adityasinha.quiz.R;
import com.adityasinha.quiz.controller.QuestionService;
import com.adityasinha.quiz.model.Question;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class QuestionsFragment extends Fragment {

    private static final String TAG = "Logging";
    private static final String DEFAULT_ANSWER = "";
    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioButton option4;
    private Button nextBtn;
    private Button clearBtn;
    ArrayList<String> userAnswers;
    public QuestionsFragment() {
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
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        List<Question> questions = QuestionService.getQuestionList();

        setFields(questions);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nextBtn.setText("Next");
            }
        });
        nextBtn.setOnClickListener(v -> {
            Log.d(TAG, "onViewCreated: counter value" + QuestionService.getCOUNTER());
                if (radioGroup.getCheckedRadioButtonId() == -1) {
//                    Toast toast = Toast.makeText(getActivity(), "Please select one option!", Toast.LENGTH_LONG);
//                    toast.show();
                    userAnswers.add(DEFAULT_ANSWER);    
                } else {
                    //extract answer
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton checkedBtn = view.findViewById(checkedId);
                    String answer = (String) checkedBtn.getText();
                    userAnswers.add(answer);
                    radioGroup.clearCheck();
                }
                //next question
                QuestionService.nextCounter();
                Log.d(TAG, "onViewCreated: counter = " + QuestionService.getCOUNTER());
                if(QuestionService.getCOUNTER() > 0 && QuestionService.getCOUNTER() < questions.size()) {
                    setFields(questions);
                }
                else { //result page
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("answers",userAnswers);
                    QuestionService.setUserAnswers(userAnswers);
                    ResultsFragment resultsFragment = new ResultsFragment();
                    resultsFragment.setArguments(bundle);
                    this.getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout,resultsFragment)
                            .addToBackStack(null)
                            .commit();
                }

        });

        clearBtn.setOnClickListener(v -> {
            radioGroup.clearCheck();
            nextBtn.setText("Skip");
        });

    }

    private void setFields(List<Question> questions) {
        questionText.setText(questions.get(QuestionService.getCOUNTER()).getQuestion());
        nextBtn.setText("Skip");
        switch (QuestionService.getRandomSequence().get(QuestionService.getCOUNTER())){
            case 0:{
                option1.setText(questions.get(QuestionService.getCOUNTER()).getCorrect_answer());
                option2.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(0));
                option3.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(1));
                option4.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(2));
            }
            break;
            case 1:{
                option2.setText(questions.get(QuestionService.getCOUNTER()).getCorrect_answer());
                option1.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(0));
                option3.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(1));
                option4.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(2));
            }
            break;
            case 2:{
                option3.setText(questions.get(QuestionService.getCOUNTER()).getCorrect_answer());
                option1.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(0));
                option2.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(1));
                option4.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(2));
            }
            break;
            case 3:{
                option4.setText(questions.get(QuestionService.getCOUNTER()).getCorrect_answer());
                option1.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(0));
                option2.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(1));
                option3.setText(questions.get(QuestionService.getCOUNTER()).getIncorrect_answers().get(2));
            }

        }
    }

    private void initialize(View view) {
        questionText = view.findViewById(R.id.questionText);
        radioGroup = view.findViewById(R.id.optionsGroup);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        clearBtn = view.findViewById(R.id.clearBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        userAnswers = new ArrayList<>();
    }
}