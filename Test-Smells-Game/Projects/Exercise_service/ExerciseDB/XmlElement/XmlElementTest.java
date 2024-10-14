import org.junit.*;
import static org.junit.Assert.*;

// Game: 1019
public class XmlElementTest {

	@Test(timeout = 4000)
	public void test_1_1304() throws Throwable {
		XmlElement e = new XmlElement("shit", "lol");
		assertEquals("lol",e.getData());
		assertEquals("lol",((XmlElement)e.clone()).getData());
	}

	@Test(timeout = 4000)
	public void test_2_1306() throws Throwable {
		XmlElement foo = new XmlElement();
		foo.addElement(new XmlElement("anus"));
		foo.removeElement(foo.getElement(0));
		assertEquals(0,foo.count());
	}

	@Test(timeout = 4000)
	public void test_3_1322() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name, hashtable);
		assertEquals("Sheffield",obj.getAttribute("Adam"));
		
	}

	@Test(timeout = 4000)
	public void test_4_1323() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name, hashtable);
		assertEquals("Sheffield",obj.getAttribute("Adam"));
	}

	@Test(timeout = 4000)
	public void test_5_1327() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name, hashtable);
		assertEquals("York",obj.getAttribute("Nor","York"));
	}

	@Test(timeout = 4000)
	public void test_6_1328() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name, hashtable);
		assertEquals("York",obj.getAttribute("Nor","York"));
	}

	@Test(timeout = 4000)
	public void test_7_1330() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name);
		obj.setAttributes(hashtable);
		assertEquals(hashtable,obj.getAttributes());
	}

	@Test(timeout = 4000)
	public void test_8_1361() throws Throwable {
		String name = "John";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
 		hashtable.put("Adam","Sheffield");
   		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name, hashtable);
		obj.removeAllElements();
		assertEquals(null,obj.getData());
	}

	@Test(timeout = 4000)
	public void test_9_1362() throws Throwable {
		String name = "John";
		XmlElement obj = new XmlElement(name);
		assertEquals("John",obj.getName());
	}

	@Test(timeout = 4000)
	public void test_10_1363() throws Throwable {
		XmlElement e = new XmlElement();
		XmlElement c = new XmlElement();
		e.addElement(c);
		assertEquals(e,c.parent);
	}

	@Test(timeout = 4000)
	public void test_11_1364() throws Throwable {
		XmlElement e = new XmlElement();
		XmlElement c = new XmlElement();
		XmlElement p = new XmlElement();
		e.addElement(c);
		assertEquals(e,c.parent);
		p.append(c);
		assertEquals(p,c.parent);
	}

	@Test(timeout = 4000)
	public void test_12_1365() throws Throwable {
		String name = "John";
		XmlElement obj = new XmlElement();
		obj.setName(name);
		obj.setData("London");
		XmlElement obj1 = new XmlElement();
		obj1.setParent(obj);
		XmlElement obj2 = new XmlElement();
		obj2.addSubElement(obj);
		assertEquals(false,obj.equals(obj1));
	}

	@Test(timeout = 4000)
	public void test_13_1366() throws Throwable {
		assertEquals(0,new XmlElement().attributes.size());
		assertEquals(0,new XmlElement("poop").attributes.size());
	}

	@Test(timeout = 4000)
	public void test_14_1367() throws Throwable {
		String alpha = "    ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+1234567890-={}[]:\"\\:|<>?,./~`    ";
		XmlElement p = new XmlElement(alpha);
		
		XmlElement q = new XmlElement(alpha,alpha);
		assertEquals(p.name,q.name);
		assertEquals(p.subElements.size(),q.subElements.size());
		
	}

	@Test(timeout = 4000)
	public void test_15_1369() throws Throwable {
		XmlElement e = new XmlElement("top");
		XmlElement bottom = e.addSubElement("..middle.bottom");
		
		assertEquals("middle",bottom.getParent().getName());
		assertEquals(e,bottom.getParent().getParent());
	}

	@Test(timeout = 4000)
	public void test_16_1370() throws Throwable {
		XmlElement t = new XmlElement("foo");
		XmlElement m = t.addSubElement("m");
		XmlElement c = t.addSubElement("m.baz");
		assertEquals("baz",c.getName());
		assertEquals(m,c.getParent());
	}

	@Test(timeout = 4000)
	public void test_17_1372() throws Throwable {
		XmlElement e = new XmlElement();
		XmlElement o = new XmlElement();
		e.insertElement(o,0);
		assertEquals(e,o.getParent());
		assertEquals(1,e.subElements.size());
		
	}

	@Test(timeout = 4000)
	public void test_18_1373() throws Throwable {
		XmlElement e = new XmlElement("abc","def");
		assertEquals("abc",e.getName());
		assertEquals("def",e.getData());
	}

	@Test(timeout = 4000)
	public void test_19_1375() throws Throwable {
		XmlElement p = new XmlElement("a");
		XmlElement m = p.addSubElement("m","abc");
		XmlElement c = m.addSubElement("c","def");
		assertEquals("def",c.getData());
		XmlElement ret = p.addSubElement(c);
		assertEquals(p,ret.getParent());
	}

	@Test(timeout = 4000)
	public void test_20_1382() throws Throwable {
		XmlElement e = new XmlElement();
		e.setData("potatoes");
		e.addAttribute("bananas","fruit");
		e.addSubElement("a.b");
		e.name = "a";
		int hashCode = 23;
		hashCode += 17 * e.data.hashCode();
		hashCode += 29 * e.name.hashCode(); 
		hashCode += 57 * e.subElements.hashCode();
		hashCode += 13 * e.attributes.hashCode();
		assertEquals(hashCode, e.hashCode());
	}

	@Test(timeout = 4000)
	public void test_21_1383() throws Throwable {
		assertFalse((new XmlElement()).equals(null));
	}

	@Test(timeout = 4000)
	public void test_22_1384() throws Throwable {
		XmlElement a = new XmlElement();
		XmlElement b = new XmlElement();
		a.addAttribute("p","a");
		assertFalse(a.equals(b));
		b.addAttribute("p","a");
		b.setData("abc");
		a.setData("abc");
		
		XmlElement z = new XmlElement();
		a.addSubElement(z);
		b.addSubElement(z);
		assertTrue(a.equals(b));
		
	}

	@Test(timeout = 4000)
	public void test_23_1386() throws Throwable {
		XmlElement a = new XmlElement("loollers","lols");
		a.addSubElement("a.b.c.e.q");
		XmlElement c = (XmlElement)a.clone();
		assertEquals(a,c);
	}

	@Test(timeout = 4000)
	public void test_24_1387() throws Throwable {
		String name = "John";
		XmlElement obj = new XmlElement();
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		XmlElement obj1 = new XmlElement(null, hashtable);
		obj.setData(null);
		obj.setName(null);
		assertEquals(null, obj.addAttribute(null, null));
	}

	@Test(timeout = 4000)
	public void test_25_1388() throws Throwable {
		String name = "John";
		XmlElement obj = new XmlElement(name,(String)null);
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		obj.setAttributes(hashtable);
		obj.getAttributeNames();
		obj.clone();
	}

	@Test(timeout = 4000)
	public void test_26_1406() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement obj1 = new XmlElement();
		java.util.Enumeration e1 = obj.getAttributeNames();
		java.util.Enumeration e2 = obj1.getAttributeNames();
		assertSame(e1, e2);
	}

	@Test(timeout = 4000)
	public void test_27_1407() throws Throwable {
		XmlElement obj = new XmlElement();
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>(10);
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		obj.attributes = hashtable;
		obj.name = "Test";
		obj.data = "Data";
		Object oo = obj.addAttribute(null, null);
		assertEquals(oo, obj.addAttribute(null, null));
		Object oo1 = obj.addAttribute("any", null);
		assertEquals(oo1, obj.addAttribute("any", null));
		
	}

	@Test(timeout = 4000)
	public void test_28_1409() throws Throwable {
        String name_ = "name";
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>(10);
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		XmlElement obj = new XmlElement(name_,hashtable);
		obj.name=name_;
		XmlElement obj1 = new XmlElement();
		obj1.attributes = new java.util.Hashtable<String, String>(10);
		XmlElement obj2 = new XmlElement(name_);
		obj2.attributes = new java.util.Hashtable<String, String>(10);
		obj2.data="";
	}

	@Test(timeout = 4000)
	public void test_29_1410() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement.printNode(obj, (String)null);
		XmlElement obj1 = new XmlElement("name", "data");
		XmlElement.printNode(obj1, "any");
		XmlElement obj2 = new XmlElement((String) null, "data");
	    XmlElement.printNode(obj2, (String) null);
	    XmlElement obj3 = new XmlElement((String) null, "");
	    XmlElement.printNode(obj3, "");
	    java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		obj3.attributes = hashtable;
		XmlElement obj4 = (XmlElement) obj3.clone();
		assertNotSame(obj3, obj4);
	}

	@Test(timeout = 4000)
	public void test_30_1411() throws Throwable {
		XmlElement obj = new XmlElement();
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
	    hashtable.put("Adam","Sheffield");
	    hashtable.put("Sam","Leeds");
		obj.attributes = hashtable;
		Object oo1 = obj.addAttribute(null, "any");
		assertEquals(oo1, obj.addAttribute(null, "any"));
		
	}

	@Test(timeout = 4000)
	public void test_31_1412() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement cobj1 = new XmlElement();
		XmlElement cobj2 = new XmlElement();
		obj.setAttributes(null);
		assertEquals(null, obj.attributes);
		obj.addElement(cobj1);
		obj.insertElement(cobj2, 1);
		obj.removeElement(0);
	}

	@Test(timeout = 4000)
	public void test_32_1413() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement cobj1 = new XmlElement();
		XmlElement cobj2 = new XmlElement();
		obj.addElement(cobj1);
		assertEquals(cobj1, obj.getElement(0));
		try { 
			assertEquals(null, obj.getElement(1));
	        fail("ArrayIndexOutOfBoundsException");
	      } catch(ArrayIndexOutOfBoundsException e) {}
	}

	@Test(timeout = 4000)
	public void test_33_1423() throws Throwable {
		XmlElement foo = new XmlElement();
		foo.addSubElement("lol.poo.bum");
		foo.addSubElement("lol.poo.ass");
		foo.addSubElement("lol.poo.poop");
		assertEquals(foo.getElement(".lol.poo"),foo.getElement("lol.poo"));
	}

	@Test(timeout = 4000)
	public void test_34_1427() throws Throwable {
		XmlElement foo = new XmlElement("fuck");
		XmlElement child = new XmlElement("shit","cunt");
		foo.addSubElement(new XmlElement("shitbag","piss"));
		foo.addSubElement(new XmlElement("shit_newspapers","daily mail"));
		foo.addElement(child);
		XmlElement ret = foo.removeElement(child);
		assertEquals(child, ret);
		//assertEquals(2, foo.subElements.size());
	}

	@Test(timeout = 4000)
	public void test_35_1429() throws Throwable {
		XmlElement anus = new XmlElement();
		XmlElement poop = anus.addSubElement("shit","fuck");
		assertEquals(anus, poop.getParent());
		assertEquals("shit", poop.name);
	}

	@Test(timeout = 4000)
	public void test_36_1431() throws Throwable {
		assertEquals(2,XmlElement.class.getDeclaredMethod("equals",Object.class,Object.class).getModifiers());
		
	}

	@Test(timeout = 4000)
	public void test_37_1434() throws Throwable {
		XmlElement a = new XmlElement("a");
		XmlElement b = new XmlElement("a");
		
		a.addAttribute("shit","abc");
		b.addAttribute(new String("shit"),new String("abc"));
		
		assertEquals(a,b);
		
	}

	@Test(timeout = 4000)
	public void test_38_1437() throws Throwable {
		XmlElement a = new XmlElement("arse", (java.util.Hashtable)null);
		assertEquals("arse", a.name);
	}

	@Test(timeout = 4000)
	public void test_39_1438() throws Throwable {
		assertEquals("", new XmlElement("abc").data);
	}

	@Test(timeout = 4000)
	public void test_40_1439() throws Throwable {
		XmlElement a = new XmlElement("a");
		XmlElement b = new XmlElement("a");
		
		a.addAttribute("shit","abc");
		b.attributes = null;
		
		assertNotEquals(a,b);
	}

	@Test(timeout = 4000)
	public void test_41_1440() throws Throwable {
		XmlElement foo = new XmlElement();
		assertFalse(foo.equals(null));
		assertFalse(foo.equals("shit"));
		
	}

	@Test(timeout = 4000)
	public void test_42_1441() throws Throwable {
		XmlElement obj1 = new XmlElement();
		obj1.attributes = new java.util.Hashtable<String, String>(10);
		java.util.Enumeration e1 = obj1.getAttributeNames();
		java.util.Enumeration e2 = obj1.attributes.keys();
		assertSame(e2, e1);
	}

	@Test(timeout = 4000)
	public void test_43_1442() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement obj1 = new XmlElement();
		obj1.setParent(obj);
		obj1.removeFromParent();
		assertEquals(null,obj1.getParent());
	}

	@Test(timeout = 4000)
	public void test_44_1443() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement obj1 = new XmlElement("this");
		obj.addSubElement(obj1);
		obj.getElement("this.element");
		assertEquals("this", obj.getElement(0).name);
	}

	@Test(timeout = 4000)
	public void test_45_1444() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement o1 = new XmlElement();
		XmlElement o2 = new XmlElement();
		XmlElement o3 = new XmlElement();
		obj.addElement(o1);
		obj.addElement(o2);
		obj.addElement(o3);
		assertEquals(o2,obj.getElement(1));
		try { 
	        obj.getElement(null);
	        fail("NullPointerException"); 
	      } catch(NullPointerException e) {}
	}

	@Test(timeout = 4000)
	public void test_46_1445() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement obj1 = new XmlElement();
		XmlElement sub1 = new XmlElement();
		XmlElement sub2 = new XmlElement();
		obj.addElement(sub1);
		obj.addSubElement(sub2);
		java.util.Hashtable<String, String> hashtable = new java.util.Hashtable<String, String>();
		hashtable.put("Adam","Sheffield");
		hashtable.put("Sam","Leeds");
		obj.setAttributes(hashtable);
		obj1 = (XmlElement)obj.clone();
		assertEquals("Leeds",obj1.getAttribute("Sam"));
	}

	@Test(timeout = 4000)
	public void test_47_1446() throws Throwable {
		XmlElement obj = new XmlElement();
		XmlElement obj1 = new XmlElement("a","b");
		assertEquals(obj1,obj.addSubElement("a", "b"));
	}

	@Test(timeout = 4000)
	public void test_48_1448() throws Throwable {
		XmlElement obj = new XmlElement();
		obj.setAttributes(new java.util.Hashtable<String, String>(10));
		assertEquals(obj.attributes.keys(),obj.getAttributeNames());
	}

	@Test(timeout = 4000)
	public void test_49_1449() throws Throwable {
		XmlElement obj = new XmlElement();
		obj.attributes = new java.util.Hashtable<String, String>(10);
		assertNotSame(new java.util.Hashtable<Integer, Integer>(10), obj.attributes);
		XmlElement obj1 = new XmlElement("name");
		obj1.attributes = new java.util.Hashtable<String, String>(10);
		assertNotSame(new java.util.Hashtable<Integer, Integer>(10), obj1.attributes);
	}

}
