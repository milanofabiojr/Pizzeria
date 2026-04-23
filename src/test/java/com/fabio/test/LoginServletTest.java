package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.main.LoginServlet;
import com.fabio.model.Utente;
import jakarta.servlet.http.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import jakarta.servlet.RequestDispatcher;

import static org.mockito.Mockito.*;

class LoginServletTest {

	@Test
	void testLoginSuccess() throws Exception {

		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet(dao);

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);

		when(req.getParameter("username")).thenReturn("u");
		when(req.getParameter("password")).thenReturn("p");

		Utente u = new Utente("u", "p");

		when(dao.login("u", "p")).thenReturn(u);
		when(req.getSession(true)).thenReturn(session);

		servlet.doPost(req, resp);

		verify(session).setAttribute("user", u);
		verify(resp).sendRedirect("dashboard");
	}

	@Test
	void testLoginFail() throws Exception {

		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet(dao);

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher rd = mock(RequestDispatcher.class);

		when(req.getParameter("username")).thenReturn("x");
		when(req.getParameter("password")).thenReturn("y");

		when(dao.login("x", "y")).thenReturn(null);

		when(req.getRequestDispatcher("login.jsp")).thenReturn(rd);

		servlet.doPost(req, resp);

		verify(rd).forward(req, resp);
	}

	@Test
	void testLoginSuccessRedirect() throws Exception {
		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet(dao);

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);

		when(req.getParameter("username")).thenReturn("user");
		when(req.getParameter("password")).thenReturn("pass");
		when(req.getSession(true)).thenReturn(session);

		when(dao.login("user", "pass")).thenReturn(new Utente("user", "pass"));

		servlet.doPost(req, resp);

		verify(session).setAttribute(eq("user"), any());
		verify(resp).sendRedirect("dashboard");
	}

	@Test
	void testLoginFailForward() throws Exception {
		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet(dao);

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);

		when(req.getParameter("username")).thenReturn("x");
		when(req.getParameter("password")).thenReturn("y");

		when(dao.login("x", "y")).thenReturn(null);

		RequestDispatcher rd = mock(RequestDispatcher.class);
		when(req.getRequestDispatcher("login.jsp")).thenReturn(rd);

		servlet.doPost(req, resp);

		verify(rd).forward(req, resp);
	}
	
	@Test
	void testLoginNullInput() throws Exception {

	    PizzeriaDAO dao = mock(PizzeriaDAO.class);
	    LoginServlet servlet = new LoginServlet(dao);

	    HttpServletRequest req = mock(HttpServletRequest.class);
	    HttpServletResponse resp = mock(HttpServletResponse.class);
	    RequestDispatcher rd = mock(RequestDispatcher.class);

	    when(req.getParameter("username")).thenReturn(null);
	    when(req.getParameter("password")).thenReturn(null);

	    when(dao.login(null, null)).thenReturn(null);

	    when(req.getRequestDispatcher("login.jsp")).thenReturn(rd);

	    servlet.doPost(req, resp);

	    verify(rd).forward(req, resp);
	}
}