package com.fabio.service;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Impasto;
import com.fabio.model.Ingrediente;
import com.fabio.model.Utente;
import com.fabio.model.Pizza;
//rest service
@Path("/service")
public class Service {
	
	private PizzeriaDAO dao;
	
	public Service() {
	    this.dao = new PizzeriaDAO();
	}

    public Service(PizzeriaDAO dao) {
        this.dao = dao;
    }
	
	@GET
	@Path("/utenti")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Utente> getUtenti_JSON(){
		List<Utente> utenti = dao.getAllUtenti();
		return utenti;
	}
	
	@GET
	@Path("/impasti")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Impasto> getImpasti_JSON(){
		List<Impasto> impasti = dao.findAllImpasti();
		return impasti;
	}
	
	@GET
	@Path("/ingredienti")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Ingrediente> getIngredienti_JSON(){
		List<Ingrediente> ingredienti = dao.findAllIngredienti();
		return ingredienti;
	}
	
	@GET
	@Path("/pizze")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pizza> getPizze_JSON(){
		List<Pizza> pizze = dao.findAllPizze();
		return pizze;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON })
    public Pizza addPizza(Pizza p) {
        return dao.addPizza(p);
    }
	
	@PUT
	@Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response updatePizza(@PathParam("id") int id, Pizza nuovap) {
		Pizza p = dao.findPizza(id);
		if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        p.setNome(nuovap.getNome());
        p.setImpasto(nuovap.getImpasto());
        p.setIngredienti(nuovap.getIngredienti());
        dao.updatePizza(p);
        return Response.ok().build();
    }
	
	@DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public void deletePizza(@PathParam("id") int id) {
       dao.deletePizza(id);
    }
}
