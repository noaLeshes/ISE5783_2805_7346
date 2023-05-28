package geometries;

import primitives.Point;
import primitives.Vector;


public abstract class RadialGeometry extends Geometry 
{

	// The radius of the radial geometry
	protected final double radius;

	/**
	 * Constructs a new radial geometry with the specified radius.
	 * @param radius - the radius of the radial geometry
	 */
	public RadialGeometry(double radius) 
	{
		this.radius = radius;
	}

	/**
	 * @return the radius of the radial geometry
	 */
	public double getRadius()
	{
		return radius;
	}

	/**
	 * Returns the normal vector to the radial geometry at the specified point.
	 * @param p - the point to get the normal vector at
	 * @return null
	 */
	public Vector getNormal(Point p)
	{
		return null;
	}
}