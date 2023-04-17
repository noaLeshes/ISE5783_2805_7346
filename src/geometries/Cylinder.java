package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube
{

    double height;

    /**
     * construct a cylinder from a tube and a height
     *
     */
    
	// ***************** Constructors ********************** //
    public Cylinder(Ray axisRay, double radius, double height) 
    {
        super(radius, axisRay);
        this.height = height;
    }

    // ***************** Getters/Setters ********************** // 
    public Vector getNormal(Point p0)
    {
        return null;
    }
}