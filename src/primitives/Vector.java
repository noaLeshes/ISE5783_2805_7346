package primitives;
 
public class Vector extends Point
{
	// ***************** Constructors ********************** //

	public Vector(double x, double y, double z)
	{
		super(x,y,z);
		if(coordinates.equals(Double3.ZERO))
		{ 
			throw new IllegalArgumentException("Vector cannot be zero vector");
		}
	}
	
	public Vector(Double3 myCoordinates)
	{
		super(myCoordinates);
		if(coordinates.equals(Double3.ZERO))
		{ 
			throw new IllegalArgumentException("Vector cannot be zero vector");
		}
	}
	
	
	   // ***************** Funcs ********************** // 

	public Vector add(Vector v)
	{
		return new Vector(v.coordinates.add(this.coordinates));
	}
	
	public Vector scale(double num)
	{
		return new Vector(coordinates.scale(num));
	}
	
	public double dotProduct(Vector v)
	{
		double d = (coordinates.d1 * v.coordinates.d1)
			      +(coordinates.d2 * v.coordinates.d2)
			      +(coordinates.d3 * v.coordinates.d3);
		return d;
	}
	
	public Vector crossProduct(Vector v)
	{
		 return new Vector(
	                this.coordinates.d2 * v.coordinates.d3 - this.coordinates.d3 * v.coordinates.d2,
	                this.coordinates.d3 * v.coordinates.d1 - this.coordinates.d1 * v.coordinates.d3,
	                this.coordinates.d1 * v.coordinates.d2 - this.coordinates.d2 * v.coordinates.d1
	        );	}
	
	public double lengthSquared()
	{
		return dotProduct(this);
	}
	
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	public Vector normalize()
	{
		return new Vector(this.coordinates.reduce(length()));
	}

	
	
	
	
	@Override
	public int hashCode() 
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		 return (obj instanceof Vector other)
				 && this.coordinates.equals(other.coordinates);

	}
	
	@Override
	public String toString() 
	{
		return "Vector [coordinates=" + coordinates + "]";
	}

}