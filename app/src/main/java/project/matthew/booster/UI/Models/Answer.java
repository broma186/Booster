package project.matthew.booster.UI.Models;

import org.simpleframework.xml.Attribute;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Matthew on 30/04/2018.
 */

public class Answer extends RealmObject {
    @PrimaryKey
    @Attribute(name = "id")
    private int id;

    @Attribute(name = "value")
    private int value;

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "isSelected")
    private boolean isSelected;

    public Answer() {}

    public Answer(int id, int value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
