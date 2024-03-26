package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;
public class InputHandle {

    private Scanner scanner;

    private EntityManagerFactory factory;
    private EntityManager manager;

    private void createTower(){
        this.manager.getTransaction().begin();
        System.out.println("Podaj imie");
        String name = this.scanner.nextLine();
        System.out.println("Podaj wysokosc");
        int height = Integer.parseInt(this.scanner.nextLine());

        Tower tower = new Tower();
        tower.setHeight(height);
        tower.setName(name);

        this.manager.persist(tower);
        this.manager.getTransaction().commit();
        System.out.println("Dodano wieże");
    }

    private void createMage(){
        this.manager.getTransaction().begin();
        System.out.println("Podaj imie");
        String name = this.scanner.nextLine();
        System.out.println("Podaj poziom");
        int level = Integer.parseInt(this.scanner.nextLine());

        System.out.println("Podaj nazwe wiezy");
        String towername = this.scanner.nextLine();


        Mage mage = new Mage();
        mage.setLevel(level);
        mage.setName(name);

        if(!towername.equals("")){
            Tower tower = manager.find(Tower.class, towername);
            mage.setTower(tower);
            tower.addMage(mage);
        }

        manager.persist(mage);
        manager.getTransaction().commit();
    }

    private void createHandler(){
        System.out.println("1-mag, 2-wieża");
        int option = Integer.parseInt(this.scanner.nextLine());
        switch (option){
            case 1:
                this.createMage();
                break;
            case 2:
                this.createTower();
                break;
        }

    }

    private void printTowers(){
        manager.getTransaction().begin();
        List<Tower> towers = manager.createQuery("Select t FROM Tower t", Tower.class).getResultList();
        for(Tower tower: towers){
            System.out.println(tower.toString());
        }

        manager.getTransaction().commit();
    }

    private void printMages(){
        manager.getTransaction().begin();
        List<Mage> mages = manager.createQuery("Select t FROM Mage t", Mage.class).getResultList();
        for(Mage mage: mages){
            System.out.println(mage.toString());
        }

        manager.getTransaction().commit();
    }



    private void printHandler(){
        System.out.println("1-mag, 2-wieża");
        int option = Integer.parseInt(this.scanner.nextLine());
        switch (option){
            case 1:
                this.printMages();
                break;
            case 2:
                this.printTowers();
                break;
        }

    }
    public InputHandle(){
        this.factory = Persistence.createEntityManagerFactory("default");
        this.manager = factory.createEntityManager();

        this.scanner = new Scanner(System.in);

        while(true){
            System.out.println("1-dodaj, 2-usun. 3-wypisz, 4-wyjscie");
            int option = Integer.parseInt(this.scanner.nextLine());
            switch (option){
                case 1:
                    this.createHandler();
                    break;
                case 2:

                    break;
                case 3:
                    this.printHandler();
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }
}
