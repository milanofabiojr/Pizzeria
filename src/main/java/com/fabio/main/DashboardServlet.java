package com.fabio.main;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private PizzeriaDAO dao = new PizzeriaDAO();

    // MOSTRA DASHBOARD
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // BLOCCO ACCESSO SE NON LOGGATO
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Utente u = (Utente) session.getAttribute("user");

        // CARICO DATI PER LA JSP
        List<Impasto> impasti = dao.findAllImpasti();
        List<Ingrediente> ingredienti = dao.findAllIngredienti();
        List<Pizza> pizze = dao.findAllbyUtenteId(u.getId());

        req.setAttribute("impasti", impasti);
        req.setAttribute("ingredienti", ingredienti);
        req.setAttribute("pizze", pizze);

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

    // CREA PIZZA E DELETE PIZZA
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // SICUREZZA SESSIONE
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Utente u = (Utente) session.getAttribute("user");

        // ELIMINAZIONE PIZZA
        if (req.getParameter("deleteId") != null) {

            int idPizza = Integer.valueOf(req.getParameter("deleteId"));
            dao.delete(idPizza);

            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        // CREAZIONE PIZZA
        String nomePizza = req.getParameter("nomePizza");
        String idImpasto = req.getParameter("impasto");
        String[] idIngredienti = req.getParameterValues("ingredienti");

        // VALIDAZIONE BASE
        if (nomePizza == null || idImpasto == null || nomePizza.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        int id_Impasto = Integer.valueOf(idImpasto);
        Impasto impasto = dao.findImpasto(id_Impasto);

        Pizza pizza = new Pizza();
        pizza.setNome(nomePizza);
        pizza.setImpasto(impasto);
        pizza.setUtente(u);

        List<Ingrediente> listaIngredienti = new ArrayList<>();
        if (idIngredienti != null) {
            for (String id : idIngredienti) {
                int ingrId = Integer.valueOf(id);
                Ingrediente ingr = dao.findIngrediente(ingrId);
                listaIngredienti.add(ingr);
            }
        }

        pizza.setIngredienti(listaIngredienti);

        // SALVATAGGIO DB
        dao.save(pizza);

        // REFRESH DASHBOARD
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}