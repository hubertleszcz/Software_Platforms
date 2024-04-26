package org.example;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoTest {
    private MageRepository repository;

    private Mage mage;

    @BeforeEach
    public void setUp(){
        repository = new MageRepository();
        mage = new Mage("Ten karzeł z Jackassów", 2);

    }
    @Test
    @DisplayName("Test zapisu maga")
    void testSave(){
        assertDoesNotThrow(()->repository.save(mage));
    }

    @Test
    @DisplayName("Test zapisu istniejącego maga")
    void testExisteingSave(){
        repository.save(mage);
        Mage mage2 = new Mage("Ten karzeł z Jackassów", 2);
        assertThrows(IllegalArgumentException.class,()->repository.save(mage2));
    }

    @Test
    @DisplayName("Test odczytu maga")
    void testRead(){
        repository.getCollection().add(mage);
        Optional<Mage> check = repository.find(mage.getName());
        Assertions.assertTrue(check.isPresent());
        Assertions.assertEquals(mage, check.get());
    }

    @Test
    @DisplayName("Test odczytu nieistniejącego maga")
    void testUnexistingRead(){
        Optional<Mage> check = repository.find(mage.getName());
        Assertions.assertTrue(check.isEmpty());
    }
    @Test
    @DisplayName("Test usunięcia maga")
    void testDelete(){
        repository.getCollection().add(mage);
        Assertions.assertDoesNotThrow(()->repository.delete(mage.getName()));
    }

    @Test
    @DisplayName("Test usunięcia nieistniejącego maga")
    void testUnexistingDelete(){
        assertThrows(IllegalArgumentException.class, ()->repository.delete(mage.getName()));
    }

    @AfterEach
    public void finish(){
        repository.getCollection().clear();
    }
}
