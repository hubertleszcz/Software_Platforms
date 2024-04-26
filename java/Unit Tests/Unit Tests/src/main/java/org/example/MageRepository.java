package org.example;

import java.util.*;

public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository() {this.collection = new ArrayList<Mage>();}
    public Optional<Mage> find(String name) {
        for(Mage mage: collection){
            if(Objects.equals(mage.getName(), name)) return Optional.of(mage);
        }
        return Optional.empty();
    }
    public void delete(String name) throws IllegalArgumentException {
        Optional<Mage> check = this.find(name);
        if (check.isEmpty()) throw new IllegalArgumentException("Mag nie istnieje");
        else this.collection.remove(check.get());
    }
    public void save(Mage mage) throws IllegalArgumentException{
        Optional<Mage> check = this.find(mage.getName());
        if (check.isEmpty()) collection.add(mage);
        else throw new IllegalArgumentException("Mag istnieje\n");
    }

    public Collection<Mage> getCollection() {
        return collection;
    }
}
