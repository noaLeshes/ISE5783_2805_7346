package unittests.geometries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class GeometriesTests 
{

	/**
	 * Test method for {@link geometries.Plane#findIntsersections(primitives.Point)}.
	 */
    @Test
	void testFindIntsersections() 
	{
		try 
		{
			Geometries collection = new Geometries();
			Sphere sphere = new Sphere(1, new Point(1, 0, 0));
			Triangle triangle = new Triangle(new Point(-4, 0, 0), new Point(0, 0, 5), new Point(0, -5, 0));
			Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2));
			collection.add(sphere, triangle, plane);

			
			// ============ Equivalence Partitions Tests ================

			// TC01: Some (but not all) shapes are cut 
			// triangle and plane cut
			assertEquals("wrong number of intersactions", 2,
					collection.findIntsersections(new Ray(new Point(-4, -3, 2), new Vector(9, 5, -1))).size());
			
			// =============== Boundary Values Tests ==================

			// TC02: Empty body collection 
			assertEquals("An empty body collection must return null",
					new Geometries().findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10, -1, 0))),
					collection.findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10, -1, 0))));

			// TC03: No cut shape 
			assertNull("No cut shape must return 0",
					collection.findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10, -1, 0))));

			// TC04: Some (but not all) shapes are cut 
			// triangle and plane cut
			assertEquals("wrong number of intersactions", 2,
					collection.findIntsersections(new Ray(new Point(-4, -3, 2), new Vector(9, 5, -1))).size());

			// TC05: Only one shape is cut 
			// the plane cut
			assertEquals("wrong number of intersactions", 1,
					collection.findIntsersections(new Ray(new Point(-0.8, -3, 1), new Vector(3.4, 3, 1.57))).size());

			// TC06: All shapes are cut
			assertEquals("wrong number of intersactions", 4,
					collection.findIntsersections(new Ray(new Point(-4, -3, 0), new Vector(6, 3, 0.5))).size());
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
			fail("dont need throws exceptions!!!");
		}
		
	}

}