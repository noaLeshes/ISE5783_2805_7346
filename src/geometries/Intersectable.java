package geometries;

import java.util.List;

import geometries.Intersectable.Border;
import geometries.Intersectable.GeoPoint;
import primitives.*;


/*
 * @author Miri Ordentlich and Noa Leshes
 * interface for ray intersections
 * */

public abstract class Intersectable 
{
	
	protected static boolean cbr = false;

	
	/**
	 * the box for the bvh
	 */
	protected Border box = null;

	/**
	 * class Border is a class that represents the box of the bvh
	 */
	public static class Border {
		/**
		 * this values represent the minimum point of the geometry
		 */
		protected double minX;
		protected double minY;
		protected double minZ;

		/**
		 * this values represent the maximum point of the geometry
		 */
		protected double maxX;
		protected double maxY;
		protected double maxZ;

		/**
		 * constructor of a border by values
		 * 
		 * @param x1 minimum x
		 * @param y1 minimum y
		 * @param z1 minimum z
		 * @param x2 maximum x
		 * @param y2 maximum y
		 * @param z2 maximum z
		 */
		public Border(double x1, double y1, double z1, double x2, double y2, double z2) {
			minX = x1;
			minY = y1;
			minZ = z1;
			maxX = x2;
			maxY = y2;
			maxZ = z2;
		}

		public Border() {
			minX = Double.POSITIVE_INFINITY;
			maxX = Double.NEGATIVE_INFINITY;
			minY = Double.POSITIVE_INFINITY;
			maxY = Double.NEGATIVE_INFINITY;
			minZ = Double.POSITIVE_INFINITY;
			maxZ = Double.NEGATIVE_INFINITY;
		}
		
		/**
		 * This function calculates if the given ray intersects with the border of the geometry.
		 * It performs intersection tests along the X, Y, and Z axes to determine the range of intersection.
		 *
		 * @param ray  The ray to check for intersection.
		 * @param dis  The maximum distance for valid intersections.
		 * @return True if the ray intersects with the border, false otherwise.
		 */
		protected boolean intersect(Ray ray, double dis) {
		    Point origin = ray.getP0();
		    double originX = origin.getX();
		    double originY = origin.getY();
		    double originZ = origin.getZ();
		    Vector dir = ray.getDir();
		    double dirX = dir.getX();
		    double dirY = dir.getY();
		    double dirZ = dir.getZ();

		    // Initially, set the values of tMin and tMax to negative and positive infinity, respectively,
		    // representing the range of parameter t along the ray where intersections can occur.

		    double tMin = Double.NEGATIVE_INFINITY;
		    double tMax = Double.POSITIVE_INFINITY;

		    // Calculate the intersection range along the X axis based on the direction of the ray's X component.

		    if (dirX > 0) {
		        // If the ray's X component is positive, calculate tMin and tMax by dividing the difference between
		        // the minimum X value of the border and the X value of the ray's origin by the ray's X component.
		        // This corresponds to finding the value of t where the ray intersects the plane defined by the minimum X value.
		        // The same calculation is performed for the maximum X value.
		        tMin = (minX - originX) / dirX; // Intersection of ray with minX plane: minX = dirX * tMin + originX
		        tMax = (maxX - originX) / dirX; // Intersection of ray with maxX plane: maxX = dirX * tMax + originX
		    } else if (dirX < 0) {
		        // If the ray's X component is negative, the calculation is reversed to find the intersections
		        // with the maximum X value first and then the minimum X value.
		        tMin = (maxX - originX) / dirX; // Intersection of ray with maxX plane: maxX = dirX * tMin + originX
		        tMax = (minX - originX) / dirX; // Intersection of ray with minX plane: minX = dirX * tMax + originX
		    }

		    // Calculate the intersection range along the Y axis based on the direction of the ray's Y component.

		    double tMinY = Double.NEGATIVE_INFINITY;
		    double tMaxY = Double.POSITIVE_INFINITY;
		    if (dirY > 0) {
		        // If the ray's Y component is positive, calculate tMinY and tMaxY similarly to tMin and tMax.
		        tMinY = (minY - originY) / dirY; // Intersection of ray with minY plane: minY = dirY * tMinY + originY
		        tMaxY = (maxY - originY) / dirY; // Intersection of ray with maxY plane: maxY = dirY * tMaxY + originY
		    } else if (dirY < 0) {
		        // If the ray's Y component is negative, calculate tMinY and tMaxY in reverse order.
		        tMinY = (maxY - originY) / dirY; // Intersection of ray with maxY plane: maxY = dirY * tMinY + originY
		        tMaxY = (minY - originY) / dirY; // Intersection of ray with minY plane: minY = dirY * tMaxY + originY
		    }

		    // If either the maximum Y value is smaller than the overall minimum value or the minimum Y value is bigger than the overall
		    // maximum, there is no intersection. Return false.
		    // Otherwise, update the overall tMin and tMax values based on the Y-axis intersection range.

		    if ((tMin > tMaxY) || (tMinY > tMax))
		        return false;

		    if (tMinY > tMin)
		        tMin = tMinY;
		    if (tMaxY < tMax)
		        tMax = tMaxY;

		    // Calculate the intersection range along the Z axis based on the direction of the ray's Z component.

		    double tMinZ = Double.NEGATIVE_INFINITY;
		    double tMaxZ = Double.POSITIVE_INFINITY;
		    if (dirZ > 0) {
		        // If the ray's Z component is positive, calculate tMinZ and tMaxZ similarly to tMin and tMax.
		        tMinZ = (minZ - originZ) / dirZ; // Intersection of ray with minZ plane: minZ = dirZ * tMinZ + originZ
		        tMaxZ = (maxZ - originZ) / dirZ; // Intersection of ray with maxZ plane: maxZ = dirZ * tMaxZ + originZ
		    } else if (dirZ < 0) {
		        // If the ray's Z component is negative, calculate tMinZ and tMaxZ in reverse order.
		        tMinZ = (maxZ - originZ) / dirZ; // Intersection of ray with maxZ plane: maxZ = dirZ * tMinZ + originZ
		        tMaxZ = (minZ - originZ) / dirZ; // Intersection of ray with minZ plane: minZ = dirZ * tMaxZ + originZ
		    }

		    // If either the maximum Z value is smaller than the overall minimum value or the minimum Z value is bigger than the overall
		    // maximum, there is no intersection. Otherwise, return true to indicate an intersection.

		    return tMin <= tMaxZ && tMinZ <= tMax;
		}
	}

	/**
	 * setter for Conservative Bounding Region flag
	 */
	public static void setCbr() 
	{
		Intersectable.cbr = true;
	}

	
	public static class GeoPoint 
	{
		public Geometry geometry;
		public Point point;

		public GeoPoint(Geometry geometry, Point point) 
		{
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (!(obj instanceof GeoPoint))
			{
				return false;
			}
			GeoPoint other = (GeoPoint) obj;
			return this.geometry == other.geometry && this.point.equals(other.point);
		}
		
		 @Override
		public String toString()
	    {
			 return "GeoPoint [geometry=" +geometry + ", point=" + point + "]";
	    }

	}

	 /**
     * Finds all intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of intersection points with the geometry, or null if there are no intersections.
     */
	public List<Point> findIntersections(Ray ray) 
	{
		// Find the list of GeoPoint objects representing the intersection points
		var geoList = findGeoIntersections(ray);
		// Map the GeoPoint objects to Point objects and return the list if not null
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}
	
	/**
     * Finds all GeoPoints representing the intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of GeoPoints representing the intersection points with the geometry, or null if there are no intersections.
     */
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}
	
	/**
	 * find all intersections with the ray (points and geometries) with limited
	 * distance
	 * 
	 * @param ray ray that intersect
	 * @param dis the maximum distance
	 * @return list of geopoints
	 */
	public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) 
	{
		return box != null && !box.intersect(ray, maxDistance) ? null : findGeoIntersectionsHelper(ray, maxDistance);
	}
	
	 /**
     * Helper method for finding the GeoPoints representing the intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of GeoPoints representing the intersection points with the geometry, or null if there are no intersections.
     */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) ;

}
