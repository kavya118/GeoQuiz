package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private static final int REQUEST_CODE_CHEAT = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mpreviousButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private boolean mIsCheater;
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
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater =
                    CheatActivity.wasAnswerShown(data);
        }
    }

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


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new
                                               View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       checkAnswer(true);
                                                   }
                                               });

        mFalseButton = (Button) findViewById(R.id.false_button);
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
                                                       mIsCheater = false;
                                                       updateQuestion();
                                                   }
                                               });

        mpreviousButton = (Button)
                findViewById(R.id.previous_button);
        mpreviousButton.setOnClickListener(new
                                                   View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           mCurrentIndex = (mQuestionBank.length + mCurrentIndex - 1) %
                                                                   mQuestionBank.length;
                                                           updateQuestion();
                                                       }
                                                   });
        updateQuestion();
        mCheatButton =
                (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new
                                                View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        boolean answerIsTrue =
                                                                mQuestionBank[mCurrentIndex].ismAnswerTrue();
                                                        Intent intent =
                                                                CheatActivity.newIntent(QuizActivity.this,
                                                                        answerIsTrue);
                                                        startActivityForResult(intent,
                                                                REQUEST_CODE_CHEAT);
                                                        /*How does the ActivityManager
                                                        know which Activity to start? That
                                                        information is in the Intent parameter.

                                                        An intent is an object that a component
                                                        can use to communicate with the OS.
                                                        Extras are arbitrary data that the calling
                                                         activity can include with an intent.

                                                        The OS forwards the intent to
                                                        the recipient activity, which can then
                                                        access the extras and retrieve the data

                                                        The OS forwards the intent to
                                                        the recipient activity, which can then
                                                        access the extras and retrieve the data,

An extra is structured as a key-value
pair, like the one you used to save out
the value of mCurrentIndex in
QuizActivity.onSaveInstanceState
*/
                                                    }
                                                });
    }


    private void updateQuestion() {
        int question =
                mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue =
                mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }

        }
        Toast.makeText(this, messageResId,
                    Toast.LENGTH_SHORT).show();
        }
    }
