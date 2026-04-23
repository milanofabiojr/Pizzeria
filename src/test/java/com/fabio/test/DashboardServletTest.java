package com.fabio.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import com.fabio.main.DashboardServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Impasto;
import com.fabio.model.Ingrediente;
import com.fabio.model.Utente;

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
        Field field = DashboardServlet.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(servlet, dao);
    }

    @Test
    void testLoadDashboard() throws Exception {
        Utente user = new Utente();
        user.setId(1);

        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(dao.findAllImpasti()).thenReturn(List.of(new Impasto("Napoli")));
        when(dao.findAllIngredienti()).thenReturn(List.of(new Ingrediente("Mozzarella")));
        when(dao.findAllbyUtenteId(1)).thenReturn(List.of());

        when(req.getRequestDispatcher("dashboard.jsp")).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(dispatcher).forward(req, resp);
    }
}