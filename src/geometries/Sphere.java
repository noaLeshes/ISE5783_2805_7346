package geometries;

import java.util.List;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static geometries.Intersectable.GeoPoint;


public class Sphere extends RadialGeometry 
{

	// The center point of the sphere
	final Point center;

	/**
	 * Constructs a new sphere with the specified radius and center point.
	 * @param radius1 - the radius of the sphere
	 * @param p - the center point of the sphere
	 */
	public Sphere(double radius1, Point p)
	{
		super(radius1);
		this.center = p;
		
	    // if cbr is true create the box for the sphere 
		if (cbr) {
			double minX = center.getX() - radius;
			double maxX = center.getX() + radius;
			double minY = center.getY() - radius;
			double maxY = center.getY() + radius;
			double minZ = center.getZ() - radius;
			double maxZ = center.getZ() + radius;
			this.box= new Border(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	/**	  
	 * @return the center point of the sphere
	 */
	public Point getCenter()
	{
		return center;
	}
	
	/**	  
	 * @return the radius of the sphere
	 */
	public double getRadius() 
	{
		return radius;
	}

	/**
	 * Returns the normal vector to the sphere at the specified point.
	 * @param point - the point to get the normal vector at
	 */
	@Override
	public Vector getNormal(Point point)
	{
		 Vector N = point.subtract(center);
	     return N.normalize();
	 }
	
	/**	  
	 * @return a list of the intersection points with the sphere 
	 * @param ray - the ray that intersects with the sphere
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		if (ray.getP0().equals(center)) // if the start point of the ray in the center, the point, is on the radius
		{
			return List.of(new GeoPoint(this,ray.getPoint(radius)));
		}
		Vector u = center.subtract(ray.getP0());
		double tM = alignZero(ray.getDir().dotProduct(u));
		double d = alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
		double tH = alignZero(Math.sqrt(radius*radius - d*d));
		double t1 = alignZero(tM+tH);
		double t2 = alignZero(tM-tH);
		if (d > radius)
		{
			return null; // there are no intersections
		}
		if (t1 <=0 && t2<=0)
		{
			return null;
		}
		if (t1 > 0 && t2 >0)
		{
			return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));// returns the list
		}
		
		if (t1 > 0)
		{
			return List.of(new GeoPoint(this,ray.getPoint(t1)));// returns the list
		}
		else
		{
			return List.of(new GeoPoint(this,ray.getPoint(t2)));// returns the list
		}

	}
}