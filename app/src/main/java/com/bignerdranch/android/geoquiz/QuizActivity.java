package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    //private ImageButton mpreviousButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question_australia,
                            true),
                    new Question(R.string.question_mideast,
                            false),
                    new Question(R.string.question_africa,
                            false),
                    new Question(R.string.question_americas,
                            true),
                    new Question(R.string.question_asia, true),
            };
    @Override
    public void onSaveInstanceState(Bundle
                                            savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    private int mCurrentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState != null) {
            mCurrentIndex =
                    savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView)
                findViewById(R.id.question_text_view);          //Tying the text view with the code TextView


        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                      checkAnswer(true);
                                                   }
                                               });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       checkAnswer(false);

                                                   }
                                               });

        mNextButton = (Button)
                findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       mCurrentIndex = (mCurrentIndex + 1) %
                                                               mQuestionBank.length;
                                                       updateQuestion();
                                                   }
                                               });

       /* mpreviousButton = (ImageButton)
                findViewById(R.id.previous_button);
        mpreviousButton.setOnClickListener(new
                                                   View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           mCurrentIndex = (mQuestionBank.length+mCurrentIndex - 1)%
                                                                   mQuestionBank.length;
                                                           updateQuestion();
                                                       }
                                                   });*/
        updateQuestion();
    }

    private void updateQuestion() {
        int question =
                mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue =
                mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId,
                Toast.LENGTH_SHORT)
                .show();
    }
}
