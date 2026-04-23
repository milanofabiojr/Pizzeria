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
    
    @Test
    void testCreatePizza() throws Exception {

        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        DashboardServlet servlet = new DashboardServlet(dao);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        Utente u = new Utente("u", "p");
        u.setId(1);

        Impasto impasto = new Impasto();
        impasto.setId(1);

        Ingrediente ing = new Ingrediente();
        ing.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);

        when(req.getParameter("deleteId")).thenReturn(null);
        when(req.getParameter("nomePizza")).thenReturn("Margherita");
        when(req.getParameter("impasto")).thenReturn("1");
        when(req.getParameterValues("ingredienti"))
                .thenReturn(new String[]{"1"});

        when(dao.findImpasto(1)).thenReturn(impasto);
        when(dao.findIngrediente(1)).thenReturn(ing);

        when(req.getContextPath()).thenReturn("");

        servlet.doPost(req, resp);

        verify(dao).save(any());
    }
    
    @Test
    void testDashboardSessionNull() throws Exception {

        DashboardServlet servlet = new DashboardServlet(mock(PizzeriaDAO.class));

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getSession(false)).thenReturn(null);

        servlet.doPost(req, resp);

        verify(resp).sendRedirect(anyString());
    }
    
    @Test
    void testDashboardValidationFail() throws Exception {

        DashboardServlet servlet = new DashboardServlet(mock(PizzeriaDAO.class));

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession(false)).thenReturn(session);

        when(req.getParameter("nomePizza")).thenReturn("");
        when(req.getParameter("impasto")).thenReturn(null);

        servlet.doPost(req, resp);

        verify(resp).sendRedirect(anyString());
    }
    
    @Test
    void testDashboardUserNull() throws Exception {

        PizzeriaDAO dao = mock(PizzeriaDAO.class);
        DashboardServlet servlet = new DashboardServlet(dao);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(req, resp);

        verify(resp).sendRedirect(anyString());
    }
}