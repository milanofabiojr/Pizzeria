package com.fabio.test;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

import com.fabio.main.LoginServlet;
import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Utente;

class LoginServletTest {

	@Test
	void shouldRedirectToDashboardWhenLoginSuccess() throws Exception {
		// mock
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);

		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet();

		// inject DAO
		java.lang.reflect.Field field = LoginServlet.class.getDeclaredField("dao");
		field.setAccessible(true);
		field.set(servlet, dao);

		when(req.getParameter("username")).thenReturn("mario");
		when(req.getParameter("password")).thenReturn("mario123");
		when(req.getSession(true)).thenReturn(session);

		when(dao.login("mario", "mario123")).thenReturn(new Utente("mario", "mario123"));

		servlet.doPost(req, resp);

		verify(resp).sendRedirect("dashboard");
		verify(session).setAttribute(eq("user"), any());
	}

	@Test
	void shouldForwardToLoginWhenLoginFails() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		PizzeriaDAO dao = mock(PizzeriaDAO.class);
		LoginServlet servlet = new LoginServlet();

		java.lang.reflect.Field field = LoginServlet.class.getDeclaredField("dao");
		field.setAccessible(true);
		field.set(servlet, dao);

		when(req.getParameter("username")).thenReturn("wrong");
		when(req.getParameter("password")).thenReturn("wrong");
		when(req.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

		when(dao.login("wrong", "wrong")).thenReturn(null);

		servlet.doPost(req, resp);

		verify(req).setAttribute(eq("error"), any());
		verify(dispatcher).forward(req, resp);
	}

	@Test
	void shouldForwardToLoginWhenParamsMissing() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		when(request.getParameter("username")).thenReturn(null);
		when(request.getParameter("password")).thenReturn(null);
		when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);

		LoginServlet servlet = new LoginServlet();
		servlet.doPost(request, response);

		verify(dispatcher).forward(request, response);
	}
}