import org.junit.*;
import static org.junit.Assert.*;

// Game: 1012
public class ParameterParserTest {

	@Test(timeout = 4000)
	public void test_1_1605() throws Throwable {
		ParameterParser pp = new ParameterParser();
		assertFalse(pp.isLowerCaseNames());
		pp.setLowerCaseNames(true);
		assertTrue(pp.isLowerCaseNames());
		
	}

	@Test(timeout = 4000)
	public void test_2_1608() throws Throwable {
		char[] chars = null;
		ParameterParser pp = new ParameterParser();
		java.util.HashMap map = (java.util.HashMap)pp.parse(chars, ',');
		assertEquals(map, new java.util.HashMap());
		
		String s = null;
		map = (java.util.HashMap)pp.parse(s, ',');
		assertEquals(map, new java.util.HashMap());
	}

	@Test(timeout = 4000)
	public void test_3_1609() throws Throwable {
		ParameterParser pp = new ParameterParser();
		String s = "test=123;test2=456";
		char delimiter = ';';
		java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
		assertEquals(map.get("test"), "123");
		assertEquals(map.get("test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_4_1610() throws Throwable {
		ParameterParser pp = new ParameterParser();
        String s = "Test=123;Test2=456";
        pp.setLowerCaseNames(true);
        char delimiter = ';';
        java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
        assertEquals(map.get("test"), "123");
        assertEquals(map.get("test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_5_1611() throws Throwable {
		ParameterParser pp = new ParameterParser();
        String s = "Test=123;Test2=456";
        pp.setLowerCaseNames(false);
        char delimiter = ';';
        java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
        assertEquals(map.get("Test"), "123");
        assertEquals(map.get("Test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_6_1612() throws Throwable {
		ParameterParser pp = new ParameterParser();
        String s = " Test=123;Test2=456";
        pp.setLowerCaseNames(false);
        char delimiter = ';';
        java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
        assertEquals(map.get("Test"), "123");
        assertEquals(map.get("Test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_7_1613() throws Throwable {
		ParameterParser pp = new ParameterParser();
        String s = " Test=\"123\";Test2=\"456\"";
        pp.setLowerCaseNames(false);
        char delimiter = ';';
        java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
        assertEquals(map.get("Test"), "123");
        assertEquals(map.get("Test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_8_1614() throws Throwable {
		ParameterParser pp = new ParameterParser();
        String s = " Test   =\"123\";Test2=\"456\"";
        pp.setLowerCaseNames(false);
        char delimiter = ';';
        java.util.HashMap map = (java.util.HashMap) pp.parse(s, delimiter);
        assertEquals(map.get("Test"), "123");
        assertEquals(map.get("Test2"), "456");
	}

	@Test(timeout = 4000)
	public void test_9_1615() throws Throwable {
		ParameterParser pp = new ParameterParser();
        char[] sep = new char[]{};
        char[] sep2 = null;
        java.util.HashMap map = (java.util.HashMap)pp.parse("string", sep);
        assertEquals(map, new java.util.HashMap());

        map = (java.util.HashMap)pp.parse("string", sep2);
        assertEquals(map, new java.util.HashMap());
	}

	@Test(timeout = 4000)
	public void test_10_1617() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abc, 2se, 356, man";
		char ch = 'a'; 
		java.util.Map m = p.parse(coma, ch);
		String results = "{bc, 2se, 356, m=null, n=null}";
		assertEquals(results, m.toString());
		assertEquals(2, m.size());
	}

	@Test(timeout = 4000)
	public void test_11_1618() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abc, 2se, 356, man";
		char ch = 'a'; 
		java.util.Map m = p.parse(coma, ch);
		String results = "{bc, 2se, 356, m=null, n=null}";
		assertEquals(true, m.toString().equals(results));
		assertEquals(2, m.size());
	}

	@Test(timeout = 4000)
	public void test_12_1620() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abe, 2se, 356, man";
		char ch[] = {'a', 'e'}; 
		java.util.Map m = p.parse(coma, ch);
		String results = "{n=null, be, 2se, 356, m=null}";
		assertEquals(results, m.toString());
		assertEquals(2, m.size());
	}

	@Test(timeout = 4000)
	public void test_13_1621() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abe, 2se, 356, man";
		char ch[] = {'a', 'e', 'a','c','b'}; 
		char ch2 = 'a'; 
		java.util.Map m = p.parse(ch, ch2);
		String results = "{cb=null, e=null}";
		assertEquals(results, m.toString());
		assertEquals(2, m.size());
	}

	@Test(timeout = 4000)
	public void test_14_1622() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abe, 2se, 356, man";
		char ch[] = {'a', 'e', 'a','c','b'}; 
		char ch2 = 'a'; 
		java.util.Map m = p.parse(ch, 2, ch.length, ch2);;
		String results = "{cb=null}";
		assertEquals(results, m.toString());
		assertEquals(1, m.size());
	}

	@Test(timeout = 4000)
	public void test_15_1623() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abe, 2se, 356, man";
		char ch[] = {'a', 'e', 'a','c','b','a', 'e', 'a','c','b', '='}; 
		char ch2 = '='; 
		java.util.Map m = p.parse(ch, 0, ch.length, ch2);
		String results = "{aeacbaeacb=null}";
		assertEquals(results, m.toString());
		assertEquals(1, m.size());
	}

	@Test(timeout = 4000)
	public void test_16_1624() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abe, 2se, 356, man";
		char ch[] = {'a', 'E', 'A','c','b','a', 'e', 'a','c','b', '='}; 
		char ch2 = '='; 
		p.setLowerCaseNames(true);
		java.util.Map m = p.parse(ch, 0, ch.length, ch2);;
		String results = "{aeacbaeacb=null}";
		assertEquals(results, m.toString());
		assertEquals(true, p.isLowerCaseNames());
	}

	@Test(timeout = 4000)
	public void test_17_1629() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abc, '2se', 356, man. a. S";
		char ch[] = {'a'}; 
		java.util.Map m = p.parse(coma, ch);
		String results = "{n.=null, . S=null, bc, '2se', 356, m=null}";
		assertEquals(results, m.toString());
		assertEquals(3, m.size());
	}

	@Test(timeout = 4000)
	public void test_18_1631() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = "abc, '2se', 356, man. a. s";
		char ch[] = {'a', 's'}; 
		java.util.Map m = p.parse(coma, ch);
		//System.out.println(m.toString());
		String results = "{n.=null, bc, '2se', 356, m=null, . s=null}";
		assertEquals(results, m.toString());
		assertEquals(3, m.size());
	}

	@Test(timeout = 4000)
	public void test_19_1632() throws Throwable {
		// test here!
				ParameterParser p = new ParameterParser();
		String coma = null;
		char ch[] = {'a', 's'}; 
		boolean b = p.parse(coma, ch) instanceof java.util.HashMap;
		assertEquals(true, b);
	}

	@Test(timeout = 4000)
	public void test_20_1667() throws Throwable {
		// test here!
		ParameterParser p=new ParameterParser();
		assertEquals(false, p.isLowerCaseNames());
	}

	@Test(timeout = 4000)
	public void test_21_1683() throws Throwable {
		// test here!
		ParameterParser p=new ParameterParser();
		char[] cadena= {'h','o','l','a','m','u','n','d','o'};
		assertEquals("{hola=null}", p.parse(cadena, 0, 4, 'm').toString());
	}

	@Test(timeout = 4000)
	public void test_22_1775() throws Throwable {
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.isLowerCaseNames());
	}

	@Test(timeout = 4000)
	public void test_23_1782() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.parse("first/test", ','));
	}

	@Test(timeout = 4000)
	public void test_24_1786() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.parse("nulltest", ' '));
	}

	@Test(timeout = 4000)
	public void test_25_1787() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.parse("", '.'));
	}

	@Test(timeout = 4000)
	public void test_26_1790() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.parse((String)null, '.'));
	}

	@Test(timeout = 4000)
	public void test_27_1796() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNotNull(pp.parse((""+""+""), '.'));
	}

	@Test(timeout = 4000)
	public void test_28_1800() throws Throwable {
		ParameterParser pp = new ParameterParser();
		Assert.assertEquals(pp.parse((""+"="+"."), ',').size(), 0);
	}

	@Test(timeout = 4000)
	public void test_29_1803() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertEquals(pp.parse(("."+"="+"."), ',').size(), 1);
	}

	@Test(timeout = 4000)
	public void test_30_1808() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertEquals(pp.parse(("===,"), ',').size(), 0);
	}

	@Test(timeout = 4000)
	public void test_31_1812() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertEquals(pp.parse(("+==,"), ',').size(), 1);
	}

	@Test(timeout = 4000)
	public void test_32_1814() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertEquals(pp.parse(("+==,"), ',').get("+"), "=");
	}

	@Test(timeout = 4000)
	public void test_33_1815() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNull(pp.parse(("+= "), ',').get("+"));
	}

	@Test(timeout = 4000)
	public void test_34_1816() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNull(pp.parse(("+="), ',').get("+"));
	}

	@Test(timeout = 4000)
	public void test_35_1817() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNull(pp.parse(("="), ',').get("+"));
	}

	@Test(timeout = 4000)
	public void test_36_1818() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertNull(pp.parse((""), ',').get(""));
	}

	@Test(timeout = 4000)
	public void test_37_1820() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertTrue(pp.parse("UPPER=lower", ',').containsKey("upper"));
	}

	@Test(timeout = 4000)
	public void test_38_1821() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertTrue(pp.parse("NULL=lower", ',').containsKey("null"));
	}

	@Test(timeout = 4000)
	public void test_39_1822() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertFalse(pp.parse("=lower", ',').containsKey(""));
	}

	@Test(timeout = 4000)
	public void test_40_1823() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertTrue(pp.parse(",lower=lower", ',').containsKey("lower"));
	}

	@Test(timeout = 4000)
	public void test_41_1824() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertTrue(pp.parse("lower = ", ',').containsKey("lower"));
		Assert.assertNull(pp.parse("lower = ", ',').get("lower"));
	}

	@Test(timeout = 4000)
	public void test_42_1825() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertFalse(pp.parse(" = ,", ',').containsKey(" "));
		Assert.assertNull(pp.parse("lower = ", ',').get(" "));
	}

	@Test(timeout = 4000)
	public void test_43_1826() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		pp.setLowerCaseNames(true);
		Assert.assertFalse(pp.parse(" = ,", ',').containsKey(" "));
		Assert.assertNull(pp.parse(" = ", ',').get(" "));
	}

	@Test(timeout = 4000)
	public void test_44_1827() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertTrue(pp.parse("sometext=someother,", ',').get("sometext").equals("someother"));
	}

	@Test(timeout = 4000)
	public void test_45_1828() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertTrue(pp.parse("1 2 3 4 5=some 1 1 1,", ',').containsKey("1 2 3 4 5"));
	}

	@Test(timeout = 4000)
	public void test_46_1829() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertTrue(pp.parse("1 2 3 4 5=some 1 1 1 ,", ',').get("1 2 3 4 5").equals("some 1 1 1"));
	}

	@Test(timeout = 4000)
	public void test_47_1830() throws Throwable {
		// test here!
		ParameterParser pp = new ParameterParser();
		Assert.assertTrue(pp.getClass().getFields().length == 0);
	}

}
