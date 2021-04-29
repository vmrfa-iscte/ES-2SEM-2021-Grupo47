package detection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.MethodIdentity;
import classes.MethodMetrics;
import classes.Metrics;

class CheckRuleCombinationsTest {

	private MethodMetrics mm;
	private boolean v;
	private boolean f;

	@BeforeEach
	void setUp() throws Exception {
		mm = new MethodMetrics(new MethodIdentity("methodName", "className", "packageName", 0),
				new Metrics(5, 5, 5, 5, 5));
		v = true;
		f = false;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIsGodLesserGreaterLesser_AA() {
		CheckRuleCombinations crb = new CheckRuleCombinations(6, 4, 6);// VVV
		CheckRuleCombinations crb1 = new CheckRuleCombinations(4, 4, 6);// FVV
		CheckRuleCombinations crb2 = new CheckRuleCombinations(4, 6, 6);// FFV
		CheckRuleCombinations crb3 = new CheckRuleCombinations(4, 6, 4);// FFF
		CheckRuleCombinations crb4 = new CheckRuleCombinations(6, 6, 4);// VFF
		CheckRuleCombinations crb5 = new CheckRuleCombinations(6, 4, 4);// VVF
		assertNotNull(crb);
		assertNotNull(crb1);
		assertNotNull(crb2);
		assertNotNull(crb3);
		assertNotNull(crb4);
		assertNotNull(crb5);
		assertEquals(v, crb.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, crb1.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, crb2.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, crb3.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, crb4.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, crb5.isGodLesserGreaterLesser_AndAnd(mm));

	}

	@Test
	void testisGodLesserGreaterLesser_AndOr() {
		
		
	}

	@Test
	void testConstructor2() {
		CheckRuleCombinations crb = new CheckRuleCombinations(12, 12);
		assertNotNull(crb);

	}

}
