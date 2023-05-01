/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
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
	

	@Test
	public void testfindIntersections() 
	{
		try 
		{
			Triangle triangle = new Triangle(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0));

			// ============ Equivalence Partitions Tests ====================

			// TC01: The ray cuts the plane In front of the side of the triangle
			Ray ray = new Ray(new Point(6.94, -2.39, 0), new Vector(-2.68, 5.72, 0));
			assertNull("the intersection point is out of the triangle - need 0 intersections",
					triangle.findIntsersections(ray));

			// TC02: The ray intersects the plane on which the triangle is in front of the
			// vertex
			Ray ray1 = new Ray(new Point(-0.93, 6.2, 0), new Vector(2.54, 2.23, 0));
			assertNull("the intersection point is out of the triangle - need 0 intersections",
					triangle.findIntsersections(ray1));

			// TC03: The ray cuts the plane within the boundaries of the triangle
			Ray ray2 = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3));
			assertEquals("the intersection point is in the triangle - need 1 intersections", 1,
					triangle.findIntsersections(ray2).size());

			// =============== Boundary Values Tests ==================

			// TC11: The ray intersects on the side of the triangle
			ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(-0.15, 3.07, -2.14));
			assertNull("on the side - need 0 intersections", triangle.findIntsersections(ray));

			// TC12: The ray intersects on one of the vertices of the triangle
			ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(1.3, 0.71, -1.44));
			assertNull("on the vertex - need 0 intersections", triangle.findIntsersections(ray));

			// TC13: The ray On the straight line continuing the side of the triangle ray =
			// new Ray(new Point(3.86, -4.95, 0), new Vector(2.97, 1.28, 0));
			assertNull("the intersection point is out of the triangle - need 0 intersections",
					triangle.findIntsersections(ray1));

		}

		catch (Exception ex) 
		{
			ex.printStackTrace();
			fail("not need throws exception!");
		}
	}

}
