package project.matthew.booster.UI.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Matthew on 30/04/2018.
 */

@Root(name = "row", strict = false)

public class Question extends RealmObject {
    @PrimaryKey
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "title")
    private String title;

    @Attribute(name = "isAnswered")
    private Boolean isAnswered;

    private RealmList<Answer> answers;

    public Question() {}

    public Question(int id, String title, RealmList<Answer> answers) {
        this.id = id;
        this.title = title;
        this.answers = answers;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnswers(RealmList<Answer> answers) {
        this.answers = answers;
    }

    public RealmList<Answer> getAnswers() {
        return answers;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public Boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }
}
