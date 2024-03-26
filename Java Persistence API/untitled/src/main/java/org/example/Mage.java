package org.example;
import javax.persistence.*;

@Entity
public class Mage {
    @Id
    private String name;

    private int level;

    @ManyToOne
    private Tower tower;

    public Mage(){}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    public String toString(){
        String result =  "Name: " + this.name + " level: " + this.level + " tower: ";
        if(this.tower == null){
            result = result + "brak";
        }
        else{
            result = result + this.tower.getName();
        }
        return result;
    }
}
