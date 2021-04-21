//package G47.Grupo47;
//
//import java.util.ArrayList;
//
//import junit.framework.TestCase;
//
//public class CodeSmellsDetectorTest extends TestCase {
//	
// 
//	public void testConstructor() {
//		ArrayList<Metrics> metrics = new ArrayList<>();
//		CodeSmellsDetector csd = new CodeSmellsDetector(1, 2, "guy", metrics);
//		assertNotNull(metrics);
//		assertNotNull(csd);
//				
//	}
//	
//	public void testDetectGodClass() {
//		ArrayList<Metrics> metrics = new ArrayList<>();
//		CodeSmellsDetector csd = new CodeSmellsDetector(1, 2, "guy", metrics);
////		assertEquals(csd);
//		assertNotNull(metrics);
//		assertNotNull(csd);
//		
//		ArrayList<Metrics> metricsB = new ArrayList<>();
//		Metrics a = new Metrics("Methodtest","ClassTest","PackageTeste",3);
//		assertNotNull(a);
//		Metrics b = new Metrics("Methodtest2","ClassTest2","PackageTeste2",4);
//		assertNotNull(b);
//		Metrics c = new Metrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		Metrics c2 = new Metrics("Methodtest4","ClassTest4","PackageTeste3",12);
//		assertNotNull(c);
//		metricsB.add(a);
//		metricsB.add(b);
//		metricsB.add(c);
//		assertNotNull(metricsB);
//		
//		Rules r1 = new Rules("method1","limit1","operator1","method2","limit2");
//		Rules r2 = new Rules("method1","limit1","operator1","method2","limit2");
//
//		CodeSmellsDetector csdB = new CodeSmellsDetector(1,2,"nando", metricsB);
//		assertNotNull(csdB);
////		assertEquals(csdB.countNumberOfMethods(),2);
//		
//		
//	}
//
//}
