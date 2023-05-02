/**
 * 
 */
package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import primitives.Vector;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;

/**
 * @author Miri Ordentlich and Noa Leshes
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

	/**
	 * Test method for {@link geometries.Sphere#findIntsersections(primitives.Point)}.
	 */
	  @Test
	    public void testFindIntersections()
	    {
	    	try
	    	{
	        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
	        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
	        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
	        List<Point> result = sphere.findIntsersections(new Ray(new Point(-1, 0, 0),new Vector(3, 1, 0)));
	        
	        // ============ Equivalence Partitions Tests ==============

	        // TC01: The ray's line is outside the sphere (0 points)
	        assertNull(sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))), "Ray's line out of sphere");

	        // TC02: The ray starts before and crosses the sphere (2 points)
	        assertEquals(2, result.size(), "Wrong number of points");
	        if (result.get(0).getX() > result.get(1).getX())
	        result = List.of(result.get(1), result.get(0));
	        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
	        
	        // TC03: The ray starts inside the sphere (1 point)	        
	        result = sphere.findIntsersections(new Ray(new Point(1, 0.5, 0),new Vector(-1, -1, -2)));
	        assertEquals("Wrong number of points", 1, result.size());
	        
	        // TC04: The ray starts after the sphere (0 points)
	        assertNull("The ray starts after the sphere", sphere.findIntsersections(new Ray(new Point(4, 10, 0),new Vector(1, 2, 0))));

	        // =============== Boundary Values Tests ==================

	        // **** Group: The ray's line crosses the sphere (but not the center)
	        // TC05: The ray starts at sphere and goes inside of it (1 points)
	        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),new Vector(-1, 0, 1)));
	        assertEquals("Wrong number of points", 1, result.size());
	        
	        // TC06: The ray starts at sphere and goes outside of it (0 points)
	        assertNull("The ray starts at sphere and goes outside", sphere.findIntsersections(new Ray(new Point(3, 0, 0),new Vector(1, 0, 0))));		

	        // **** Group: The ray's line goes through the center
	        // TC07: The ray starts before the sphere (2 points)
	        result = sphere.findIntsersections(new Ray(new Point(1, -2, 0),new Vector(0, 1, 0)));
	        assertEquals("Wrong number of points", 2, result.size());
	        if (result.get(0).getX() > result.get(1).getX())
	        result = List.of(result.get(1), result.get(0));
	        assertEquals("Ray crosses sphere", List.of(new Point(1, 1, 0), new Point(1, -1, 0)), result);
	        
	        // TC08: The ray starts at sphere and goes inside of it (1 points)
	        result = sphere.findIntsersections(new Ray(new Point(1, -1, 0),new Vector(0, 1, 0)));
	        assertEquals("Wrong number of points", 1, result.size());
	        
	        // TC09: The ray starts inside the sphere (1 points)
	        result = sphere.findIntsersections(new Ray(new Point(0.5, 0, 0),new Vector(4, 0, 0)));
	        assertEquals("Wrong number of points", 1, result.size());
	        
	        // TC10: The ray starts at the center (1 points)
	        result = sphere.findIntsersections(new Ray(new Point(1, 0, 0),new Vector(2.52,-5.02, 0)));
	        assertEquals("Wrong number of points", 1, result.size());
	        
	        // TC11: The ray starts at sphere and goes outside (0 points)
	        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0),new Vector(1, 0, 0)));
	        assertNull("Wrong number of points", result);
	        assertNull("The ray starts at sphere and goes outside", sphere.findIntsersections(new Ray(new Point(1, 1, 0),new Vector(0, 1, 0))));
	        
	        // TC12: The ray starts after sphere (0 points)	        
	        assertNull("The ray starts  after sphere", sphere.findIntsersections(new Ray(new Point(1, 2, 0),new Vector(0, 1, 0))));
	        
	        // **** Group: The ray's line is tangent to the sphere (all tests 0 points)
	        // TC13: The ray starts before the tangent point
	        assertNull("The ray starts before the tangent point", sphere.findIntsersections(new Ray(new Point(-0.5,-0.5, 0),new Vector(-2, -1, 0))));
	        
	        // TC14: The ray starts at the tangent point
	        assertNull("The ray starts at the tangent point", sphere.findIntsersections(new Ray(new Point(1, 0, 1),new Vector(-1,-1, 0))));
	        
	        // TC15: The ray starts after the tangent point
	        assertNull("The ray starts after the tangent point", sphere.findIntsersections(new Ray(new Point(1, 1, 1),new Vector(0, -2, 1))));
	        
	        // **** Group: Special cases
	        // TC16: The ray's line is outside the sphere, the ray is orthogonal to the line from the ray's start to the sphere's center
	        assertNull("The ray's line is outside, ray is orthogonal to ray start to sphere's center line", sphere.findIntsersections(new Ray(new Point(-0.5, 0, 0),new Vector(0, -2, 1))));
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        	fail("not need throws exception!");
	        	
	        }
	    }

}
