/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * @author USER
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() 
	{
		// ============ Equivalence Partitions Tests ==============//
		// TC: Testing that getNormal returns the right vector
		Point p1 = new Point(0, 0, 0);
		Point p2 = new Point(0, 0, 1);
		Vector v = new Vector(0, 0, 1);
		Sphere sphere = new Sphere(1.0, p1);
		assertEquals(sphere.getNormal(p2), v, "Wrong normal to the sphere");
		
	}

}
