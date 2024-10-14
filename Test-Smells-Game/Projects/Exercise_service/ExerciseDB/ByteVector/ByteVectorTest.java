import org.junit.*;
import static org.junit.Assert.*;

// Game: 1002
public class ByteVectorTest {

	@Test(timeout = 4000)
	public void test_1_260() throws Throwable {
		ByteVector bv = new ByteVector();
		assertEquals(bv.data.length,64);
	}

	@Test(timeout = 4000)
	public void test_2_261() throws Throwable {
			int i = 0;
			ByteVector bv = new ByteVector(i);
			assertEquals(bv.data.length,i);
		
			
	}

	@Test(timeout = 4000)
	public void test_3_262() throws Throwable {
		int i = 42;
		ByteVector bv = new ByteVector(i);
		assertEquals(bv.data.length,i);
	}

	@Test(timeout = 4000)
	public void test_4_264() throws Throwable {
		int i = -1;
		try{
			ByteVector bv = new ByteVector(i);
			fail("had to fail");
		}catch(NegativeArraySizeException e){}
	}

	@Test(timeout = 4000)
	public void test_5_265() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putByte(42);
		assertEquals((int)bv.data[0],42);
	}

	@Test(timeout = 4000)
	public void test_6_267() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putByte(42);
		bv = bv.putByte(0);
		bv = bv.putByte(1);
		assertEquals((int)bv.data[2],1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_7_268() throws Throwable {
		int i = 4;
		ByteVector bv = new ByteVector(4);
		bv = bv.putByte(42);
		bv = bv.putByte(0);
		bv = bv.putByte(1);
		assertEquals((int)bv.data[2],1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_8_270() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.put11(42,24);
		assertEquals((int)bv.data[1],24);
		assertEquals((int)bv.data.length,2);
	}

	@Test(timeout = 4000)
	public void test_9_272() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putShort(42);
		bv = bv.putShort(0);
		assertEquals((int)bv.data[1],42);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_10_275() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putShort(Integer.MAX_VALUE);
		bv = bv.putShort(0);
		assertEquals((int)bv.data[1],-1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_11_277() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.put12(42, 24);
		assertEquals((int)bv.data[2],24);
		assertEquals((int)bv.data.length,3);
	}

	@Test(timeout = 4000)
	public void test_12_279() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putInt(Integer.MAX_VALUE);
		assertEquals((int)bv.data[1],-1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_13_281() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putInt(Integer.MAX_VALUE);
		assertEquals((int)bv.data[0],127);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_14_282() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putInt(Integer.MAX_VALUE);
		assertEquals((int)bv.data[2],-1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_15_283() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putInt(Integer.MAX_VALUE);
		assertEquals((int)bv.data[3],-1);
		assertEquals((int)bv.data.length,4);
	}

	@Test(timeout = 4000)
	public void test_16_286() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(Long.MAX_VALUE);
		assertEquals((int)bv.data[0],127);
		assertEquals((int)bv.data.length,8);
	}

	@Test(timeout = 4000)
	public void test_17_287() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(Long.MAX_VALUE);
		assertEquals((int)bv.data[1],-1);
		assertEquals((int)bv.data[2],-1);
	}

	@Test(timeout = 4000)
	public void test_18_288() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(Long.MAX_VALUE);
		assertEquals((int)bv.data[3],-1);
		assertEquals((int)bv.data[4],-1);
	}

	@Test(timeout = 4000)
	public void test_19_289() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(Long.MAX_VALUE);
		assertEquals((int)bv.data[5],-1);
		assertEquals((int)bv.data[6],-1);
	}

	@Test(timeout = 4000)
	public void test_20_290() throws Throwable {
		int i = 8;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(Long.MAX_VALUE);
		assertEquals((int)bv.data[7],-1);
		assertEquals((int)bv.data.length,8);
	}

	@Test(timeout = 4000)
	public void test_21_291() throws Throwable {
		int i = 9;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(0);
		assertEquals((int)bv.data[7],0);
		assertEquals((int)bv.data.length,9);
	}

	@Test(timeout = 4000)
	public void test_22_294() throws Throwable {
		int i = 7;
		ByteVector bv = new ByteVector(i);
		bv = bv.putLong(42);
		assertEquals((int)bv.data[7],42);
		assertEquals((int)bv.data.length,14);
	}

	@Test(timeout = 4000)
	public void test_23_296() throws Throwable {
		int i = 9;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("\u6002");
		assertEquals((int)bv.data[3],-128);
		assertEquals((int)bv.data.length,9);
	}

	@Test(timeout = 4000)
	public void test_24_299() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("\u6002");
		assertEquals((int)bv.data[2],-26);
		assertEquals((int)bv.data.length,7);
	}

	@Test(timeout = 4000)
	public void test_25_303() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("\u6002\u0001");
		assertEquals((int)bv.data[5],1);
		assertEquals((int)bv.data.length,8);
	}

	@Test(timeout = 4000)
	public void test_26_306() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		assertEquals((int)bv.data[11],-126);
		assertEquals((int)bv.data.length,17);
	}

	@Test(timeout = 4000)
	public void test_27_310() throws Throwable {
		int i = 1207;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u0177\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002\u07FF");
		assertEquals((int)bv.data[111],-26);
		assertEquals((int)bv.data.length,1207);
	}

	@Test(timeout = 4000)
	public void test_28_314() throws Throwable {
		int i = 1206;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("\uFF6E\uFFFF\u0000XYZ\u0177\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002XYZ\u6002\u0001\u6002\u07FF");
		assertEquals((int)bv.data[1027],0);
		assertEquals((int)bv.data.length,1206);
	}

	@Test(timeout = 4000)
	public void test_29_315() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 0, 5);
		assertEquals((int)bv.data[7],-126);
		assertEquals((int)bv.data.length,17);
	}

	@Test(timeout = 4000)
	public void test_30_318() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 1, 3);
		assertEquals((int)bv.data[14],89);
		assertEquals((int)bv.data.length,17);
	}

	@Test(timeout = 4000)
	public void test_31_320() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 1, 3);
		bv = bv.putByteArray(x, 3, 1);
		bv = bv.putByteArray(x, 10, 5);
		assertEquals((int)bv.data[14],89);
		assertEquals((int)bv.data.length,34);
	}

	@Test(timeout = 4000)
	public void test_32_326() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 1, 3);
		bv = bv.putByteArray(x, 3, 1);
		bv = bv.putByteArray(x, 10, 5);
		
		try{
			bv = bv.putByteArray(x, -1, 2);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		try{
			bv = bv.putByteArray(x, 142, 0);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, 10, 50);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		assertEquals((int)bv.data[30],0);
		assertEquals((int)bv.data.length,71);
	}

	@Test(timeout = 4000)
	public void test_33_331() throws Throwable {
		int i = 20;
		ByteVector bv = new ByteVector(i);
		bv.putByte(0);
		bv.putByte(-100);
		bv.put11(2000,-1);
		bv.putShort(12312312);
		bv.put12(123,Integer.MIN_VALUE);
		bv.putInt(100000000);
		bv.putLong(Long.MIN_VALUE);
		bv = bv.putUTF8("\u1F00\u3F000xC0");
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 5, 2);
		
		
		try{
			bv = bv.putByteArray(x, 2, 100);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, 100, 5);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, -1, 2);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		try{
			bv = bv.putByteArray(x, 1420, 0);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, 10, 5000);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		assertEquals((int)bv.data[1731],0);
		assertEquals((int)bv.data.length,5047);
	}

	@Test(timeout = 4000)
	public void test_34_336() throws Throwable {
		int i = 600;
		ByteVector bv = new ByteVector(i);
		bv.putByte(0);
		bv.putByte(-100);
		bv.put11(2000,-1);
		bv.putShort(12312312);
		bv.put12(123,Integer.MIN_VALUE);
		bv.putInt(100000000);
		bv.putLong(Long.MIN_VALUE);
		bv = bv.putUTF8("\u1F00\u3F000xC0");
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 5, 2);
		bv = bv.putByteArray(x, 2, 100);
		bv = bv.putByteArray(x, 100, 5);
		
		try{
			bv = bv.putByteArray(x, -1, 2);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		try{
			bv = bv.putByteArray(x, 1420, 0);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, 10, 5000);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		assertEquals((int)bv.data[47],-48);
		assertEquals((int)bv.data.length,5152);
	}

	@Test(timeout = 4000)
	public void test_35_362() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putUTF8("\u07F0");
		assertEquals((int)bv.data[2],-33);
		assertEquals((int)bv.data.length,6);
	}

	@Test(timeout = 4000)
	public void test_36_376() throws Throwable {
		ByteVector byteVector = new ByteVector();
		byteVector.length = 50;
		byte[] byteArr = new byte[20];
		byteVector.putByteArray(byteArr, 0, 20);	
		assertEquals(byteVector.data.length, 128);
		
		ByteVector byteVector2 = new ByteVector(0);
		byteVector2.putByteArray(byteArr, 0, 20);
		assertEquals(byteVector2.data.length, 20);
		
	}

	@Test(timeout = 4000)
	public void test_37_379() throws Throwable {
		byte[] byteArr = new byte[]{1,2,3,4,5,6,7,8,9,10};	
		ByteVector byteVector = new ByteVector(0);
		byteVector.putByteArray(byteArr, 0, 10);
		assertEquals(byteVector.data.length, 10);
		assertArrayEquals(byteArr, byteVector.data);
	}

	@Test(timeout = 4000)
	public void test_38_382() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.put12(42, 24);
		assertEquals((int)bv.data[0],42);
		assertEquals((int)bv.data[1],0);
	}

	@Test(timeout = 4000)
	public void test_39_385() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.put12(Integer.MAX_VALUE, Integer.MAX_VALUE);
		assertEquals((int)bv.data[0],-1);
		assertEquals((int)bv.data[1],-1);
	}

	@Test(timeout = 4000)
	public void test_40_386() throws Throwable {
		int i = 5;
		ByteVector bv = new ByteVector(i);
		bv = bv.put12(Integer.MAX_VALUE, Integer.MAX_VALUE);
		assertEquals((int)bv.data[1],-1);
		assertEquals((int)bv.data[2],-1);
	}

	@Test(timeout = 4000)
	public void test_41_389() throws Throwable {
		int i = 5;
		ByteVector bv = new ByteVector(i);
		bv = bv.put12(1000,-1000);
		assertEquals((int)bv.data[1],-4);
		assertEquals((int)bv.data[2],24);
	}

	@Test(timeout = 4000)
	public void test_42_390() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		bv = bv.putInt(Integer.MAX_VALUE);
		assertEquals((int)bv.data[1],-1);
		assertEquals((int)bv.data[3],-1);
	}

	@Test(timeout = 4000)
	public void test_43_392() throws Throwable {
		int i = 300;
		ByteVector bv = new ByteVector(i);
		bv.putByte(0);
		bv.putByte(-100);
		bv.put11(2000,-1);
		bv.putShort(12312312);
		bv.put12(123,Integer.MIN_VALUE);
		bv.putInt(100000000);
		bv.putLong(Long.MIN_VALUE);
		bv = bv.putUTF8("\u1F00\u3F000xC0");
		bv = bv.putUTF8("XYZ\u6002\u0001\u6002");
		byte[] x= bv.data;
		bv = bv.putByteArray(x, 5, 2);
		bv = bv.putByteArray(x, 2, 100);
		bv = bv.putByteArray(x, 100, 5);
		
		try{
			bv = bv.putByteArray(x, -1, 2);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		try{
			bv = bv.putByteArray(x, 1420, 0);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		
		try{
			bv = bv.putByteArray(x, 10, 5000);
			fail("should've failed");
		} catch (ArrayIndexOutOfBoundsException e){}
		bv = bv.putByteArray(x, 5, 5);
		bv = bv.putByteArray(x, 1, 0);
		bv = bv.putByteArray(null, 1, 0);
		assertEquals((int)bv.data[23],-31);
		assertEquals((int)bv.data.length,5152);
	}

	@Test(timeout = 4000)
	public void test_44_394() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("\u0800");
	}

	@Test(timeout = 4000)
	public void test_45_396() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("a");
		assertEquals(1,b.data[1]);
	}

	@Test(timeout = 4000)
	public void test_46_397() throws Throwable {
		String a = "aaaa";
		a = a + a + a + a;
		a = a + a;
		a = a + a;
		a = a + a;
		a = a + a;
		ByteVector bv = new ByteVector();
		bv.putUTF8(a);
		assertEquals(1,bv.data[0]);
	}

	@Test(timeout = 4000)
	public void test_47_398() throws Throwable {
		ByteVector b = new ByteVector();
		byte[] x = new byte[260];
		x[256] = 1;
		x[257] = 1;
		x[258] = 1;
		
		b.putByteArray(x,256,2);
		assertEquals(1,b.data[0]);
	}

	@Test(timeout = 4000)
	public void test_48_400() throws Throwable {
		ByteVector b = new ByteVector();
		b.putByteArray(null,0,256);
		assertEquals(256,b.length);
	}

	@Test(timeout = 4000)
	public void test_49_417() throws Throwable {
		int i = Integer.MAX_VALUE+1;
		try{
		ByteVector bv = new ByteVector(i);
		fail("...");
		} catch(NegativeArraySizeException e){}
	}

	@Test(timeout = 4000)
	public void test_50_420() throws Throwable {
		int i = 1;
		ByteVector bv = new ByteVector(i);
		assertEquals((int)bv.data.length,i);
		
		i = 1000000;
		ByteVector bv2 = new ByteVector(i);
		assertEquals((int)bv2.data.length,i);
	}

	@Test(timeout = 4000)
	public void test_51_424() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("ab");
		assertEquals(0,b.data[4]);
		assertEquals(0,b.data[8]);
	}

	@Test(timeout = 4000)
	public void test_52_425() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("ab");
		assertEquals(0,b.data[10]);
		assertEquals(0,b.data[14]);
	}

	@Test(timeout = 4000)
	public void test_53_429() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("\u0000\uFFFF");
		assertEquals(0,b.data[15]);
		assertEquals(0,b.data[16]);
	}

	@Test(timeout = 4000)
	public void test_54_432() throws Throwable {
		ByteVector b = new ByteVector();
		b.putUTF8("\u0000\uFFFF");
		assertEquals(0,b.data[20]);
		assertEquals(0,b.data[24]);
	}

	@Test(timeout = 4000)
	public void test_55_435() throws Throwable {
		ByteVector b = new ByteVector(1);
		b.putUTF8("\u0000\uFFFF");
		assertEquals(-64,b.data[2]);
		assertEquals(-65,b.data[5]);
	}

}
