import org.junit.*;
import static org.junit.Assert.*;

// Game: 1015
public class SubjectParserTest {

	@Test(timeout = 4000)
	public void test_1_1847() throws Throwable {
		// test here!
		String input = " asd asd asd hello Hello HELLO test[] ()";
		SubjectParser sp = new SubjectParser(input);
		String verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
		assertEquals("11-1asd asd asd hello Hello HELLO test null", verify);
	}

	@Test(timeout = 4000)
	public void test_2_1849() throws Throwable {
		// test here!
		String input = " [hello] (world) , test";
		SubjectParser sp = new SubjectParser(input);
		String verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
		assertEquals("11-1[hello] (world) , testnull", verify);
	}

	@Test(timeout = 4000)
	public void test_3_1857() throws Throwable {
		String input = "(nooooo]";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("11-1(nooooo]null", verify);
	}

	@Test(timeout = 4000)
	public void test_4_1867() throws Throwable {
		String input = "([[tesT (blah [helb])] / [tesT (blah [helb])] (tesT) ]/[tesT (blah [helb])] [[tesT (blah [helb])] / [tesT (blah [helb])] (tesT) ] [tesT (blah [helb])])";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("11-1(blah [helb])] / [tesT (blah [helb])] (tesT) ]/[tesT (blah [helb])] [[tesT (blah [helb])] / [tesT (blah [helb])] (tesT) ] [tesT (blah [helb])])null", verify);
	}

	@Test(timeout = 4000)
	public void test_5_1888() throws Throwable {
		String input = " 1 (2) [3] 2 1 2 3 2 1 ";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1111   2 1 2 3 2 1 null", verify);
	}

	@Test(timeout = 4000)
	public void test_6_1903() throws Throwable {
		String input = "1 2/2/3/4 [42] (1)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1112/2/3/4  null", verify);
	}

	@Test(timeout = 4000)
	public void test_7_1908() throws Throwable {
		String input = "1 ((2)) (((3/4))) / 2/2/3/4 [42] (1)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("431((2)) (()) / 2/2/3/4  (3/4)431((2)) (()) / 2/2/3/4  (3/4)431((2)) (()) / 2/2/3/4  (3/4)", verify);
	}

	@Test(timeout = 4000)
	public void test_8_1918() throws Throwable {
		String input = "42 [3000000000] [4[2]2] (42) (-1) -7 ((42)) / [,] (4";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1142 [4[2]2]  (-1) -7 ((42)) / [,] (4null1142 [4[2]2]  (-1) -7 ((42)) / [,] (4null1142 [4[2]2]  (-1) -7 ((42)) / [,] (4null", verify);
	}

	@Test(timeout = 4000)
	public void test_9_1928() throws Throwable {
		String input = "42 [4] [1] [7] [/] 42 [/]";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1142[4] [1] [7] [/] 42 [/]1142[4] [1] [7] [/] 42 [/]1142[4] [1] [7] [/] 42 [/]", verify);
	}

	@Test(timeout = 4000)
	public void test_10_1936() throws Throwable {
		String input = "42 [1/2/3] [/] [1/] [4/2] [2/4] [/1] [,] [////] [///2//1]";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1142[1/2/3] [/] [1/] [4/2] [2/4] [/1] [,] [////] [///2//1]1142[1/2/3] [/] [1/] [4/2] [2/4] [/1] [,] [////] [///2//1]1142[1/2/3] [/] [1/] [4/2] [2/4] [/1] [,] [////] [///2//1]", verify);
	}

	@Test(timeout = 4000)
	public void test_11_1942() throws Throwable {
		String input = "0 [11/42/720]";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("42110[11/42/720]42110[11/42/720]42110[11/42/720]", verify);
	}

	@Test(timeout = 4000)
	public void test_12_1951() throws Throwable {
		String input = "00 [11/42/720] (22/77/42/1111/1111111/10) 42";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("77220[11/42/720]  42(22/77/42/1111/1111111/10)77220[11/42/720]  42(22/77/42/1111/1111111/10)77220[11/42/720]  42(22/77/42/1111/1111111/10)", verify);
	}

	@Test(timeout = 4000)
	public void test_13_2029() throws Throwable {
		String input = "00 (1/2/4/41/123//1231231/)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("210(1/2/4/41/123//1231231/)210(1/2/4/41/123//1231231/)210(1/2/4/41/123//1231231/)", verify);
	}

	@Test(timeout = 4000)
	public void test_14_2036() throws Throwable {
		String input = "teSt (//1/2/4/41/123//1231231/) (/) (///////) (1\\2222)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("11-1(//1/2/4/41/123//1231231/) (/)  (1\\2222)(///////)11-1(//1/2/4/41/123//1231231/) (/)  (1\\2222)(///////)11-1(//1/2/4/41/123//1231231/) (/)  (1\\2222)(///////)", verify);
	}

	@Test(timeout = 4000)
	public void test_15_2047() throws Throwable {
		String input = "teSt (1/2/4/41/123/1231231) (42/1) (1111/1)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("11111-1(1/2/4/41/123/1231231) (42/1) (1111/1)11111-1(1/2/4/41/123/1231231) (42/1) (1111/1)11111-1(1/2/4/41/123/1231231) (42/1) (1111/1)", verify);
	}

	@Test(timeout = 4000)
	public void test_16_2057() throws Throwable {
		String input = "92233720368547758 [92233720368547758/1/1/42]";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("1192233720368547758[92233720368547758/1/1/42]1192233720368547758[92233720368547758/1/1/42]1192233720368547758[92233720368547758/1/1/42]", verify);
	}

	@Test(timeout = 4000)
	public void test_17_2062() throws Throwable {
		String input = "922337203685 (1/2) (1/2)) ((1111/222)";
		SubjectParser sp = new SubjectParser(input);
		String verify = "";
		try{
			verify = "" + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString() + sp.getUpperRange() + sp.getThisRange() + sp.getId() + sp.getTitle() + sp.getRangeString();
			//fail("");
		} catch(Exception e){
			assertEquals("",e.getMessage());
		}
		assertEquals("2221111922337203685(1/2) (1/2)) ((1111/222)2221111922337203685(1/2) (1/2)) ((1111/222)2221111922337203685(1/2) (1/2)) ((1111/222)", verify);
	}

}
