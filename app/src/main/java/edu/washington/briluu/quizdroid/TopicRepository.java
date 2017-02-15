package edu.washington.briluu.quizdroid;

import android.os.Environment;
import android.text.style.QuoteSpan;
import android.util.JsonReader;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by briluu on 2/10/17.
 */

public class TopicRepository {

    // Singleton Design pattern
    // only one topic repository will be created
    private static TopicRepository instance = new TopicRepository();

    private static HashMap<String, Topic> topics;

    // initialize some Topics and Questions for now
    public static TopicRepository getInstance() throws IOException {

        String external = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.v("TopicRepository", "Here is the path: " + external);
        // /storage/emulated/0

        File questions = new File(Environment.getExternalStorageDirectory(), "questions.json");

        topics = new HashMap<String, Topic>();

        try {
            FileInputStream stream = new FileInputStream(questions);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                jsonStr = Charset.defaultCharset().decode(bb).toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                stream.close();
            }

            JSONArray jsonArray = new JSONArray(jsonStr);

            // loop through the topics
            for (int i = 0;i < jsonArray.length(); i++) {
                // grab a topic
                JSONObject topicObj = jsonArray.getJSONObject(i);
                String title = topicObj.getString("title");
                String desc = topicObj.getString("desc");

                // loop through the all questions of a topic
                List<Question> allQuestions = new ArrayList<Question>();
                JSONArray jsonQuestions = topicObj.getJSONArray("questions");
                int numQuestions = jsonQuestions.length();
                for (int j = 0; j < numQuestions; j++) {
                    Question quizQuestion = new Question();
                    JSONObject jsonQ = jsonQuestions.getJSONObject(j);
                    String questionText = jsonQ.getString("text");
                    int answer = Integer.parseInt(jsonQ.getString("answer"));
                    quizQuestion.setQuestionText(questionText);
                    quizQuestion.setCorrectAns(answer);

                    JSONArray jsonAnswers = jsonQ.getJSONArray("answers");
                    String[] answers = new String[jsonAnswers.length()];

                    for (int k = 0; k < jsonAnswers.length(); k++) {
                        answers[k] = jsonAnswers.getString(k);
                    }
                    quizQuestion.setAnswers(answers);
                    allQuestions.add(j, quizQuestion);
                }
                Topic topic = new Topic(title, desc, desc, allQuestions);
                topics.put(title, topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        Topic mathTopic = new Topic("Math");
        Topic physicsTopic = new Topic("Physics");
        Topic marvelTopic = new Topic("Marvel Super Heroes");
        mathTopic.setLongDescription("Math is the study of topics such as quantity (numbers)." +
                " In this quiz, there will be math questions from simple algebra to multivariable calculus");
        physicsTopic.setLongDescription("Physics is the natural science that involves the study of matter and " +
                "its motion and behavior through space and time, along with related concepts such as energy and force." +
                " In this quiz, there will be physics questions from basic mechanics to quantum mechanics.");
        marvelTopic.setLongDescription("The Marvel Universe contains a vast crew of superheroes and villains. " +
                "In this quiz, there will be questions that focus on the details of the Marvel Super Heroes only.");

        Question question = new Question();
        question.setQuestionText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Donec non est dictum, gravida odio eget, dapibus tellus. Sed elementum, tellus" +
                " in semper aliquam, lectus mauris vulputate ex, eu euismod enim metus ac diam?");
        question.setCorrectAns(1);
        question.setAnswers(new String[]{"Lorem ipsum dolor sit amet.", "Correct answer.",
                "Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet."});

        // add the same question to each topic 5 times just for now

        for (int i = 0; i < 5; i++) {
            physicsTopic.addQuestions(question);
            mathTopic.addQuestions(question);
            marvelTopic.addQuestions(question);
        }
        topics = new HashMap<String, Topic>();
        topics.put("Math", mathTopic);
        topics.put("Physics", physicsTopic);
        topics.put("Marvel Super Heroes", marvelTopic);
        */

        return instance;
    }

    public HashMap<String, Topic> getAllTopics() {
        return topics;
    }


}
