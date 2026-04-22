package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.service.Service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceTest {


    // TEST GET PIZZE
    @Test
    void testGetPizze() {
        try (MockedStatic<PizzeriaDAO> mockedDAO = mockStatic(PizzeriaDAO.class)) {

            List<Pizza> mockList = new ArrayList<>();
            mockList.add(new Pizza());

            mockedDAO.when(PizzeriaDAO::findAllPizze).thenReturn(mockList);

            Service service = new Service();
            List<Pizza> result = service.getPizze_JSON();

            assertNotNull(result);
            assertEquals(1, result.size());
        }
    }


    // TEST ADD PIZZA
    @Test
    void testAddPizza() {
        try (MockedStatic<PizzeriaDAO> mockedDAO = mockStatic(PizzeriaDAO.class)) {

            Pizza pizza = new Pizza();
            pizza.setNome("Margherita");

            mockedDAO.when(() -> PizzeriaDAO.addPizza(pizza)).thenReturn(pizza);

            Service service = new Service();
            Pizza result = service.addPizza(pizza);

            assertNotNull(result);
            assertEquals("Margherita", result.getNome());
        }
    }


    // TEST UPDATE PIZZA SUCCESS
    @Test
    void testUpdatePizzaSuccess() {
        try (MockedStatic<PizzeriaDAO> mockedDAO = mockStatic(PizzeriaDAO.class)) {

            Pizza existing = new Pizza();
            existing.setNome("Vecchia");

            Pizza nuova = new Pizza();
            nuova.setNome("Nuova");

            mockedDAO.when(() -> PizzeriaDAO.findPizza(1)).thenReturn(existing);
            mockedDAO.when(() -> PizzeriaDAO.updatePizza(existing)).thenReturn(existing);

            Service service = new Service();
            Response response = service.updatePizza(1, nuova);

            assertEquals(200, response.getStatus());
            assertEquals("Nuova", existing.getNome());
        }
    }


    // TEST UPDATE PIZZA NOT FOUND
    @Test
    void testUpdatePizzaNotFound() {
        try (MockedStatic<PizzeriaDAO> mockedDAO = mockStatic(PizzeriaDAO.class)) {

            mockedDAO.when(() -> PizzeriaDAO.findPizza(1)).thenReturn(null);

            Service service = new Service();
            Response response = service.updatePizza(1, new Pizza());

            assertEquals(404, response.getStatus());
        }
    }


    // TEST DELETE PIZZA
    @Test
    void testDeletePizza() {
        try (MockedStatic<PizzeriaDAO> mockedDAO = mockStatic(PizzeriaDAO.class)) {

            Service service = new Service();
            service.deletePizza(1);

            mockedDAO.verify(() -> PizzeriaDAO.deletePizza(1), times(1));
        }
    }
}