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

class GeometriesTests {
  /***@Test
	void testAdd() 
	{
		try 
		{
			Triangle triangle = new Triangle(new Point(1,0,0),new Point(1,1,0),new Point(1,0,1));
			Sphere sphere = new Sphere(1,new Point(1, 0, 0));
			Plane plane = new Plane (new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2));
			//Tube tube = new Tube(1.5, new Ray(new Point(1, 2, 3),new Vector(new Point3D(5, -3, 0))));
			//Cylinder cylinder = new Cylinder(2, new Ray(new Point(1, 4, -2),new Vector(new Point3(1, 0, 2))), 15);
			
			Geometries collection= new Geometries(sphere, triangle, plane);
			//collection.add(sphere, triangle, plane/*, tube ,cylinder*///);
			
			/***collection.add(null); // add 0 things
			
			assertEquals("the length of the list is worng", 3, collection.getIntsersectionsPoints().size());
			Triangle t = new Triangle(new Point(1,8,-6),new Point(1,0,0),new Point(1,0,2));
			collection.add(t);
			
			assertEquals("the length of the list is worng", 4, collection.getIntsersectionPoints().size());
			
			***/
			
			
			
		//}
		/***catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("dont need throws exceptions!!!");
		}
	}***/

	@Test
	void testFindIntsersections() 
	{
		try 
		{
			//=====Empty body collection (BVA)=====//
			Geometries collection= new Geometries();
			assertEquals("An empty body collection must return null",  new Geometries().findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10,-1,0))), collection.findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10,-1,0))));
			
			//=====No cut shape (BVA)=====//
			Sphere sphere = new Sphere(1,new Point(1, 0, 0)); 
			Triangle triangle = new Triangle(new Point(-4,0,0), new Point(0, 0, 5), new Point(0, -5, 0));
			Plane plane = new Plane (new Point(0, 0, 1), new Point(1, 0, 0), new Point(4, 0, 2));
			
			collection.add(sphere, triangle, plane/*, tube ,cylinder*/);
		
			assertNull("No cut shape must return 0",collection.findIntsersections(new Ray(new Point(0, -8, 0), new Vector(-10,-1,0))));
			
			
			//=====Some (but not all) shapes are cut (EP)=====//
			//triangle and plan cut
			assertEquals("wrong number of intersactions", 2, collection.findIntsersections(new Ray(new Point(-4, -3, 2), new Vector(9,5,-1))).size());
			
			//=====Only one shape is cut (BVA)=====//
			//the plane cut
			assertEquals("wrong number of intersactions", 1, collection.findIntsersections(new Ray(new Point(-0.8, -3, 1), new Vector(3.4,3,1.57))).size());

			
			//=====All shapes are cut (BVA)=====//
			assertEquals("wrong number of intersactions", 4, collection.findIntsersections(new Ray(new Point(-4, -3, 0), new Vector(6,3,0.5))).size());			

		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("dont need throws exceptions!!!");
		}
	}

}