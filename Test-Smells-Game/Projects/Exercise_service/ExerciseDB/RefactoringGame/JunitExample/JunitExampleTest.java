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
        assertEquals(4, obj.sizeOfStudent());
    }

    @Test
    public void testSize() {
        obj.add("Emma");
        obj.add("Ronan");
        obj.add("Antonio");
        assertEquals(3, obj.sizeOfStudent());
    }

    @Test
    public void testRemove() {
        obj.add("Antonio");
        obj.add("Paul");
        obj.remove("Paul");
        assertEquals(1, obj.sizeOfStudent());
    }

    @Test
    public void removeAll() {
        obj.removeAll();
    }
}  

