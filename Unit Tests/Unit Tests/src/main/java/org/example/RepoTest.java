package org.example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RepoTest {
    private MageRepository repository;

    private Mage mage;

    @BeforeEach
    public void setUp(){
        repository = new MageRepository();
        mage = new Mage("Ten karzeł z Jackassów", 2);

    }

    @AfterEach
    public void finish(){
        repository.getCollection().clear();
    }
}
