package geometries;


import primitives.Point;
import primitives.Vector;


public abstract class RadialGeometry implements Geometry
{
	 protected double radius;
	 
	// ***************** Constructors ********************** //

	 
	 public RadialGeometry(double radius)
	 {
			this.radius = radius;
	 }

	// ***************** Getters/Setters ********************** // 

	public double getRadius() 
	{
		return radius;
	}

	// ***************** Funcs ********************** // 

	public Vector getNormal(Point p)
	{
		return null;
	}

}
