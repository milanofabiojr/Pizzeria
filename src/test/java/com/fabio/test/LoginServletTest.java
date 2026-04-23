package com.fabio.test;

import com.fabio.main.LoginServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Utente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class LoginServletTest {

    @InjectMocks
    private LoginServlet servlet;

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

        Field f = LoginServlet.class.getDeclaredField("dao");
        f.setAccessible(true);
        f.set(servlet, dao);
    }

    @Test
    void testLoginSuccess() throws Exception {
        Utente u = new Utente("fabio", "123");

        when(req.getParameter("username")).thenReturn("fabio");
        when(req.getParameter("password")).thenReturn("123");
        when(dao.login("fabio", "123")).thenReturn(u);
        when(req.getSession(true)).thenReturn(session);

        servlet.doPost(req, resp);

        verify(session).setAttribute("user", u);
        verify(resp).sendRedirect("dashboard");
    }

    @Test
    void testLoginFail() throws Exception {
        when(req.getParameter("username")).thenReturn("x");
        when(req.getParameter("password")).thenReturn("y");
        when(dao.login("x", "y")).thenReturn(null);
        when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

        servlet.doPost(req, resp);

        verify(req).setAttribute(eq("error"), any());
        verify(dispatcher).forward(req, resp);
    }
}