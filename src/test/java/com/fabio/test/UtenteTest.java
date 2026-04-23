package com.fabio.test;

import com.fabio.model.Utente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    @Test
    void testGetterSetter() {
        Utente u = new Utente();
        u.setId(1);
        u.setUsername("fabio");
        u.setPassword("123");

        assertEquals(1, u.getId());
        assertEquals("fabio", u.getUsername());
        assertEquals("123", u.getPassword());
    }

    @Test
    void testCostruttore() {
        Utente u = new Utente("fabio", "123");
        assertEquals("fabio", u.getUsername());
        assertEquals("123", u.getPassword());
    }
}