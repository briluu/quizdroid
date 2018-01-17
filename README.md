# quizdroid

A basic Android application that gives a quiz to the user, reading in question and answer data from json and displaying it to the user with fragments.

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


## Part 4

* Refactor the TopicRepository to read a JSON file ("data/questions.json") to use as the source of the Topics and Questions. Use a hard-coded file (available at http://tednewardsandbox.site44.com/questions.json) pushed to the device with adb for now; do NOT include it as part of the application's "assets", as a future version will be replacing the file after the application has been deployed.
* The application should provide a "Preferences" action bar item that brings up a "Preferences" activity containing the application's configurable settings: URL to use for question data, and how often to check for new downloads measured in minutes. If a download is currently under way, these settings should not take effect until the next download starts.

## Part 5
* The application should create some background operation (Thread, AlarmManager or Service) that will (eventually) attempt to download a JSON file containing the questions from the server every "N" minutes/hours. For now, pop a Toast message displaying the URL that will eventually be hit. Make sure this URL is what's defined in the Preferences.
* The background operation should now attempt to download the JSON file specified in the Preferences URL. Save this data to a local file as "questions.json". Make sure that this file always remains in a good state--if the download fails or is interrupted, the previous file should remain in place.
* As a user, if I am currently offline (in Airplane mode or in a no-bars area) the application should display a message telling me I have no access to the Internet. If I am in airplane mode, it should ask if I want to turn airplane mode off and take me to the Settings activity to do so. If I simply have no signal, it should just punt gracefully with a nice error message.
* As a user, if the download of the questions fails, the application should tell me and ask if I want to retry or quit the application and try again later.
