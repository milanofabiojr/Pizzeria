package com.fabio.dao;

import com.fabio.model.Impasto;
import com.fabio.model.Ingrediente;
import com.fabio.model.Pizza;
import com.fabio.model.Utente;
import com.fabio.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class PizzeriaDAO {

    public Utente login(String username, String password)  {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
        	Utente u;
        	 u = em.createQuery(
                "SELECT u FROM Utente u WHERE u.username=:u AND u.password=:p",
                Utente.class)
                .setParameter("u", username)
                .setParameter("p", password)
                .getSingleResult();
        	 return u;
        } catch (NoResultException e) {
        	return null;
        }
    }
    
    public static List<Utente> getAllUtenti(){
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    public List<Pizza> findAllbyUtenteId(int utenteId) {
    	List<Pizza> pizze;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Pizza> q = em.createQuery("SELECT DISTINCT p FROM Pizza p " +
                "LEFT JOIN FETCH p.ingredienti " +
                "JOIN FETCH p.impasto " +
                "WHERE p.utente.id = :id",Pizza.class);
        q.setParameter("id", utenteId);
        pizze = q.getResultList();
        em.close();
        return pizze;
    }

    public void save(Pizza pizza) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(pizza);
        em.getTransaction().commit();
        em.close();
    }
    
    public static Pizza addPizza(Pizza pizza) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(pizza);
        em.getTransaction().commit();
        em.close();
        return pizza;
    }

    public void update(Pizza p) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }
    
    public static Pizza updatePizza(Pizza p) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
        return p;
    }

    public void delete(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Pizza p = em.find(Pizza.class, id);
        if (p != null) {
        em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public static void deletePizza(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Pizza p = em.find(Pizza.class, id);
        if (p != null) {
        em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public static Pizza findPizza(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Pizza.class, id);
    }

    public static List<Impasto> findAllImpasti() {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT i FROM Impasto i", Impasto.class).getResultList();
    }
    
    public static List<Pizza> findAllPizze() {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
    }

    public static List<Ingrediente> findAllIngredienti() {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class).getResultList();
    }

    public Impasto findImpasto(int id) {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Impasto.class, id);
    }

    public Ingrediente findIngrediente(int id) {
    	EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        return em.find(Ingrediente.class, id);
    }
}