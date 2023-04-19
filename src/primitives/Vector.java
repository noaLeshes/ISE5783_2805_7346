package primitives;
 
public class Vector extends Point
{
	/**
	 * Constructs a vector with the specified x, y, and z coordinates.
	 * @param x The x coordinate of the vector
	 * @param y The y coordinate of the vector
	 * @param z The z coordinate of the vector
	 * @throws IllegalArgumentException if the coordinates are all 0 (creating a zero vector)
	 */
	
	public Vector(double x, double y, double z)
	{
		super(x,y,z);
		if(coordinates.equals(Double3.ZERO))
		{ 
			throw new IllegalArgumentException("Vector cannot be zero vector");
		}
	}
	
	/**
	 * Constructs a vector with the specified coordinates in a Double3 object.
	 * @param myCoordinates A Double3 object containing the x, y, and z coordinates of the vector
	 * @throws IllegalArgumentException if the coordinates are all 0 (creating a zero vector)
	 */
	
	public Vector(Double3 myCoordinates)
	{
		super(myCoordinates);
		if(coordinates.equals(Double3.ZERO))
		{ 
			throw new IllegalArgumentException("Vector cannot be zero vector");
		}
	}
	
	
	/**
	 * Adds another vector to this vector.
	 * @param v The vector to add to this vector
	 * @return A new Vector object representing the sum of this vector and the given vector
	 */
	
	public Vector add(Vector v)
	{
		return new Vector(v.coordinates.add(this.coordinates));
	}
	
	/**
	 * Scales this vector by a given factor.
	 * @param num The factor to scale this vector by
	 * @return A new Vector object representing the scaled vector
	 */
	
	public Vector scale(double num)
	{
		return new Vector(coordinates.scale(num));
	}
	

	/**
	 * Calculates the dot product of this vector with another vector.
	 * @param v The vector to calculate the dot product with
	 * @return The dot product of this vector with the given vector
	 */
	
	public double dotProduct(Vector v)
	{
		return  (v.coordinates.d1 * coordinates.d1) + (v.coordinates.d2 * coordinates.d2) +(v.coordinates.d3 * coordinates.d3 );
		
	}
	
	/**
	 * Calculates the cross product of this vector with another vector.
	 * @param v The vector to calculate the cross product with
	 * @return A new Vector object representing the cross product of this vector with the given vector
	 */
	
	public Vector crossProduct(Vector v)
	{
		 return new Vector(
	                this.coordinates.d2 * v.coordinates.d3 - this.coordinates.d3 * v.coordinates.d2,
	                this.coordinates.d3 * v.coordinates.d1 - this.coordinates.d1 * v.coordinates.d3,
	                this.coordinates.d1 * v.coordinates.d2 - this.coordinates.d2 * v.coordinates.d1);
	}
	
	/**
	 * Calculates the squared length of this vector.
	 * @return The squared length of this vector
	 */
	
	public double lengthSquared()
	{
		return dotProduct(this);
	}
	
	/**
	 * Calculates the length of this vector.
	 * @return The length of this vector
	 */
	
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * Returns a new vector that is the normalized version of this vector.
	 * @return A new Vector object representing the normalized vector
	 */
	
	public Vector normalize()
	{
		return new Vector(this.coordinates.reduce(length()));
	}


	/**
	 * @return The hash code of this vector.
	 */
	@Override	public int hashCode() 
	{
		return super.hashCode();
	}

	/**
	 * Compares this vector to the specified object.
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		 return (obj instanceof Vector other)
				 && this.coordinates.equals(other.coordinates);

	}
	/**
	 * @return A string representation of this vector.
	 */
	@Override
	public String toString() 
	{
		return "Vector [coordinates=" + coordinates + "]";
	}

}