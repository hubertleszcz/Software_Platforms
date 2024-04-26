package org.example;

public class Mage {
    private String name;
    private int level;

    public Mage(String name, int level){
        this.name = name;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return " Name: " + this.name + " level: " + this.level;
    }
}
