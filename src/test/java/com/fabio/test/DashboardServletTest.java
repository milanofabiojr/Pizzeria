package com.fabio.test;

import com.fabio.main.DashboardServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.Mockito.*;

class DashboardServletTest {

    @InjectMocks
    private DashboardServlet servlet;

    @Mock
    private PizzeriaDAO dao;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        Field f = DashboardServlet.class.getDeclaredField("dao");
        f.setAccessible(true);
        f.set(servlet, dao);
    }

    @Test
    void testRedirectIfNotLogged() throws Exception {
        when(req.getSession(false)).thenReturn(null);

        servlet.doGet(req, resp);

        verify(resp).sendRedirect(contains("login"));
    }

    @Test
    void testLoadDashboard() throws Exception {
        Utente u = new Utente();
        u.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);

        when(dao.findAllImpasti()).thenReturn(List.of(new Impasto("Napoli")));
        when(dao.findAllIngredienti()).thenReturn(List.of(new Ingrediente("Mozzarella")));
        when(dao.findAllbyUtenteId(1)).thenReturn(List.of());

        when(req.getRequestDispatcher("dashboard.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(dispatcher).forward(req, resp);
    }

    @Test
    void testDeletePizza() throws Exception {
        Utente u = new Utente();
        u.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(u);
        when(req.getParameter("deleteId")).thenReturn("1");

        servlet.doPost(req, resp);

        verify(dao).delete(1);
        verify(resp).sendRedirect(contains("dashboard"));
    }
}