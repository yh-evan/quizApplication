package yh.evanz.quizapplication;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class QuestionModel implements Parcelable {
    String text;
    Boolean answer;
    int color;

    public QuestionModel(String text, Boolean answer, int color) {
        this.text = text;
        this.answer = answer;
        this.color = color;
    }

    protected QuestionModel(Parcel in) {
        text = in.readString();
        byte tmpAnswer = in.readByte();
        answer = tmpAnswer == 0 ? null : tmpAnswer == 1;
        color = in.readInt();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(answer);
        }
        parcel.writeInt(color);

    }
}
