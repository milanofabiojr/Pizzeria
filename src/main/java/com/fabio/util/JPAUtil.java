package com.fabio.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static final String PERSISTENCE_UNIT_NAME = "pizzeriaPU";
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
    	emf =  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return emf;
    }
}