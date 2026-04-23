package com.fabio.test;

import com.fabio.model.*;
import com.fabio.service.Service;
import com.fabio.dao.PizzeriaDAO;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import jakarta.ws.rs.core.Response;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceTest {

    @Test
    void testGetUtenti() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            mock.when(PizzeriaDAO::getAllUtenti)
                .thenReturn(List.of(new Utente("fabio", "123")));

            Service s = new Service();
            assertEquals(1, s.getUtenti_JSON().size());
        }
    }

    @Test
    void testGetImpasti() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            mock.when(PizzeriaDAO::findAllImpasti)
                .thenReturn(List.of(new Impasto("Napoli")));

            Service s = new Service();
            assertEquals(1, s.getImpasti_JSON().size());
        }
    }

    @Test
    void testUpdatePizza_NotFound() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            mock.when(() -> PizzeriaDAO.findPizza(1)).thenReturn(null);

            Service s = new Service();
            Response r = s.updatePizza(1, new Pizza());

            assertEquals(404, r.getStatus());
        }
    }

    @Test
    void testUpdatePizza_OK() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            Pizza p = new Pizza();

            mock.when(() -> PizzeriaDAO.findPizza(1)).thenReturn(p);

            Service s = new Service();
            Response r = s.updatePizza(1, new Pizza());

            assertEquals(200, r.getStatus());
        }
    }

    @Test
    void testDeletePizza() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            Service s = new Service();
            s.deletePizza(1);

            mock.verify(() -> PizzeriaDAO.deletePizza(1));
        }
    }

    @Test
    void testAddPizza() {
        try (MockedStatic<PizzeriaDAO> mock = mockStatic(PizzeriaDAO.class)) {

            Pizza p = new Pizza();

            mock.when(() -> PizzeriaDAO.addPizza(p)).thenReturn(p);

            Service s = new Service();
            Pizza result = s.addPizza(p);

            assertNotNull(result);
        }
    }
}