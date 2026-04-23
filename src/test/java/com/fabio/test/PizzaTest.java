package com.fabio.test;

import com.fabio.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    @Test
    void testPizza() {
        Impasto impasto = new Impasto("Napoli");
        Utente u = new Utente("fabio", "123");

        Pizza p = new Pizza("Margherita", impasto, u);

        assertEquals("Margherita", p.getNome());
        assertEquals(impasto, p.getImpasto());
        assertEquals(u, p.getUtente());
    }

    @Test
    void testIngredienti() {
        Pizza p = new Pizza();
        Ingrediente i = new Ingrediente("Mozzarella");

        p.setIngredienti(List.of(i));

        assertEquals(1, p.getIngredienti().size());
    }
}