package edu.washington.briluu.quizdroid;

import android.app.Application;
import android.util.Log;

/**
 * Created by briluu on 2/10/17.
 */

public class QuizApp extends Application {

    public QuizApp() {
        Log.d("QuizApp", "Constructor fired!");
    }

    // Method for accessing the repository
    public TopicRepository getRepository() {
        return TopicRepository.getInstance();
    }

    @Override
    public void onCreate() {
        Log.d("QuizApp", "Loading QuizApp...");
    }
}
