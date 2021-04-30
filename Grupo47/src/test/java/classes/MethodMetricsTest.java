package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodMetricsTest {

	@BeforeEach
	void setUp() throws Exception {
		MethodMetrics mm = new MethodMetrics(new MethodIdentity("", "", "", 0), new Metrics(0, 0, 0, 0, 0));
		MethodMetrics mm1 = new MethodMetrics(new MethodIdentity("", "", "", 0), null);
		assertNotNull(mm);
		assertNotNull(mm1);
		
	}

	@Test
	void test() {
		MethodMetrics mm = new MethodMetrics(new MethodIdentity("", "", "", 0), new Metrics(0, 0, 0, 0, 0));
		assertNotNull(mm);

		mm.setClasse("classname");
		assertEquals("classname",mm.getClasse());

		mm.setNOM_class(0);
		assertEquals(0,mm.getNOM_class());

		mm.setLOC_method(0);
		assertEquals(0,mm.getLOC_method());

		mm.setPacote("pacote");
		assertEquals("pacote",mm.getPacote());

		mm.setNome_metodo("methodname");
		assertEquals("methodname",mm.getNome_metodo());

		mm.setLOC_class(0);
		assertEquals(0,mm.getLOC_class());

		mm.setWMC_class(0);
		assertEquals(0, mm.getWMC_class());

		mm.setCYCLO_method(0);
		assertEquals(0, mm.getCYCLO_method());

		mm.setMethod_ID(0);
		assertEquals(0, mm.getMethod_ID());
	}

	@Test
	void testToString() {
		MethodMetrics mm = new MethodMetrics(new MethodIdentity("", "", "", 0), new Metrics(0, 0, 0, 0, 0));
		assertNotNull(mm);
		String s = mm.getPacote() +"  ||  "+mm.getClasse()+"  ||  "+mm.getNome_metodo()+"  ||  LOC_class: "+mm.getLOC_class()+"  ||  LOC_method: "+mm.getLOC_method() + "  ||  NOM_class: "+mm.getNOM_class()+"  ||  WMC: "+mm.getWMC_class()+"  ||  CYCLO: "+mm.getCYCLO_method();
		assertEquals(mm.toString(),s);

	}
}


