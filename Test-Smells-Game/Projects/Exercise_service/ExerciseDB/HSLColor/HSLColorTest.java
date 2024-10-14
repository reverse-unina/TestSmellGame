import org.junit.*;
import static org.junit.Assert.*;

// Game: 1007
public class HSLColorTest {

	@Test(timeout = 4000)
	public void test_1_2116() throws Throwable {
		// test here!
		int R = 0;
		int G = 0;
		int B = 0;
		HSLColor c = new HSLColor();
		c.initHSLbyRGB(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("17000000", result);
	}

	@Test(timeout = 4000)
	public void test_2_2118() throws Throwable {
		// test here!
		int R = Integer.MAX_VALUE;
		int G = 0;
		int B = 255;
		HSLColor c = new HSLColor();
		c.initHSLbyRGB(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("00-421075221474836470255", result);
	}

	@Test(timeout = 4000)
	public void test_3_2120() throws Throwable {
		int R = 100;
		int G = 200;
		int B = 1;
		HSLColor c = new HSLColor();
		c.initHSLbyRGB(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("642521011002001", result);
	}

	@Test(timeout = 4000)
	public void test_4_2122() throws Throwable {
		int R = 1;
		int G = 100;
		int B = 200;
		HSLColor c = new HSLColor();
		c.initHSLbyRGB(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("1492521011100200", result);
	}

	@Test(timeout = 4000)
	public void test_5_2124() throws Throwable {
		int R = 1;
		int G = 100;
		int B = 200;
		HSLColor c = new HSLColor();
		c.initRGBbyHSL(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("1100200222179178", result);
	}

	@Test(timeout = 4000)
	public void test_6_2126() throws Throwable {
		int R = 100;
		int G = 1;
		int B = 200;
		HSLColor c = new HSLColor();
		c.initRGBbyHSL(R, G, B);
		
		String result = "";
		result += c.getHue();
		result += c.getSaturation();
		result += c.getLuminence();
		result += c.getRed();
		result += c.getGreen();
		result += c.getBlue();
		
		assertEquals("1001200200200200", result);
	}

	@Test(timeout = 4000)
	public void test_7_2130() throws Throwable {
		String result = "";
		int R = 100;
		int G = 1;
		int B = 200;
		HSLColor c = new HSLColor();
		
		c.setHue(42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setHue(Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setHue(Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setHue(255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setHue(0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.setLuminence(42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setLuminence(Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setLuminence(Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setLuminence(255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.setLuminence(0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

		assertEquals("420000012700000127000002550000000000000424242420025525525525500000000255255255255000000", result);
	}

	@Test(timeout = 4000)
	public void test_8_2132() throws Throwable {
		String result = "";
		int R = 100;
		int G = 1;
		int B = 200;
		HSLColor c = new HSLColor();
		
		c.initHSLbyRGB(42,42,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(255,255,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(0,0,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initHSLbyRGB(42,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(Integer.MAX_VALUE,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(Integer.MIN_VALUE,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(255,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(0,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

		c.initHSLbyRGB(1,42,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,Integer.MAX_VALUE,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,Integer.MIN_VALUE,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,255,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,0,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initHSLbyRGB(1,1,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,1,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,1,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,1,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(1,1,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initHSLbyRGB(2,1,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(2,1,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(2,1,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(2,1,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initHSLbyRGB(2,1,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		
		assertEquals("1700424242421700021474836472147483647214748364717000-2147483648-2147483648-2147483648170025525525525517000000024322421100-4210751214748364711850-4210751-214748364811025512825511127255101185243221421850-421075112147483647100-42107511-21474836481852551281255121325511011702432211421700-421075111214748364700-421075111-2147483648170255128112554225511101722432221421700-421075121214748364700-421075021-214748364817125512821255212551210", result);
	}

	@Test(timeout = 4000)
	public void test_9_2134() throws Throwable {
		String result = "";
		int R = 100;
		int G = 1;
		int B = 200;
		HSLColor c = new HSLColor();
		
		c.initRGBbyHSL(42,42,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(255,255,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(0,0,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initRGBbyHSL(42,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(Integer.MAX_VALUE,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(Integer.MIN_VALUE,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(255,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(0,1,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

		c.initRGBbyHSL(1,42,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,Integer.MAX_VALUE,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,Integer.MIN_VALUE,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,255,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,0,1);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initRGBbyHSL(1,1,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,1,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,1,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,1,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(1,1,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		c.initRGBbyHSL(2,1,42);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(2,1,Integer.MAX_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(2,1,Integer.MIN_VALUE);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(2,1,255);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		c.initRGBbyHSL(2,1,0);
		result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
		
		
		assertEquals("424242494935214748364721474836472147483647-1400-2147483648-2147483648-2147483648255-84214982552552552552552552550000004211111214748364711111-214748364811111255111110111111421111121474836471-8421502255-84215031-21474836481-8421502255-84215031255120010111111424242421121474836470255-111-21474836480001125525525525511000021424242422121474836470255-121-214748364800021255255255255210000", result);
	}

	@Test(timeout = 4000)
	public void test_10_2135() throws Throwable {
		    String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initRGBbyHSL(42,42,42);
    c.brighten(42f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.brighten(0);
    c.initRGBbyHSL(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.brighten(0.5f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(255,255,255);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(0,0,0);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initRGBbyHSL(42,1,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MAX_VALUE,1,1);
    c.brighten(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MIN_VALUE,1,1);
    c.brighten(0.5f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(255,1,1);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(0,1,1);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    c.initRGBbyHSL(1,42,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,Integer.MAX_VALUE,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,Integer.MIN_VALUE,1);
    c.brighten(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,255,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,0,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(1,1,42,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MAX_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MIN_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,255,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,0,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(2,1,42,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MAX_VALUE,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MIN_VALUE,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,2550,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,0, 0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    
    assertEquals("424225525525525521474836472147483647255249255255-2147483648-214748364800002552552552552552550000004211111214748364711111-214748364810000255100000100001421111121474836471-8421502255-84215031-21474836481-8421502255-8421503125512001011111702432211421700-421075111214748364700-421075111-2147483648170255128112554225511101701533114170-4-21053721121474837142-54-231591111-21474834171-3181276212550171-3271148212294", result);
	}

	@Test(timeout = 4000)
	public void test_11_2139() throws Throwable {
		String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initRGBbyHSL(42,42,42);
    c.setSaturation(42);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initRGBbyHSL(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
	c.setSaturation(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.setSaturation(5);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(255,255,255);
    c.setSaturation(1);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(0,0,0);
    c.setSaturation(100);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initRGBbyHSL(42,1,1);
    c.setSaturation(Integer.MAX_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MAX_VALUE,1,1);
    c.setSaturation(Integer.MIN_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(Integer.MIN_VALUE,1,1);
    c.setSaturation(-1);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(255,1,1);
    c.setSaturation(42);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(0,1,1);
    c.setSaturation(42);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    c.initRGBbyHSL(1,42,1);
    c.setSaturation(10);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,Integer.MAX_VALUE,1);
    c.setSaturation(1000);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,Integer.MIN_VALUE,1);
    c.setSaturation(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,255,1);
    c.setSaturation(-1);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initRGBbyHSL(1,0,1);
    c.setSaturation(Integer.MAX_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(1,1,42,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MAX_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MIN_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,255,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,0,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(2,1,42,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MAX_VALUE,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MIN_VALUE,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,2550,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,0, 0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    
    assertEquals("424242494935214748364702147483647842150384215038421503-21474836485-2147483648000255125525525525501000000422551220214748364701111-2147483648011112554211110421111110111112551200101111101111125512001702432211421700-421075111214748364700-421075111-2147483648170255128112554225511101701533114170-4-21053721121474837142-54-231591111-21474834171-3181276212550171-3271148212294", result);
	}

	@Test(timeout = 4000)
	public void test_12_2141() throws Throwable {
		String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initHSLbyRGB(42,42,42);
    c.brighten(42f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.brighten(0);
    c.initHSLbyRGB(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.brighten(0.5f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,255,255);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,0,0);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initHSLbyRGB(42,1,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MAX_VALUE,1,1);
    c.brighten(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,1,1);
    c.brighten(0.5f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,1,1);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,1,1);
    c.brighten(0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    c.initHSLbyRGB(1,42,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MAX_VALUE,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MIN_VALUE,1);
    c.brighten(0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,255,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,0,1);
    c.brighten(1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(1,1,42,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MAX_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MIN_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,255,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,0,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(2,1,42,0.1f);
	c.brighten(10f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MAX_VALUE,0.1f);
	c.brighten(100f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MIN_VALUE,0.1f);
	c.brighten(50f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,2550,1f);
	c.brighten(42);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,0, 0.1f);
	c.brighten(101);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    
    assertEquals("17002552552552551700000017000000170025525525525517000000024322431100-421075121474836471185000000255122400127255000085243221431850000000-42107511-21474836481852551281255121325512021702432211421700-421075111214748364700-421075111-21474836481702551281125542255111017015330121248170-400002-4255254255255171-318255255255254424255255255255", result);
	}

	@Test(timeout = 4000)
	public void test_13_2143() throws Throwable {
		String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initHSLbyRGB(42,42,42);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.reverseColor();
    c.initHSLbyRGB(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,255,255);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,0,0);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initHSLbyRGB(42,1,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MAX_VALUE,1,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,1,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,1,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,1,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    c.initHSLbyRGB(1,42,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MAX_VALUE,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MIN_VALUE,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,255,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,0,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(1,1,42,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MAX_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(1,1,Integer.MIN_VALUE,1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.blend(2,1,42,0.1f);
	c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MAX_VALUE,0.1f);
	c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,Integer.MIN_VALUE,0.1f);
	c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,2550,1f);
	c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.blend(2,1,0, 0.1f);
	c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    
    assertEquals("4204242424242000004200000420255255255255420000012724322144431270-4210751-4210751-4210751-42107512120-4210751-4210751-4210751-42107511272551281255255254255120021224322431442120-4210751-4210751-4210751-42107511270-4210751-4210751-4210751-421075121225512825512558525510201702432211421700-421075111214748364700-421075111-21474836481270-4210751-4210751-4210751-42107512540-4210751-4210751-4210751-4210751420-3789672-3789672-3789672-37896724252905425255255255129-4210560425525525543-3181276255255416924811638229", result);
	}

	@Test(timeout = 4000)
	public void test_14_2145() throws Throwable {
		String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initHSLbyRGB(42,42,42);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.reverseColor();
    c.initHSLbyRGB(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,255,255);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,0,0);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    c.initHSLbyRGB(42,2,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MAX_VALUE,2,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,2,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,2,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,2,1);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    c.initHSLbyRGB(1,42,2);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MAX_VALUE,2);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,Integer.MIN_VALUE,2);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,255,2);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,0,2);
    c.reverseColor();
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

		
    c.initHSLbyRGB(1,2,42);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,2,Integer.MAX_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,2,Integer.MIN_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,2,255);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(1,2,0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();

    
    assertEquals("4204242424242000004200000420255255255255420000012924322142431270-4210751-4210751-4210751-42107512120-4210750-4210750-4210750-42107501282551281255255233255120121424322431422120-4210751-4210751-4210751-4210751420-4210750-4210750-4210750-421075021325512825512556325511201682432212421700-4210751122147483647850-421075012-214748364816925512812255642551120", result);
	}

	@Test(timeout = 4000)
	public void test_15_2149() throws Throwable {
		String result = "";
    int R = 100;
    int G = 1;
    int B = 200;
    HSLColor c = new HSLColor();
    
    c.initHSLbyRGB(42,42,42);
    c.blend(41,41,41,0);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    c.blend(40,44,44,-1);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    c.blend(40,40,40,-0.5f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(255,255,255);
    c.blend(4,4,4,0.1f);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    c.initHSLbyRGB(0,0,0);
    c.blend(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    result += c.getHue(); result += c.getSaturation(); result += c.getLuminence(); result += c.getRed(); result += c.getGreen(); result += c.getBlue();
    
    
    assertEquals("1700424242421700021474836472147483647214748364717000-2147483648-2147483648-2147483648170022922922922917000000", result);
	}

}
