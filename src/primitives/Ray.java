
package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

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
	 * A constant for the size of moving first rays for shading rays
	 * */
	private static final double DELTA = 0.1; //Rayhead offset size for shading rays	
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
	 * Constructs a new ray with the given origin point, direction vector, and surface normal.
	* The ray is offset from the origin point along the surface normal direction by a small delta value.
	* @param p0 The origin point of the ray.
	* @param direction The direction vector of the ray.
	* @param normal The surface normal vector.
	*/
	public Ray(Point p0, Vector direction, Vector normal)
	{
		// Calculate the offset vector based on the surface normal and the dot product of the direction and normal vectors.
		Vector delta = normal.scale(normal.dotProduct(direction) > 0 ? DELTA : - DELTA);
		// Add the offset vector to the origin point to create a new starting point for the ray.
		this.p0 = p0.add(delta);

		// Set the direction vector of the ray.
		this.dir = direction;
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

	public Point findClosestPoint(List<Point> points) 
	{
		 return points == null || points.isEmpty() ? null : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}
	
	public GeoPoint findClosestGeoPoint(List<GeoPoint> lst)
	{
		if(lst == null)
		{
			return null;
		}
		
		GeoPoint closestPoint = lst.get(0);
		for(GeoPoint geoPoint: lst)
		{
			if(geoPoint.point.distance(p0) < closestPoint.point.distance(p0))
			{
				closestPoint = geoPoint;
			}
		}
		return closestPoint;
	}
	

 
}