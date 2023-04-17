package primitives;

import java.util.Objects;
public class Point
{
	public Double3 coordinates;
	
	// ***************** Constructors ********************** //
	
	public Point(double x, double y, double z) 
	{
		coordinates= new Double3(x,y,z);
	}
	
	Point(Double3 d)
	{
		coordinates = d;
	}
	
	// ***************** Funcs ********************** // 

	public Vector subtract(Point p)
	{
		return new Vector(coordinates.subtract(p.coordinates));
	}
	
	public Point add(Vector v)
	{
		return new Point(coordinates.add(v.coordinates));
	}
	
	public double distanceSquared(Point p)
	{
		double d = (coordinates.d1 - p.coordinates.d1)*(coordinates.d1 - p.coordinates.d1)
				  +(coordinates.d2 - p.coordinates.d2)*(coordinates.d2 - p.coordinates.d2)
				  +(coordinates.d3 - p.coordinates.d3)*(coordinates.d3 - p.coordinates.d3);
		return d;
	}
	
	public double distance(Point p)
	{
		return Math.sqrt(distanceSquared(p));
	}

	
	@Override
	public int hashCode() 
	{
		return Objects.hash(coordinates);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		return (obj instanceof Point other)
				&& this.coordinates.equals(other.coordinates);
	}

	@Override
	public String toString() 
	{
		return "Point [coordinates=" + coordinates + "]";
	}
		
}