import org.junit.*;
import static org.junit.Assert.*;

// Game: 1018
public class WeakHashtableTest {

	@Test(timeout = 4000)
	public void test_1_1162() throws Throwable {
		// test here!
		WeakHashtable wh = new WeakHashtable();
		assertNotNull(wh.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_2_1168() throws Throwable {
		// test here!
		WeakHashtable wh = new WeakHashtable();
		assertTrue(wh.isEmpty());

	}

	@Test(timeout = 4000)
	public void test_3_1178() throws Throwable {
		// test here!
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));
		wh.put("d",new Integer(4));

		assertTrue(wh.containsKey("a"));
	}

	@Test(timeout = 4000)
	public void test_4_1179() throws Throwable {
		// test here!
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));
		wh.put("d",new Integer(4));

		assertFalse(wh.containsKey("e"));
	}

	@Test(timeout = 4000)
	public void test_5_1187() throws Throwable {
		WeakHashtable wilko = new WeakHashtable();



		assertFalse(wilko.containsKey("123"));
	}

	@Test(timeout = 4000)
	public void test_6_1192() throws Throwable {
		// test here!
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));
		wh.put("d",new Integer(4));

		assertFalse(wh.equals("new Integer(5)"));
	}

	@Test(timeout = 4000)
	public void test_7_1264() throws Throwable {

		java.util.HashMap foo = new java.util.HashMap();
		WeakHashtable w = new WeakHashtable();
		foo.put("a","b");
		w.putAll(foo);
		assertTrue(w.keySet().contains("a"));
		assertTrue(w.containsKey("a"));

	}

	@Test(timeout = 4000)
	public void test_8_1271() throws Throwable {
		WeakHashtable ww = new WeakHashtable();
		try{
			ww.put(null,null);
			fail("Expected exception");
		}catch(NullPointerException npe){
			assertEquals("Null keys are not allowed",npe.getMessage());
		}
		try{
			ww.put("shit",null);
			fail("Expected exception");
		}catch(NullPointerException npe){
			assertEquals("Null values are not allowed",npe.getMessage());
		}
	}

	@Test(timeout = 4000)
	public void test_9_1272() throws Throwable {
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));
		wh.put("d",new Integer(4));

		wh.remove(new String("c"));

		assertFalse(wh.containsKey("c"));
	}

	@Test(timeout = 4000)
	public void test_10_1279() throws Throwable {
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));

		java.util.Enumeration e = wh.keys();
		assertEquals("b",e.nextElement());
		e.nextElement();
		assertEquals("c", e.nextElement());
	}

	@Test(timeout = 4000)
	public void test_11_1284() throws Throwable {
		WeakHashtable wh = new WeakHashtable();
		wh.put("a",new Integer(1));
		wh.put("b",new Integer(2));
		wh.put("c",new Integer(3));
		assertEquals(1,wh.get("a"));
		//<img src="https://www.google.co.uk/logos/doodles/2016/2016-doodle-fruit-games-day-5-5688836437835776-hp.gif">
	}

}
