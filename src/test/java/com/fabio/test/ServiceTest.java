package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.service.Service;

import jakarta.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void testAddPizza() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        Service service = new Service(dao);

        Pizza p = new Pizza();

        when(dao.addPizza(p)).thenReturn(p);

        Pizza result = service.addPizza(p);

        assertEquals(p, result);

        verify(dao).addPizza(p);
    }

    @Test
    void testDeletePizza() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        Service service = new Service(dao);

        service.deletePizza(1);

        verify(dao).deletePizza(1);
    }
    
    @Test
    void testDashboardServletDefaultConstructor() {

        Service servlet = new Service();

        assertNotNull(servlet);
    }
    
    @Test
    void testUpdatePizzaOK() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        
        Pizza existing = new Pizza();
        existing.setNome("Vecchia");

        Pizza nuova = new Pizza();
        nuova.setNome("Nuova");

        when(dao.findPizza(1)).thenReturn(existing);

        Service service = new Service(dao);

        Response response = service.updatePizza(1, nuova);

        assertEquals(200, response.getStatus());
        assertEquals("Nuova", existing.getNome());
        verify(dao).updatePizza(existing);
    }
    
    @Test
    void testUpdatePizzaNotFound() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        when(dao.findPizza(1)).thenReturn(null);

        Service service = new Service(dao);

        Response response = service.updatePizza(1, new Pizza());

        assertEquals(404, response.getStatus());
    }
}