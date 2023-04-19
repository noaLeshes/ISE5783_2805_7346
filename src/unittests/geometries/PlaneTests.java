/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import geometries.Plane;


/**
 * @author USER
 *
 */
class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() 
	{
		 //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the correct vector
		Point p1 = new Point(0,0,0);
		Point p2 = new Point(0,1,0);
		Point p3 = new Point(1,0,0);
		Vector v1 = new Vector(0,0,1);
		Vector v2 = new Vector(0,0,-1);
        Plane plane = new Plane(p1, p2, p3);
        assertTrue(v1.equals(plane.getNormal(p1)) || v2.equals(plane.getNormal(p1)),"Wrong normal to the plane");
	}

}
