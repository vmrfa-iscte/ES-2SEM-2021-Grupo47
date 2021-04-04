package G47.Grupo47;

import java.util.ArrayList;

import junit.framework.TestCase;

public class StatisticsTest extends TestCase {
	
	public void testConstructor() {
		ArrayList<Metrics> list = new ArrayList<>();
		Statistics stat = new Statistics(list);
		assertNotNull(list);
		assertNotNull(stat);
	}

	public void testcountNumberOfMethods() {
		// empty list case
		ArrayList<Metrics> list = new ArrayList<>();
		Statistics stat = new Statistics(list);
		assertEquals(stat.countNumberOfMethods(),0);
		assertNotNull(list);
		assertNotNull(stat);
		// empty list case

		
		//filled list case
		ArrayList<Metrics> listB = new ArrayList<>();
		Metrics a = new Metrics("Methodtest","ClassTest","PackageTeste",3);
		assertNotNull(a);
		Metrics b = new Metrics("Methodtest2","ClassTest2","PackageTeste2",4);
		assertNotNull(b);
		Metrics c = new Metrics("Methodtest3","ClassTest3","PackageTeste3",5);
		assertNotNull(c);
		listB.add(a);
		listB.add(b);
		listB.add(c);
		assertNotNull(listB);
		Statistics statB = new Statistics(listB);
		assertNotNull(statB);	
		assertEquals(statB.countNumberOfMethods(),3);
	}
	
	public void testCountPackages() {
		// empty list case
		ArrayList<Metrics> list = new ArrayList<>();
		Statistics stat = new Statistics(list);
		assertEquals(stat.countPackages(),0);
		assertNotNull(list);
		assertNotNull(stat);
		// empty list case

		
		//filled list case
		ArrayList<Metrics> listB = new ArrayList<>();
		Metrics a = new Metrics("Methodtest","ClassTest","PackageTeste",3);
		assertNotNull(a);
		Metrics b = new Metrics("Methodtest2","ClassTest2","PackageTeste2",4);
		assertNotNull(b);
		Metrics c = new Metrics("Methodtest3","ClassTest3","PackageTeste3",5);
		assertNotNull(c);
		listB.add(a);
		listB.add(b);
		listB.add(c);
		assertNotNull(listB);
		Statistics statB = new Statistics(listB);
		assertNotNull(statB);	
		assertEquals(statB.countPackages(),3);
		
	}
	
	public void testCountLinesOfCode() {
		// empty list case
		ArrayList<Metrics> list = new ArrayList<>();
		Statistics stat = new Statistics(list);
		assertEquals(stat.countLinesOfCode(),0);
		assertNotNull(list);
		assertNotNull(stat);
		// empty list case

		
		//filled list case
		ArrayList<Metrics> listB = new ArrayList<>();
		Metrics a = new Metrics("Methodtest","ClassTest","PackageTeste",3);
		assertNotNull(a);
		Metrics b = new Metrics("Methodtest2","ClassTest2","PackageTeste2",4);
		assertNotNull(b);
		Metrics c = new Metrics("Methodtest3","ClassTest3","PackageTeste3",5);
		assertNotNull(c);
		listB.add(a);
		listB.add(b);
		listB.add(c);
		assertNotNull(listB);
		Statistics statB = new Statistics(listB);
		assertNotNull(statB);	
		int linesOfCode = a.getLOC_method() + b.getLOC_method() + c.getLOC_method();
		assertNotNull(linesOfCode);
		assertEquals(statB.countLinesOfCode(),linesOfCode);
		
	}
	
	public void testCountClasses() {
		// empty list case
		ArrayList<Metrics> list = new ArrayList<>();
		Statistics stat = new Statistics(list);
		assertEquals(stat.countClasses(),0);
		assertNotNull(list);
		assertNotNull(stat);
		// empty list case

		
		//filled list case
		ArrayList<Metrics> listB = new ArrayList<>();
		Metrics a = new Metrics("Methodtest","ClassTest","PackageTeste",3);
		assertNotNull(a);
		Metrics b = new Metrics("Methodtest2","ClassTest2","PackageTeste2",4);
		assertNotNull(b);
		Metrics c = new Metrics("Methodtest3","ClassTest3","PackageTeste3",5);
		assertNotNull(c);
		listB.add(a);
		listB.add(b);
		listB.add(c);
		assertNotNull(listB);
		Statistics statB = new Statistics(listB);
		assertNotNull(statB);	
		assertEquals(statB.countClasses(),3);
		
	}

}
