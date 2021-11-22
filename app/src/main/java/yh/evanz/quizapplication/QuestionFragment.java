package yh.evanz.quizapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.question_fragment, container, false);;
        Bundle bundle = this.getArguments();
        assert bundle != null;
        QuestionModel question = bundle.getParcelable("question");
        TextView questionText = v.findViewById(R.id.question);
        questionText.setText(question.text);
        v.setBackgroundColor(question.color);

        return v;
    }
}
