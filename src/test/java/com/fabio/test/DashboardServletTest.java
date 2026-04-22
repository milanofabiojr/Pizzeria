package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.*;

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
    void shouldDeletePizzaWhenDeleteIdPresent() throws Exception {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        DashboardServlet servlet = new DashboardServlet();
        PizzeriaDAO dao = mock(PizzeriaDAO.class);

        java.lang.reflect.Field field = DashboardServlet.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(servlet, dao);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new Utente("fabio", "123"));
        when(req.getParameter("deleteId")).thenReturn("1");
        when(req.getContextPath()).thenReturn("/app");

        servlet.doPost(req, resp);

        verify(dao).delete(1);
        verify(resp).sendRedirect("/app/dashboard");
    }
}