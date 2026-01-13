package com.fabio.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.Impasto;
import com.fabio.model.Ingrediente;
import com.fabio.model.Utente;
import com.fabio.model.Pizza;
//rest service
@Path("/service")
public class Service {
	
	@GET
	@Path("/utenti")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Utente> getUtenti_JSON(){
		List<Utente> utenti = PizzeriaDAO.getAllUtenti();
		return utenti;
	}
	
	@GET
	@Path("/impasti")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Impasto> getImpasti_JSON(){
		List<Impasto> impasti = PizzeriaDAO.findAllImpasti();
		return impasti;
	}
	
	@GET
	@Path("/ingredienti")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Ingrediente> getIngredienti_JSON(){
		List<Ingrediente> ingredienti = PizzeriaDAO.findAllIngredienti();
		return ingredienti;
	}
	
	@GET
	@Path("/pizze")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Pizza> getPizze_JSON(){
		List<Pizza> pizze = PizzeriaDAO.findAllPizze();
		return pizze;
	}
	
	@POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Pizza addPizza(Pizza p) {
        return PizzeriaDAO.addPizza(p);
    }
	
	@PUT
	@Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updatePizza(@PathParam("id") int id, Pizza nuovap) {
		Pizza p = PizzeriaDAO.findPizza(id);
		if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        p.setNome(nuovap.getNome());
        p.setImpasto(nuovap.getImpasto());
        p.setIngredienti(nuovap.getIngredienti());
        PizzeriaDAO.updatePizza(p);
        return Response.ok().build();
    }
	
	@DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void deletePizza(@PathParam("id") int id) {
       PizzeriaDAO.deletePizza(id);
    }
}
