package geometries;

import primitives.Ray;

public class Tube extends RadialGeometry 
{
	
	Ray axisRay;
	
	// ***************** Constructors ********************** //

	
	public Tube(double radius, Ray axisRay) 
	{
		super(radius);
		this.axisRay = axisRay;
	}

 // ***************** Getters/Setters ********************** // 

	public Ray getAxisRay() 
	{
		return axisRay;
	}

}