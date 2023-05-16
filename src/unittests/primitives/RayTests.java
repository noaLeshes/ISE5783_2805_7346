package unittests.primitives;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import primitives.Vector;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;


public class RayTests 
{
	@Test
	public void findClosestPoint()
	{
		try 
		{
			Ray ray = new Ray(new Point(0,0,1),new Vector(1,0,0));
			Point p1 = new Point(1,0,0);
			Point p2 = new Point(2,0,0);
			Point p3 = new Point(3,0,0);
			List<Point >points = List.of(p2,p1,p3);

			// ============ Equivalence Partitions Tests ==============
			//TC01: A point in the middle of the list is closest to the beginning of the fund
			assertEquals("", p1, ray.findClosestPoint(points));
						
			
			// =============== Boundary Values Tests ==================
			//TC02: The first point is closest to the beginning of the foundation
			points = List.of(p1,p2,p3);
			assertEquals("", p1, ray.findClosestPoint(points));
			
			//TC03: The last point is closest to the beginning of the foundation
			points = List.of(p2,p3,p1);
			assertEquals("", p1, ray.findClosestPoint(points));
			
			//TC04: An empty list
			points = List.of(null);
			assertEquals("", null, ray.findClosestPoint(points));
			
			
			
			
		}
		catch (Exception e) {}
	}
}
