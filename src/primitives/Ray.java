
package primitives;

import java.util.List;
import java.util.Objects;

public class Ray 
{
	
	/**
	 * The origin point of the ray.
	 */
	final Point p0;
	
	/**
	 * The normalized direction vector of the ray.
	 */
	final Vector dir;
	
	/**
	 * Constructs a new ray with the given origin point and direction vector.
	 * @param p The origin point of the ray.
	 * @param v The direction vector of the ray.
	 */
	public Ray(Point p, Vector v) 
	{
		p0 = p;
		dir = v.normalize();
	}
	
    /**
	 * @return the p0 of the ray
	 */
	public Point getP0()
	{
		return p0;
	}

    /**
	 * @return the dir of the ray
	 */
	public Vector getDir() 
	{
		return dir;
	}
	
	/**
	 * Returns the hash code of this ray.
	 * @return The hash code of this ray.
	 */
	@Override
	public int hashCode() 
	{
		return Objects.hash(dir, p0);
	}

	/**
	 * Compares this ray to the specified object.
	 * @param obj The object to compare to.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) {
			return true;
		}
		return (obj instanceof Ray other)
				&& this.p0.equals(other.p0)
				&& this.dir.equals(other.dir);
	}

	/**
	 * @return A string representation of this ray.
	 */
	@Override
	public String toString()
	{
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	public Point getPoint(double t)
	{
		return p0.add(dir.scale(t));
	}

	public Point findClosestPoint(List<Point> lst)
	{
		if(lst == null)
			return null;
		
		Point closestPoint = lst.get(0);
		for(Point point: lst)
		{
			if(point.distance(p0) < closestPoint.distance(p0))
			{
				closestPoint = point;
			}
		}
		return closestPoint;
	}
 
}