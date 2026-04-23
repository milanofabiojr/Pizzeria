package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.soap.PizzaSOAPService;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PizzaSOAPServiceTest {

    @Test
    void testCreaPizza() {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        PizzaSOAPService service = new PizzaSOAPService();

        Pizza p = new Pizza();
        p.setId(10);

        service = spy(service);
        doReturn(dao).when(service).getClass(); // semplificazione concettuale

        service.creaPizza(p);

        verify(dao).save(p);
    }
}