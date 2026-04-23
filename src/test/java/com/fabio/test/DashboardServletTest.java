package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

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
	
	@Test
    void testLoadDashboard() throws Exception {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        DashboardServlet servlet = new DashboardServlet(dao);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        Utente u = new Utente();
        u.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);
        when(req.getRequestDispatcher("dashboard.jsp")).thenReturn(dispatcher);

        when(dao.findAllImpasti()).thenReturn(new ArrayList<>());
        when(dao.findAllIngredienti()).thenReturn(new ArrayList<>());
        when(dao.findAllbyUtenteId(1)).thenReturn(new ArrayList<>());

        servlet.doGet(req, resp);

        verify(req).setAttribute(eq("impasti"), any());
        verify(req).setAttribute(eq("ingredienti"), any());
        verify(req).setAttribute(eq("pizze"), any());
        verify(dispatcher).forward(req, resp);
    }
}