import org.junit.*;
import static org.junit.Assert.*;

// Game: 1008
public class ImprovedStreamTokenizerTest {

	@Test(timeout = 4000)
	public void test_1_2172() throws Throwable {
		// test whitespace chars
		String input = "a b\tc\nd\re";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		String result = "";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "a,b,c,d,e");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_2_2173() throws Throwable {
		// test word chars
		String input = "!#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		String result = tokenizer.nextWord();

		assertEquals(result, input);
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_3_2174() throws Throwable {
		// test double quote chars
		String input = "\"abc\"\"123\"";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		String result = "";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();
		assertEquals(result, "abc,123");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_4_2175() throws Throwable {
		// Integer parsing
		String input = "1 23 45 6";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextInteger() + ",";
		result += tokenizer.nextInteger() + ",";
		result += tokenizer.nextInteger() + ",";
		result += tokenizer.nextInteger();

		assertEquals(result, "1,23,45,6");
		assertNull(tokenizer.nextInteger());
	}

	@Test(timeout = 4000)
	public void test_5_2176() throws Throwable {
		// int parsing
		String input = "1 23 45 6";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextInt() + ",";
		result += tokenizer.nextInt() + ",";
		result += tokenizer.nextInt() + ",";
		result += tokenizer.nextInt();

		try
		{
		  result += tokenizer.nextInt();
		}
		catch(java.io.IOException e)
		{
		  assertEquals(e.getMessage(), "unexpected end of input");
		}
		assertEquals(result, "1,23,45,6");
	}

	@Test(timeout = 4000)
	public void test_6_2177() throws Throwable {
		// Boolean parsing
		String input = "0 1 true false";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean();

		assertEquals(result, "false,false,true,false");
		assertNull(tokenizer.nextBoolean());
	}

	@Test(timeout = 4000)
	public void test_7_2178() throws Throwable {
		// bool parsing
		String input = "0 1 true false";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextBool() + ",";
		result += tokenizer.nextBool() + ",";
		result += tokenizer.nextBool() + ",";
		result += tokenizer.nextBool();

		try
		{
		  result += tokenizer.nextBool();
		}
		catch(java.io.IOException e)
		{
		  assertEquals(e.getMessage(), "unexpected end of input");
		}
		assertEquals(result, "false,false,true,false");
	}

	@Test(timeout = 4000)
	public void test_8_2179() throws Throwable {
		// Byte object parsing
		String input = "10 20 30 40";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextByteObject() + ",";
		result += tokenizer.nextByteObject() + ",";
		result += tokenizer.nextByteObject() + ",";
		result += tokenizer.nextByteObject();

		assertEquals(result, "16,32,48,64");
		assertNull(tokenizer.nextByteObject());
	}

	@Test(timeout = 4000)
	public void test_9_2180() throws Throwable {
		// byte parsing
		String input = "10 20 30 40";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextByte() + ",";
		result += tokenizer.nextByte() + ",";
		result += tokenizer.nextByte() + ",";
		result += tokenizer.nextByte();

		try
		{
		  result += tokenizer.nextByte();
		}
		catch(NullPointerException e)
		{
		  assertNull(e.getMessage());
		}
		assertEquals(result, "16,32,48,64");
	}

	@Test(timeout = 4000)
	public void test_10_2181() throws Throwable {
		// Integer parsing exceptions
		String input = "1 2.3 4,5 a - \n \"";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextInteger() + ",";
		try
		{
		  result += tokenizer.nextInteger() + ",";
		}
		catch (NumberFormatException e)
		{
		  result += "For input string: \"2.3\"" + ",";
		}
		try
		{
		  result += tokenizer.nextInteger() + ",";
		}
		catch (NumberFormatException e)
		{
		  result += "For input string: \"4,5\"" + ",";
		}
		try
		{
		  result += tokenizer.nextInteger() + ",";
		}
		catch (NumberFormatException e)
		{
		  result += "For input string: \"a\"" + ",";
		}
		try
		{
		  result += tokenizer.nextInteger() + ",";
		}
		catch (NumberFormatException e)
		{
		  result += "For input string: \"-\"" + ",";
		}
		try
		{
		  result += tokenizer.nextInteger();
		}
		catch (java.io.IOException e)
		{
		  result += "non-number" + ",";
		}
		result += tokenizer.nextInteger();

		assertEquals(result, "1,For input string: \"2.3\",For input string: \"4,5\",For input string: \"a\",For input string: \"-\",non-number,null");
		assertNull(tokenizer.nextInteger());
	}

	@Test(timeout = 4000)
	public void test_11_2182() throws Throwable {
		// Boolean parsing exceptions
		String input = "99 abc - \n [ ] $ \"";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		try
		{
		  result += tokenizer.nextBoolean() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-boolean" + ",";
		}
		result += tokenizer.nextBoolean();

		assertEquals(result, "false,false,false,false,false,false,non-boolean,null");
		assertNull(tokenizer.nextBoolean());
	}

	@Test(timeout = 4000)
	public void test_12_2183() throws Throwable {
		// Byte object parsing exceptions
		String input = "3 $ \n 333 a aa [] \"";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);

		String result = "";
		try
		{
		  result += tokenizer.nextByteObject() + ",";
		}
		catch (StringIndexOutOfBoundsException e)
		{
		  result += "String index out of range: 1" + ",";
		}
		try
		{
		  result += tokenizer.nextByteObject() + ",";
		}
		catch (StringIndexOutOfBoundsException e)
		{
		  result += "String index out of range: 1" + ",";
		}
		result += tokenizer.nextByteObject() + ",";
		try
		{
		  result += tokenizer.nextByteObject() + ",";
		}
		catch (StringIndexOutOfBoundsException e)
		{
		  result += "String index out of range: 1" + ",";
		}
		result += tokenizer.nextByteObject() + ",";
		result += tokenizer.nextByteObject() + ",";
		try
		{
		  result += tokenizer.nextByteObject();
		}
		catch (java.io.IOException e)
		{
		  result += "non-byte" + ",";
		}
		result += tokenizer.nextByteObject();

		assertEquals(result, "String index out of range: 1,String index out of range: 1,51,String index out of range: 1,-86,-1,non-byte,null");
		assertNull(tokenizer.nextByteObject());
	}

	@Test(timeout = 4000)
	public void test_13_2184() throws Throwable {
		// test custom syntax whitespaces and quotes
		String input = "a b\tc\nd\re|f||g\"a b\"\"=";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" |", "\"", false);
		String result = "";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "a,b,non-string,c,d,e,f,g,a b,=");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_14_2185() throws Throwable {
		// test custom syntax word chars
		String input = "!#$%&()*+,-./0123456789:;<=>?@ ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", false);
		String result = tokenizer.nextWord() + "," + tokenizer.nextWord() + "," + tokenizer.nextWord();

		assertEquals(result, "!#$%&()*+,-,/0123456789:;<=>?@,ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_15_2186() throws Throwable {
		// test charToHex
		String result = "";
		result += ImprovedStreamTokenizer.charToHex('0');
		result += ImprovedStreamTokenizer.charToHex('1');
		result += ImprovedStreamTokenizer.charToHex('2');
		result += ImprovedStreamTokenizer.charToHex('3');
		result += ImprovedStreamTokenizer.charToHex('4');
		result += ImprovedStreamTokenizer.charToHex('5');
		result += ImprovedStreamTokenizer.charToHex('6');
		result += ImprovedStreamTokenizer.charToHex('7');
		result += ImprovedStreamTokenizer.charToHex('8');
		result += ImprovedStreamTokenizer.charToHex('9');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('A');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('B');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('C');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('a');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('b');
		result += ",";
		result += ImprovedStreamTokenizer.charToHex('c');

		assertEquals(result, "0123456789,10,11,12,42,43,44");
	}

	@Test(timeout = 4000)
	public void test_16_2187() throws Throwable {
		// test comments
		String input = "x\n//y\nz";
		String result = "";

		// test case 1: no commentChar
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";

		// test case 2: commentChar set to '/'
		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.commentChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,//y,z,x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_17_2188() throws Throwable {
		// test custom syntax comments
		String input = "x\n//y\nz";
		String result = "";

		// test case 1: useSlashSlashComments set to false
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", false);
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";

		// test case 2: useSlashSlashComments set to true
		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", true);
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";

		// test case 3: useSlashSlashComments set to false, commentChar set to '/'
		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", false);
		tokenizer.commentChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";

		// test case 4: useSlashSlashComments set to true, commentChar set to '/'
		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", true);
		tokenizer.commentChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,//y,z,x,//y,z,x,z,x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_18_2189() throws Throwable {
		// test boolean parsing with numbers
		String input = "0 1 2";
		String result = "";

		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.parseNumbers();
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean();

		assertEquals(result, "false,true,true");
		assertNull(tokenizer.nextBoolean());
	}

	@Test(timeout = 4000)
	public void test_19_2190() throws Throwable {
		// test custom syntax constructor whitespaces and quotes
		String input = "a b\tc\nd\re|f||g\"a b\"\"=";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader, " |", "\"", false);
		String result = "";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "a,b,non-string,c,d,e,f,g,a b,=");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_20_2191() throws Throwable {
		// test custom syntax constructor word chars
		String input = "!#$%&()*+,-./0123456789:;<=>?@ ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader, " .", "\"", false);
		String result = tokenizer.nextWord() + "," + tokenizer.nextWord() + "," + tokenizer.nextWord();

		assertEquals(result, "!#$%&()*+,-,/0123456789:;<=>?@,ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_21_2192() throws Throwable {
		// test custom syntax comments: this time '/' is no longer an ordinary character
		String input = "x\n//y\nz";
		String result = "";

		// test case 1: useSlashSlashComments set to false
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", false);
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		result += tokenizer.nextWord() + ",";

		// test case 2: useSlashSlashComments set to true
		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax(" .", "\"", true);
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,non-string,non-string,y,x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_22_2193() throws Throwable {
		// test comments: this time '/' is no longer an ordinary character
		String input = "x\n//y\nz";
		String result = "";

		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_23_2194() throws Throwable {
		// test comments with manual initializeSyntax: this time '/' is no longer an ordinary character
		String input = "x\n//y\nz";
		String result = "";

		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax();
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_24_2195() throws Throwable {
		// test custom syntax comments: this time '/' is no longer an ordinary character
		String input = "x\n//y\nz";
		String result = "";

		// test case 1: useSlashSlashComments set to false
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader, " .", "\"", false);
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		try
		{
		  result += tokenizer.nextWord() + ",";
		}
		catch (java.io.IOException e)
		{
		  result += "non-string" + ",";
		}
		result += tokenizer.nextWord() +",";

		reader = new java.io.StringReader(input);
		tokenizer = new ImprovedStreamTokenizer(reader, " .", "\"", true);
		tokenizer.ordinaryChar('/');
		result += tokenizer.nextWord() + ",";
		result += tokenizer.nextWord();

		assertEquals(result, "x,non-string,non-string,y,x,z");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_25_2196() throws Throwable {
		// test boolean parsing with numbers (with negative numbers this time)
		String input = "-10000000 -2 -1 0 1 2 1000000";
		String result = "";

		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.parseNumbers();
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean() + ",";
		result += tokenizer.nextBoolean();

		assertEquals(result, "true,true,true,false,true,true,true");
		assertNull(tokenizer.nextBoolean());
	}

	@Test(timeout = 4000)
	public void test_26_2197() throws Throwable {
		// test parseByte
		String result = "";
		result += ImprovedStreamTokenizer.parseByte("aa") + ",";
		result += ImprovedStreamTokenizer.parseByte("AA") + ",";
		try
		{
		  result += ImprovedStreamTokenizer.parseByte("a");
		}
		catch (StringIndexOutOfBoundsException e)
		{
		  result += "String index out of range: 1";
		}

		assertEquals(result, "-86,-86,String index out of range: 1");
	}

	@Test(timeout = 4000)
	public void test_27_2198() throws Throwable {
		// test parseByte part 2
		String result = "";
		result += ImprovedStreamTokenizer.parseByte("01") + ",";
		result += ImprovedStreamTokenizer.parseByte("23") + ",";
		result += ImprovedStreamTokenizer.parseByte("34") + ",";
		result += ImprovedStreamTokenizer.parseByte("45") + ",";
		result += ImprovedStreamTokenizer.parseByte("56") + ",";
		result += ImprovedStreamTokenizer.parseByte("67") + ",";
		result += ImprovedStreamTokenizer.parseByte("78") + ",";
		result += ImprovedStreamTokenizer.parseByte("89") + ",";
		result += ImprovedStreamTokenizer.parseByte("AB") + ",";
		result += ImprovedStreamTokenizer.parseByte("CD") + ",";
		result += ImprovedStreamTokenizer.parseByte("EF") + ",";
		result += ImprovedStreamTokenizer.parseByte("ab") + ",";
		result += ImprovedStreamTokenizer.parseByte("cd") + ",";
		result += ImprovedStreamTokenizer.parseByte("ef") + ",";
		result += ImprovedStreamTokenizer.parseByte("xy") + ",";
		result += ImprovedStreamTokenizer.parseByte("23333asdasd");

		assertEquals(result, "1,35,52,69,86,103,120,-119,-85,-51,-17,-85,-51,-17,-1,35");
	}

	@Test(timeout = 4000)
	public void test_28_2199() throws Throwable {
		// test word chars with manual initialize
		String input = "!#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax();
		String result = tokenizer.nextWord();

		assertEquals(result, input);
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_29_2200() throws Throwable {
		// test quote chars with manual initialize
		String input = "\"abc\" '123'";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		tokenizer.initializeSyntax();
		String result = "";
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord();
		}
		catch (java.io.IOException e)
		{
		  // I don't actually know why single quotes fail :)
		  result += "non-string";
		}
		assertEquals(result, "abc,non-string");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_30_2201() throws Throwable {
		// test quote chars
		String input = "\"abc\" '123'";
		java.io.StringReader reader = new java.io.StringReader(input);
		ImprovedStreamTokenizer tokenizer = new ImprovedStreamTokenizer(reader);
		String result = "";
		result += tokenizer.nextWord() + ",";
		try
		{
		  result += tokenizer.nextWord();
		}
		catch (java.io.IOException e)
		{
		  // I don't actually know why single quotes fail :)
		  result += "non-string";
		}
		assertEquals(result, "abc,non-string");
		assertNull(tokenizer.nextWord());
	}

	@Test(timeout = 4000)
	public void test_31_2204() throws Throwable {
		String x = "";
		x += (int)ImprovedStreamTokenizer.parseByte("aA");
		x += (int)ImprovedStreamTokenizer.parseByte("Aa");
		x += (int)ImprovedStreamTokenizer.parseByte("zZ");
		x += (int)ImprovedStreamTokenizer.parseByte("Zz");
		x += (int)ImprovedStreamTokenizer.parseByte("11");
		x += (int)ImprovedStreamTokenizer.parseByte("Ab");
		x += (int)ImprovedStreamTokenizer.parseByte("aB");
		x += (int)ImprovedStreamTokenizer.parseByte("aZ");
		x += (int)ImprovedStreamTokenizer.parseByte("Za");
		x += (int)ImprovedStreamTokenizer.parseByte("ZZ");
		x += (int)ImprovedStreamTokenizer.parseByte("AA");
		x += (int)ImprovedStreamTokenizer.parseByte("aa");
		x += (int)ImprovedStreamTokenizer.parseByte("zz");
		x += (int)ImprovedStreamTokenizer.parseByte("z1");
		x += (int)ImprovedStreamTokenizer.parseByte("1a");
		
		assertEquals("-86-86-1-117-85-85-1-6-1-86-86-1-1526",x);
	}

	@Test(timeout = 4000)
	public void test_32_2206() throws Throwable {
		String x = "";
		x += ImprovedStreamTokenizer.charToHex('0');
		x += ImprovedStreamTokenizer.charToHex('4');
		x += ImprovedStreamTokenizer.charToHex('9');
		x += ImprovedStreamTokenizer.charToHex('a');
		x += ImprovedStreamTokenizer.charToHex('A');
		x += ImprovedStreamTokenizer.charToHex('b');
		x += ImprovedStreamTokenizer.charToHex('B');
		x += ImprovedStreamTokenizer.charToHex('Z');
		x += ImprovedStreamTokenizer.charToHex('z');
		x += ImprovedStreamTokenizer.charToHex('x');
		x += ImprovedStreamTokenizer.charToHex('X');
		x += ImprovedStreamTokenizer.charToHex('-');
		
		assertEquals("0494210431135676533-10", x);
	}

}
