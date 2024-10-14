import org.junit.*;
import static org.junit.Assert.*;

// Game: 1014
public class RationalNumberTest {

	@Test(timeout = 4000)
	public void test_1_1689() throws Throwable {
		// test here!
		RationalNumber numeroRacional= new RationalNumber(1, 4);
		assertEquals(0, numeroRacional.intValue());
	}

	@Test(timeout = 4000)
	public void test_2_1696() throws Throwable {
		RationalNumber numeroRacional= new RationalNumber(1, 4);
		String s="1/4 (0.25)";
		assertEquals(s, numeroRacional.toString());
	}

	@Test(timeout = 4000)
	public void test_3_1697() throws Throwable {
		RationalNumber numeroRacional= RationalNumber.factoryMethod(2, 10);
		assertEquals(1, numeroRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_4_1698() throws Throwable {
	
		RationalNumber numeroRacional= RationalNumber.factoryMethod(2, 10);
		assertEquals(5, numeroRacional.divisor);
	}

	@Test(timeout = 4000)
	public void test_5_1700() throws Throwable {
	
		RationalNumber numeroRacional= RationalNumber.factoryMethod(Integer.MAX_VALUE, Integer.MAX_VALUE);
		assertEquals(1, numeroRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_6_1702() throws Throwable {
		RationalNumber numeroRacional= RationalNumber.factoryMethod(1, Integer.MAX_VALUE);
		assertEquals(1, numeroRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_7_1703() throws Throwable {
				RationalNumber numeroRacional= RationalNumber.factoryMethod(Integer.MAX_VALUE, 1);
		assertEquals(Integer.MAX_VALUE, numeroRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_8_1704() throws Throwable {
		RationalNumber numeroRacional= new RationalNumber(1,4);
		assertEquals("0.25", numeroRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_9_1706() throws Throwable {
		RationalNumber numeroRacional= new RationalNumber(4,2);
		assertEquals("2", numeroRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_10_1707() throws Throwable {
		RationalNumber nRacional= RationalNumber.factoryMethod(Integer.MAX_VALUE+1,Integer.MAX_VALUE+1);
		assertEquals("1", nRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_11_1709() throws Throwable {
		RationalNumber nRacional= RationalNumber.factoryMethod(Integer.MAX_VALUE+1,1);
		assertEquals("-2147483648",nRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_12_1710() throws Throwable {
		// test here!
		
		
		
		RationalNumber nRacional= RationalNumber.factoryMethod(1,Integer.MAX_VALUE+1);
		assertEquals("-0", nRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_13_1711() throws Throwable {
		// test here!
		
		RationalNumber nRacional= RationalNumber.factoryMethod(1,Integer.MIN_VALUE-1);
		assertEquals("0", nRacional.toDisplayString());
	}

	@Test(timeout = 4000)
	public void test_14_1712() throws Throwable {
		// test here!
		RationalNumber nRacional= RationalNumber.valueOf(-0.25);
		assertEquals(-1, nRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_15_1713() throws Throwable {
	RationalNumber nRacional= RationalNumber.valueOf(0);
		assertEquals(0,nRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_16_1720() throws Throwable {
		RationalNumber nR=RationalNumber.valueOf(0);
		assertEquals(1, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_17_1731() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(0, (long)(Integer.MIN_VALUE) << 1);
		
	}

	@Test(timeout = 4000)
	public void test_18_1734() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(0, (long)(Integer.MIN_VALUE) << 1);
		assertEquals(0,n.numerator);
		assertEquals(1,n.divisor);		
	}

	@Test(timeout = 4000)
	public void test_19_1736() throws Throwable {
		// test here!
		 RationalNumber nRacional= RationalNumber.factoryMethod(Long.MAX_VALUE+1, 1);
		assertEquals(0, nRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_20_1738() throws Throwable {
		// test here!
		 RationalNumber nR= RationalNumber.valueOf(1);
		assertEquals(1, nR.numerator );
	}

	@Test(timeout = 4000)
	public void test_21_1739() throws Throwable {
		// test here!
		 RationalNumber nRacional= RationalNumber.valueOf(2);
		assertEquals(2, nRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_22_1740() throws Throwable {
		// test here!
		 RationalNumber nRacional= RationalNumber.valueOf(-2);
		assertEquals(-2, nRacional.numerator);
		
	}

	@Test(timeout = 4000)
	public void test_23_1741() throws Throwable {
		// test here!
				// test here!
		 RationalNumber nRacional= RationalNumber.valueOf(-1);
		assertEquals(-1, nRacional.numerator);
	}

	@Test(timeout = 4000)
	public void test_24_1742() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( Integer.MIN_VALUE,0);
		assertEquals(1,n.numerator);
	}

	@Test(timeout = 4000)
	public void test_25_1744() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( Integer.MIN_VALUE,Integer.MIN_VALUE);
		assertEquals(1,n.numerator);
		assertEquals(1,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_26_1753() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( Integer.MIN_VALUE << 4,0x100);
		assertEquals("wrong numerator", 0,n.numerator);
		assertEquals("wrong divisor", 1,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_27_1756() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( 0x100, Integer.MIN_VALUE << 4);
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", 0,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_28_1758() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( Integer.MAX_VALUE << 1, Integer.MIN_VALUE << 4);
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", 0,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_29_1760() throws Throwable {
		// test here!
		RationalNumber nR= RationalNumber.valueOf(1.25);
		assertEquals(5, nR.numerator);
		assertEquals(4, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_30_1762() throws Throwable {
		RationalNumber nR= RationalNumber.valueOf(-1.25);
		assertEquals(-5, nR.numerator);
		assertEquals(4, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_31_1763() throws Throwable {
				RationalNumber nR= RationalNumber.valueOf(1);
		assertEquals(1, nR.numerator);
		assertEquals(1, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_32_1765() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(Integer.MIN_VALUE+(Integer.MIN_VALUE << 2), Integer.MIN_VALUE+(Integer.MIN_VALUE << 8));
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", 1,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_33_1766() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(Integer.MIN_VALUE+(Integer.MIN_VALUE << 2), Integer.MIN_VALUE+(Integer.MIN_VALUE << 1));
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", 1,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_34_1768() throws Throwable {
		RationalNumber nR= RationalNumber.valueOf(5);
		assertEquals(5, nR.numerator);
		assertEquals(1, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_35_1770() throws Throwable {
		// test here!
				RationalNumber nR= RationalNumber.valueOf(1.1);
		assertEquals(11, nR.numerator);
		assertEquals(10, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_36_1773() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(0x1300000000L, 0x300000000L);
		assertEquals("wrong numerator", 19,n.numerator);
		assertEquals("wrong divisor", 3,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_37_1774() throws Throwable {
		// test here!
 		RationalNumber nR= RationalNumber.valueOf(-100.5);
		assertEquals(-201, nR.numerator);
		assertEquals(2, nR.divisor);
	}

	@Test(timeout = 4000)
	public void test_38_1777() throws Throwable {
		// test here!
		RationalNumber nR=new RationalNumber(102, 50);
		assertEquals(2, nR.intValue());
	}

	@Test(timeout = 4000)
	public void test_39_1778() throws Throwable {
		try {
			// Rational with divide by 0
			RationalNumber n = RationalNumber.factoryMethod(44,0);	
			// Should throw before here
			//assert(false);
			// Its really silly that I'm only allowed two asserts!!
		} catch (NumberFormatException e) {
			assert(true);
		} catch (Exception e) {
			// Wrong exception class
			assert(false);
		}
	}

	@Test(timeout = 4000)
	public void test_40_1779() throws Throwable {
		// test here!
		RationalNumber numeroRacional= new RationalNumber(1,4);
		assertEquals(0.25 , numeroRacional.doubleValue(),0.0001);
		assertEquals(0.25 , numeroRacional.floatValue(),0.0001);
		
	}

	@Test(timeout = 4000)
	public void test_41_1783() throws Throwable {
		RationalNumber nR=new RationalNumber(1, 4);
		assertEquals(0, nR.longValue(),0.0001);
		
	}

	@Test(timeout = 4000)
	public void test_42_1785() throws Throwable {
		// test here!
		RationalNumber nR=new RationalNumber(Integer.MAX_VALUE, Integer.MAX_VALUE);
		assertEquals(1, nR.longValue());
	}

	@Test(timeout = 4000)
	public void test_43_1793() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod(Long.MAX_VALUE >> 22, 0x40000000L);
		assertEquals("wrong numerator", 2147483647,n.numerator);
		assertEquals("wrong divisor", 1048576,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_44_1797() throws Throwable {
		// test here!
		RationalNumber nRacional= RationalNumber.valueOf(-50.250);
		assertEquals(-201, nRacional.numerator);
		assertEquals(4, nRacional.divisor);
		
	}

	@Test(timeout = 4000)
	public void test_45_1806() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( ~0L & ~0xfL, 0x4000);
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", -1024,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_46_1810() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( 0x4000, ~0L & ~0xfL);
		assertEquals("wrong numerator", -1024,n.numerator);
		assertEquals("wrong divisor", 1,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_47_1833() throws Throwable {
		RationalNumber n=RationalNumber.factoryMethod( ~0L & ~0xfffffffL, ~0L & ~0xfffffffffL);
		assertEquals("wrong numerator", 1,n.numerator);
		assertEquals("wrong divisor", 256,n.divisor);
	}

	@Test(timeout = 4000)
	public void test_48_1835() throws Throwable {
			RationalNumber.factoryMethod((long)Integer.MAX_VALUE*5,0);
	}

	@Test(timeout = 4000)
	public void test_49_1836() throws Throwable {
		RationalNumber rn = new RationalNumber(1,Integer.MAX_VALUE);
		Assert.assertEquals(4.656612875245797E-10,rn.doubleValue(),0);
	}

	@Test(timeout = 4000)
	public void test_50_1839() throws Throwable {
				try {
			Class<?> clazz = Class.forName("RationalNumber");
			clazz.getField("TOLERANCE");
			fail();
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
		}
	}

	@Test(timeout = 4000)
	public void test_51_1840() throws Throwable {
				RationalNumber r = RationalNumber.factoryMethod(Integer.MAX_VALUE + 2l, 2);
		assertEquals(1073741824, r.numerator);
		assertEquals(1, r.divisor);
// test here!
	}

	@Test(timeout = 4000)
	public void test_52_1841() throws Throwable {
			RationalNumber r = RationalNumber.factoryMethod(10, 2);
		assertEquals(5,r.intValue());
		RationalNumber r2 = RationalNumber.factoryMethod(0, 2);
		assertEquals(0,r2.intValue());

	}

	@Test(timeout = 4000)
	public void test_53_1842() throws Throwable {
		RationalNumber r = new RationalNumber(10, 2);
		assertEquals(5,r.intValue());
		RationalNumber r2 = new RationalNumber(0, 2);
		assertEquals(0,r2.intValue());
	}

	/* This test was crashing everything! */
//	@Test(timeout = 4000)
//	public void test_54_1843() throws Throwable {
//				RationalNumber r = new RationalNumber(10, 0);
//		try {
//			r.intValue();
//			fail();
//		} catch (ArithmeticException ae) {
//		}
//
//	}

}
