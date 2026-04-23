package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.service.Service;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @SuppressWarnings("static-access")
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

    @SuppressWarnings("static-access")
	@Test
    void testDeletePizza() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        Service service = new Service(dao);

        service.deletePizza(7);

        verify(dao).deletePizza(7);
    }
}