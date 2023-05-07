package unittests;

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

class CameraRaysIntegrationTests {

	/**
	 * A function that create 9 rays from vp 3x3
	 * 
	 * @author Noa Leshes, Miri Ordentlich
	 * @return List<Ray> value for the rays 
	 * */
	public List<Ray> Creat9RaysToVeiwPlane(Camera camera)
	{
		List<Ray> raysFromCamera = new ArrayList<Ray>();
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				try 
				{
					raysFromCamera.add(camera.constructRay(3, 3, j, i));
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					fail("There can be no zero rays");
				}
			}
		}
		return raysFromCamera;
	}
	
	/**
	 * A function that return list of intersection points
	 * 
	 * @author Noa Leshes, Miri Ordentlich
	 * @return List<Point> value for the intersection points 
	 * */
	public List<Point> findIntersectionPoints(Camera camera, Intersectable geomety)
	{
		List<Ray> raysList = Creat9RaysToVeiwPlane(camera);
		
		List<Point> intersectionPoints = new ArrayList<Point>();
		for (Ray ray : raysList) 
		{
			List<Point> temp;
			try 
			{
				temp = geomety.findIntsersections(ray);
		
				if (temp != null)
					{
						intersectionPoints.addAll(temp);
					}
			}
			catch (Exception e) 
			{
				//e.printStackTrace();
				fail("dont need exception here");
			}
		}
		if(intersectionPoints.size() == 0)
			return null;
		return intersectionPoints;
	}
	/***
	 *Function for integrating tests between the creation of rays from a camera and the calculation of intersections of a ray with sphere
	 */
	@Test
	public void constructRaySphere() 
	{
		try
		{
		
			//TC01:2 intersection points
			Sphere sphere=new Sphere(1,new Point(0,0,-3));
			Camera camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("The count of intersections are not correct", 2, findIntersectionPoints(camera, sphere).size());

		
			//TC02:18 intersection points
			sphere=new Sphere(2.5,new Point(0,0,-2.5));
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertEquals("The count of intersections are not correct", 18, findIntersectionPoints(camera, sphere).size());
		
			//TC03:10 intersection points
			sphere=new Sphere(2,new Point(0,0,-2));
			//same camera like tc02
			assertEquals("The count of intersections are not correct", 10, findIntersectionPoints(camera, sphere).size());
		
			//TC04:9 intersection points
			sphere = new Sphere( 4,new Point(0,0,0));
			camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("The count of intersections are not correct", 9, findIntersectionPoints(camera, sphere).size());	
		
			//TC05:0 intersection points
			sphere=new Sphere( 0.5,new Point(0,0,1));
			camera = new Camera(new Point(0,0,0.5), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);	
			assertNull("The count of intersections are not correct", findIntersectionPoints(camera, sphere));
		
		}
		catch(Exception ex)
		{
			fail("dont need throws exception");
		}

	}
	
	/***
	 *Function for integrating tests between the creation of rays from a camera and the calculation of intersections of a ray with plane
	 */
	@Test
	public void constructRayPlane() 
	{	
		try 
		{
			//TC01:9 intersection points
			Plane plane =new Plane(new Point(0,2,0), new Vector(0,1,0));
			Camera camera =new Camera(new Point(0,0,0), new Vector(0,1,0) , new Vector(0,0,-1)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("The count of intersections are not correct", 9, findIntersectionPoints(camera, plane).size());	

			//TC02:9 intersection points	
			plane =new Plane(new Point(2,0,0),new Vector(1,2,-0.5));
			//same camera
			assertEquals("The count of intersections are not correct", 9, findIntersectionPoints(camera, plane).size());	

			//TC03:6 intersection points
			plane =new Plane(new Point(2,0,0), new Vector(1,1,0));
			//same camera
			assertEquals("The count of intersections are not correct", 6, findIntersectionPoints(camera, plane).size());	
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
			fail("dont need throws exception");
		}

	}
	/***
	 *Function for integrating tests between the creation of rays from a camera and the calculation of intersections of a ray with triangle
	 */
	@Test
	public void constructRayTriangle() 
	{
		try 
		{
			//TC01:1 intersection points
			Triangle triangle=new Triangle(new Point(0,1,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			Camera camera=new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)).setVPDistance(1).setVPSize(3, 3);
			assertEquals("The count of intersections are not correct", 1, findIntersectionPoints(camera, triangle).size());	
			
			//TC02:2 intersection points
			triangle=new Triangle(new Point(0,20,-2),new Point(1,-1,-2),new Point(-1,-1,-2));
			//same camera
			assertEquals("The count of intersections are not correct", 2, findIntersectionPoints(camera, triangle).size());	
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			fail("dont need throws exception");
		}
			
	}

}