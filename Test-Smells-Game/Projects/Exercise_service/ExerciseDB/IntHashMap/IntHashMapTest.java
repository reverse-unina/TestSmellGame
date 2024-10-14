import org.junit.*;
import static org.junit.Assert.*;

// Game: 1011
public class IntHashMapTest {

	@Test(timeout = 4000)
	public void test_1_1564() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertEquals(0, hm.size());
	}

	@Test(timeout = 4000)
	public void test_2_1568() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertTrue(hm.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_3_1569() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.clear();
		Assert.assertTrue(hm.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_4_1570() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.rehash();
		Assert.assertTrue(hm.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_5_1572() throws Throwable {
		IntHashMap hm = new IntHashMap();
		try{
			hm.contains(null);
			fail("NullPointerException was supposed to be thrown when the value is null!");
		}
		catch(Exception e){
		}
	}

	@Test(timeout = 4000)
	public void test_6_1574() throws Throwable {
		IntHashMap hm = new IntHashMap();
		try{
			hm.containsValue(null);
			fail("NullPointerException was supposed to be thrown when the value is null!");
		}
		catch(Exception e){

		}
	}

	@Test(timeout = 4000)
	public void test_7_1575() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		assertNull(result);
	}

	@Test(timeout = 4000)
	public void test_8_1576() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		Object result2 = hm.put(1, "one");
		assertSame("The same value was supposed to be returned!", "one", result2);
	}

	@Test(timeout = 4000)
	public void test_9_1577() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		int hm_size = hm.size();
		Assert.assertEquals(hm_size, 1);
	}

	@Test(timeout = 4000)
	public void test_10_1578() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		hm.clear();
		int hm_size = hm.size();
		Assert.assertEquals(hm_size, 0);
	}

	@Test(timeout = 4000)
	public void test_11_1579() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		hm.remove(1);
		int hm_size = hm.size();
		Assert.assertEquals(hm_size, 0);
	}

	@Test(timeout = 4000)
	public void test_12_1580() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.remove(1);
		Assert.assertNull(result);
	}

	@Test(timeout = 4000)
	public void test_13_1581() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Object result = hm.remove(1);
		Assert.assertNotNull("Null was not supposed to be returned!", result);
	}

	@Test(timeout = 4000)
	public void test_14_1582() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Object result = hm.remove(1);
		Assert.assertSame("one", result);
	}

	@Test(timeout = 4000)
	public void test_15_1583() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(-1, 1);
			fail("IllegalArgumentException was supposed to be thrown for initialCapacity<0!");
		} catch(IllegalArgumentException excep){
			
		}
	}

	@Test(timeout = 4000)
	public void test_16_1584() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(0, 0);
			fail("IllegalArgumentException was supposed to be thrown for loadFactor<=0!");
		} catch(IllegalArgumentException excep){
			
		}
	}

	@Test(timeout = 4000)
	public void test_17_1585() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(0, -1);
			fail("IllegalArgumentException was supposed to be thrown for loadFactor<=0!");
		} catch(IllegalArgumentException excep){
			
		}
	}

	@Test(timeout = 4000)
	public void test_18_1586() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(1, 1);
		} catch(Throwable thr){
			fail("No throwable was supposed to be thrown for initialCapacity>0!");
		}
	}

	@Test(timeout = 4000)
	public void test_19_1587() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(0, 1);
		} catch(Throwable thr){
			fail("No throwable was supposed to be thrown for initialCapacity==0!");
		}
	}

	@Test(timeout = 4000)
	public void test_20_1588() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(5, 5);
		} catch(Throwable thr){
			fail("No throwable was supposed to be thrown for loadFactor>=0!");
		}
	}

	@Test(timeout = 4000)
	public void test_21_1589() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Assert.assertTrue(hm.contains("one"));
	}

	@Test(timeout = 4000)
	public void test_22_1590() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Assert.assertTrue(hm.containsValue("one"));
	}

	@Test(timeout = 4000)
	public void test_23_1591() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertFalse(hm.contains("one"));
	}

	@Test(timeout = 4000)
	public void test_24_1592() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertFalse(hm.containsValue("one"));
	}

	@Test(timeout = 4000)
	public void test_25_1593() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertFalse(hm.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_26_1594() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Assert.assertTrue(hm.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_27_1597() throws Throwable {
	IntHashMap hm = new IntHashMap();
		Object value = hm.get(1);
		Assert.assertNull(value);
	}

	@Test(timeout = 4000)
	public void test_28_1598() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object obj = new Object();
		hm.put(1, obj);
		Object value = hm.get(1);
		Assert.assertSame(obj, value);
	}

	@Test(timeout = 4000)
	public void test_29_1625() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertNotNull(hm.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_30_1626() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		boolean isEmpty = hm.isEmpty();
		Assert.assertFalse(isEmpty);
	}

	@Test(timeout = 4000)
	public void test_31_1628() throws Throwable {
		IntHashMap hm = new IntHashMap();
		int size = hm.size();
		Assert.assertTrue(size >= 0);
	}

	@Test(timeout = 4000)
	public void test_32_1630() throws Throwable {
		IntHashMap hm = new IntHashMap();
		int size = hm.size();
		Assert.assertNotNull(size);
	}

	@Test(timeout = 4000)
	public void test_33_1635() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertEquals(0, hm.size());
	}

	@Test(timeout = 4000)
	public void test_34_1636() throws Throwable {
		IntHashMap hm = new IntHashMap();
		int size = hm.size();
		Assert.assertNotEquals(20, size);
	}

	@Test(timeout = 4000)
	public void test_35_1637() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Assert.assertNotNull(hm.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_36_1638() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		Assert.assertNotNull(hm.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_37_1640() throws Throwable {
		IntHashMap ihm = new IntHashMap(0, 1);
		Assert.assertEquals(0, ihm.size());
	}

	@Test(timeout = 4000)
	public void test_38_1641() throws Throwable {
		IntHashMap m = new IntHashMap();
		String a1= new String("pippo");
		m.put(1, a1);
		String a2= new String("pippo");
		assertTrue(m.contains(a2));
	}

	@Test(timeout = 4000)
	public void test_39_1642() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		Object result2 = hm.put(1, "two");
		Assert.assertNotNull(result2);
	}

	@Test(timeout = 4000)
	public void test_40_1643() throws Throwable {
		try {
			new IntHashMap(-10);
		} catch (IllegalArgumentException iae) {
			assertEquals("Illegal Capacity: -10", iae.getMessage());
		}

	}

	@Test(timeout = 4000)
	public void test_41_1644() throws Throwable {
		try {
			new IntHashMap(10,0);
		} catch (IllegalArgumentException iae) {
			assertEquals("Illegal Load: 0.0", iae.getMessage());
		}

	}

	@Test(timeout = 4000)
	public void test_42_1645() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		Object result2 = hm.put(1, "two");
		Assert.assertNotEquals(result2, result);
	}

	@Test(timeout = 4000)
	public void test_43_1646() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		Object result2 = hm.put(1, "two");
		Object value = hm.get(1);
		Assert.assertNotEquals(value, (Object)"one");
	}

	@Test(timeout = 4000)
	public void test_44_1647() throws Throwable {
		try {
			Class<?> innerClazz = Class.forName("IntHashMap$Entry");
			assertEquals(10,innerClazz.getModifiers());
		} catch (ClassNotFoundException e) {
		}
	}

	@Test(timeout = 4000)
	public void test_45_1648() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(1, "two");
		Object value = hm.get(1);
		Assert.assertEquals(value, (Object)"two");
	}

	@Test(timeout = 4000)
	public void test_46_1650() throws Throwable {
			try {
				Class<?> innerClazz = Class.forName("IntHashMap$Entry");
				innerClazz.getField("hash");
				fail();
			} catch (ClassNotFoundException | NoSuchFieldException | SecurityException e) {
			}
		// test here!
	}

	@Test(timeout = 4000)
	public void test_47_1651() throws Throwable {
		IntHashMap ihm = new IntHashMap();
		ihm.put(1, "one");
		ihm.remove(1);
		Assert.assertEquals(0, ihm.size());
	}

	@Test(timeout = 4000)
	public void test_48_1652() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(0, 5);
			hm.put(1, "one");
			hm.put(2, "two");
			fail ("The initial capacity should have been set to 1!");
		} catch(Throwable thr){
			
		}
	}

	@Test(timeout = 4000)
	public void test_49_1653() throws Throwable {
		try{
			IntHashMap hm = new IntHashMap(0, 5);
			hm.put(1, "one");
		} catch(Throwable thr){
			fail("Initial capacity was supposed to become 1!");
		}
	}

	@Test(timeout = 4000)
	public void test_50_1654() throws Throwable {
		IntHashMap m = new IntHashMap();
		m.put(1, "a");
		assertEquals("a", m.remove(1));
		assertFalse(m.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_51_1655() throws Throwable {
		IntHashMap m = new IntHashMap();
		m.put(1, "a");
		m.remove(1);
		assertTrue(m.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_52_1656() throws Throwable {
		IntHashMap hm = new IntHashMap();
		int size = hm.size();
		Assert.assertNotNull(size);
	}

	@Test(timeout = 4000)
	public void test_53_1657() throws Throwable {
		IntHashMap m = new IntHashMap(1,1);
		m.put(1, "a");
		m.put(2, "b");
		assertTrue(m.containsValue("b"));
		// test here!
	}

	@Test(timeout = 4000)
	public void test_54_1658() throws Throwable {
				IntHashMap m = new IntHashMap(1,1);
		m.put(1, "a");
		m.clear();
		assertFalse(m.containsValue("a"));

	}

	@Test(timeout = 4000)
	public void test_55_1659() throws Throwable {
		IntHashMap m = new IntHashMap(1,1);
		m.put(1, "a");
		assertEquals(1,m.size());
		assertEquals(1,m.size());
		// test here!
	}

	@Test(timeout = 4000)
	public void test_56_1660() throws Throwable {
		IntHashMap hm = new IntHashMap();
			try{
				Object result = hm.put((Integer) null, "null");
				Assert.fail("It should not be possible for the key to be null!");
			}catch(Exception excep){
				
			}
	}

	@Test(timeout = 4000)
	public void test_57_1662() throws Throwable {
		IntHashMap hm = new IntHashMap();
			try{
				hm.containsValue(null);
				fail("NullPointerException was supposed to be thrown when the value is null!");
			}
			catch(NullPointerException e){

			}
	}

	@Test(timeout = 4000)
	public void test_58_1663() throws Throwable {
		IntHashMap hm = new IntHashMap();
			try{
				hm.contains(null);
				fail("NullPointerException was supposed to be thrown when the value is null!");
			}
			catch(NullPointerException e){

			}
	}

	@Test(timeout = 4000)
	public void test_59_1664() throws Throwable {
		IntHashMap hm = new IntHashMap();
		Object result = hm.put(1, "one");
		Object result2 = hm.put(1, "two");
		Assert.assertEquals(result2, "one");
	}

	@Test(timeout = 4000)
	public void test_60_1665() throws Throwable {
		IntHashMap hm = new IntHashMap();
			hm.put(-1, "-1");
			Assert.assertTrue(hm.containsKey(-1));
	}

	@Test(timeout = 4000)
	public void test_61_1666() throws Throwable {
		IntHashMap hm = new IntHashMap();
			hm.put(-1, "-1");
			Assert.assertNotNull(hm.containsKey(-1));
	}

	@Test(timeout = 4000)
	public void test_62_1668() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(0, "O");
		Assert.assertTrue(hm.containsKey(0));
	}

	@Test(timeout = 4000)
	public void test_63_1669() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, null);
		Assert.assertTrue(hm.containsKey(1));
	}

	@Test(timeout = 4000)
	public void test_64_1670() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, null);
		Assert.assertFalse(hm.containsKey(2));
	}

	@Test(timeout = 4000)
	public void test_65_1671() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.remove(1);
		int size = hm.size();
		Assert.assertEquals(0, size);
	}

	@Test(timeout = 4000)
	public void test_66_1672() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(2, "two");
		hm.put(3, "three");
		hm.remove(3);
		int size = hm.size();
		Assert.assertEquals(2, size);
	}

	@Test(timeout = 4000)
	public void test_67_1673() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(2, "two");
		hm.put(3, "three");
		hm.remove(3);
		Object value = hm.get(3);
		Assert.assertNull(value);
	}

	@Test(timeout = 4000)
	public void test_68_1674() throws Throwable {
		IntHashMap hm = new IntHashMap();
			try{
				hm.contains(null);
				fail("NullPointerException was supposed to be thrown when the value is null!");
			}
			catch(Throwable e){

			}
	}

	@Test(timeout = 4000)
	public void test_69_1675() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(2, "two");
		hm.put(3, "three");
		boolean isEmpty = hm.isEmpty();
		Assert.assertFalse(isEmpty);
	}

	@Test(timeout = 4000)
	public void test_70_1676() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "1");
		hm.put(2, "2");
		hm.put(3, "3");
		hm.put(4, "4");
		hm.put(5, "5");
		Assert.assertTrue(hm.contains("3"));
	}

	@Test(timeout = 4000)
	public void test_71_1677() throws Throwable {
			IntHashMap hm = new IntHashMap();
			hm.put(1, "1");
			hm.put(2, "2");
			hm.put(3, "3");
			hm.put(4, "4");
			hm.put(5, "5");
			Assert.assertTrue(hm.contains("5"));
	}

	@Test(timeout = 4000)
	public void test_72_1678() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "1");
		hm.put(2, "2");
		hm.put(3, "3");
		hm.put(4, "4");
		hm.put(5, "5");
		Assert.assertFalse(hm.contains("8"));
	}

	@Test(timeout = 4000)
	public void test_73_1679() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(2, "two");
		hm.put(3, "three");
		hm.put(4, "four");
		hm.put(5, "five");
		Object value = hm.get(4);
		Assert.assertEquals(value, (Object)"four");
	}

	@Test(timeout = 4000)
	public void test_74_1685() throws Throwable {
		// test here!
		int initialC = 20;
		IntHashMap ihm1 = new IntHashMap(initialC);
		IntHashMap ihm2 = new IntHashMap();
		assertEquals(20, 20);
	}

	@Test(timeout = 4000)
	public void test_75_1686() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(1, "one");
		hm.put(2, "two");
		hm.put(3, "three");
		Object value = hm.get(4);
		Assert.assertNull(value);
	}

	@Test(timeout = 4000)
	public void test_76_1687() throws Throwable {
		IntHashMap hm = new IntHashMap();
			hm.put(0, "O");
			hm.put(1, "one");
			hm.put(2, "two");
			Object value = hm.get(0);
			Object value2 = hm.get(2);
			Assert.assertEquals("O", value);
			Assert.assertEquals("two", value2);
	}

	@Test(timeout = 4000)
	public void test_77_1690() throws Throwable {
		IntHashMap hm = new IntHashMap();
			Object result = hm.put(1, "one");
			Object result2 = hm.put(2, "two");
			Assert.assertNotEquals(result2, "one");
	}

	@Test(timeout = 4000)
	public void test_78_1691() throws Throwable {
		IntHashMap hm = new IntHashMap();
			Object result = hm.put(5, "five");
			Object result2 = hm.put(0, "O");
			Assert.assertNotEquals(result2, "five");
	}

	@Test(timeout = 4000)
	public void test_79_1714() throws Throwable {
		IntHashMap m = new IntHashMap(1,1);
		try{
			m.contains(null);
		}catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}

	}

	@Test(timeout = 4000)
	public void test_80_1715() throws Throwable {
				IntHashMap m = new IntHashMap(1,1);
		try{
			m.contains(null);
		}catch (Exception e) {
			assertNull(e.getMessage());
			assertTrue(e instanceof NullPointerException);
		}
// test here!
	}

	@Test(timeout = 4000)
	public void test_81_1721() throws Throwable {
		IntHashMap hm = new IntHashMap();
		hm.put(0, "O");
		Object result = hm.remove(1);
		Assert.assertNotEquals(result, "O");
	}

	@Test(timeout = 4000)
	public void test_82_1728() throws Throwable {
			IntHashMap hm = new IntHashMap();
			hm.put(1, "");
			hm.put(2, "");
			hm.put(3, "");
			hm.put(4, "");
			Assert.assertTrue(hm.containsKey(4));
			Assert.assertFalse(hm.containsKey(0));
		
	}

	@Test(timeout = 4000)
	public void test_83_1759() throws Throwable {
		IntHashMap hm = new IntHashMap();
			hm.put(0, "O");
			hm.put(1, "one");
			hm.put(2, "two");
			hm.rehash();
			Object value = hm.get(0);
			Object value2 = hm.get(2);
			Assert.assertEquals("O", value);
			Assert.assertEquals("two", value2);
	}

}
