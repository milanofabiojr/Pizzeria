package com.fabio.test;


import com.fabio.util.JPAUtil;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class JPAUtilTest {
	
	@Test
	void testEntityManagerFactory() {
	    assertNotNull(JPAUtil.getEntityManagerFactory());
	}
	
}