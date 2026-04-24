package com.fabio.test;

import com.fabio.config.RestApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RestApplicationTest {
	
	@Test
	void testRestApplication() {
	    RestApplication app = new RestApplication();
	    assertNotNull(app);
	}
}