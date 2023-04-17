package geometries;
import primitives.Point;
import primitives.Vector;


public class Sphere extends RadialGeometry
{
	
	Point center;
	double radius;
	
	// ***************** Constructors ********************** //

	public Sphere(double radius1, Point p, double radius2) 
	{
		super(radius1);
		this.center = p;
		radius = radius2;
	}
	
	   // ***************** Getters/Setters ********************** // 

	public Point getCenter() 
	{
		return center;
	}

	public double getRadius()
	{
		return radius;
	}
	
	 // ***************** Funcs ********************** // 

	public Vector getNormal(Point point) 
	{
		return null;
	}


	
	
	

}


