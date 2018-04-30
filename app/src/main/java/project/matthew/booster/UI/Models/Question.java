package project.matthew.booster.UI.Models;

/**
 * Created by Matthew on 30/04/2018.
 */

public class Question {

    private int id;
    private String title;
    private Answer[] answers;

    public Question(int id, String title, Answer[] answers) {
        this.id = id;
        this.title = title;
        this.answers = answers;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
