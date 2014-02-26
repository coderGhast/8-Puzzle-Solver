package uk.co.jameseuesden.eightpuzzle.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.co.jameseuesden.eightpuzzle.GridState;

public class TestGridState {
    
    private GridState grid;

    @Before
    public void before(){
        grid = new GridState("120345678");
    }
    
    @Test
    public void testGridState() {
        //fail("Not yet implemented");
    }
    
    @Test
    public void testGetElements() {
        assertEquals("Strings should match on return", "120345678", grid.getElements());
        grid.setElements("876543021");
        assertEquals("Strings should match on return after change", "876543021", grid.getElements());
    }
    
    @Test
    public void testSetElements() {
        GridState testGrid = new GridState("120356478");
        grid.setElements("120356478");
        assertTrue("Strings should match after new elements set", grid.equals(testGrid));
    }

    @Test
    public void testEqualsObject() {
        GridState testGrid = new GridState("120345678");
        assertTrue("Strings should match", grid.equals(testGrid));
        testGrid.setElements("120356478");
        assertFalse("Strings should not match after change", grid.equals(testGrid));
        assertEquals("Strings should match", "120356478", testGrid.getElements());
    }

}
