package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RuleTest {

	@BeforeEach
	void setUp() throws Exception {
		Rule r = new Rule("method1","sinal1","limit1","operator","method2","signal","limit2","operator2","method3","signal3","limit3");
		assertNotNull(r);
	}

	@Test
	void testGettersAndSetters() {
		Rule r = new Rule("method1","sinal1","limit1","operator","method2","signal","limit2","operator2","method3","signal3","limit3");
		assertNotNull(r);
		
		r.setLimit1("limit1");
		assertEquals("limit1",r.getLimit1());
		
		r.setLimit2("limit2");
		assertEquals("limit2",r.getLimit2());
		
		r.setLimit3("limit3");
		assertEquals("limit3",r.getLimit3());
		
		r.setOperator("operator1");
		assertEquals("operator1",r.getOperator());
		
		r.setOperator2("operator2");
		assertEquals("operator2",r.getOperator2());
		
		r.setMethod1("method1");
		assertEquals("method1", r.getMethod1());
		
		r.setMethod2("method2");
		assertEquals("method2", r.getMethod2());
		
		r.setMethod3("method3");
		assertEquals("method3", r.getMethod3());
		
		r.setSinal1("sinal1");
		assertEquals("sinal1",r.getSinal1());
		
		r.setSinal2("sinal2");
		assertEquals("sinal2",r.getSinal2());
		
		r.setSinal3("sinal3");
		assertEquals("sinal3",r.getSinal3());
		
	}
	
	@Test
	void testToString() {
		Rule r = new Rule("method1","sinal1","limit1","operator","method2","signal","limit2","operator2","method3","signal3","limit3");
		String s = r.getMethod1() + " " + r.getSinal1() + " " + r.getLimit1() + " " + r.getOperator() + " " + r.getMethod2() + " " + r.getSinal2() + " " + r.getLimit2() + " " + r.getOperator2() + " " + r.getMethod3() + " " + r.getSinal3() + " " + r.getLimit3();
		System.out.println(s);
		assertEquals(r.toString(), s);
				
	}
	
	

}
