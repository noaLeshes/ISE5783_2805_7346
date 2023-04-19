package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
     *
     * @param p0 The point on the cylinder's surface.
     * @return null.
     */
    public Vector getNormal(Point p0)
    {
        return null;
    }
}