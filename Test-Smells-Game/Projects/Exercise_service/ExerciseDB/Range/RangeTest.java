import org.junit.*;
import static org.junit.Assert.*;

// Game: 1013
public class RangeTest {

	@Test(timeout = 4000)
	public void test_1_1295() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		try {
			new java.io.ObjectOutputStream(new java.io.ByteArrayOutputStream()).writeObject(r);
		} catch (java.io.IOException e) {
			fail(e.getMessage());
		}
	}

	@Test(timeout = 4000)
	public void test_2_1296() throws Throwable {
		try {
			Range.between(null, new Integer(100));
			fail("Elements in a range must not be null");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test(timeout = 4000)
	public void test_3_1297() throws Throwable {
		try {
			Range.between(new Integer(100), null);
			fail("Elements in a range must not be null");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test(timeout = 4000)
	public void test_4_1298() throws Throwable {
		try {
			Range.between(null, null);
			fail("Elements in a range must not be null");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test(timeout = 4000)
	public void test_5_1299() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		assertEquals(new Integer(10), r.getMinimum());
		assertEquals(new Integer(100), r.getMaximum());
	}

	@Test(timeout = 4000)
	public void test_6_1300() throws Throwable {
		assertEquals(new Integer(5), Range.is(new Integer(5)).getMinimum());
		assertEquals(new Integer(5), Range.is(new Integer(5)).getMaximum());
	}

	@Test(timeout = 4000)
	public void test_7_1301() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(!r.contains(new Integer(0)) == !r.contains(new Integer(9)) == r.contains(new Integer(10)) == r
				.contains(new Integer(50)) == !r.contains(new Integer(51)));
		assertFalse(r.contains(null));
	}

	@Test(timeout = 4000)
	public void test_8_1307() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(!r.isAfter(new Integer(0)) == !r.isAfter(new Integer(9)) == r.isAfter(new Integer(10)) == r
				.isAfter(new Integer(50)) == !r.isAfter(new Integer(51)));
		assertFalse(r.isAfter(null));
	}

	@Test(timeout = 4000)
	public void test_9_1308() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(
				r.isStartedBy(new Integer(0)) == !r.isStartedBy(new Integer(9)) == r.isStartedBy(new Integer(10)) == r
						.isStartedBy(new Integer(50)) == !r.isStartedBy(new Integer(51)));
		assertFalse(r.isStartedBy(null));
	}

	@Test(timeout = 4000)
	public void test_10_1309() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(r.isEndedBy(new Integer(0)) == !r.isEndedBy(new Integer(9)) == r.isEndedBy(new Integer(10)) == r
				.isEndedBy(new Integer(50)) == !r.isEndedBy(new Integer(51)));
		assertFalse(r.isEndedBy(null));
	}

	@Test(timeout = 4000)
	public void test_11_1310() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(r.isBefore(new Integer(0)) == !r.isBefore(new Integer(9)) == r.isBefore(new Integer(10)) == r
				.isBefore(new Integer(50)) == !r.isBefore(new Integer(51)));
		assertFalse(r.isBefore(null));
	}

	@Test(timeout = 4000)
	public void test_12_1311() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		try {
			r.elementCompareTo(null);
			fail("Element is null");
		} catch (NullPointerException e) {
			// expected
		}

		assertTrue((r.elementCompareTo(new Integer(0)) == -1) == (r.elementCompareTo(new Integer(9)) == -1) == (r
				.elementCompareTo(new Integer(10)) == 0) == (r.elementCompareTo(new Integer(11)) == 0) == (r
						.elementCompareTo(new Integer(25)) == 0) == (r.elementCompareTo(new Integer(49)) == 0) == (r
								.elementCompareTo(new Integer(50)) == 0) == (r.elementCompareTo(
										new Integer(51)) == 1) == (r.elementCompareTo(new Integer(100)) == 1));
	}

	@Test(timeout = 4000)
	public void test_13_1312() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(r.containsRange(Range.between(new Integer(10), new Integer(50))) == !r
				.containsRange(Range.between(new Integer(0), new Integer(10))) == !r
						.containsRange(Range.between(new Integer(0), new Integer(11))) == r
								.containsRange(Range.between(new Integer(11), new Integer(49))) == !r
										.containsRange(Range.between(new Integer(50), new Integer(60))));
		assertFalse(r.containsRange(null));
	}

	@Test(timeout = 4000)
	public void test_14_1313() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(r.isAfterRange(Range.between(new Integer(10), new Integer(50))) == !r
				.isAfterRange(Range.between(new Integer(0), new Integer(10))) == !r
						.isAfterRange(Range.between(new Integer(0), new Integer(11))) == r
								.isAfterRange(Range.between(new Integer(11), new Integer(49))) == !r
										.isAfterRange(Range.between(new Integer(50), new Integer(60))));
		assertFalse(r.isAfterRange(null));
	}

	@Test(timeout = 4000)
	public void test_15_1314() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(!r.isOverlappedBy(Range.between(new Integer(10), new Integer(50))) == !r
				.isOverlappedBy(Range.between(new Integer(0), new Integer(10))) == !r
						.isOverlappedBy(Range.between(new Integer(0), new Integer(11))) == r
								.isOverlappedBy(Range.between(new Integer(11), new Integer(49))) == !r
										.isOverlappedBy(Range.between(new Integer(50), new Integer(60))) == !r
												.isOverlappedBy(Range.between(new Integer(0), new Integer(5))));
		assertFalse(r.isOverlappedBy(null));
	}

	@Test(timeout = 4000)
	public void test_16_1315() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertTrue(r.isBeforeRange(Range.between(new Integer(10), new Integer(50))) == !r
				.isBeforeRange(Range.between(new Integer(0), new Integer(10))) == !r
						.isBeforeRange(Range.between(new Integer(0), new Integer(11))) == r
								.isBeforeRange(Range.between(new Integer(11), new Integer(49))) == !r
										.isBeforeRange(Range.between(new Integer(50), new Integer(60))));
		assertFalse(r.isBeforeRange(null));
	}

	@Test(timeout = 4000)
	public void test_17_1316() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));

		assertTrue((r.intersectionWith(Range.between(new Integer(10), new Integer(50))) != null) == (r
				.intersectionWith(Range.between(new Integer(0), new Integer(10))) != null) == (r
						.intersectionWith(Range.between(new Integer(0), new Integer(11))) != null) == (r
								.intersectionWith(Range.between(new Integer(11), new Integer(49))) != null) == (r
										.intersectionWith(Range.between(new Integer(50), new Integer(60))) != null));
		try {
			r.intersectionWith(null);
			fail("Cannot calculate intersection with non-overlapping range %s");
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test(timeout = 4000)
	public void test_18_1317() throws Throwable {
		Range<Integer> r1 = Range.between(new Integer(50), new Integer(10));
		Range<Integer> r2 = Range.between(new Integer(50), new Integer(10));
		assertTrue(r1.equals(r1) == !r1.equals(null) == !r1.equals(new String()));
		assertTrue(r1.equals(r2) == !Range.between(new Integer(10), new Integer(50)).equals(
				Range.between(new Integer(1), new Integer(50))) == !Range.between(new Integer(10), new Integer(50))
						.equals(Range.between(new Integer(10), new Integer(60)))

		);
	}

	@Test(timeout = 4000)
	public void test_19_1318() throws Throwable {
		int base = 37 * 17 + Range.class.hashCode();

		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		int r1 = 37 * base + r.getMinimum().hashCode();
		r1 = 37 * r1 + r.getMaximum().hashCode();
		assertEquals(r1, r.hashCode());

		r = Range.is(new Integer(5));
		int r2 = 37 * base + r.getMinimum().hashCode();
		r2 = 37 * r2 + r.getMaximum().hashCode();
		assertEquals(r2, r.hashCode());
	}

	@Test(timeout = 4000)
	public void test_20_1319() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertEquals("[10..50]", r.toString());
		r = Range.is(new Integer(5));
		assertEquals("[5..5]", r.toString());
	}

	@Test(timeout = 4000)
	public void test_21_1320() throws Throwable {
		Range<Integer> r = Range.between(new Integer(10), new Integer(50));
		assertEquals("{10:50}", r.toString("{%1$s:%2$s}"));
	}

	@Test(timeout = 4000)
	public void test_22_1331() throws Throwable {
		// test here!
		
		Range<Integer> rango =Range.between(new Integer (30),new Integer (50));
		assertEquals(-1, rango.elementCompareTo(10));
	}

	@Test(timeout = 4000)
	public void test_23_1332() throws Throwable {
		// test here!
		Range<Integer> rango =Range.between(new Integer (30),new Integer (50));
		assertEquals(1, rango.elementCompareTo(70));
	}

	@Test(timeout = 4000)
	public void test_24_1335() throws Throwable {
		// test here!
		Range<Integer> rango =Range.between(new Integer (30),new Integer (50));
		assertEquals(new Integer (30), rango.getMinimum());
		assertEquals(new Integer (50), rango.getMaximum());
	}

	@Test(timeout = 4000)
	public void test_25_1336() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(100), new Integer(10));
		assertEquals(new Integer(10), r.getMinimum());
		assertEquals(new Integer(100), r.getMaximum());

	}

	@Test(timeout = 4000)
	public void test_26_1344() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(50), new Integer(130));
		assertEquals(new Integer(50), r.intersectionWith(r2).getMinimum());
		assertEquals(new Integer(100), r.intersectionWith(r2).getMaximum());
		
	}

	@Test(timeout = 4000)
	public void test_27_1345() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(10), new Integer(100));
		assertEquals(true, r.equals(r2));
	}

	@Test(timeout = 4000)
	public void test_28_1346() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(30), new Integer(50));
		assertEquals(false, r.equals(r2));
	}

	@Test(timeout = 4000)
	public void test_29_1347() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(10), new Integer(100));
		assertEquals(new Integer(10), r.intersectionWith(r2).getMinimum());
		assertEquals(new Integer(100), r.intersectionWith(r2).getMaximum());
	
	}

	@Test(timeout = 4000)
	public void test_30_1348() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));;
		assertEquals(new Integer(10), r.intersectionWith(r).getMinimum());
		assertEquals(new Integer(100), r.intersectionWith(r).getMaximum());
	
	}

	@Test(timeout = 4000)
	public void test_31_1349() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(1), new Integer(9));
		assertEquals(true , r2.isBeforeRange(r));
	}

	@Test(timeout = 4000)
	public void test_32_1350() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		Range<Integer> r2 = Range.between(new Integer(10), new Integer(100));
		assertEquals(true, r.containsRange(r2));
		
	}

	@Test(timeout = 4000)
	public void test_33_1351() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(20));
		Range<Integer> r2 = Range.between(new Integer(21), new Integer(30));
		assertEquals(true, r2.isAfterRange(r));
	}

	@Test(timeout = 4000)
	public void test_34_1352() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		
		assertEquals(false, r.equals(null));
	}

	@Test(timeout = 4000)
	public void test_35_1360() throws Throwable {
		// test here!
		Range<Integer> r = Range.between(new Integer(10), new Integer(100));
		assertEquals(true,r.isNaturalOrdering());
	}

}
