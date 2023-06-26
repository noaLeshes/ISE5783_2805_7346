package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

public class Cylinder extends Tube
{

    final double height; // the height of the cylinder

    /**
     * Constructs a new Cylinder object from a given axis ray, radius, and height.
     *
     * @param axisRay The axis ray of the cylinder.
     * @param radius The radius of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) 
    {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * Returns the normal vector to the cylinder at a given point.
     * @param p0 The point on the cylinder's surface.
     * @return The normal vector to the cylinder at the given point, or null if the point is not on the cylinder's surface.
     * */

	@Override
	public Vector getNormal(Point point) 
	{

	   Point p0 = getAxisRay().getP0();
	   Vector dir = getAxisRay().getDir();
	   Point pTop = p0.add(dir.scale(height));

	   //if the point is at the top of the cylinder
	   if (point.equals(pTop) || Util.isZero(dir.dotProduct(point.subtract(pTop))))
	     {
	        return dir;
	     }

	   //if the point is at the base of the cylinder
	   if (point.equals(p0) || Util.isZero(dir.dotProduct(point.subtract(p0))))
	     {
	        return dir.scale(-1);
	     }

	   return super.getNormal(point);
	 }
	 
	/**	  
	* @return a list of the intersection points with the Cylinder 
	* @param ray - the ray that intersects with the Cylinder
	*/
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		return null;
	}
	
}

