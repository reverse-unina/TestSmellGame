import org.junit.*;
import static org.junit.Assert.*;

// Game: 1016
public class TimeStampTest {

	@Test(timeout = 4000)
	public void test_1_1962() throws Throwable {
		Long val = Long.MAX_VALUE;
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Tue, Feb 26 2104 09:42:24.000 UTCTue, Feb 26 2104 09:42:24.0007fffffff.ffffffff00000000.0000002a-214748364829942807392802276637fffffff.ffffffff4294967295423346214400021474836479223372036854775807",result);
	}

	@Test(timeout = 4000)
	public void test_2_1965() throws Throwable {
		Long val = Long.MIN_VALUE;
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Sat, Jan 20 1968 03:14:08.000 UTCSat, Jan 20 1968 03:14:08.00080000000.0000000000000000.0000002a-2147483648-347033357580000000.000000000-615051520002147483648-9223372036854775808",result);
	}

	@Test(timeout = 4000)
	public void test_3_1968() throws Throwable {
		Long val = 0l;
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Feb 07 2036 06:28:16.000 UTCThu, Feb 07 2036 06:28:16.00000000000.0000000000000000.0000002a0-895920942047926681600000000.000000000208597849600000",result);
	}

	@Test(timeout = 4000)
	public void test_4_1983() throws Throwable {
		java.util.Date val = new java.util.Date(2016, 11, 1, 23, 42);
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Tue, Jun 28 2011 05:06:16.000 UTCTue, Jun 28 2011 06:06:16.000d1b3dfc8.000000007fffffff.ffffffff-776740920d1b3dfc8.00000000013092375760003518226376-3336076848864952320",result);
		ts = new TimeStamp(ts.toString());
		result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Tue, Jun 28 2011 05:06:16.000 UTCTue, Jun 28 2011 06:06:16.000d1b3dfc8.000000007fffffff.ffffffff-776740920d1b3dfc8.00000000013092375760003518226376-3336076848864952320",result);
	}

	@Test(timeout = 4000)
	public void test_5_1988() throws Throwable {
		java.util.Date val = null;
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Feb 07 2036 06:28:16.000 UTCThu, Feb 07 2036 06:28:16.00000000000.000000007fffffff.ffffffff000000000.000000000208597849600000",result);
		ts = new TimeStamp(ts.toString());
		result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Feb 07 2036 06:28:16.000 UTCThu, Feb 07 2036 06:28:16.00000000000.000000007fffffff.ffffffff000000000.000000000208597849600000",result);
	}

	@Test(timeout = 4000)
	public void test_6_1991() throws Throwable {
		java.util.Date val = null;
		TimeStamp ts = new TimeStamp(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		TimeStamp ts2 = new TimeStamp(42);
		assertEquals("-101","" + ts.compareTo(ts2)+ ts.compareTo(ts) + ts2.compareTo(ts));
		assertEquals("falsetruefalse","" + ts.equals(ts2)+ ts.equals(ts) + ts2.equals(ts));
	}

	@Test(timeout = 4000)
	public void test_7_1994() throws Throwable {
		TimeStamp ts = TimeStamp.getCurrentTime();
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		TimeStamp ts2 = new TimeStamp(ts.toString());
		assertEquals("000","" + ts.compareTo(ts2)+ ts.compareTo(ts) + ts2.compareTo(ts));
		assertEquals("truetruetrue","" + ts.equals(ts2)+ ts.equals(ts) + ts2.equals(ts));
	}

	@Test(timeout = 4000)
	public void test_8_1999() throws Throwable {
		Long ts = TimeStamp.getCurrentTime().getTime();
		Long ts2 = TimeStamp.getCurrentTime().getTime();
		assertTrue(((ts2-ts)>=0));
		assertTrue(ts > 1471453130667l);

	}

	@Test(timeout = 4000)
	public void test_9_2001() throws Throwable {
		TimeStamp ts = new TimeStamp(42);
		assertTrue(ts instanceof java.io.Serializable);
		assertTrue(ts instanceof java.lang.Comparable);
	}

	@Test(timeout = 4000)
	public void test_10_2011() throws Throwable {
		TimeStamp ts = new TimeStamp(Long.MAX_VALUE-1);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		long hex2time = ts.decodeNtpHexString(ts.toString());
		TimeStamp ts2 = new TimeStamp(hex2time);
		result += ts.decodeNtpHexString(ts.toString());
		result += ts2.decodeNtpHexString(ts2.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Tue, Feb 26 2104 09:42:24.000 UTCTue, Feb 26 2104 09:42:24.0007fffffff.fffffffe7fffffff.ffffffff-2147483647922337203685477580692233720368547758064294967294423346214400021474836479223372036854775806", result);
	}

	@Test(timeout = 4000)
	public void test_11_2019() throws Throwable {
		long hex2time = TimeStamp.decodeNtpHexString("ffffffff");
		TimeStamp ts = new TimeStamp(hex2time);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		
		try{
			result += ts.decodeNtpHexString("0007fffffff");
			result += ts.decodeNtpHexString("0007ff");
			
			result += ts.decodeNtpHexString("f2.2f");
			result += ts.decodeNtpHexString("f2f2f2f2.fafafafa");
			result += ts.decodeNtpHexString("");
			result += ts.decodeNtpHexString(null);
			fail();
		}
		catch(NullPointerException npe){assertEquals("null", npe.getMessage());}
		catch(java.lang.NumberFormatException e){}
		
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Feb 07 2036 06:28:15.000 UTCThu, Feb 07 2036 06:28:15.000ffffffff.000000007fffffff.ffffffff-1922337203255980851287917980549121039382085679-9404222467602526780020859784950004294967295-4294967296", result);
	}

	@Test(timeout = 4000)
	public void test_12_2020() throws Throwable {
		TimeStamp ts = new TimeStamp(42);
		assertFalse(ts.equals("42"));
	}

	@Test(timeout = 4000)
	public void test_13_2027() throws Throwable {
		Long val = 1234l;
		TimeStamp ts = TimeStamp.getNtpTime(val);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		Long t = null;
		try{
		ts = TimeStamp.getNtpTime(t);
			fail();
		}catch(NullPointerException npe){}
		result += ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		ts = TimeStamp.getNtpTime(Long.MAX_VALUE);
		result += ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		ts = TimeStamp.getNtpTime(Long.MIN_VALUE);
		result += ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(42) + ts.hashCode() + ts.toNtpTime(val);
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.parseNtpString(ts.toString());
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Jan 01 1970 00:00:01.234 UTCThu, Jan 01 1970 01:00:01.23483aa7e81.3be76c8b00000000.0000002a-1202908662-895920941517927717383aa7e81.3be76c8b100502234712342208988801-8959209415179277173Thu, Jan 01 1970 00:00:01.234 UTCThu, Jan 01 1970 01:00:01.23483aa7e81.3be76c8b00000000.0000002a-1202908662-895920941517927717383aa7e81.3be76c8b100502234712342208988801-8959209415179277173Tue, Mar 12 2058 06:06:47.807 UTCTue, Mar 12 2058 06:06:47.807298dd277.ce978d4f00000000.0000002a-417702088-8959209415179277173298dd277.ce978d4f346603860727831388078076971603112994280739280227663Thu, Feb 07 2036 06:28:15.192 UTCThu, Feb 07 2036 06:28:15.192ffffffff.3126e97900000000.0000002a-824633722-8959209415179277173ffffffff.3126e97982463372120859784951924294967295-3470333575",result);
	}

	@Test(timeout = 4000)
	public void test_14_2069() throws Throwable {
		long hex2time = TimeStamp.decodeNtpHexString("ffffffff");
		TimeStamp ts = new TimeStamp(hex2time);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.decodeNtpHexString("f2f2f2f2.fafafafa");
		result += ts.decodeNtpHexString("c1a089bd.fc904f6d");
		result += ts.decodeNtpHexString("c1a089bd");
		result += ts.decodeNtpHexString("fc904f6d");
		
		try{
			result += ts.decodeNtpHexString(".fc904f6d");
			fail();
		}
		catch(java.lang.NumberFormatException e){result+= ">>"+e.getMessage();}
		
		try{
			result += ts.decodeNtpHexString("fc904f6d.");
			fail();
		}
		catch(java.lang.NumberFormatException e){result+= ">>"+e.getMessage();}
		
		
		try{
			result += ts.decodeNtpHexString("0007fffffff");
			result += ts.decodeNtpHexString("0007ff");
			
			result += ts.decodeNtpHexString("f2.2f");
			
			result += ts.decodeNtpHexString("");
			result += ts.decodeNtpHexString(null);
			fail();
		}
		catch(NullPointerException npe){result+= ">>>"+npe.getMessage();}
		catch(java.lang.NumberFormatException e){result+= ">>>>"+e.getMessage();}
		
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result += ts.ntpValue();
		result += ts.ntpValue();
		result = result.replaceAll("\n","---").replaceAll("\r","+++");
		assertEquals("Thu, Feb 07 2036 06:28:15.000 UTCThu, Feb 07 2036 06:28:15.000ffffffff.000000007fffffff.ffffffff-1-940422246760252678-4494440979036614803-4494440983273930752-247610649935347712>>For input string: \"\">>For input string: \"\"9223372032559808512879179805491210393820856790>>>>null02085978495000208597849500042949672954294967295-4294967296-4294967296-4294967296", result);
	}

	@Test(timeout = 4000)
	public void test_15_2077() throws Throwable {
		long hex2time = TimeStamp.decodeNtpHexString("ffffffff.ffffffff");
		TimeStamp ts = new TimeStamp(hex2time);
		String result = ts.toUTCString() + ts.toDateString() + ts.toString() + ts.toString(Long.MAX_VALUE) + ts.hashCode();
		//result += ts.parseNtpString(ts.toUTCString());
		//result += ts.parseNtpString(ts.toDateString());
		result += ts.decodeNtpHexString("f2f2f2f2.fafafafa");
		result += ts.decodeNtpHexString("c1a089bd.fc904f6d");
		result += ts.decodeNtpHexString("c1a089bd");
		result += ts.decodeNtpHexString("fc904f6d");
		
		try{
			result += ts.decodeNtpHexString(".fc904f6d");
			fail();
		}
		catch(java.lang.NumberFormatException e){result+= "<<"+e.getMessage() + e.getClass().getCanonicalName();}
		
		try{
			result += ts.decodeNtpHexString("fc904f6d.");
			fail();
		}
		catch(java.lang.NumberFormatException e){result+= ">>"+e.getMessage() + e.getClass().getCanonicalName();}
		
		
		try{
			result += ts.decodeNtpHexString("0007fffffff");
			result += ts.decodeNtpHexString("0007ff");
			
			result += ts.decodeNtpHexString("f2.2f");
			
			result += ts.decodeNtpHexString("");
			result += ts.decodeNtpHexString(null);
			fail();
		}
		catch(NullPointerException npe){result+= ">>>"+npe.getMessage();}
		catch(java.lang.NumberFormatException e){result+= ">>>>"+e.getMessage() + e.getClass().getCanonicalName();}
		
		
		
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.getFraction();
		result += ts.ntpValue();
		result += ts.ntpValue();
		result += ts.getTime();
		result += ts.getSeconds();
		result += ts.ntpValue();
		result += ts.getFraction();
		result += ts.getTime();
		result += ts.ntpValue();
		result += ts.getClass().getDeclaredFields().length;
		result = result.replaceAll("\n","---").replaceAll("\r","+++").replaceAll("\"","_");
		assertEquals("-374748652", ""+result.hashCode());
	}

}
