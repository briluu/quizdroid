package edu.washington.briluu.quizdroid;

import android.os.Environment;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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


        // download and save quiz json questions
        URL url = null;
        int interval = 3; // set default interval
        try {
            // default url
            url = new URL("http://tednewardsandbox.site44.com/questions.json");
        } catch (IOException e) {
            e.printStackTrace();
            // if url doesnt work then deal with it here
        }
        DownLoadFilesTask downLoadFilesTask = new DownLoadFilesTask();
        downLoadFilesTask.execute(url);

        // access the json file and read it
        File questions = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "questions.json");

        // initialize topic repository with json file
        topics = new HashMap<>();

        if(questions.exists() && !questions.isDirectory()) {
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
                parseFile(jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public HashMap<String, Topic> getAllTopics() {
        return topics;
    }

    private static void parseFile(String jsonStr) throws JSONException {

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
    }
}
