package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Tube extends RadialGeometry 
{

	// The axis ray of the tube
	final Ray axisRay;

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
	public Vector getNormal(Point point)
	{
		if(point.equals(axisRay.getP0()))
		{
			return axisRay.getDir();
		}
		
		Point p = axisRay.getP0();
		double d = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
		
		if(d != 0)
		{
			p = p.add(axisRay.getDir().scale(d));
		}
		
		return point.subtract(p).normalize();
	}
	
	   /**	  
	   * @return a list of the intersection points with the Cylinder 
   	   * @param ray - the ray that intersects with the Cylinder
  	   */
	    @Override
	    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
		{
		   return null;
		}
	}
