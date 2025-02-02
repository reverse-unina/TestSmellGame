import org.junit.*;
import static org.junit.Assert.*;

// Game: 1004
public class FontInfoTrunkedTest {

    @Test
    public void test_0_791() throws Throwable {
        FontInfo f = new FontInfo();
    }

    @Test(timeout = 4000)
    public void test_1_791() throws Throwable {
        FontInfo f = new FontInfo();
        assertEquals(12, f.getSize());
        assertEquals("Monospaced", f.getFamily());
    }

    @Test(timeout = 4000)
    public void test_3_794() throws Throwable {
        FontInfo f = new FontInfo();
        f.setFamily(null);
        assertEquals("Monospaced", f.getFamily());
    }

    @Test(timeout = 4000)
    public void test_4_795() throws Throwable {
        // test here!
        FontInfo f = new FontInfo();
        f.setIsItalic(true);
        assertEquals(true, f.isItalic());
    }

    @Test(timeout = 4000)
    public void test_6_806() throws Throwable {
        FontInfo f = new FontInfo();
        try {
            f.setFont(null);
            fail("Null Font passed");
        } catch (IllegalArgumentException e) {
            // expected
        }

        java.awt.Font font = f.createFont();
        f.setFont(font);
        assertFalse(f.isItalic());
    }

    @Test(timeout = 4000)
    public void test_7_811() throws Throwable {
        FontInfo f = new FontInfo();
        assertFalse(f.doesFontMatch(null));
        java.awt.Font font = f.createFont();
        assertTrue(f.doesFontMatch(font));
    }

    @Test(timeout = 4000)
    public void test_8_814() throws Throwable {
        // test here!
        java.awt.Font font = new java.awt.Font("Tahoma", java.awt.Font.BOLD, 12);
        FontInfo fi = new FontInfo(font);
        String fstring = fi.toString();
        assertEquals("Dialog, 12, bold",fstring);

    }

    @Test(timeout = 4000)
    public void test_48_886() throws Throwable {
        // test here!
        FontInfo f = new FontInfo();

        f.setIsBold(true);
        f.setIsItalic(false);

        assertEquals(1,f.generateStyle());

    }

    @Test(timeout = 4000)
    public void test_49_887() throws Throwable {
        FontInfo f1 = new FontInfo();
        f1.setFamily("Helvetiva");
        FontInfo f2 = new FontInfo();
        f1.setFamily("Arial");
        assertFalse(f1.equals(f2));
    }

    @Test(timeout = 4000)
    public void test_51_890() throws Throwable {
        FontInfo f1 = new FontInfo();
        f1.setIsBold(true);
        FontInfo f2 = new FontInfo();
        f2.setIsBold(false);
        assertFalse(f1.equals(f2));
    }

    @Test(timeout = 4000)
    public void test_69_2077() throws Throwable {
    }
}
