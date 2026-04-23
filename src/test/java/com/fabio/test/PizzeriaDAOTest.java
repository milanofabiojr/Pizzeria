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
}