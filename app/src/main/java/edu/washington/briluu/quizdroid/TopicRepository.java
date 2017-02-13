package edu.washington.briluu.quizdroid;

import java.util.HashMap;

/**
 * Created by briluu on 2/10/17.
 */

public class TopicRepository {

    // Singleton Design pattern
    // only one topic repository will be created
    private static TopicRepository instance = new TopicRepository();

    private static HashMap<String, Topic> topics;

    // initialize some Topics and Questions for now
    public static TopicRepository getInstance() {
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

        return instance;
    }

    public HashMap<String, Topic> getAllTopics() {
        return topics;
    }


}
