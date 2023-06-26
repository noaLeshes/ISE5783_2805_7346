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

	public static long boxCheckCounter = 0;
	public static long intersectionCounter = 0;
	
	/**
	 * the box for the bvh
	 */
	protected Border box = null;

	/**
	 * class Border is a class that represents the box of the bvh
	 * 
	 * @author Efrat Wexler and Sari Zilberlicht
	 *
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
		 * this function calculate if the ray trace the border of the geometry
		 * 
		 * @param ray the crosses ray
		 * @return true for intersection, false for not intersection
		 */
		protected boolean intersect(Ray ray, double dis) {
			//++boxCheckCounter;
			
			Point origin = ray.getP0();
			double originX = origin.getX();
			double originY = origin.getY();
			double originZ = origin.getZ();
			Vector dir = ray.getDir();
			double dirX = dir.getX();
			double dirY = dir.getY();
			double dirZ = dir.getZ();

			// Initially will receive the values of tMinX and tMaxX
			double tMin = Double.NEGATIVE_INFINITY;
			double tMax = Double.POSITIVE_INFINITY;

			// the values are depend on the direction of the ray

			if (dirX > 0) {
				tMin = (minX - originX) / dirX; // b=D*t+O => y=mx+b =>dirx*tmin+originx=minx
				tMax = (maxX - originX) / dirX;
			} else if (dirX < 0) {
				tMin = (maxX - originX) / dirX;
				tMax = (minX - originX) / dirX;
			}

			double tMinY = Double.NEGATIVE_INFINITY;
			double tMaxY = Double.POSITIVE_INFINITY;
			if (dirY > 0) {
				tMinY = (minY - originY) / dirY;
				tMaxY = (maxY - originY) / dirY;
			} else if (dirY < 0) {
				tMinY = (maxY - originY) / dirY;
				tMaxY = (minY - originY) / dirY;
			}

			// If either the max value of Y is smaller than overall min value, or min value
			// of Y is bigger than the overall
			// max, we can already return false.
			// Otherwise we'll update the overall min and max values and perform the same
			// check on the Z values.
			if ((tMin > tMaxY) || (tMinY > tMax))
				return false;

			if (tMinY > tMin)
				tMin = tMinY;
			if (tMaxY < tMax)
				tMax = tMaxY;

			double tMinZ = Double.NEGATIVE_INFINITY;
			double tMaxZ = Double.POSITIVE_INFINITY;
			if (dirZ > 0) {
				tMinZ = (minZ - originZ) / dirZ;
				tMaxZ = (maxZ - originZ) / dirZ;
			} else if (dirZ < 0) {
				tMinZ = (maxZ - originZ) / dirZ;
				tMaxZ = (minZ - originZ) / dirZ;
			}

			// If either the max value of Z is smaller than overall min value, or min value
			// of Z is bigger than the overall
			// max, we can already return false. Otherwise we can return true since no other
			// coordinate checks are needed.
			return tMin <= tMaxZ && tMinZ <= tMax;
		}

	}

	public static void setCbr() {
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
