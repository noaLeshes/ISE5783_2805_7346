package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/*
 * @author Miri Ordentlich and Noa Leshes
 * interface for ray intersections
 * */

public abstract class Intersectable 
{
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
		return findGeoIntersectionsHelper(ray);
	}
	
	 /**
     * Helper method for finding the GeoPoints representing the intersection points between the geometry and a given ray.
     * @param ray The ray to intersect with the geometry.
     * @return A list of GeoPoints representing the intersection points with the geometry, or null if there are no intersections.
     */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray) ;

}