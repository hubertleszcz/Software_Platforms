package org.example;

import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerTest {
    private MageRepository repo;
    private MageController controller;
    private Mage mage1;

    @BeforeEach
    void setUp(){
        repo = mock(MageRepository.class);
        controller = new MageController(repo);
        mage1 = new Mage("Steve O", 11);
    }

    @Test
    @DisplayName("Test zapisu nowego maga")
    void testSave(){

        String result = controller.save(mage1.getName(), Integer.toString(mage1.getLevel()));
        assertEquals("done", result);
    }

    @Test
    @DisplayName("Test zapisu istniejącego maga")
    void testExistingSave(){
        doThrow(new IllegalArgumentException("Mag istnieje\n")).when(repo).save(any(Mage.class));

        String result = controller.save(mage1.getName(), Integer.toString(mage1.getLevel()));
        assertEquals("bad request", result);
    }
    @Test
    @DisplayName("Test usunięcia istniejącego maga")
    void testDelete(){
        repo.getCollection().add(mage1);
        String result = controller.delete(mage1.getName());
        assertEquals("done", result);
    }
    @Test
    @DisplayName("Test usunięcia nieistniejącego maga")
    void testUnexistingDelete(){
        doThrow(new IllegalArgumentException("Mag nie istnieje\n")).when(repo).delete(any(String.class));
        String result = controller.delete(mage1.getName());
        assertEquals("not found", result);
    }
    @Test
    @DisplayName("Test odczytu istniejącego maga")
    void testRead(){

        when(repo.find(any(String.class)))
                .thenReturn(Optional.of(mage1));

        String result = controller.find(mage1.getName());
        assertEquals(mage1.toString(), result);
    }

    @Test
    @DisplayName("Test odczytu nieistniejącego maga")
    void testUnexistingRead(){

        String result = controller.find(mage1.getName());
        assertEquals("not found", result);
    }

    public static class RepoTest {
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
            assertTrue(check.isPresent());
            assertEquals(mage, check.get());
        }

        @Test
        @DisplayName("Test odczytu nieistniejącego maga")
        void testUnexistingRead(){
            Optional<Mage> check = repository.find(mage.getName());
            assertTrue(check.isEmpty());
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
}
