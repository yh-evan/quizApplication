package yh.evanz.quizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<QuestionModel> questionList = new ArrayList<>();
    ArrayList<ResultModel> results = new ArrayList<>();
    FragmentManager fm;
    int score = 0;
    int index = 0;
    int attempt = 0;
    int progress = 0;
    ProgressBar progressBar;
    StorageManager storageManager;
    ExternalStorageManager externalStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storageManager = ((myApp)getApplication()).getStorageManager();
        externalStorageManager = ((myApp) getApplication()).getExternalStorageManager();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(progress);
        fm = getSupportFragmentManager();
        if(savedInstanceState == null) {
            getQuestion();
            Collections.shuffle(questionList);
            createQuestionFragment();
        }

    }

    private void getQuestion() {
        questionList.add(new QuestionModel(getString(R.string.question1), true, Color.YELLOW));
        questionList.add(new QuestionModel(getString(R.string.question2), true, Color.GREEN));
        questionList.add(new QuestionModel(getString(R.string.question3), true, Color.RED));
        questionList.add(new QuestionModel(getString(R.string.question4), false, Color.BLUE));
    }

    private void createQuestionFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("question", questionList.get(index));
        fm.beginTransaction().replace(R.id.questionFrame,QuestionFragment.class, bundle).commit();
    }


    public void true_clicked(View view) {
        if (questionList.get(index).answer) {
            score += 1;
            Toast.makeText(view.getContext(), R.string.answerCorrect, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), R.string.answerWrong, Toast.LENGTH_SHORT).show();
        }
        index += 1;
        progress += 25;
        if (index >= questionList.size()){
            alertDialog(view);
        } else {
            createQuestionFragment();
        }
        progressBar.setProgress(progress);

    }

    public void false_clicked(View view) {
        if (!questionList.get(index).answer) {
            score += 1;
            Toast.makeText(view.getContext(), R.string.answerCorrect, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(view.getContext(), R.string.answerWrong, Toast.LENGTH_SHORT).show();
        }
        index += 1;
        progress += 25;
        if (index >= questionList.size()){
            alertDialog(view);
        } else {
            createQuestionFragment();
        }
        progressBar.setProgress(progress);
    }

    public void alertDialog(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setTitle(R.string.thanksTitle);
        alertDialog.setMessage(getString(R.string.yourScore) + score + " / " + questionList.size());
        alertDialog.setNegativeButton(R.string.ignore, (dialogInterface, i) -> {
            index = 0;
            score = 0;
            progress = 0;
            Collections.shuffle(questionList);
            progressBar.setProgress(progress);
            createQuestionFragment();
        });
        alertDialog.setPositiveButton(R.string.save, (dialogInterface, i) -> {
            attempt += 1;
            ResultModel newResult = new ResultModel(attempt, score);
            results.add(newResult);
            storageManager.saveNewResultInInternalPrivateFile(MainActivity.this, newResult);
            index = 0;
            score = 0;
            progress = 0;
            Collections.shuffle(questionList);
            progressBar.setProgress(progress);
            createQuestionFragment();
        });

        alertDialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("previousQuestions", questionList);
        outState.putInt("previousIndex", index);
        outState.putInt("previousScore", score);
        outState.putInt("previousAttempt", attempt);
        outState.putInt("previousProgress", progress);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        questionList =  savedInstanceState.getParcelableArrayList("previousQuestions");
        index = savedInstanceState.getInt("previousIndex");
        score = savedInstanceState.getInt("previousScore");
        attempt = savedInstanceState.getInt("previousAttempt");
        progress = savedInstanceState.getInt("previousProgress");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int totalScore = 0;

        if (item.getItemId() == R.id.average){
            results = storageManager.getResultsFromInternalPrivateFile(MainActivity.this);
            if (results.size() > 0) {
                for (ResultModel r : results) {
                    totalScore += r.score;
                }
            }
            attempt = results.size();
            averageDialog(this, totalScore);
            return true;
        } else if (item.getItemId() == R.id.reset){
            storageManager.resetTheStorage(MainActivity.this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    public void averageDialog(Context context, int totalScore) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(getString(R.string.yourCorrectAnswer) + totalScore + getString(R.string.in) + attempt + getString(R.string.attempts));
        alertDialog.setNegativeButton(R.string.ok, null);
        alertDialog.setPositiveButton(R.string.save, (dialogInterface, i) -> {
            externalStorageManager.saveNewResultPrivateExternal(this, results);
        });
        alertDialog.show();

    }

}