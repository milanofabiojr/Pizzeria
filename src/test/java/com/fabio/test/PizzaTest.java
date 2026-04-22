package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.fabio.model.*;

import java.util.Arrays;

class PizzaTest {

    @Test
    void shouldSetAndGetPizzaFieldsCorrectly() {
        Impasto impasto = new Impasto("Classico");
        Utente utente = new Utente("fabio", "123");

        Pizza pizza = new Pizza();
        pizza.setNome("Margherita");
        pizza.setImpasto(impasto);
        pizza.setUtente(utente);

        assertEquals("Margherita", pizza.getNome());
        assertEquals(impasto, pizza.getImpasto());
        assertEquals(utente, pizza.getUtente());
    }

    @Test
    void shouldHandleIngredientiCorrectly() {
        Pizza pizza = new Pizza();

        Ingrediente i1 = new Ingrediente("Mozzarella");
        Ingrediente i2 = new Ingrediente("Pomodoro");

        pizza.setIngredienti(Arrays.asList(i1, i2));

        assertEquals(2, pizza.getIngredienti().size());
    }
}