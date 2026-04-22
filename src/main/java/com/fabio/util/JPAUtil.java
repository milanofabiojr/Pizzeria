package com.fabio.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	
	private static final String PERSISTENCE_UNIT_NAME = "pizzeriaPU";
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
    	emf =  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return emf;
    }
}