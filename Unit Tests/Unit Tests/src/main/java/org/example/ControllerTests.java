package org.example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class ControllerTests {
    private MageRepository repository;
    private MageController controller;

    private MageDTO mage;

    @BeforeEach
    public void setup(){
        //repository = mock(MageRepository.class);
        controller = new MageController(repository);
        mage = new MageDTO("Steve-O", 4);
    }
}
