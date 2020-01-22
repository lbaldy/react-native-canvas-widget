package pl.baldy.rncanvas;

import java.util.ArrayList;

public class Command {
    private String name;
    private ArrayList<Object> value;

    public ArrayList<Object> getValue() {
        return value;
    }

    public void setValue(ArrayList<Object> value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
