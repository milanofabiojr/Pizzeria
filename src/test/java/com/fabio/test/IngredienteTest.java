package com.fabio.test;

import com.fabio.model.Ingrediente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IngredienteTest {

    @Test
    void testIngrediente() {
        Ingrediente i = new Ingrediente("Mozzarella");
        assertEquals("Mozzarella", i.getNome());
    }
}