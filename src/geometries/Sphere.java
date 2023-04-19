package geometries;

import primitives.Point;
import primitives.Vector;


public class Sphere extends RadialGeometry 
{

	// The center point of the sphere
	final Point center;

	/**
	 * Constructs a new sphere with the specified radius and center point.
	 * 
	 * @param radius1 - the radius of the sphere
	 * @param p - the center point of the sphere
	 */
	public Sphere(double radius1, Point p)
	{
		super(radius1);
		this.center = p;
	}

	/**	  
	 * @return the center point of the sphere
	 */
	public Point getCenter()
	{
		return center;
	}

	/**
	 * Returns the normal vector to the sphere at the specified point.
	 * 
	 * @param point - the point to get the normal vector at
	 * @return null
	 */
	public Vector getNormal(Point point)
	{
		/* Vector N = point.subtract(center);
	     return N.normalize();*/
		return null;
	 }
}