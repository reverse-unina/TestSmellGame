import org.junit.*;
import static org.junit.Assert.*;

// Game: 1006
public class HierarchyPropertyParserTest {

	@Test(timeout = 4000)
	public void test_1_712() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		//String tree = hpp.showTree();
		
		assertEquals(".", sep);
		assertEquals(4, depth);
	}

	@Test(timeout = 4000)
	public void test_2_716() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		//String tree = hpp.showTree();
		
		assertEquals("node1", hpp.fullValue());
		assertEquals(null, hpp.context());
	}

	@Test(timeout = 4000)
	public void test_3_736() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		assertEquals("node1(0)[null]|------node1_1(1)[node1]||------node1_1_1(2)[node1.node1_1]|||------node1_1_1_1(3)[node1.node1_1.node1_1_1]|||------node1_1_1_2(3)[node1.node1_1.node1_1_1]|||------node1_1_1_3(3)[node1.node1_1.node1_1_1]||------node1_1_2(2)[node1.node1_1]|||------node1_1_2_1(3)[node1.node1_1.node1_1_2]||------node1_1_3(2)[node1.node1_1]||------node1_1_3_1(3)[node1.node1_1.node1_1_3]|------node1_2(1)[node1]||------node1_2_1(2)[node1.node1_2]|||------node1_2_1_1(3)[node1.node1_2.node1_2_1]||------node1_2_3(2)[node1.node1_2]||------node1_2_3_1(3)[node1.node1_2.node1_2_3]|------node1_3(1)[node1]|------node1_3_3(2)[node1.node1_3]|------node1_3_3_1(3)[node1.node1_3.node1_3_3]|------node1_3_3_2(3)[node1.node1_3.node1_3_3]", tree);
		//assertEquals(null, parent);
	}

	@Test(timeout = 4000)
	public void test_4_738() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: "+ hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		
		assertEquals("here we go: truenode1_2_1node1.node1_2.node1_2_1falsefalsenode1_2_1truenode1_2_1_1 | node1.node1_2.node1_2_1.node1_2_1_1 leaf? true root? falsenode1_2_13node1.node1_2.node1_2_1", magic);

	}

	@Test(timeout = 4000)
	public void test_5_740() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: "+ hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		assertEquals("here we go: truenode1_2_1node1.node1_2.node1_2_1falsefalsenode1_2_1truenode1_2_1_1 | node1.node1_2.node1_2_1.node1_2_1_1 leaf? true root? falsenode1_2_13node1.node1_2.node1_2_1false root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? false", magic);

	}

	@Test(timeout = 4000)
	public void test_6_741() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		
		hpp.setSeperator(": ");
		String sep =  hpp.getSeperator();
		assertEquals(": ", sep);
	}

	@Test(timeout = 4000)
	public void test_7_742() throws Throwable {
		// test here!
		StringBuffer sb = new StringBuffer();

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		int depth = hpp.depth();
		assertEquals(0, depth);
		
	}

	@Test(timeout = 4000)
	public void test_8_745() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: "+ hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: truenode1_2_1node1.node1_2.node1_2_1falsefalsenode1_2_1truenode1_2_1_1 | node1.node1_2.node1_2_1.node1_2_1_1 leaf? true root? falsenode1_2_13node1.node1_2.node1_2_1false root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? falsenode1_1 | node1.node1_11[node1_1_1, node1_1_2, node1_1_3]true: node1 | node1", magic);

	}

	@Test(timeout = 4000)
	public void test_9_748() throws Throwable {
		// test here!
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		int numKids = hpp.numChildren();
		assertEquals(3, numKids);
	}

	@Test(timeout = 4000)
	public void test_10_750() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		int x = hpp.search(null, "node1");
		assertEquals (-1, x);

	}

	@Test(timeout = 4000)
	public void test_11_757() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_2, ");
		sb.append("node1.node1_1.node1_1_1.node1_1_1_3, ");
		sb.append("node1.node1_1.node1_1_2.node1_1_2_1, ");
		sb.append("node1.node1_1.node1_1_3.node1_1_3_1, ");
		sb.append("node1.node1_2.node1_2_1.node1_2_1_1, ");
		sb.append("node1.node1_2.node1_2_3.node1_2_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_1, ");
		sb.append("node1.node1_3.node1_3_3.node1_3_3_2, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		try{
		hpp.build("node1", "._ ,");
			fail("should've failed");
		}catch (Exception e){}
		hpp.build("", "._ ,");
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]||------node1_1_1(2)[node1.node1_1]|||------node1_1_1_1(3)[node1.node1_1.node1_1_1]|||------node1_1_1_2(3)[node1.node1_1.node1_1_1]|||------node1_1_1_3(3)[node1.node1_1.node1_1_1]||------node1_1_2(2)[node1.node1_1]|||------node1_1_2_1(3)[node1.node1_1.node1_1_2]||------node1_1_3(2)[node1.node1_1]||------node1_1_3_1(3)[node1.node1_1.node1_1_3]|------node1_2(1)[node1]||------node1_2_1(2)[node1.node1_2]|||------node1_2_1_1(3)[node1.node1_2.node1_2_1]||------node1_2_3(2)[node1.node1_2]||------node1_2_3_1(3)[node1.node1_2.node1_2_3]|------node1_3(1)[node1]|------node1_3_3(2)[node1.node1_3]|------node1_3_3_1(3)[node1.node1_3.node1_3_3]|------node1_3_3_2(3)[node1.node1_3.node1_3_3]truenode1_2_1node1.node1_2.node1_2_1falsefalsenode1_2_1truenode1_2_1_1 | node1.node1_2.node1_2_1.node1_2_1_1 leaf? true root? falsenode1_2_13node1.node1_2.node1_2_1false root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? falsenode1_1 | node1.node1_11[node1_1_1, node1_1_2, node1_1_3]true: node1 | node1", magic);

	}

	@Test(timeout = 4000)
	public void test_12_764() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1, ");
		sb.append("node1.node1_1, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		try{
		hpp.build("node1", "._ ,");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]falsenode1node1falsefalsenode1falsenode1 | node1 leaf? false root? truenull0nullfalse root? truefalse value: node1 | node1 level: 0 leaf? false root? truenode1 | node10[node1_1]true: node1 | node1", magic);

	}

	@Test(timeout = 4000)
	public void test_13_768() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		try{
		hpp.build("node1", "._ ,");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree;
		
		try{
		magic += hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
			fail(":-(");
		} catch(NullPointerException npe){}
		assertEquals("here we go: null(0)[null]", magic);
		assertEquals(0,depth);

	}

	@Test(timeout = 4000)
	public void test_14_772() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1, ");
		sb.append("node1.node1_2, ");
		sb.append("node2.node2_1, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		try{
			hpp.build("node1", "._ ,");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]|------node1_2(1)[node1]|------node2_1(1)[node1]falsenode1node1falsefalsenode1falsenode1 | node1 leaf? false root? truenull0nullfalse root? truefalse value: node1 | node1 level: 0 leaf? false root? truenode1 | node10[node1_1, node1_2, node2_1]true: node1 | node1", magic);
		assertEquals(2,depth);
	}

	@Test(timeout = 4000)
	public void test_15_775() throws Throwable {
		// test here!
		StringBuffer sb = new StringBuffer();
		sb.append(" node1.node1_1.node1_1_1.node1_1_1_1, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		
		assertEquals(false, hpp.contains(" node1 "));
	}

	@Test(timeout = 4000)
	public void test_16_776() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1.node1_1_1_1_1_1, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		try{
			hpp.build("node1", "._ ,");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += java.util.Arrays.toString(chd);
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]|------node1_1_1(2)[node1.node1_1]|------node1_1_1_1(3)[node1.node1_1.node1_1_1]|------node1_1_1_1_1(4)[node1.node1_1.node1_1_1.node1_1_1_1]|------node1_1_1_1_1_1(5)[node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1]falsenode1node1falsefalsenode1falsenode1 | node1 leaf? false root? truenull0nullfalse root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? falsenode1_1 | node1.node1_11[node1_1_1]true: node1 | node1", magic);
		assertEquals(6,depth);
	}

	@Test(timeout = 4000)
	public void test_17_781() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1.node1_1_1_1_1_1| ");
		sb.append("node1.node1_2| ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, "| ");
		try{
			hpp.build("node1", ".");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += "kids:[" + java.util.Arrays.toString(chd) + "]";
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]||------node1_1_1(2)[node1.node1_1]||------node1_1_1_1(3)[node1.node1_1.node1_1_1]||------node1_1_1_1_1(4)[node1.node1_1.node1_1_1.node1_1_1_1]||------node1_1_1_1_1_1(5)[node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1]|------node1_2(1)[node1]falsenode1node1falsefalsenode1falsenode1 | node1 leaf? false root? truenull0nullfalse root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? falsenode1_1 | node1.node1_11kids:[[node1_1_1]]true: node1 | node1", magic);
		assertEquals(6,depth);
	}

	@Test(timeout = 4000)
	public void test_18_784() throws Throwable {
		StringBuffer sb = new StringBuffer();
		sb.append("node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1.node1_1_1_1_1_1| ");
		sb.append("node1.node1_2| ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, "| ");
		try{
			hpp.build("node1", ".");
			fail("should've failed");
		}catch (Exception e){}
		
		String sep =  hpp.getSeperator();
		int depth = hpp.depth();
		String tree = hpp.showTree().toString();
		String parent = "";
		try{
			parent = hpp.parentValue().toString();
			fail("no daddy");
		}catch(NullPointerException npe){}
		int level = hpp.getLevel();
		tree = tree.replace("\n", "").replace("\r", "").replace(" ","");
		
		String magic = "here we go: " + tree + hpp.goTo("node1.node1_2.node1_2_1") + hpp.getValue() + hpp.fullValue() + hpp.isLeafReached() + hpp.goDown("node1") + hpp.getValue() + hpp.goToChild("node1_2_1_1") + hpp.getValue() + " | " + hpp.fullValue() +
					" leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.parentValue() + hpp.getLevel() + hpp.context();
		hpp.goToRoot();
		magic += hpp.isLeafReached()
					+ " root? " + hpp.isRootReached() + hpp.goDown("node1_1.node1_1_1") +
					" value: " + hpp.getValue() + " | " + hpp.fullValue()
					+ " level: " + hpp.getLevel()
					+ " leaf? " + hpp.isLeafReached()
					+ " root? " + hpp.isRootReached();
		hpp.goToParent();
		magic += hpp.getValue() + " | " + hpp.fullValue() + hpp.getLevel();
		
		String[] chd = hpp.childrenValues();
			
		magic += "kids:[" + java.util.Arrays.toString(chd) + "==" + chd.length + "]";
		hpp.goDown(chd[0]);
		magic+=hpp.getValue() + " | " +
						hpp.fullValue() +
						"(level: " + hpp.getLevel() + ")";
		magic+=hpp.goTo("node1")
					+ ": " + hpp.getValue() + " | " + hpp.fullValue();
		
		assertEquals("here we go: node1(0)[null]|------node1_1(1)[node1]||------node1_1_1(2)[node1.node1_1]||------node1_1_1_1(3)[node1.node1_1.node1_1_1]||------node1_1_1_1_1(4)[node1.node1_1.node1_1_1.node1_1_1_1]||------node1_1_1_1_1_1(5)[node1.node1_1.node1_1_1.node1_1_1_1.node1_1_1_1_1]|------node1_2(1)[node1]falsenode1node1falsefalsenode1falsenode1 | node1 leaf? false root? truenull0nullfalse root? truetrue value: node1_1_1 | node1.node1_1.node1_1_1 level: 2 leaf? false root? falsenode1_1 | node1.node1_11kids:[[node1_1_1]==1]node1_1_1 | node1.node1_1.node1_1_1(level: 2)true: node1 | node1", magic);
		assertEquals(6,depth);
	}

	@Test(timeout = 4000)
	public void test_19_785() throws Throwable {
		// test here!
		StringBuffer sb = new StringBuffer();
		sb.append(" node1.node1_1.node1_1_1.node1_1_1_1, ");

		String p = sb.toString();
		
		HierarchyPropertyParser hpp = new HierarchyPropertyParser(p, ", ");
		
		assertEquals(false, hpp.contains(" node1"));
	}

	@Test(timeout = 4000)
	public void test_20_786() throws Throwable {
    HierarchyPropertyParser hpp = new HierarchyPropertyParser();
    hpp.add("test.value");
    hpp.goTo("test");
    hpp.add("new.real");
    hpp.goToRoot();
		assertEquals(2, hpp.numChildren());
	}

	@Test(timeout = 4000)
	public void test_21_787() throws Throwable {
		HierarchyPropertyParser hpp = new HierarchyPropertyParser();
		assertEquals(0, hpp.depth());
		assertEquals(0, hpp.depth());
	}

	@Test(timeout = 4000)
	public void test_22_789() throws Throwable {
		HierarchyPropertyParser hpp = new HierarchyPropertyParser();
	    hpp.add("test.value");
    	hpp.add("test.new");
    	hpp.goTo("test.new");
		assertEquals("test(0)[null]\n  |------ value(1)[test]\n  |------ new(1)[test]\n", hpp.showTree());

	}

	@Test(timeout = 4000)
	public void test_23_790() throws Throwable {
		HierarchyPropertyParser hpp = new HierarchyPropertyParser();
		String sep = hpp.getSeperator();
		assertTrue(sep == hpp.getSeperator());
	}

}
