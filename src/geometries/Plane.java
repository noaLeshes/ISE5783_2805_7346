package geometries;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;
public class Plane extends Geometry 
{
	
	final Point point;
	final Vector normal; 
	
	/**
	 * Constructs a new plane with the specified point and normal vector.
	 * The normal vector is normalized to have a length of 1.
	 * @param p - the point on the plane
	 * @param n - the normal vector to the plane
	 */
    public Plane(Point p ,Vector n) 
    {
    	point = p;
    	normal = n.normalize(); 			
   	}
    
    /**
     * Constructs a new plane from three non - collinear points.
     * @param p1 - the first point
     * @param p2 - the second point
     * @param p3 - the third point
     */
    public Plane(Point p1, Point p2, Point p3) 
    {
    try { // try for case the constructor get all the points on the same vector or at least two points are the same
		point = p1;
        normal = p1.subtract(p2).crossProduct(p1.subtract(p3)).normalize();
        } 
    	catch (IllegalArgumentException e)
    	{
            throw new IllegalArgumentException("the points are on the same vector");
        }
	}
    
    /**
     * @return the point on the plane
     */
    public Point getPoint()
    {
		return point;
	}
    
    /**
     * @return the normal vector to the plane
     */
	public Vector getNormal() 
	{
		return normal;
	}

	/**
	 * Returns the normal vector to the plane at the specified point.
	 * @param point - the point to get the normal vector at
	 */
	@Override
	public Vector getNormal(Point point) 
	{
		return normal;
	} 
	
	/**	  
	 * @return a list of the intersection points with the plane 
	 * @param ray - the ray that intersects with the plane
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		double nv = normal.dotProduct(ray.getDir());
		
		// If the ray is parallel to the plane, return null
		if (isZero(nv))
		{
			return null;
		}
		try 
		{
			Vector pSubtractP0 = point.subtract(ray.getP0());
			double t = alignZero((normal.dotProduct(pSubtractP0))/nv);
			
			 // If the intersection point is behind the ray's origin, return null
			if(t <= 0)
			{
				return null;
			}
			 // Return a list containing the intersection point with the plane
			return List.of(new GeoPoint(this,ray.getPoint(t)));// returns the list
		}
		catch(Exception ex) 
		{
			return null;
		}  
    
	}
 
}