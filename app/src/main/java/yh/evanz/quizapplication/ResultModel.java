package yh.evanz.quizapplication;

public class ResultModel {
    int attempts;
    int score;

    public ResultModel(int attempts, int score) {
        this.attempts = attempts;
        this.score = score;
    }

    @Override
    public String toString() {
        return this.attempts + "-" + this.score;
    }
}
