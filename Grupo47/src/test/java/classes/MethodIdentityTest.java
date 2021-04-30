package classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodIdentityTest {

	@BeforeEach
	void setUp() throws Exception {
		MethodIdentity mi = new MethodIdentity("methodname","classname","packagename",0);
		assertNotNull(mi);
	}

	@Test
	void test() {
		MethodIdentity mi = new MethodIdentity("methodname","classname","packagename",0);
		assertNotNull(mi);
		
		String methodname = "methodname";
		assertEquals(methodname,mi.getMethodName());
		
		String classname = "classname";
		assertEquals(classname,mi.getClassName());
		
		String packagename = "packagename";
		assertEquals(packagename,mi.getPackageName());
		
		int methodid = 0;
		assertEquals(methodid,mi.getMethod_id());
		
		
	}

}
