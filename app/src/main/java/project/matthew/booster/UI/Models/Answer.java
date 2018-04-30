package project.matthew.booster.UI.Models;

/**
 * Created by Matthew on 30/04/2018.
 */

public class Answer {

    private int id, value;
    private String name;

    public Answer(int id, int value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
