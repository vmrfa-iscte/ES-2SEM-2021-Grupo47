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
	
	public void testConstructor3() {
		n= new Metrics("x","y","z",1,2,3,4,5);
		assertNotNull(n);
		assertEquals("x",n.getNome_metodo());
		assertEquals("y",n.getClasse());
		assertEquals("z",n.getPacote());
		assertEquals(1,n.getLOC_method());	
		assertEquals(2,n.getLOC_class());
		assertEquals(3,n.getCYCLO_method());
		assertEquals(4,n.getNOM_class());	
		assertEquals(5,n.getWMC_class());	
	}
	
	
	
	

}
