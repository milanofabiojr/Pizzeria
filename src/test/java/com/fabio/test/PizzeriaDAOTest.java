package com.fabio.test;

import com.fabio.dao.PizzeriaDAO;
import com.fabio.model.*;
import com.fabio.util.JPAUtil;
import jakarta.persistence.*;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PizzeriaDAOTest {

	private PizzeriaDAO dao;

	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction tx;

	private MockedStatic<JPAUtil> mocked;

	@BeforeEach
	void setUp() {
		dao = new PizzeriaDAO();

		emf = mock(EntityManagerFactory.class);
		em = mock(EntityManager.class);
		tx = mock(EntityTransaction.class);

		mocked = mockStatic(JPAUtil.class);
		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);

		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(tx);
	}

	@AfterEach
	void tearDown() {
		mocked.close();
	}

	@Test
	void testLoginSuccess() {
		Utente u = new Utente("user", "pass");

		TypedQuery query = mock(TypedQuery.class);

		when(em.createQuery(anyString(), eq(Utente.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(u);

		Utente result = dao.login("user", "pass");

		assertNotNull(result);
		assertEquals("user", result.getUsername());
	}

	@Test
	void testLoginFail() {
		TypedQuery query = mock(TypedQuery.class);

		when(em.createQuery(anyString(), eq(Utente.class))).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());

		Utente result = dao.login("x", "y");

		assertNull(result);
	}

	@Test
	void testSavePizza() {
		Pizza p = new Pizza();
		dao.save(p);

		verify(em).persist(p);
		verify(tx).begin();
		verify(tx).commit();
	}

	@Test
	void testDeletePizza() {
		Pizza p = new Pizza();
		when(em.find(Pizza.class, 1)).thenReturn(p);

		dao.delete(1);

		verify(em).remove(p);
	}

	@Test
	void testFindPizza() {

		Pizza p = new Pizza();

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.find(Pizza.class, 1)).thenReturn(p);

		Pizza result = dao.findPizza(1);

		assertNotNull(result);
	}

	@Test
	void testFindAllImpasti() {

		TypedQuery query = mock(TypedQuery.class);

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.createQuery(anyString(), eq(Impasto.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of());

		List<Impasto> result = dao.findAllImpasti();

		assertNotNull(result);
	}

	@Test
	void testUpdatePizza() {

		Pizza p = new Pizza();

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);
		when(em.getTransaction()).thenReturn(tx);

		dao.updatePizza(p);

		verify(em).merge(p);
		verify(tx).begin();
		verify(tx).commit();
	}

	@Test
	void testFindAllPizze() {

		TypedQuery query = mock(TypedQuery.class);

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.createQuery(anyString(), eq(Pizza.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of());

		List<Pizza> result = dao.findAllPizze();

		assertNotNull(result);
	}

	@Test
	void testFindAllPizze_notEmpty() {
		List<Pizza> pizze = dao.findAllPizze();

		assertNotNull(pizze);
	}



	@Test
	void testDeletePizzaNull() {

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.find(Pizza.class, 1)).thenReturn(null);

		dao.deletePizza(1);

		verify(em, never()).remove(any());
	}

	@Test
	void testFindAllIngredienti() {

		TypedQuery query = mock(TypedQuery.class);

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.createQuery(anyString(), eq(Ingrediente.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of());

		List<Ingrediente> result = dao.findAllIngredienti();

		assertNotNull(result);
	}

	@Test
	void testGetAllUtenti() {

		TypedQuery query = mock(TypedQuery.class);

		mocked.when(JPAUtil::getEntityManagerFactory).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.createQuery(anyString(), eq(Utente.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of());

		List<Utente> result = dao.getAllUtenti();

		assertNotNull(result);
	}
}