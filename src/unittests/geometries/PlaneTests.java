/**
 * 
 */
package unittests.geometries;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


/**
 * @author Miri Ordentlich and Noa Leshes
 *
 */
class PlaneTests 
{

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

    @Test
    void testConstructor() 
    {
        // =============== Boundary Values Tests ==================

        // check constructor, two points are the same
    	Point p1 = new Point(0,0,0);
    	Point p2 = new Point(1,2,3);
    	Point p3 = new Point(1,2,3);
    	Point p4 = new Point(2,4,6);
    	Point p5 = new Point(4,8,12);
        assertThrows(IllegalArgumentException.class, ()-> new Plane(p2,p3,p1),"ERROR: ctor get two point the same");
        //check constructor  all point on the same line
        assertThrows(IllegalArgumentException.class,() -> new Plane(p3,p4,p5),"ERROR: ctor get all point on the same line");
    }

	/**
	 * Test method for {@link geometries.Plane#findIntsersections(primitives.Point)}.
	 */
    @Test
	public void testfindIntersections() 
	{
		try
		{
			Plane myPlane = new Plane(new Point(0,5,0), new Point(-5,0,0), new Point(0,0,3));
			Ray myRay= new Ray(new Point(0,5,0), new Vector(-5,0,0));//the plane include this ray

			// ============ Equivalence Partitions Tests ================
			
			// TC01: The Ray must be neither orthogonal nor parallel to the plane and starts outside of the plane
			// The ray intersects the plane (1 points)
			myRay= new Ray(new Point(4,3,0), new Vector(-5.75,3.57,0));
			assertEquals("Ray is neither orthogonal nor parallel and intersects the plane ",1, myPlane.findIntersections(myRay).size());
			
			// TC02: The Ray must be neither orthogonal nor parallel to the plane and starts outside of the plane
			//The ray does not intersect the plane (0 points) 
			myRay= new Ray(new Point(1,2,0), new Vector(-3,-7,0));
			assertNull("Ray is neither orthogonal nor parallel but doesnt intersects the plane", myPlane.findIntersections(myRay));
			
			// =============== Boundary Values Tests ==================
			
			// **** Group: The ray is parallel to the plane
			// TC03: the ray is included in the plane (0 points)
			assertNull("A included ray has zero intersection points", myPlane.findIntersections(myRay));
			// TC04: The ray is not included in the plane (0 points)
			myRay= new Ray(new Point(0,-5,0), new Vector(5,0,0));
			assertNull("An un included ray has zero intersection points", myPlane.findIntersections(myRay));
			
			// **** Group: The ray is orthogonal to the plane
			// TC05: The ray is before the plane (1 points)
			myRay= new Ray(new Point(2,4,0), new Vector(-3,3,5));
			assertEquals("Ray is orthogonal to the plane and starts before the plane",1, myPlane.findIntersections(myRay).size());
			// TC06: The ray is in the plane (0 points)
			myRay= new Ray(new Point(-5,0,0), new Vector(-3,3,5));
			assertNull("Ray is orthogonal to the plane and starts at the plane", myPlane.findIntersections(myRay));
			// TC07: The ray is after the plane (0 points)
			myRay= new Ray(new Point(-7,2,4), new Vector(-3,3,5));
			assertNull("Ray is orthogonal to the plane and starts after the plane",myPlane.findIntersections(myRay));
			
			// TC08: The ray is neither orthogonal nor parallel to the plane and begins at the plane (0 points)
			myRay= new Ray(new Point(-1,-1,0), new Vector(1,0,0));
			assertNull("Ray is neither orthogonal nor parallel to and begins at reference point in the plane", myPlane.findIntersections(myRay));
			
			// TC09: The ray is neither orthogonal nor parallel to the plane and begins at the same point which appears as reference point in the plane (0 points)
			myRay= new Ray(new Point(0,0,3), new Vector(-5,4,-3));
			assertNull("Ray is neither orthogonal nor parallel to and begins at the plane", myPlane.findIntersections(myRay));
		}
		catch(Exception ex)
		{
			
		}
	}


}
