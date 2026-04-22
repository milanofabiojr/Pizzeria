package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.fabio.model.Utente;

class UtenteTest {

    @Test
    void shouldCreateUserCorrectly() {
        Utente u = new Utente("fabio", "123");

        assertEquals("fabio", u.getUsername());
        assertEquals("123", u.getPassword());
    }
}