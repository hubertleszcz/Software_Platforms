package org.example;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
@Entity
public class Tower {

    @Id
    private String name;
    private int height;
    @OneToMany
    private List<Mage> mages;

    public Tower(){
        this.mages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }


    public void deleteMage(Mage mage) {
        mages.remove(mage);
    }

    public void deleteAllMages() {
        mages.clear();
    }

    public void addMage(Mage newMage){
        this.mages.add(newMage);
    }

    @Override
    public String toString(){
        String result = "Name: " + this.name + "height: " + this.height + "Mages: { ";
        for(Mage mage: this.mages){
            result = result + mage.toString();
        }
        result = result + "}";
        return result;
    }
}
