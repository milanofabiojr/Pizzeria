package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.model.Utente;

class PizzeriaDAOTest {
	// test comportamento
	@Test
	void loginShouldReturnNullIfUserNotFound() {
		PizzeriaDAO dao = new PizzeriaDAO();

		Utente result = dao.login("wrong", "wrong");

		assertNull(result);
	}

	@Test
	void shouldSavePizza() {
		PizzeriaDAO dao = new PizzeriaDAO();

		Pizza pizza = new Pizza();
		pizza.setNome("Margherita");

		dao.addPizza(pizza);

		assertNotNull(pizza.getId());
	}

	@Test
	void shouldDeletePizza() {
		PizzeriaDAO dao = new PizzeriaDAO();

		Pizza pizza = new Pizza();
		pizza.setNome("Test");
		dao.addPizza(pizza);

		dao.deletePizza(pizza.getId());

		Pizza result = dao.findPizza(pizza.getId());
		assertNull(result);
	}

	@Test
	void shouldReturnNullWhenPizzaNotFound() {
		PizzeriaDAO dao = new PizzeriaDAO();

		Pizza pizza = dao.findPizza(9999);

		assertNull(pizza);
	}
}