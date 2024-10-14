import org.junit.*;
import static org.junit.Assert.*;

// Game: 1001
public class ByteArrayHashMapTest {

	@Test(timeout = 4000)
	public void test_1_195() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)0},"Shit");
		assertNull(old);
		
		Object shit = map.put(new byte[]{(byte)0},"ShitArse");
		assertEquals("Shit",shit);
	}

	@Test(timeout = 4000)
	public void test_2_197() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)0},"Shit");
		
		
		Object out = map.get(new byte[]{(byte)0});
		assertEquals("Shit",out);
		
		
	}

	@Test(timeout = 4000)
	public void test_3_198() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)1,0,0},"Shit");
		assertNull(old);
		
		Object shit = map.put(new byte[]{(byte)0,0,1},"ShitArse");
		assertNull(shit);
	}

	@Test(timeout = 4000)
	public void test_4_199() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		
		assertTrue(map.isEmpty());
		
		Object old = map.put(new byte[]{(byte)1,0,0},"Shit");
		assertFalse(map.isEmpty());
		
	}

	@Test(timeout = 4000)
	public void test_5_201() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1);
		map.put(new byte[]{1,2,4}, "shit");
		assertEquals("shit",map.get(new byte[]{0,1,2,4},1,3));
	}

	@Test(timeout = 4000)
	public void test_6_202() throws Throwable {
		// test here!
				try {
			ByteArrayHashMap map = new ByteArrayHashMap(-1, 10);
	        fail("Expected an IndexOutOfBoundsException to be thrown");
        } catch (IllegalArgumentException e) {
        	assertEquals(e.getMessage(), "Illegal initial capacity: " + -1);
        }
	}

	@Test(timeout = 4000)
	public void test_7_203() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		map.put("sina is a twat".getBytes(),"lol");
		ByteArrayHashMap<Object> map2 = map.duplicate();
		assertEquals(
			map2.get("sina is a twat".getBytes()),
			map.get("sina is a twat".getBytes()));
	}

	@Test(timeout = 4000)
	public void test_8_204() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)0},"Shit");
		assertEquals(1,map.size());
		
		map.clear();
		assertEquals(0,map.size());
	}

	@Test(timeout = 4000)
	public void test_9_206() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)0},"Shit");
		assertTrue(map.containsKey(new byte[]{0}));
		map.remove(new byte[]{0});
		assertFalse(map.containsKey(new byte[]{0}));
	}

	@Test(timeout = 4000)
	public void test_10_208() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(0,2f);
		map.put(new byte[]{(byte)0},"Shit1");
		map.put(new byte[]{(byte)1},"Shit2");
		
		assertEquals(map.values().size(),map.keys().size());
	}

	@Test(timeout = 4000)
	public void test_11_209() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)1,0,0},"Shit1");
		map.put(new byte[]{(byte)0,1,0},"Shit2");
		map.put(new byte[]{(byte)0,0,1},"Shit3");
		
		assertEquals("Shit2",map.remove(new byte[]{(byte)0,1,0}));
		
		assertEquals(2, map.size());
	}

	@Test(timeout = 4000)
	public void test_12_211() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)1,0,0},"Shit1");
		map.put(new byte[]{(byte)0,1,0},"Shit1");
		map.put(new byte[]{(byte)0,0,1},"Shit1");
		map.put(new byte[]{(byte)1},"Shit1");
		assertTrue(map.containsKey(new byte[]{(byte)1,0,0}));
	}

	@Test(timeout = 4000)
	public void test_13_212() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)1,0,0},"Shit1");
		map.put(new byte[]{(byte)0,1,0},"Shit2");
		map.put(new byte[]{(byte)0,0,1},"Shit3");
		
		ByteArrayHashMap<Object> other = map.duplicate();
		assertEquals(other.size(),map.size());
	}

	@Test(timeout = 4000)
	public void test_14_213() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)1,0,0},"Shit1");
		map.put(new byte[]{(byte)0,1,0},"Shit2");
		map.put(new byte[]{(byte)0,0,1},"Shit3");
		

		map.put(new byte[]{(byte)2,0,0},"Shit1");
		map.put(new byte[]{(byte)0,2,0},"Shit2");
		map.put(new byte[]{(byte)0,0,2},"Shit3");
		
		map.put(new byte[]{(byte)1,0,2},"Shit1");
		map.put(new byte[]{(byte)0,1,2},"Shit2");
		map.put(new byte[]{(byte)0,0,3},"Shit3");
		
		assertEquals(9,map.size());
		map.clear();
		assertEquals(0,map.size());
	}

	@Test(timeout = 4000)
	public void test_15_214() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(0);
		
	}

	@Test(timeout = 4000)
	public void test_16_216() throws Throwable {
		// test here!
		ByteArrayHashMap map = new ByteArrayHashMap<>(2, 2);
		assertTrue(map.loadFactor == 2);
	}

	@Test(timeout = 4000)
	public void test_17_218() throws Throwable {
		// test here!
				ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		assertTrue(map.isEmpty());
	}

	@Test(timeout = 4000)
	public void test_18_219() throws Throwable {
		// test here!
				ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object hash = map.put(new byte[]{(byte)0},"Hash1");
		assertFalse(map.isEmpty());
		assertTrue(map.size() > 0);
	}

	@Test(timeout = 4000)
	public void test_19_220() throws Throwable {
		try{
			ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,-1f);
			fail("Expecting Illegal load factor");
		}catch(IllegalArgumentException e){
			assertTrue(e.getMessage().contains("load factor"));
		}
		
	}

	@Test(timeout = 4000)
	public void test_20_221() throws Throwable {
		// test here!
				ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object hash1 = map.put(new byte[]{(byte)0},"hash");
		assertTrue(map.containsKey(new byte[]{(byte)0}));
		map.clear();
		assertFalse(map.containsKey(new byte[]{(byte)0}));
	}

	@Test(timeout = 4000)
	public void test_21_222() throws Throwable {
		// test here!
		try{
			ByteArrayHashMap<Integer> blah = new ByteArrayHashMap<Integer>(42,0);
			fail("expected exception");
		} catch(Exception e){
            
		}
	}

	@Test(timeout = 4000)
	public void test_22_223() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		Object old = map.put(new byte[]{(byte)0},"Shit");
		assertNull(old);
		
		Object shit = map.put(new byte[]{(byte)0},"ShitArse");
		assertEquals("Shit",shit);
	}

	@Test(timeout = 4000)
	public void test_23_225() throws Throwable {
		assertEquals(1 << 30, ByteArrayHashMap.MAXIMUM_CAPACITY);
		assertEquals(0.75f, ByteArrayHashMap.DEFAULT_LOAD_FACTOR,0.0000001f);
	}

	@Test(timeout = 4000)
	public void test_24_226() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		assertEquals(map.size,map.size());
	}

	@Test(timeout = 4000)
	public void test_25_227() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		assertEquals(ByteArrayHashMap.DEFAULT_INITIAL_CAPACITY,map.table.length);
		
	}

	@Test(timeout = 4000)
	public void test_26_229() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		map.put("bollocks".getBytes(),"shit");
		map.removeEntryForKey("bollocks".getBytes());
		assertEquals(0,map.size());
		assertEquals(0,map.size);


		
	}

	@Test(timeout = 4000)
	public void test_27_230() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		map.createEntry(0, new byte[]{0}, "arse", 0);
		assertEquals(1,map.size);
		
	}

	@Test(timeout = 4000)
	public void test_28_234() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap();
		map.table = new ByteArrayHashMap.Entry[4];
		map.table[0] = new ByteArrayHashMap.Entry<Object>(1,new byte[]{1}, "shit", null);
		map.table[1] = map.table[0];
		map.table[3] = map.table[0];
		assertEquals(map.keys().size(),3);
	}

	@Test(timeout = 4000)
	public void test_29_235() throws Throwable {
		// test here!
				ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object hash1 = map.put(new byte[]{(byte)0},"hash");
		int MAXIMUM_CAPACITY = 1 << 30;
		int DEFAULT_INITIAL_CAPACITY = 16;
		float DEFAULT_LOAD_FACTOR = 0.75f;
		assertTrue(MAXIMUM_CAPACITY == map.MAXIMUM_CAPACITY);
		assertTrue(DEFAULT_INITIAL_CAPACITY == map.DEFAULT_INITIAL_CAPACITY);
		//assertTrue(DEFAULT_LOAD_FACTOR == map.DEFAULT_LOAD_FACTOR);
	}

	@Test(timeout = 4000)
	public void test_30_236() throws Throwable {
		// test here!
		float x = ByteArrayHashMap.DEFAULT_LOAD_FACTOR;
		assertEquals(x,0.75f,0.00001);
	}

	@Test(timeout = 4000)
	public void test_31_237() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1);
		assertEquals(1, map.table.length);
		assertEquals(0, map.size());
	}

	@Test(timeout = 4000)
	public void test_32_239() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.table = new ByteArrayHashMap.Entry[4];
		ByteArrayHashMap.Entry[] oldTable = map.table;
		map.resize(5);
		assertNotSame(map.table, oldTable);
		
	}

	@Test(timeout = 4000)
	public void test_33_240() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0},"ShitArse");
		assertEquals("ShitArse",map.put(new byte[]{(byte)0},"Shit"));
	}

	@Test(timeout = 4000)
	public void test_34_241() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0},"ShitArse");
		assertEquals("ShitArse",map.put(new byte[]{(byte)0},"Shit"));
	}

	@Test(timeout = 4000)
	public void test_35_242() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		Object old = map.put(new byte[]{(byte)0},"Shit");
		
		assertFalse(map.containsKey(new byte[]{0,0,0,0,0}));
	}

	@Test(timeout = 4000)
	public void test_36_243() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(2,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)1},"Shit");
		
		assertNotNull(map.table[0]);
		assertNotNull(map.table[1]);
	}

	@Test(timeout = 4000)
	public void test_37_245() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(40,50f);
		map.put(new byte[]{(byte)1,0},"Shiter");
		map.put(new byte[]{(byte)1,1},"Shited");
		
		assertEquals("Shiter",map.table[31].value);
		assertEquals("Shited",map.table[32].value);
		
	}

	@Test(timeout = 4000)
	public void test_38_246() throws Throwable {
		ByteArrayHashMap b = new ByteArrayHashMap(1000000, (float) 0.75);
    	assertEquals(b.table.length, 1048576);
    	assertTrue(true);
	}

	@Test(timeout = 4000)
	public void test_39_250() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(5);
		map.table[0] = new ByteArrayHashMap.Entry<Object>(1,"abc".getBytes(),"poo",null);
		map.table[1] = new ByteArrayHashMap.Entry<Object>(1,"def".getBytes(),"fart",null);
		map.table[2] = new ByteArrayHashMap.Entry<Object>(1,"ghi".getBytes(),"",null);
		
		map.keys();
		map.keys();
		map.keys();
		
		assertArrayEquals("abc".getBytes(),map.keys().get(0));
		assertArrayEquals("ghi".getBytes(),map.keys().get(2));
	}

	@Test(timeout = 4000)
	public void test_40_251() throws Throwable {
		try {
			ByteArrayHashMap map = new ByteArrayHashMap(-2);
			Assert.fail("Expected exception");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test(timeout = 4000)
	public void test_41_254() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0,0},"Shit");
		map.put(new byte[]{(byte)0,0,0},"Shit");
		
		int size = map.size();
		map.removeEntryForKey(new byte[]{1});
		assertEquals(size,map.size());
		
		size = map.size();
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		assertEquals(size-1,map.size());
	}

	@Test(timeout = 4000)
	public void test_42_255() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0,0},"Shit");
		map.put(new byte[]{(byte)0,0,0},"Shit");
		
		int size = map.size();
		map.removeEntryForKey(new byte[]{1});
		assertEquals(size,map.size());
		
		size = map.size();
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		assertEquals(size-1,map.size());
	}

	@Test(timeout = 4000)
	public void test_43_256() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0,0},"Shit");
		map.put(new byte[]{(byte)0,0,0},"Shit");
		
		int size = map.size();
		map.removeEntryForKey(new byte[]{1});
		assertEquals(size,map.size());
		
		size = map.size();
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		assertEquals(size-1,map.size());
	}

	@Test(timeout = 4000)
	public void test_44_257() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0,0},"Shit");
		map.put(new byte[]{(byte)0,0,0},"Shit");
		
		int size = map.size();
		map.removeEntryForKey(new byte[]{1});
		assertEquals(size,map.size());
		
		size = map.size();
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		assertEquals(size-1,map.size());
	}

	@Test(timeout = 4000)
	public void test_45_258() throws Throwable {
		ByteArrayHashMap<Object> map = new ByteArrayHashMap(1,0.5f);
		map.put(new byte[]{(byte)0},"Shit");
		map.put(new byte[]{(byte)0,0},"Shit");
		map.put(new byte[]{(byte)0,0,0},"Shit");
		
		int size = map.size();
		map.removeEntryForKey(new byte[]{1});
		assertEquals(size,map.size());
		
		size = map.size();
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		map.removeEntryForKey(new byte[]{0});
		assertEquals(size-1,map.size());
	}

	@Test(timeout = 4000)
	public void test_46_259() throws Throwable {
		assertEquals(16,ByteArrayHashMap.DEFAULT_INITIAL_CAPACITY);
	}

}
