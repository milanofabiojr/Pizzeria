package com.fabio.test;

import com.fabio.main.DashboardServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.*;

class DashboardServletTest {

    private DashboardServlet servlet;

    private PizzeriaDAO dao;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setup() throws Exception {

        // creo mock MANUALMENTE (evitiamo problemi annotation)
        dao = mock(PizzeriaDAO.class);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);

        servlet = new DashboardServlet(dao);

        // 🔥 injection DAO mock
        Field field = DashboardServlet.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(servlet, dao);
    }

    @Test
    void testLoadDashboard() throws Exception {

        Utente u = new Utente();
        u.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);

        when(dao.findAllImpasti()).thenReturn(List.of(new Impasto("Napoli")));
        when(dao.findAllIngredienti()).thenReturn(List.of(new Ingrediente("Mozzarella")));

        when(req.getRequestDispatcher("dashboard.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(dispatcher).forward(req, resp);
    }
}