package unittests.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;
import static primitives.Util.*;

class CameraRaysIntegrationTests 
{
	/**
	 * Creates 9 rays from the view plane 3x3.
	 * @param camera The camera object to create the rays from.
	 * @return A list of Ray objects.
	 */
	private List<Ray> Creat9RaysToVeiwPlane(Camera camera , int size)
	{
		List<Ray> raysFromCamera = new ArrayList<Ray>();
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				// Construct a ray from the camera's position to the point on the view plane
				// with coordinates (j, i) and add it to the list of rays.
				try 
				{
					raysFromCamera.add(camera.constructRay(size, size, j, i));
				} 
				catch (Exception e) 
				{
					fail("There can be no zero rays");
				}
			}
		}
		return raysFromCamera;
	}
	
	/**
	 * Finds the intersection points between a given geometry and a set of rays
	 * originating from a given camera.
	 * @param camera The camera object to create the rays from.
	 * @param geometry The geometry object to find the intersection points with.
	 * @return A list of Point objects representing the intersection points.
	 */
	private List<Point> findIntersectionPoints(Camera camera, Intersectable geomety)
	{
		int size = 3;
		List<Ray> raysList = Creat9RaysToVeiwPlane(camera, size);// a list of 9 rays from the camera to the view plane
		List<Point> intersectionPoints = new ArrayList<Point>();// a list to hold the intersection points
		for (Ray ray : raysList)// iterating over the list of rays and find the intersection points with the geometry
		{
			List<Point> temp;
			try 
			{
				temp = geomety.findIntersections(ray);// finding the intersection points between the geometry and the current ray	
				if (temp != null)// if there are intersection points, add them to the list of the intersection points
					{
						intersectionPoints.addAll(temp);
					}
			}
			catch (Exception e) 
			{
				fail("dont need exception here");
			}
		}
		if(intersectionPoints.size() == 0)// if no intersection points were found, return null
		{
			return null;
		}
		return intersectionPoints;
	}
	
	/**
	 * Test method for {@link Camera#constructRaySphere()}.
	 * Function for integration tests between the creation of rays from the camera and the calculation of the intersections of the ray with the sphere
	 */
	@Test
	public void constructRaySphere() 
	{
		try
		{
			Sphere sphere=new Sphere(1,new Point(0,0,-3));
			Camera camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			
	        //TC01: The sphere is in front of the camera (2 points)
			assertEquals("Number of intersection points is not correct", 2, findIntersectionPoints(camera, sphere).size());

	        //TC02: The sphere intersects the view plane before the camera (18 points)
			sphere=new Sphere(2.5,new Point(0,0,-2.5));
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertEquals("Number of intersection points is not correct", 18, findIntersectionPoints(camera, sphere).size());
		
	        //TC03: The sphere intersects the view plane before the camera (10 points)
			sphere=new Sphere(2,new Point(0,0,-2));
			assertEquals("Number of intersection points is not correct", 10, findIntersectionPoints(camera, sphere).size());
		
	        //TC04: The sphere contains the view plane and the camera (9 points)
			sphere = new Sphere( 4,new Point(0,0,0));
			camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("Number of intersection points is not correct", 9, findIntersectionPoints(camera, sphere).size());	
		
	        //TC05: The sphere is behind the camera (0 points)
			sphere=new Sphere( 0.5,new Point(0,0,1));
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertNull("Number of intersection points is not correct", findIntersectionPoints(camera, sphere));
		}
		catch(Exception ex)
		{
			fail("dont need throws exception");
		}
	}
	
	/***
	 * Test method for {@link Camera#constructRayPlane()}.
	 * Function for integration tests between the creation of rays from the camera and the calculation of the intersections of the ray with the plane
	 */
	@Test
	public void constructRayPlane() 
	{	
		try 
		{
			Plane plane =new Plane(new Point(0,2,0), new Vector(0,1,0));
			Camera camera =new Camera(new Point(0,0,0), new Vector(0,1,0) , new Vector(0,0,-1)).setVPDistance(1).setVPSize(3, 3);
			
	        //TC01: The plane is against the camera, parallel to the view plane (9 points)
			assertEquals("Number of intersection points is not correct", 9, findIntersectionPoints(camera, plane).size());	

	        //TC02: The plane has an acute angle to the view plane, all rays intersect (9 points)
			plane =new Plane(new Point(2,0,0),new Vector(1,2,-0.5));
			assertEquals("Number of intersection points is not correct", 9, findIntersectionPoints(camera, plane).size());	

	        //TC03: The plane has an obtuse angle to the view plane, parallel to lower rays (6 points)
			plane =new Plane(new Point(2,0,0), new Vector(1,1,0));
			assertEquals("Number of intersection points is not correct", 6, findIntersectionPoints(camera, plane).size());	
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			fail("dont need throws exception");
		}
	}
	
	/***
	 * Test method for {@link Camera#constructRayTriangle()}.
	 * Function for integration tests between the creation of rays from the camera and the calculation of the intersections of the ray with the triangle
	 */
	@Test
	public void constructRayTriangle() 
	{
		try 
		{
			Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			Camera camera=new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			
	        //TC01: A small triangle in front of the view plane (1 points)
			assertEquals("Number of intersection points is not correct", 1, findIntersectionPoints(camera, triangle).size());	
			
	        //TC02: A large triangle in front of the view plane (2 points)
			triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			assertEquals("Number of intersection points is not correct", 2, findIntersectionPoints(camera, triangle).size());	
		} 
		catch (Exception e) 
		{
			fail("dont need throws exception");
		}
			
	}

}