package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Tube extends RadialGeometry 
{

	// The axis ray of the tube
	Ray axisRay;

	/**
	 * Constructs a new tube with the specified radius and axis ray.
	 * 
	 * @param radius - the radius of the tube
	 * @param axisRay - the axis ray of the tube
	 */
	public Tube(double radius, Ray axisRay) 
	{
		super(radius);
		this.axisRay = axisRay;
	}

	/**
	
	 * @return the axis ray of the tube
	 */
	public Ray getAxisRay() 
	{
		return axisRay;
	}

	/**
	 * Returns the normal vector to the tube at the specified point.
	 * @param p0 - the point to get the normal vector at
	 * @return null
	 */
	public Vector getNormal(Point p0)
	{
		return null;
	}
}