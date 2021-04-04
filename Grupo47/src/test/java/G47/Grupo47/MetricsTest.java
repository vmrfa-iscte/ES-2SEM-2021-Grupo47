package G47.Grupo47;

import junit.framework.TestCase;

public class MetricsTest extends TestCase {
	
	private Metrics n;


	public void testConstructor() {
		n = new Metrics("x","y");
		assertNotNull(n);
		assertEquals("x",n.getClasse());
		assertEquals("y",n.getPacote());
	}
	
	public void testConstructor2() {
		n= new Metrics("x","y","z",4);
		assertNotNull(n);
		assertEquals("x",n.getNome_metodo());
		assertEquals("y",n.getClasse());
		assertEquals("z",n.getPacote());
		assertEquals(4,n.getLOC_method());
		
		
	}

}
