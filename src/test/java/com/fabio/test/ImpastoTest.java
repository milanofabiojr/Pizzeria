package com.fabio.test;

import com.fabio.model.Impasto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImpastoTest {

    @Test
    void testImpasto() {
        Impasto i = new Impasto("Integrale");
        assertEquals("Integrale", i.getNome());
    }
}