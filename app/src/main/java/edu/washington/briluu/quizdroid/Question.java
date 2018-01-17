package edu.washington.briluu.quizdroid;

/**
 * Created by briluu on 2/10/17.
 */

public class Question {

    private String questionText;
    private int correctAns;
    private String[] answers;

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAns() {
        return correctAns;
    }

}
