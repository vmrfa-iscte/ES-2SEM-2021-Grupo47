package G47.Grupo47;

import junit.framework.TestCase;

public class HasCodeSmellTest extends TestCase {
	
	public void testGetters() {
		HasCodeSmell hcs = new HasCodeSmell("Alin", "Guy","Fernandinho");
		assertEquals("Alin",hcs.getName());
		assertEquals("Guy",hcs.getHasCodeSmell());
		assertEquals("Fernandinho",hcs.getMethod_ID());
	}

	
	
	
}
