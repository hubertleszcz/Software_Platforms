package org.example;

import javax.swing.text.html.Option;
import java.util.Optional;

public class MageController {
    private MageRepository repository;
    public MageController(MageRepository repository) {
        this.repository = repository;
    }
    public String find(String name) {
        Optional<Mage> check = repository.find(name);
        if(check.isEmpty()) return "not found";
        else return check.get().toString();
    }
    public String delete(String name) {
        try{
            repository.delete(name);
            return "done";
        }
        catch(IllegalArgumentException e){
            return "not found";
        }
    }
    public String save(String name, String level) {
        try{
            repository.save(new Mage(name, Integer.parseInt(level)));
            return "done";
        }catch(IllegalArgumentException e){
            return "bad request";
        }
    }
}
