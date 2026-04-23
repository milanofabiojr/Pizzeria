package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.main.DashboardServlet;
import com.fabio.model.*;
import jakarta.servlet.http.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import jakarta.servlet.RequestDispatcher;


import static org.mockito.Mockito.*;

class DashboardServletTest {
    @Test
    void testDoGetLoggedUser() throws Exception {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        DashboardServlet servlet = new DashboardServlet(dao);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);

        Utente u = new Utente("u", "p");
        u.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);
        
        when(req.getRequestDispatcher("dashboard.jsp")).thenReturn(rd);

        when(dao.findAllImpasti()).thenReturn(List.of());
        when(dao.findAllIngredienti()).thenReturn(List.of());
        when(dao.findAllbyUtenteId(1)).thenReturn(List.of());

        servlet.doGet(req, resp);

        verify(rd).forward(req, resp);
    }


    @Test
    void testDoGetNotLoggedRedirect() throws Exception {
        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        DashboardServlet servlet = new DashboardServlet(dao);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession(false)).thenReturn(null);

        servlet.doGet(req, resp);

        verify(resp).sendRedirect(anyString());
    }
}