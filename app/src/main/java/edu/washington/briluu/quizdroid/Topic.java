package edu.washington.briluu.quizdroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by briluu on 2/10/17.
 */

public class Topic {

    private String title;
    private String shortDescription;
    private String longDescription;
    private List<Question> questions;

    public Topic(String title) {
        this(title, null, null, new ArrayList<Question>());
    }

    public Topic(String title, String shortDescription, String longDescription, List questions) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.questions = questions;
    }

    public void addQuestions(Question question) {
        questions.add(question);
    }

    public void setTopic(String title) {
        this.title = title;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getTitle() {
        return title;
    }

}
