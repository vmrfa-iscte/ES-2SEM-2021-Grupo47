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
	private CheckRuleCombinations combination;
	private CheckRuleCombinations combination1;
	private CheckRuleCombinations combination2;
	private CheckRuleCombinations combination3;
	private CheckRuleCombinations combination4;
	private CheckRuleCombinations combination5;
	private CheckRuleCombinations combination6;

	/**
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mm = new MethodMetrics(new MethodIdentity("methodName", "className", "packageName", 0),
				new Metrics(5, 5, 5, 5, 5));
		v = true;
		f = false;
		 combination = new CheckRuleCombinations(6, 4, 6);// VVV
		 combination1 = new CheckRuleCombinations(4, 4, 6);// FVV
		 combination2 = new CheckRuleCombinations(4, 6, 6);// FFV
		 combination3 = new CheckRuleCombinations(4, 6, 4);// FFF
		 combination4 = new CheckRuleCombinations(6, 6, 4);// VFF
		 combination5 = new CheckRuleCombinations(6, 4, 4);// VVF
		 combination6 = new CheckRuleCombinations(4, 4, 4); // FVF
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIsGodLesserGreaterLesser_AA() {
		assertNotNull(combination);
		assertNotNull(combination1);
		assertNotNull(combination2);
		assertNotNull(combination3);
		assertNotNull(combination4);
		assertNotNull(combination5);
		assertEquals(v, combination.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, combination1.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, combination2.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, combination3.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, combination4.isGodLesserGreaterLesser_AndAnd(mm));
		assertEquals(f, combination5.isGodLesserGreaterLesser_AndAnd(mm));

	}

	@Test
	void testisGodLesserGreaterLesser_AndOr() {
		assertNotNull(combination);
		assertNotNull(combination1);
		assertNotNull(combination2);
		assertNotNull(combination3);
		assertNotNull(combination4);
		assertNotNull(combination5);
		assertEquals(v, combination.isGodLesserGreaterLesser_AndOr(mm));
		assertEquals(v, combination1.isGodLesserGreaterLesser_AndOr(mm));
		assertEquals(v, combination2.isGodLesserGreaterLesser_AndOr(mm));
		assertEquals(f, combination3.isGodLesserGreaterLesser_AndOr(mm));
		assertEquals(f, combination4.isGodLesserGreaterLesser_AndOr(mm));
		assertEquals(v, combination5.isGodLesserGreaterLesser_AndOr(mm));

	}

	@Test
	void isGodLesserGreaterLesser_OrAnd() {
		assertNotNull(combination);
		assertNotNull(combination1);
		assertNotNull(combination2);
		assertNotNull(combination3);
		assertNotNull(combination4);
		assertNotNull(combination5);
		assertNotNull(combination6);
		assertEquals(v, combination.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(v, combination1.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(f, combination2.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(f, combination3.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(v, combination4.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(v, combination5.isGodLesserGreaterLesser_OrAnd(mm));
		assertEquals(f, combination6.isGodLesserGreaterLesser_OrAnd(mm));

	}

	@Test
	void testIsGodLesserGreaterLesser_OrOr() {
		assertNotNull(combination);
		assertNotNull(combination1);
		assertNotNull(combination2);
		assertNotNull(combination3);
		assertNotNull(combination4);
		assertNotNull(combination5);
		assertNotNull(combination6);
		assertEquals(v, combination.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(v, combination1.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(v, combination2.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(f, combination3.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(v, combination4.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(v, combination5.isGodLesserGreaterLesser_OrOr(mm));
		assertEquals(v, combination6.isGodLesserGreaterLesser_OrOr(mm));

	}

	@Test
	void testisGodLesserGreaterGreater_AndAnd() {
		assertNotNull(combination);
		assertNotNull(combination1);
		assertNotNull(combination2);
		assertNotNull(combination3);
		assertNotNull(combination4);
		assertNotNull(combination5);
		assertNotNull(combination6);
		//tou a acabar este
		
	
		

	}

	@Test
	void testConstructor2() {
		CheckRuleCombinations crb = new CheckRuleCombinations(12, 12);
		assertNotNull(crb);

	}

}
