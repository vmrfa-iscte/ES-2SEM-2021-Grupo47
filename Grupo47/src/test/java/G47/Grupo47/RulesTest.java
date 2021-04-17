package G47.Grupo47;

import junit.framework.TestCase;

public class RulesTest extends TestCase {

	
	public void testConstructor() {
		Rules r = new Rules("method1","limit1","operator","method2","limit2"); 
		assertNotNull(r);
	}
	public void testSetters() {
		Rules r = new Rules("method1","limit1","operator","method2","limit2"); 
		r.setLimit1("limit1");
		assertEquals("limit1",r.getLimit1());
		
		
		
		r.setLimit2("limit2");
		assertEquals("limit2",r.getLimit2());
		
		r.setOperator("operator");
		assertEquals("operator",r.getOperator());
		
		r.setMethod2("method2");
		assertEquals("method2",r.getMethod2());
		
		r.setMethod1("method1");
		assertEquals("method1",r.getMethod1());
		
	}
	
	public void testToString() {
		
		    Rules r1 = new Rules("method1","limit1","operator","method2","limit2");
		    String s=r1.getMethod1() + " > " + r1.getLimit1()+ " "+ r1.getOperator()+ " " + r1.getMethod2()+ " > " + r1.getLimit2();
		   System.out.println(s);
		    assertEquals(r1.toString(), s);
		
	}
}
