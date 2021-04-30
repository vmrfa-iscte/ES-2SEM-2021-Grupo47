package G47.Grupo47;

import classes.HasCodeSmell;
import classes.MethodIdentity;
import classes.MethodMetrics;
import classes.Metrics;
import junit.framework.TestCase;

public class HasCodeSmellTest extends TestCase {
	
	public void testConstructor() {
		HasCodeSmell hcs = new HasCodeSmell("hasCodeSmell","positive",(new MethodMetrics(new MethodIdentity("", "", "", 0), new Metrics(0,0,0,0,0))),true);
		assertNotNull(hcs);
		
	}
		

		
		
}


