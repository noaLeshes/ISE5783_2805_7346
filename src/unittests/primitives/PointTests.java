/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import primitives.Util;

/**
 * Unit tests for primitives.Point class
 * @author USER
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() 
	{
		 Point p1 = new Point(1, 2, 3);

	     // ============ Equivalence Partitions Tests ==============
	     // TC01: Proper Point subtraction test
	     assertTrue(new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)), "ERROR: Point - Point does not work correctly");
	     
	    

	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() 
	{
		 // ============ Equivalence Partitions Tests ==============
        // TC01: Proper Point adding test
		Point p1 = new Point(1, 2, 3);
        assertTrue(p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0)), "ERROR: Point + Vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() 
	{
		 Point p1 = new Point(1, 2, 3);

	        // ============ Equivalence Partitions Tests ==============
	        // TC01: Checking the correctness of the Distance Squared between points
	        assertTrue(isZero(p1.distanceSquared(new Point(2, 3, 4)) - 3), "ERROR: DistanceSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() 
	{
	    Point p1 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Checking the correctness of the Distance between points
        assertTrue(isZero(p1.distance(new Point(1, 2, 8))- 5), "ERROR: Distance() wrong value");
	}
	

}
