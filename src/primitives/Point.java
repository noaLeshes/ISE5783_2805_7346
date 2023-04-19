
package primitives;

import java.util.Objects;

public class Point 
{
	
	/**
	 * The coordinates of the point.
	 */
	final Double3 coordinates;
	
	/**
	 * Constructs a new point with the given coordinates.
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @param z The z-coordinate of the point.
	 */
	public Point(double x, double y, double z) 
	{
		coordinates= new Double3(x,y,z);
	}
	
	/**
	 * Constructs a new point with the given Double3 object.
	 * @param d The Double3 object that represents the coordinates of the point.
	 */
	Point(Double3 d)
	{
		coordinates = d;
	}
	
	/**
	 * Calculates the vector from this point to another point.
	 * @param p The other point.
	 * @return The vector from this point to the other point.
	 */
	public Vector subtract(Point p) 
	{
		return new Vector(coordinates.subtract(p.coordinates));
	}
	
	/**
	 * Adds a vector to this point.
	 * @param v The vector to add.
	 * @return The new point after adding the vector.
	 */
	public Point add(Vector v) 
	{
		return new Point(coordinates.add(v.coordinates));
	}
	
	/**
	 * Calculates the square of the distance between this point and another point.
	 * @param p The other point.
	 * @return The square of the distance between this point and the other point.
	 */
	public double distanceSquared(Point p)
	{
		double d = (coordinates.d1 - p.coordinates.d1)*(coordinates.d1 - p.coordinates.d1)
				  +(coordinates.d2 - p.coordinates.d2)*(coordinates.d2 - p.coordinates.d2)
				  +(coordinates.d3 - p.coordinates.d3)*(coordinates.d3 - p.coordinates.d3);
		return d;
	}
	
	/**
	 * Calculates the distance between this point and another point.
	 * @param p The other point.
	 * @return The distance between this point and the other point.
	 */
	public double distance(Point p) 
	{
		return Math.sqrt(distanceSquared(p));
	}

	/**
	 * @return The hash code of this point.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(coordinates);
	}

	/**
	 * Compares this point to the specified object.
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		return (obj instanceof Point other)
				&& this.coordinates.equals(other.coordinates);
	}

	/**
	 * @return A string representation of this point.
	 */
	@Override
	public String toString()
	{
		return "Point [coordinates=" + coordinates + "]";
	}
}