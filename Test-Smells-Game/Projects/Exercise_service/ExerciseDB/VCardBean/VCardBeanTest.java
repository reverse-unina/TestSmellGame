import org.junit.*;
import static org.junit.Assert.*;

// Game: 1017
public class VCardBeanTest {

	@Test(timeout = 4000)
	public void test_1_996() throws Throwable {
		// test here!
		VCardBean vcb = new VCardBean();
		vcb.setFirstName("Joe");
		assertEquals("Joe", vcb.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_2_997() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		v.setVCard(v.getVCard());
		
		assertEquals("Abdullah", v.getFirstName());
		assertEquals("Alsharif", v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_3_1000() throws Throwable {
		VCardBean vcb = new VCardBean();
		vcb.setLastName("Chacko");
		assertEquals("Chacko", vcb.getLastName());
	}

	@Test(timeout = 4000)
	public void test_4_1001() throws Throwable {
		VCardBean v = new VCardBean();
    	assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_5_1002() throws Throwable {
		VCardBean vcb = new VCardBean();
		vcb.setMiddleName("");
		assertEquals("", vcb.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_6_1003() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		v.setVCard(v.getVCard());
		
		assertEquals("00000000", v.getFax());
		assertEquals("abdullah@shaffield.ac.uk", v.getEmail());
	}

	@Test(timeout = 4000)
	public void test_7_1004() throws Throwable {
		VCardBean v = new VCardBean();
	    assertNull(v.getFirstName());
    	assertNull(v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_8_1005() throws Throwable {
		VCardBean v = new VCardBean();
    	assertNull(v.getMiddleName());
    	assertNull(v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_9_1006() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setLastName("campos");
		v.setEmail("jose.campos@email.com");
		v.setOrganization("shef");

		String vCard = v.getVCard();
		assertNotNull(vCard);
    	assertEquals("BEGIN:VCARD\nFN:jose campos\nN:campos;;jose;\nORG:shef\nEMAIL;TYPE=INTERNET:jose.campos@email.com\nEND:VCARD", vCard);
	}

	@Test(timeout = 4000)
	public void test_10_1007() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		v.setVCard(car);
		
		assertEquals(car, v.getVCard());
		assertEquals("None", v.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_11_1008() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setLastName("campos");
		v.setEmail("jose.campos@email.com");
		v.setOrganization("shef");

		String vCard = v.toString();
    	assertEquals("BEGIN:VCARD\nFN:jose campos\nN:campos;;jose;\nORG:shef\nEMAIL;TYPE=INTERNET:jose.campos@email.com\nEND:VCARD", vCard);
	}

	@Test(timeout = 4000)
	public void test_12_1010() throws Throwable {
        VCardBean vcb = new VCardBean();
        vcb.setEmail("mhm@dizni.org");
        vcb.setFax("555-5555");
        vcb.setFirstName("Michael");
        vcb.setMiddleName("H");
        vcb.setLastName("Mouse");
        vcb.setFormattedName("Mickey Mouse");
        vcb.setOrganization("Dizni");
        vcb.setPhone("555-5555");
        vcb.setTitle("Dr");
        
        String vc = "BEGIN:VCARD\n" +
                "FN:Mickey Mouse\n" +
                "N:Mouse;H;Michael;\n" +
                "TITLE:Dr\n" +
                "ORG:Dizni\n" +
                "EMAIL;TYPE=INTERNET:mhm@dizni.org\n" +
                "TEL;TYPE=VOICE:555-5555\n" +
                "TEL;TYPE=FAX:555-5555\n" +
                "END:VCARD";
        
        assertEquals(vc, vcb.getVCard());

        vc = "BEGIN:VCARD\n" +
                "FN:Donald Duck\n" +
                "N:Duck;H;Donald;\n" +
                "TITLE:Professor\n" +
                "ORG:Dizni\n" +
                "EMAIL;TYPE=INTERNET:dhd@dizni.org\n" +
                "TEL;TYPE=VOICE:555-5000\n" +
                "TEL;TYPE=FAX:555-5000\n" +
                "END:VCARD";
        vcb.setVCard(vc);
        assertEquals(vc, vcb.getVCard());
	}

	@Test(timeout = 4000)
	public void test_13_1011() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfoo:\nend:vcard\nfn:test");
		assertNull(v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_14_1012() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setLastName("campos");
		v.setEmail("jose.campos@email.com");
		v.setOrganization("shef");

		String vCard = v.toString();
    	assertEquals("BEGIN:VCARD\nFN:jose campos\nN:campos;;jose;\nORG:shef\nEMAIL;TYPE=INTERNET:jose.campos@email.com\nEND:VCARD", vCard);
	}

	@Test(timeout = 4000)
	public void test_15_1013() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfoo:\nfn:test\nend:vcard");
		assertEquals("test",v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_16_1014() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setLastName("campos");
		v.setEmail("jose.campos@email.com");
		v.setOrganization("shef");

		String vCard = v.toString();
		assertEquals("BEGIN:VCARD\nFN:jose campos\nN:campos;;jose;\nORG:shef\nEMAIL;TYPE=INTERNET:jose.campos@email.com\nEND:VCARD", vCard);
	}

	@Test(timeout = 4000)
	public void test_17_1015() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn;shit=lol:test\ntel;type=fAx:123\nTEL:poo\nend:vcard");
		assertEquals("123",v.getFax());
		assertEquals("poo",v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_18_1016() throws Throwable {
		VCardBean v = new VCardBean();
    	assertFalse(v.isValidVCard());
	}

	@Test(timeout = 4000)
	public void test_19_1017() throws Throwable {
		VCardBean v = new VCardBean();
    	v.setVCard("BEGIN:VCARD");
    	assertTrue(v.isValidVCard());
	}

	@Test(timeout = 4000)
	public void test_20_1019() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setFormattedName("jcc");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00000");
		v.setFax("+44 000 00000");

		assertEquals("jose", v.getFirstName());
		assertEquals("carlos", v.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_21_1020() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("Testy");
		v.setLastName("Face");
		v.setMiddleName("McTest");
		v.setOrganization("Test R Us");
		v.setEmail("roflcopter");
		
		assertNotNull(v.getVCard());
		v.setEmail(null);
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_22_1021() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setFormattedName("jcc");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00000");
		v.setFax("+44 000 00000");

		assertEquals("campos", v.getLastName());
		assertEquals("jcc", v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_23_1022() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setFormattedName("jcc");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00000");
		v.setFax("+44 000 00000");

		assertEquals("Mr", v.getTitle());
		assertEquals("shef", v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_24_1023() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("Testy");
		v.setLastName("Face");
		v.setMiddleName("McTest");
		v.setOrganization("Test R Us");
		v.setEmail("roflcopter");
		
		assertNotNull(v.getVCard());
		v.setFirstName(null);
		v.setLastName(null);
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_25_1024() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setFormattedName("jcc");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00001");
		v.setFax("+44 000 00000");

		assertEquals("jose.campos@email.com", v.getEmail());
		assertEquals("+44 000 00001", v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_26_1025() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setFormattedName("jcc");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00001");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jcc"
			+ "\nN:campos;carlos;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00001"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_27_1026() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		v.setVCard(car);
		
		assertEquals("SHef", v.getOrganization());
		assertEquals("1011112", v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_28_1027() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("carlos");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00001");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose carlos campos"
			+ "\nN:campos;carlos;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00001"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_29_1028() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("Testy");
		v.setLastName("Face");
		v.setMiddleName("McTest");
		v.setOrganization("Test R Us");
		v.setEmail("roflcopter");
		
		assertEquals(v.toString(),v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_30_1029() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00001");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00001"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_31_1030() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		v.setVCard(car);
		
		assertEquals("Mr", v.getTitle());
	}

	@Test(timeout = 4000)
	public void test_32_1031() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00001");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00001"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_33_1032() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		//v.setVCard(car);
		
		assertEquals("Mr", v.getTitle());
	}

	@Test(timeout = 4000)
	public void test_34_1033() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nn:test;fart;poo;ass\nend:vcard");
		assertEquals("test",v.getLastName());
		assertEquals("poo",v.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_35_1034() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_36_1035() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		//v.setVCard(car);
		
		assertEquals("SHef", v.getOrganization());
		assertEquals("1011112", v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_37_1036() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("");
		v.setFax("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_38_1038() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\r\n \nn:test;fart;\r\n poo;ass\nend:\r\n vcard");
		assertEquals("test",v.getLastName());
		assertEquals("poo",v.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_39_1039() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		assertNull(v.getVCard());
		assertNull(v.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_40_1040() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00000");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_41_1041() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		assertNull(v.getVCard());
		assertNull(v.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_42_1042() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("jose");
		v.setMiddleName("");
		v.setLastName("campos");
		v.setTitle("Mr");
		v.setOrganization("shef");
		v.setEmail("jose.campos@email.com");
		v.setPhone("+44 000 00000");
		v.setFax("");

		assertEquals("BEGIN:VCARD"
			+ "\nFN:jose campos"
			+ "\nN:campos;;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00000"
			+ "\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_43_1044() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard(null);
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_44_1045() throws Throwable {
		VCardBean vcb = new VCardBean();
		String fax = "hello      ";
		vcb.setFax(fax);
		assertEquals(fax, vcb.getFax());
	}

	@Test(timeout = 4000)
	public void test_45_1046() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getFax());
		v.setFax("+44 000 00000");
		assertEquals("+44 000 00000", v.getFax());
	}

	@Test(timeout = 4000)
	public void test_46_1047() throws Throwable {
		VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		assertEquals(false, v.isValidVCard());
	}

	@Test(timeout = 4000)
	public void test_47_1048() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getEmail());
		assertNull(v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_48_1049() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\r\n \nn:test;fart;\r\n poo;ass\nend:\r\n vcard");
		assertEquals("fart",v.getMiddleName());
		v.setMiddleName("shitbag");
		assertEquals("shitbag",v.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_49_1050() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getTitle());
		assertNull(v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_50_1051() throws Throwable {
		VCardBean v = new VCardBean();
		//v.setFirstName("");
		v.setLastName("");
		v.setEmail("");
		v.setOrganization("");
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_51_1052() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("");
		//v.setLastName("");
		v.setEmail("");
		v.setOrganization("");
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_52_1053() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("");
		//v.setLastName("");
		v.setEmail("");
		v.setOrganization("");
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_53_1054() throws Throwable {
		// test here!
		
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setFormattedName("Abdullah Alsharif");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		
		assertEquals("BEGIN:VCARD\nFN:Abdullah Alsharif\nN:Alsharif;None;Abdullah;\nTITLE:Mr\nORG:SHef\nEMAIL;TYPE=INTERNET:abdullah@shaffield.ac.uk\nTEL;TYPE=VOICE:1011112\nTEL;TYPE=FAX:00000000\nEND:VCARD", v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_54_1056() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("");
		v.setLastName("");
		//v.setEmail("");
		v.setOrganization("");
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_55_1058() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setFormattedName("Abdullah Alsharif");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		assertEquals("BEGIN:VCARD\nFN:Abdullah Alsharif\nN:Alsharif;None;Abdullah;\nTITLE:Mr\nORG:SHef\nEMAIL;TYPE=INTERNET:abdullah@shaffield.ac.uk\nTEL;TYPE=VOICE:1011112\nTEL;TYPE=FAX:00000000\nEND:VCARD", v.toString());
	}

	@Test(timeout = 4000)
	public void test_56_1059() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("");
		v.setLastName("");
		v.setEmail("");
		//v.setOrganization("");
		assertNull(v.getVCard());
	}

	@Test(timeout = 4000)
	public void test_57_1060() throws Throwable {
		VCardBean v = new VCardBean();
    	assertNull(v.getFax());
	}

	@Test(timeout = 4000)
	public void test_58_1062() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getMiddleName());
		assertNull(v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_59_1063() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getFirstName());
		assertNull(v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_60_1065() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\norg:lols\nend:vcard");
		assertEquals("lols",v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_61_1066() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("");
		assertNull(v.getFirstName());
		assertNull(v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_62_1067() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setFormattedName("Abdullah Alsharif");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		
		VCardBean v2 = new VCardBean();
		v2.setVCard(v.getVCard());
		
		
		
		//assertEquals(false, v.isValidVCard());
		//System.out.println(v.hashCode());
		assertEquals("BEGIN:VCARD\nFN:Abdullah Alsharif\nN:Alsharif;None;Abdullah;\nTITLE:Mr\nORG:SHef\nEMAIL;TYPE=INTERNET:abdullah@shaffield.ac.uk\nTEL;TYPE=VOICE:1011112\nTEL;TYPE=FAX:00000000\nEND:VCARD", v2.toString());
	}

	@Test(timeout = 4000)
	public void test_63_1068() throws Throwable {
		VCardBean v = new VCardBean();
    	assertNull(v.getEmail());
    	assertNull(v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_64_1069() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nORG:lols\nend:vcard");
		assertEquals("lols",v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_65_1070() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getTitle());
		assertNull(v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_66_1072() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getFirstName());
		v.setFirstName("jose");
		assertEquals("jose", v.getFirstName());
	}

	@Test(timeout = 4000)
	public void test_67_1074() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nORG:lols      \nend:vcard");
		assertEquals("lols",v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_68_1075() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getMiddleName());
		v.setMiddleName("carlos");
		assertEquals("carlos", v.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_69_1076() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nORG  :lols\nend   :vcard  ");
		assertEquals("lols",v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_70_1077() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getLastName());
		v.setLastName("campos");
		assertEquals("campos", v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_71_1078() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getFormattedName());
		v.setFormattedName("jcc");
		assertEquals("jcc", v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_72_1079() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getTitle());
		v.setTitle("Mr");
		assertEquals("Mr", v.getTitle());
	}

	@Test(timeout = 4000)
	public void test_73_1080() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getOrganization());
		v.setOrganization("Shef");
		assertEquals("Shef", v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_74_1081() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcardshit\nfn:lollers\nbegin:vcard\nORG:lols\nend:vcard");
		assertEquals("lols",v.getOrganization());
		assertEquals(null,v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_75_1082() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.getEmail());
		v.setEmail("jose.campos@email.com");
		assertEquals("jose.campos@email.com", v.getEmail());
	}

	@Test(timeout = 4000)
	public void test_76_1083() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcardshit\nend:vcard\nfn:lollers\nbegin:vcard\nORG:lols\nend:vcard");
		assertEquals("lols",v.getOrganization());
		assertEquals(null,v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_77_1084() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertNull(v.getFirstName());
		assertNull(v.getFax());
	}

	@Test(timeout = 4000)
	public void test_78_1085() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nFN:jcc"
			+ "\nN:campos;carlos;jose;"
			+ "\nTITLE:Mr"
			+ "\nORG:shef"
			+ "\nEMAIL;TYPE=INTERNET:jose.campos@email.com"
			+ "\nTEL;TYPE=VOICE:+44 000 00001"
			+ "\nTEL;TYPE=FAX:+44 000 00000"
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertEquals("jose", v.getFirstName());
		assertEquals("+44 000 00000", v.getFax());
	}

	@Test(timeout = 4000)
	public void test_79_1086() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcardshit\norg:lols");
		assertNull(v.getOrganization());
		assertEquals(null,v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_80_1088() throws Throwable {
		try {
		  new java.io.ObjectOutputStream(
			  new java.io.ByteArrayOutputStream()).writeObject(new VCardBean());
		} catch (java.io.IOException e) {
		  fail("java.io.NotSerializableException");
		}
	}

	@Test(timeout = 4000)
	public void test_81_1089() throws Throwable {
		// test here!
		VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setFormattedName("Abdullah Alsharif");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		
		VCardBean v2 = new VCardBean();
		v2.setVCard(v.getVCard());
		
		
		
		//assertEquals(false, v.isValidVCard());
		//System.out.println(v.hashCode());
		assertEquals(v2.toString().length(), "BEGIN:VCARD\nFN:Abdullah Alsharif\nN:Alsharif;None;Abdullah;\nTITLE:Mr\nORG:SHef\nEMAIL;TYPE=INTERNET:abdullah@shaffield.ac.uk\nTEL;TYPE=VOICE:1011112\nTEL;TYPE=FAX:00000000\nEND:VCARD".length());
		assertEquals(true, v2.toString().equals("BEGIN:VCARD\nFN:Abdullah Alsharif\nN:Alsharif;None;Abdullah;\nTITLE:Mr\nORG:SHef\nEMAIL;TYPE=INTERNET:abdullah@shaffield.ac.uk\nTEL;TYPE=VOICE:1011112\nTEL;TYPE=FAX:00000000\nEND:VCARD"));
	}

	@Test(timeout = 4000)
	public void test_82_1093() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		v.setVCard(v.getVCard());
		
		assertEquals(true, v.getFax().equals("00000000"));
		assertEquals(true, v.getEmail().equals("abdullah@shaffield.ac.uk"));
	}

	@Test(timeout = 4000)
	public void test_83_1094() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nFN jcc"
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertNull(v.getTitle());
		assertNull(v.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_84_1095() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		//String car = v.getVCard();
		//v.setVCard(car);
		assertEquals(true, v.getOrganization().equals("SHef"));
		assertEquals(true, v.getMiddleName().equals("None"));
	}

	@Test(timeout = 4000)
	public void test_85_1098() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		v.setEmail("abdullah@shaffield.ac.uk");
		v.setFax("00000000");
		v.setFirstName("Abdullah");
		v.setLastName("Alsharif");
		v.setMiddleName("None");
		v.setOrganization("SHef");
		v.setPhone("1011112");
		v.setTitle("Mr");
		String car = v.getVCard();
		//v.setVCard(car);
		
		assertEquals(true, v.getTitle().equals("Mr"));
		assertEquals(true, v.getPhone().equals("1011112"));
	}

	@Test(timeout = 4000)
	public void test_86_1099() throws Throwable {
		// test here!
				VCardBean v = new VCardBean();
		assertEquals(true, v instanceof java.io.Serializable);
	}

	@Test(timeout = 4000)
	public void test_87_1100() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nFN:"
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertNull(v.getTitle());
		assertNull(v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_88_1102() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nFN:"
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertNull(v.getFirstName());
		assertNull(v.getLastName());
	}

	@Test(timeout = 4000)
	public void test_89_1103() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcardshit\norg:lols");
		assertNull(v.getOrganization());
		assertTrue(v.isValidVCard());

	}

	@Test(timeout = 4000)
	public void test_90_1104() throws Throwable {
		VCardBean v = new VCardBean();
		
		v.setFax("abc");
		assertEquals("abc",v.getFax());
		assertTrue(v.getFormattedName() == null &&
				   v.getMiddleName() == null &&
				   v.getLastName() == null &&
				   v.getFirstName() == null &&
				   v.getTitle() == null &&
				   v.getOrganization() == null &&
				   v.getEmail() == null &&
				   v.getPhone() == null);
		
	}

	@Test(timeout = 4000)
	public void test_91_1105() throws Throwable {
		VCardBean v = new VCardBean();
		assertNull(v.toString());
	}

	@Test(timeout = 4000)
	public void test_92_1107() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\r\n\t\norg:lols\nend:vcard");
		assertEquals("lols",v.getOrganization());
	}

	@Test(timeout = 4000)
	public void test_93_1108() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn;shit=lol:test\ntel        ;type=fAx:123\nTEL:poo\nend:vcard");
		assertEquals("123",v.getFax());
		assertEquals("poo",v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_94_1109() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn;shit=lol:test\ntel;type=fAx:    123\nTEL:   poo\nend:vcard");
		assertEquals("123",v.getFax());
		assertEquals("poo",v.getPhone());
	}

	@Test(timeout = 4000)
	public void test_95_1110() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn;shit=lol:test\nend:vcard");
		assertEquals("test",v.getFormattedName());
		assertTrue(v.getFax() == null &&
				   v.getMiddleName() == null &&
				   v.getLastName() == null &&
				   v.getFirstName() == null &&
				   v.getTitle() == null &&
				   v.getOrganization() == null &&
				   v.getEmail() == null &&
				   v.getPhone() == null);
	}

	@Test(timeout = 4000)
	public void test_96_1112() throws Throwable {
		VCardBean v = new VCardBean();
		v.setFirstName("A");
		v.setMiddleName("B");
		v.setLastName("C");
		v.setEmail("lollers");
		v.setOrganization("lol r us");
		assertTrue(v.toString().contains("A B C"));
	}

	@Test(timeout = 4000)
	public void test_97_1113() throws Throwable {
				VCardBean v = new VCardBean();
			v.setFax("lol");
			assertEquals("lol",v.getFax());
			v.setFax("poo");
			assertEquals("poo",v.getFax());
	}

	@Test(timeout = 4000)
	public void test_98_1114() throws Throwable {
		VCardBean v = new VCardBean();
		assertEquals(null, v.getFax());

	}

	@Test(timeout = 4000)
	public void test_99_1115() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn;shit=lol:t\r\n\te\r\n st\nend:vcard");
		assertEquals("test",v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_100_1117() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nn: a  ; b  ; c  ");
		assertEquals("a",v.getLastName());
		assertTrue(
			"b".equals(v.getMiddleName()) &&
			"c".equals(v.getFirstName())
		);
		
	}

	@Test(timeout = 4000)
	public void test_101_1128() throws Throwable {
		VCardBean vb = new VCardBean();
		vb.setVCard("BEGIN:VCARD\nTitle:hi;");
		String x = vb.getTitle();
		assertEquals("hi;",x);
	}

	@Test(timeout = 4000)
	public void test_102_1131() throws Throwable {
		// test here!
		VCardBean vb = new VCardBean();
		vb.setVCard("BEGIN:VCARD\nTitle:hi\nORG:shef");
		String x = vb.getTitle();
		assertEquals("hi",x);
		String y = vb.getOrganization();
		assertEquals("shef",y);
	}

	@Test(timeout = 4000)
	public void test_103_1132() throws Throwable {
		VCardBean vb = new VCardBean();
		vb.setVCard("BEGIN:VCARD\nTitle: hi \nORG: shef ");
		String x = vb.getTitle();
		assertEquals("hi",x);
		String y = vb.getOrganization();
		assertEquals("shef",y);
	}

	@Test(timeout = 4000)
	public void test_104_1136() throws Throwable {
		VCardBean vb = new VCardBean();
		vb.setVCard("BEGIN:VCARD\nTitle:hi\nFAX: shef ");
		String x = vb.getTitle();
		assertEquals("hi",x);
		String y = vb.toString().replaceAll("\n",":D").replaceAll("\r",":P");
		assertEquals("BEGIN:VCARD:DTitle:hi:DFAX: shef ",y);
	}

	@Test(timeout = 4000)
	public void test_105_1137() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nn: a  ; b ");
		assertTrue(v.getFax() == null &&
				   v.getMiddleName() == null &&
				   v.getLastName() == null &&
				   v.getFirstName() == null &&
				   v.getTitle() == null &&
				   v.getOrganization() == null &&
				   v.getEmail() == null &&
				   v.getPhone() == null &&
				   v.getFormattedName() == null);
	}

	@Test(timeout = 4000)
	public void test_106_1138() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nfn:  aa  ");
		assertTrue(v.getFax() == null &&
				   v.getMiddleName() == null &&
				   v.getLastName() == null &&
				   v.getFirstName() == null &&
				   v.getTitle() == null &&
				   v.getOrganization() == null &&
				   v.getEmail() == null &&
				   v.getPhone() == null);
		assertEquals("aa",v.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_107_1142() throws Throwable {
		VCardBean vcb = new VCardBean();
		String title = "lowercase";
		vcb.setTitle(title);
		assertEquals(title, vcb.getTitle());
	}

	@Test(timeout = 4000)
	public void test_108_1143() throws Throwable {
		VCardBean vcb = new VCardBean();
		String title = "lowercase";
		vcb.setTitle(title);
		assertEquals(title, vcb.getTitle());
	}

	@Test(timeout = 4000)
	public void test_109_1157() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\ntitle:  aa  ");
		assertEquals("aa", v.getTitle());
	}

	@Test(timeout = 4000)
	public void test_110_1160() throws Throwable {
		VCardBean vb = new VCardBean();
		String x = "BEGIN:VCARD\nEND:VCARD\nfn:jesus";
		vb.setVCard(x);
		assertEquals(null, vb.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_111_1212() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\nm");
		assertTrue(v.getFax() == null &&
				   v.getMiddleName() == null &&
				   v.getLastName() == null &&
				   v.getFirstName() == null &&
				   v.getTitle() == null &&
				   v.getOrganization() == null &&
				   v.getEmail() == null &&
				   v.getPhone() == null &&
				   v.getFormattedName() == null);
	}

	@Test(timeout = 4000)
	public void test_112_1217() throws Throwable {
		VCardBean vb = new VCardBean();
		String x = "BEGIN:VCARD\nfn: hello \nEND:VCARD";
		vb.setVCard(x);
		assertEquals("hello",vb.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_113_1218() throws Throwable {
		VCardBean vb = new VCardBean();
		String x = "BEGIN:VCARD\nfn: hello \nEND:VCARD";
		vb.setVCard(x);
		assertEquals("hello",vb.getFormattedName());
	}

	@Test(timeout = 4000)
	public void test_114_1219() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard");
		assertTrue(v.isValidVCard());
	}

	@Test(timeout = 4000)
	public void test_115_1220() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard");
		assertTrue(v.isValidVCard());
	}

	@Test(timeout = 4000)
	public void test_116_1221() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("__not_valid__");
		assertFalse(v.isValidVCard());
		assertNull(v.getFax());
	}

	@Test(timeout = 4000)
	public void test_117_1222() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nTITLE:  Mr  "
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertEquals("Mr", v.getTitle());
	}

	@Test(timeout = 4000)
	public void test_118_1223() throws Throwable {
		String s = "BEGIN:VCARD"
			+ "\nTEL;TYPE=FAX:     +44 000 00000      "
			+ "\nEND:VCARD";

		VCardBean v = new VCardBean();
		v.setVCard(s);
		assertEquals("+44 000 00000", v.getFax());
	}

	@Test(timeout = 4000)
	public void test_119_1224() throws Throwable {
		String mn = "abc-de";
		VCardBean vcb = new VCardBean();
		vcb.setMiddleName(mn);
		assertEquals(mn, vcb.getMiddleName());
	}

	@Test(timeout = 4000)
	public void test_120_1226() throws Throwable {
				VCardBean v = new VCardBean();
		v.setFirstName("A");
		v.setMiddleName("B");
		v.setLastName("C");
		v.setEmail("lollers");
		v.setOrganization("lol!@#$%^&*()-=~`|.,/\\[]{} ;r, us");
		assertTrue(v.toString().contains("lol!@#$%^&*()-=~`|.,/\\[]{} ;r, us"));
	}

	@Test(timeout = 4000)
	public void test_121_1227() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\ntel;type=fax: lol!@#$%^&*()-=~`|.,/\\[]{} ;r, us \nend:vcard");
		assertEquals("lol!@#$%^&*()-=~`|.,/\\[]{} ;r, us", v.getFax());
		
	}

	@Test(timeout = 4000)
	public void test_122_1228() throws Throwable {
		VCardBean v = new VCardBean();
		v.setVCard("begin:vcard\ntel;type=fax: \tA234lo123ABCl!@#$%^&*()-=~`|.,/\\[]{} ;r, us \nend:vcard");
		assertEquals("A234lo123ABCl!@#$%^&*()-=~`|.,/\\[]{} ;r, us", v.getFax());
		
	}

	@Test(timeout = 4000)
	public void test_123_1243() throws Throwable {
		VCardBean vb = new VCardBean();
		String x = "BEGIN:VCARD\nORG: hello\\;world\\;;\\;yeah\\;\\;\\,\\,\nend:vcard";
		vb.setVCard(x);
		assertEquals("hello\\;world\\;;\\;yeah\\;\\;\\,\\,",vb.getOrganization());
	}

}
