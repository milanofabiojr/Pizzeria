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
}