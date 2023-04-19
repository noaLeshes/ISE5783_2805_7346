/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;

/**
 * @author USER
 *
 */
class TriangleTests 
{

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal()
	{
		// ============ Equivalence Partitions Tests ==============


		// TC01: There is a simple single test here - using a quad
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
		Triangle pol = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)); // ensure there are no
																									// exceptions
		assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pol.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 2; ++i)
			assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
					"Triangle's normal is not orthogonal to one of the edges");
	}

}
