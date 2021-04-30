package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HasCodeSmellTest {
	
	private boolean v;
	private boolean f;
	private MethodIdentity mi = new MethodIdentity("","","",0);
	private MethodIdentity mi2 = new MethodIdentity("nomemetodo","nomeclasse3","package1",0);
	private MethodIdentity mi3 = new MethodIdentity("","nomeclasse","package3",0);
	private MethodIdentity mi4 = new MethodIdentity("nomemetodo","nomeclasse3","package1",0);
	private MethodIdentity mi5 = new MethodIdentity("","classname","package2",0);
	private MethodIdentity mi6 = new MethodIdentity("nomemetodo","classname","package1",0);
	private MethodIdentity mi7 = new MethodIdentity("nomemetodo1","nomeclasse1","package3",0);
	private MethodIdentity mi8 = new MethodIdentity("nomemetodo1","nomeclasse2","package4",0);
	private MethodIdentity mi9 = new MethodIdentity("nomemetodo2","nomeclasse2","package4",0);
	
	private Metrics m = new Metrics(0,0,0,0,0);
	private MethodMetrics mm = new MethodMetrics(mi,m);
	private MethodMetrics mm2 = new MethodMetrics(mi2,m);
	private MethodMetrics mm3 = new MethodMetrics(mi3,m);
	private MethodMetrics mm4 = new MethodMetrics(mi4,m);
	private MethodMetrics mm5 = new MethodMetrics(mi5,m);
	private MethodMetrics mm6 = new MethodMetrics(mi6,m);
	private MethodMetrics mm7 = new MethodMetrics(mi7,m);
	private MethodMetrics mm8 = new MethodMetrics(mi8,m);
	private MethodMetrics mm9 = new MethodMetrics(mi9,m);
	
	private HasCodeSmell hcs;
	private HasCodeSmell hcs2;
	private HasCodeSmell hcs3;
	private HasCodeSmell hcs4;
	private HasCodeSmell hcs5;
	private HasCodeSmell hcs6;
	private HasCodeSmell hcs7;
	private HasCodeSmell hcs8;
	private HasCodeSmell hcs9;

	@BeforeEach
	void setUp() throws Exception {
		hcs = new HasCodeSmell("hasCodeSmell","positive",mm,false);
		hcs2 = new HasCodeSmell("hcs2","positive",mm2,true);
		hcs3 = new HasCodeSmell("hcs3","positive",mm3,true);
		hcs4 = new HasCodeSmell("hcs4", "positive", mm4, true);
		hcs5 = new HasCodeSmell("hcs5","positive",mm5,true);
		hcs6 = new HasCodeSmell("hcs6", "positive", mm6, true);
		hcs7 = new HasCodeSmell("hcs7", "positive", mm7, true);
		hcs8 = new HasCodeSmell("hcs8", "positive", mm8, true);
		hcs9 = new HasCodeSmell("hcs9", "positive", mm9, true);
		
		assertNotNull(hcs);
		assertNotNull(hcs2);
		assertNotNull(hcs3);
		assertNotNull(hcs4);
		assertNotNull(hcs5);
		assertNotNull(hcs6);
		assertNotNull(hcs7);
		assertNotNull(hcs8);
		assertNotNull(hcs9);
		v = true;
		f = false;
		
	}

	@Test
	void testGettersAndSetters() {
		
		assertNotNull(mi.getMethod_id());
		assertNotNull(mi.getClassName());
		assertNotNull(mi.getPackageName());
		assertNotNull(mi.getMethodName());
		
		hcs.setMethod(v);
		boolean ismethod = hcs.isMethod();
		assertEquals(v,ismethod);
		
//		String methodid = "methodid";
//		assertEquals(methodid,hcs.getMethod_ID());
//		
//		String methodname = "methodname";
//		assertEquals(methodname,hcs.getMethodName());
//		
//		String hcs1 = "hasCodeSmell";
//		assertEquals(hcs1,hcs.getHasCodeSmell());
//		
//		String classname = "classname";
//		assertEquals(classname,hcs.getClassName());
//		
//		String packagename = "packagename";
//		assertEquals(packagename,hcs.getPackageName());
	
		hcs.setQuality("quality");
		assertEquals("quality",hcs.getQuality());
		
	}
	
	@Test
	void testIsMethod() {
		Boolean ismethod = hcs.isMethod();
		assertEquals(false,ismethod);
		
	}
	
	@Test
	void testIsEqual() {
		assertTrue(hcs2.isEqual(hcs4)); //VVV
		assertFalse(hcs3.isEqual(hcs5)); //VFF
		assertFalse(hcs4.isEqual(hcs5)); //VFV
		assertFalse(hcs2.isEqual(hcs3)); //FFF
		assertFalse(hcs6.isEqual(hcs7)); //FFV
		assertFalse(hcs5.isEqual(hcs6)); //FVF
		assertFalse(hcs7.isEqual(hcs8)); //VVF
//		assertFalse(hcs2.isEqual(hcs6)); //VFV
		assertFalse(hcs8.isEqual(hcs9));//FVV
	
	}
	
	@Test
	void testToString() {
		HasCodeSmell hcs = new HasCodeSmell("hasCodeSmell","positive",mm,true);
		String s =  "HasCodeSmell [method_name=" + hcs.getMethodName() + ", hasCodeSmell=" + hcs.getHasCodeSmell() + ", method_id="
				+ hcs.getMethod_ID() + ", package_name=" + hcs.getPackageName() + ", class_name=" + hcs.getClassName()
				+ ", isPositiveOrNegative=" + hcs.getQuality() + ", isMethod=" + hcs.isMethod() + "]";
		assertEquals(hcs.toString(),s);
	}
	

}
