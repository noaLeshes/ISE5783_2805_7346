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
 * @author USER
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

        // check ctor two point the same
    	Point p1 = new Point(0,0,0);
    	Point p2 = new Point(1,2,3);
    	Point p3 = new Point(1,2,3);
    	Point p4 = new Point(2,4,6);
    	Point p5 = new Point(4,8,12);

    	
        assertThrows(IllegalArgumentException.class, ()-> new Plane(p2,p3,p1),"ERROR: ctor get two point the same");

        //check ctor  all point on the same line
        assertThrows(IllegalArgumentException.class,() -> new Plane(p3,p4,p5),"ERROR: ctor get all point on the same line");
    }

    @Test
	public void testfindIntersections() 
	{
		try
		{
			Plane myPlane = new Plane(new Point(0,5,0), new Point(-5,0,0), new Point(0,0,3));
			// =============== Boundary Values Tests ==================
			
			//Ray is parallel to the plane
			// TC01: the ray included in the plane
			Ray myRay= new Ray(new Point(0,5,0), new Vector(-5,0,0));//the plane include this ray
			assertNull("A included ray has zero intersection points", myPlane.findIntsersections(myRay));
			// TC02: the ray not included in the plane
			myRay= new Ray(new Point(0,-5,0), new Vector(5,0,0));//the plane un included this ray
			assertNull("An un included ray has zero intersection points", myPlane.findIntsersections(myRay));
			
			//Ray is orthogonal to the plane
			// TC03:נ�‘ƒ0 before the plane
			myRay= new Ray(new Point(2,4,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertEquals("Ray is orthogonal to the plane and starts before the plane",1, myPlane.findIntsersections(myRay).size());
			// TC04:נ�‘ƒ0 at the plane
			myRay= new Ray(new Point(-5,0,0), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull("Ray is orthogonal to the plane and starts at the plane", myPlane.findIntsersections(myRay));
			// TC05:נ�‘ƒ0 after the plane
			myRay= new Ray(new Point(-7,2,4), new Vector(-3,3,5));//the ray is orthogonal to the plane
			assertNull("Ray is orthogonal to the plane and starts after the plane",myPlane.findIntsersections(myRay));
			
			//Ray is neither orthogonal nor parallel to and begins at the plane
			// TC06:
			myRay= new Ray(new Point(-1,-1,0), new Vector(1,0,0));//the ray isnt orthogonal or parallel to the plane
			assertNull("Ray is neither orthogonal nor parallel to and begins at reference point in the plane", myPlane.findIntsersections(myRay));
			
			//Ray is neither orthogonal nor parallel to the plane and begins in
			//the same point which appears as reference point in the plane
			// TC07:
			myRay= new Ray(new Point(0,0,3), new Vector(-5,4,-3));//the ray isn't orthogonal or parallel to the plane but not intersects the plane
			assertNull("Ray is neither orthogonal nor parallel to and begins at the plane", myPlane.findIntsersections(myRay));
			
			// ============ Equivalence Partitions Tests ================
			// TC08: The Ray must be neither orthogonal nor parallel to the plane
			//Ray does not intersect the plane
			myRay= new Ray(new Point(1,2,0), new Vector(-3,-7,0));
			assertNull("Ray is neither orthogonal nor parallel but doesnt intersects the plane", myPlane.findIntsersections(myRay));
			
			// TC09:
			// Ray intersects the plane
			myRay= new Ray(new Point(4,3,0), new Vector(-5.75,3.57,0));//the ray isnt orthogonal or parallel to the plane and intersects the plane
			assertEquals("Ray is neither orthogonal nor parallel and intersects the plane ",1, myPlane.findIntsersections(myRay).size());
		}
		catch(Exception ex)
		{
			
		}
	}


}
