package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;
import com.fabio.soap.PizzaSOAPService;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class PizzaSOAPServiceTest {
	@Test
	void testCreaPizza() {
	    PizzeriaDAO dao = mock(PizzeriaDAO.class);
	    PizzaSOAPService service = new PizzaSOAPService(dao);

	    Pizza p = new Pizza();
	    p.setId(10);

	    // inject via reflection (necessario qui)
	    try {
	        java.lang.reflect.Field field = PizzaSOAPService.class.getDeclaredField("dao");
	        field.setAccessible(true);
	        field.set(service, dao);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }

	    int id = service.creaPizza(p);

	    verify(dao).save(p);
	    assertEquals(10, id);
	}
}