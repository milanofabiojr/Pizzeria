package com.fabio.soap;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Pizza;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class PizzaSOAPService {

    private PizzeriaDAO dao;
    
    public PizzaSOAPService() {
        this.dao = new PizzeriaDAO();
    }

    public PizzaSOAPService(PizzeriaDAO dao) {
        this.dao = dao;
    }

    // CREA PIZZA (SOAP)
    @WebMethod
    public int creaPizza(Pizza pizza) {
        dao.save(pizza);   
        return pizza.getId();
    }

    // CANCELLA PIZZA (SOAP)
    @WebMethod
    public void cancellaPizza(int idPizza) {
        dao.delete(idPizza);
    }
}
