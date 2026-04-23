package com.fabio.main;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private PizzeriaDAO dao;

    public LoginServlet() {
        this.dao = new PizzeriaDAO();
    }

    public LoginServlet(PizzeriaDAO dao) {
        this.dao = dao;
    }

    // MOSTRA login.jsp
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    // GESTIONE LOGIN
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Utente utente = dao.login(username, password);

        // LOGIN FALLITO
        if (utente != null) {
        	// LOGIN OK  CREO SESSIONE
            HttpSession session = req.getSession(true);
            session.setAttribute("user", utente);

            // REDIRECT ALLA DASHBOARD
            resp.sendRedirect("dashboard");
        } else {
        	req.setAttribute("error", "Credenziali errate!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}