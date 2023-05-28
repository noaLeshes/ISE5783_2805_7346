package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/*
 * @author Miri Ordentlich and Noa Leshes
 * interface for ray intersections
 * */

public abstract class Intersectable {
	public static class GeoPoint {
		public Geometry geometry;
		public Point point;

		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
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

	/*
	 * findIntsersections is a function that returns all the intersection points
	 * with geometry
	 */
	public List<Point> findIntersections(Ray ray) 
	{
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}
	
	public List<GeoPoint> findGeoIntersections(Ray ray) 
	{
		return findGeoIntersectionsHelper(ray);
	}
	
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray) ;

}
