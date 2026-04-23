package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import com.fabio.main.DashboardServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Utente;

class DashboardServletTest {

	@Test
	void shouldRedirectToLoginIfNotLogged() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);

		when(req.getSession(false)).thenReturn(null);
		when(req.getContextPath()).thenReturn("/app");

		DashboardServlet servlet = new DashboardServlet();

		servlet.doGet(req, resp);

		verify(resp).sendRedirect("/app/login");
	}
}