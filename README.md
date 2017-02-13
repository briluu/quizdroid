# quizdroid
## Stories
## Part 1

* As a user, when I start the app, I should see a list of different topics on which to take a quiz. (For now, these should be hard-coded to read "Math", "Physics", and "Marvel Super Heroes", as well as any additional topics you feel like adding into the mix.)  

* As a user, when I select a topic from the list, it should take me to the "topic overview" page, which displays a brief description of this topic, the total number of questions in this topic, and a "Begin" button taking me to the first question.  

* As a user, when I press the "Begin" button from the topic overview page, it should take me to the first question page, which will consist of a question (TextView), four radio buttons each of which consist of one answer, and a "Submit" button.  

* As a user, when I am on a question page and I press the "Submit" button, if no radio button is selected, it should do nothing. If a radio button is checked, it should take me to the "answer" page. (Ideally, the Submit button should not be visible until a radio button is selected.)  

* As a user, when I am on the "answer" page, it should display the answer I gave, the correct answer, and tell me the total number of correct vs incorrect answers so far ("You have 5 out of 9 correct"), and display a "Next" button taking me to the next question page, or else display a "Finish" button if that is the last question in the topic.  

* As a user, when I am on the "answer" page, and it is the last question of the topic, the "Finish" button should take me back to the first topic-list page, so that I can take a new quiz.  

## Part 2
* As a developer, the "topic overview", "question" and "answer" pages should be merged into a single Activity, using Fragments to swap between the question UI and the answer UI. This means, then, that there will be only two Activities in the entire system: the list of topics at the front of the application, and the multi-use activity that serves for the topic overview, the question, and answer pages.

1pt: Topic Overview uses fragment  
1pt: Question uses fragment  
1pt: Answer uses fragment  
2pts: Two activities  

## Part 3
* Create a class called QuizApp extending android.app.Application and make sure it is referenced from the app manifest; override the onCreate() method to emit a message to the diagnostic log to ensure it is being loaded and run

* Use the "Repository" pattern to create a TopicRepository interface; create one implementation that simply stores elements in memory from a hard-coded list initialized on startup. Create domain objects for Topic and Quiz, where a Quiz is question text, four answers, and an integer saying which of the four answers is correct, and Topic is a title, short description, long description, and a collection of Question objects.

* Make the QuizApp object a singleton, and provide a method for accessing the TopicRepository.

* Refactor the activities in the application to use the TopicRepository. On the topic list page, the title and the short description should come from the similar fields in the Topic object. On the topic overview page, the title and long description should come from the similar fields in the Topic object. The Question object should be similarly easy to match up to the UI.

1pt: QuizApp extends Application, is referenced from manifest, and writes to log  
1pt: QuizApp is a Singleton  
3pts: TopicRepository  
