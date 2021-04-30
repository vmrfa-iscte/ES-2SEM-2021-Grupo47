package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetricsTest {

	@BeforeEach
	void setUp() throws Exception {
		Metrics m = new Metrics(0,0,0,0,0);
		assertNotNull(m);
	}

	@Test
	void testGettersAndSetters() {
		Metrics m = new Metrics(0,0,0,0,0);
		assertNotNull(m);
		
		m.setNOM_class(0);
		assertEquals(0,m.getNOM_class());
		
		m.setLOC_method(0);
		assertEquals(0,m.getLOC_method());
		
		m.setLOC_class(0);
		assertEquals(0,m.getLOC_class());
		
		m.setWMC_class(0);
		assertEquals(0,m.getWMC_class());
		
		m.setCYCLO_method(0);
		assertEquals(0,m.getCYCLO_method());
	}

}
