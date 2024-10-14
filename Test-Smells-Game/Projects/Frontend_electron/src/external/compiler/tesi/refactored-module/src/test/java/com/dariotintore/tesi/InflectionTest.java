import org.junit.*;
import static org.junit.Assert.*;

// Game: 1010
public class InflectionTest {

	@Test(timeout = 4000)
	public void test_1_1516() throws Throwable {
		assertEquals(Inflection.pluralize("buoyancy"),"buoyancies");
	}

	@Test(timeout = 4000)
	public void test_2_1517() throws Throwable {
		assertEquals(Inflection.pluralize("quy"),"quies");
	}

	@Test(timeout = 4000)
	public void test_3_1521() throws Throwable {
		assertEquals(Inflection.pluralize("berry"),"berries");
	}

	@Test(timeout = 4000)
	public void test_4_1523() throws Throwable {
		assertEquals(Inflection.pluralize("any"),"anies");
	}

	@Test(timeout = 4000)
	public void test_5_1524() throws Throwable {
		assertEquals(Inflection.pluralize("benny"),"bennies");
	}

	@Test(timeout = 4000)
	public void test_6_1525() throws Throwable {
		// verify replace() method with case sensitive matching
		Inflection inflectionCaseSensitive = new Inflection("person", "people", false);
		assertEquals(inflectionCaseSensitive.replace("person"), "people");
		assertNotEquals(inflectionCaseSensitive.replace("Person"), "people");
	}

	@Test(timeout = 4000)
	public void test_7_1526() throws Throwable {
		assertEquals(Inflection.pluralize("erny"),"ernies");
		assertEquals(Inflection.pluralize("irny"),"irnies");
	}

	@Test(timeout = 4000)
	public void test_8_1527() throws Throwable {
		// verify replace() method with case insensitive matching
		Inflection inflectionCaseInsensitive = new Inflection("person", "people", true);
		assertEquals(inflectionCaseInsensitive.replace("person"), "people");
		assertEquals(inflectionCaseInsensitive.replace("Person"), "people");
	}

	@Test(timeout = 4000)
	public void test_9_1528() throws Throwable {
		assertEquals(Inflection.pluralize("orny"),"ornies");
		assertEquals(Inflection.pluralize("urny"),"urnies");
	}

	@Test(timeout = 4000)
	public void test_10_1529() throws Throwable {
		assertEquals(Inflection.pluralize("yny"),"ynies");
	}

	@Test(timeout = 4000)
	public void test_11_1530() throws Throwable {
		assertEquals(Inflection.pluralize("quy"),"quies");
	}

	@Test(timeout = 4000)
	public void test_12_1532() throws Throwable {
		// case-insensitive uncountable matching
		assertFalse(Inflection.isUncountable("cow"));
		assertFalse(Inflection.isUncountable("Cow"));
	}

	@Test(timeout = 4000)
	public void test_13_1533() throws Throwable {
		// case-insensitive uncountable matching
		assertTrue(Inflection.isUncountable("sheep"));
		assertTrue(Inflection.isUncountable("Sheep"));
	}

	@Test(timeout = 4000)
	public void test_14_1534() throws Throwable {
		Inflection inflectionCaseSensitive = new Inflection("person", "people", true);
		assertEquals(inflectionCaseSensitive.replace("Person"), "people");
	}

	@Test(timeout = 4000)
	public void test_15_1536() throws Throwable {
		// case-insensitive uncountable matching: make sure entire string is checked
		assertFalse(Inflection.isUncountable("xsheepx"));
		assertFalse(Inflection.isUncountable("xSheepx"));
	}

	@Test(timeout = 4000)
	public void test_16_1537() throws Throwable {
		Inflection inflectionCaseSensitive = new Inflection("peRsOn", "people", true);
		assertEquals(inflectionCaseSensitive.replace("PerSon"), "people");
	}

	@Test(timeout = 4000)
	public void test_17_1540() throws Throwable {
		Inflection inflectionCaseSensitive = new Inflection("person", "equipment", true);
		//test should be "people" as it is not case sensitive
		String test = inflectionCaseSensitive.replace("perSon");
		//result should be true
		boolean result = inflectionCaseSensitive.isUncountable(test);
		assertEquals(true, result);
	}

	@Test(timeout = 4000)
	public void test_18_1542() throws Throwable {
		assertEquals(Inflection.pluralize("[^aeiouy]y"),"[^aeiouy]ies");
	}

	@Test(timeout = 4000)
	public void test_19_1544() throws Throwable {
		// check if '$' of regular expression is still present
		assertEquals(Inflection.pluralize("query"), "queries");
		assertNotEquals(Inflection.pluralize("queryyyy"), "queriesyyy");
	}

	@Test(timeout = 4000)
	public void test_20_1545() throws Throwable {
		// check if '$' of regular expression is still present
		assertNotEquals(Inflection.pluralize("quer"), "queries");
	}

	@Test(timeout = 4000)
	public void test_21_1561() throws Throwable {
		// check regular expression handling for irregular cases (e.g. man -> men)
		// only the last part of a word is checked
		assertEquals(Inflection.pluralize("manman"), "manmen");
		assertEquals(Inflection.singularize("menmen"), "menman");
	}

	@Test(timeout = 4000)
	public void test_22_1599() throws Throwable {
		Inflection inflection = new Inflection("perSon");
		assertEquals(inflection.match("person"), true);
		assertEquals(inflection.match("Person"), true);
	}

	@Test(timeout = 4000)
	public void test_23_1600() throws Throwable {
		Inflection inflection = new Inflection("perSon","people");
		assertEquals(inflection.match("person"), true);
		assertEquals(inflection.match("Person"), true);
	}

	@Test(timeout = 4000)
	public void test_24_1601() throws Throwable {
		Inflection inflection = new Inflection("perSon","people",false);
		assertEquals(inflection.match("person"), false);
		assertEquals(inflection.match("perSon"), true);
	}

	@Test(timeout = 4000)
	public void test_25_1603() throws Throwable {
		Inflection inflection = new Inflection("ABCDEFGHIJKLMNOPQRSTUVWXYZ","ABCDEFGHIJKLMNOPQRSTUVWXYZs",true);
		assertEquals(inflection.match("abcdefghijklmnopqrstuvwxyz"), true);
		Inflection inflection2 = new Inflection("ABCDEFGHIJKLMNOPQRSTUVWXYZ","ABCDEFGHIJKLMNOPQRSTUVWXYZs",false);
		assertEquals(inflection2.match("abcdefghijklmnopqrstuvwxyz"), false);
	}

	@Test(timeout = 4000)
	public void test_26_1619() throws Throwable {
		assertEquals(Inflection.singularize("equipment"), "equipment");
	}

}
