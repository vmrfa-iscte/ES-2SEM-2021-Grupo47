//package G47.Grupo47;
//
//import java.util.ArrayList;
//
//import classes.MethodIdentity;
//import classes.MethodMetrics;
//import classes.Metrics;
//import classes.Statistics;
//import junit.framework.TestCase;
//
//public class StatisticsTest extends TestCase {
//	
//
//	
//	public void testConstructor() {
//		ArrayList<MethodMetrics> list = new ArrayList<>();
//		Statistics stat = new Statistics(list);
//		assertNotNull(list);
//		assertNotNull(stat);
//	}
//
//	public void testcountNumberOfMethods() {
//		// empty list case
//		ArrayList<MethodMetrics> list = new ArrayList<>();
//		Statistics stat = new Statistics(list);
//		assertEquals(stat.countNumberOfMethods(),0);
//		assertNotNull(list);
//		assertNotNull(stat);
//		// empty list case
//
//		
//		//filled list case
//		ArrayList<MethodMetrics> listB = new ArrayList<>();
//		MethodMetrics a = new MethodMetrics("Methodtest","ClassTest","PackageTeste",3);
//		assertNotNull(a);
//		MethodMetrics b = new MethodMetrics("Methodtest2","ClassTest2","PackageTeste2",4);
//		assertNotNull(b);
//		MethodMetrics c = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		MethodMetrics c2 = new MethodMetrics("Methodtest4","ClassTest4","PackageTeste3",12);
//		assertNotNull(c);
//		listB.add(a);
//		listB.add(b);
//		listB.add(c);
//		assertNotNull(listB);
//		Statistics statB = new Statistics(listB);
//		assertNotNull(statB);	
//		assertEquals(statB.countNumberOfMethods(),3);
//		
//	
//	}
//	
//	public void testCountPackages() {
//		// empty list case
//		ArrayList<MethodMetrics> list = new ArrayList<>();
//		Statistics stat = new Statistics(list);
//		assertEquals(stat.countPackages(),0);
//		assertNotNull(list);
//		assertNotNull(stat);
//		// empty list case
//
//		
//		//filled list case
//		ArrayList<MethodMetrics> listB = new ArrayList<>();
//		MethodMetrics a = new MethodMetrics("Methodtest","ClassTest","PackageTeste",3);
//		assertNotNull(a);
//		MethodMetrics b = new MethodMetrics("Methodtest2","ClassTest2","PackageTeste2",4);
//		assertNotNull(b);
//		MethodMetrics c = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		MethodMetrics d = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		listB.add(a);
//		listB.add(b);
//		listB.add(c);
//		listB.add(d);
//		assertNotNull(listB);
//		Statistics statB = new Statistics(listB);
//		assertNotNull(statB);	
//		assertEquals(statB.countPackages(),3);
//		
//	}
//	
//	public void testCountLinesOfCode() {
//		// empty list case
//		ArrayList<MethodMetrics> list = new ArrayList<>();
//		Statistics stat = new Statistics(list);
//		assertEquals(stat.countLinesOfCode(),0);
//		assertNotNull(list);
//		assertNotNull(stat);
//		// empty list case
//
//		
//		//filled list case
//		ArrayList<MethodMetrics> listB = new ArrayList<>();
//		MethodMetrics a = new MethodMetrics("Methodtest","ClassTest","PackageTeste",3);
//		assertNotNull(a);
//		MethodMetrics b = new MethodMetrics("Methodtest2","ClassTest2","PackageTeste2",4);
//		assertNotNull(b);
//		MethodMetrics c = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		listB.add(a);
//		listB.add(b);
//		listB.add(c);
//		assertNotNull(listB);
//		Statistics statB = new Statistics(listB);
//		assertNotNull(statB);	
//		int linesOfCode = a.getLOC_method() + b.getLOC_method() + c.getLOC_method();
//		assertNotNull(linesOfCode);
//		assertEquals(statB.countLinesOfCode(),linesOfCode);
//		
//	}
//	
//	public void testCountClasses() {
//		// empty list case
//		ArrayList<MethodMetrics> list = new ArrayList<>();
//		Statistics stat = new Statistics(list);
//		assertEquals(stat.countClasses(),0);
//		assertNotNull(list);
//		assertNotNull(stat);
//		// empty list case
//
//		
//		//filled list case
//		ArrayList<MethodMetrics> listB = new ArrayList<>();
//		MethodMetrics a = new MethodMetrics("Methodtest","ClassTest","PackageTeste",3);
//		assertNotNull(a);
//		MethodMetrics twoAtributte = new MethodMetrics("Test2AtributteConstructor","Test2AtributteConstructor");
//		MethodMetrics b = new MethodMetrics("Methodtest2","ClassTest2","PackageTeste2",4);
//		assertNotNull(b);
//		MethodMetrics c = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(c);
//		MethodMetrics d = new MethodMetrics("Methodtest3","ClassTest3","PackageTeste3",5);
//		assertNotNull(d);
//		listB.add(a);
//		listB.add(b);
//		listB.add(c);
//		listB.add(d);
//		assertNotNull(listB);
//		Statistics statB = new Statistics(listB);
//		assertNotNull(statB);	
//		assertEquals(statB.countClasses(),3);
//		
//	}
//
//}
