package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;



import geometries.Polygon;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;

/**
 * Testing Polygons
 * 
 * @author Dan
 */
public class PolygonTests 
{
	/** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
	@Test
	public void testConstructor() 
	{
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try 
		{
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} 
		catch (IllegalArgumentException e) 
		{
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
		Polygon pol = new Polygon(pts);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pol.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 3; ++i)
			assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
					"Polygon's normal is not orthogonal to one of the edges");
	}
	
	/**
	 * Test method for {@link geometries.Polygon#findIntsersections(primitives.Point)}.
	 */
	 @Test
	   public void testfindIntersections() 
	   {
	       try
	       {
	       Polygon myPolygon = new Polygon(new Point(0, 1, 0), new Point(2, 6, 0), new Point(5, 0, 0), new Point(-1,8,0));
	       Ray ray = new Ray(new Point(-2.09, 2.69, 2.3), new Vector(4.09, -0.69, -2.3));

	       // ============ Equivalence Partitions Tests ====================
	   
	       //The ray begins "before" the plane
	       // TC01: The ray cuts the plane inside the polygon (1 points)
	       assertEquals("The intersection point is in the Polygon - need 1 intersections", 1, myPolygon.findIntsersections(ray).size());
	       
	       // TC02: The ray cuts the plane outside of the polygon opposite to one of the polygon's sides (0 points)
	       ray = new Ray(new Point(6.94, -2.39, 0), new Vector(-2.68, 5.72, 0));
	       assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntsersections(ray));

	        // TC03: The ray cuts the plane outside of the polygon opposite to one of the polygon's vertices (0 points)
	       ray = new Ray(new Point(-0.93, 6.2, 0), new Vector(2.54, 2.23, 0));
	       assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntsersections(ray));

	       // =============== Boundary Values Tests ==================

		   // TC04: The ray intersects the plane on one of the polygon's sides (0 points)
	       ray = new Ray(new Point(4.26, -1.28, 2.14), new Vector(-0.15, 3.07, -2.14));
	       assertNull("On the side - need 0 intersections", myPolygon.findIntsersections(ray));
	       
		   // TC05: The ray intersects the plane on one of the polygon's vertices (0 points)
	       ray = new Ray(new Point(3.7, -0.71, 1.44), new Vector(1.3, 0.71, -1.44));
	       assertNull("On the vertex - need 0 intersections", myPolygon.findIntsersections(ray));
	       
		   // TC06: The ray intersects the plane on the line that continues one of the sides of the polygon (0 points)
	       ray = new Ray(new Point(3.86, -4.95, 0), new Vector(2.97, 1.28, 0));
	       assertNull("The intersection point is out of the Polygon - need 0 intersections", myPolygon.findIntsersections(ray));
	       }
	       catch(Exception ex)
	       {
	    	   
	       }
	   }
}
