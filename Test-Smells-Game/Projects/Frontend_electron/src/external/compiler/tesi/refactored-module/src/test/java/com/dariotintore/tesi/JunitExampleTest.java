package com.dariotintore.tesi;

import org.junit.*;
import static org.junit.Assert.*;

public class JunitExampleTest {

    JunitExample obj = new JunitExample();

    @Test
    public void testAdd() {
        obj.add("Emma");
        obj.add("Ronan");
        obj.add("Antonio");
        obj.add("Paul");
	  int quattro = 4;
        assertEquals(quattro, obj.sizeOfStudent());
    }

    @Test
    public void testSize() {
        obj.add("Emma");
        obj.add("Ronan");
        obj.add("Antonio");
	  int tre = 3;
        assertEquals(tre, obj.sizeOfStudent());
    }

    @Test
    public void testRemove() {
        obj.add("Antonio");
        obj.add("Paul");
        obj.remove("Paul");
	  int uno = 1;
        assertEquals(uno, obj.sizeOfStudent());
    }

    @Test
    public void removeAll() {
        obj.removeAll();
    }
}  

