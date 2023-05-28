package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;



public class Triangle extends Polygon 
{

	/**
	 * Constructs a new triangle with the specified vertices.
	 * @param p1 - the first vertex of the triangle
	 * @param p2 - the second vertex of the triangle
	 * @param p3 - the third vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) 
	{
		super(p1, p2, p3);
	}
	
	/**	  
	 * @return a list of the intersection points with the triangle 
	 * @param ray - the ray that intersects with the triangle
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
	{
		
		List<GeoPoint> rayPoints = plane.findGeoIntersections(ray);
		if (rayPoints == null)
		{
			return null;
		}
		rayPoints.get(0).geometry = this;
		
	
		//check if the points are in, out or on the triangle:
		Vector v1 = vertices.get(0).subtract(ray.getP0());
		Vector v2 = vertices.get(1).subtract(ray.getP0());
		Vector v3 = vertices.get(2).subtract(ray.getP0());
		
		Vector n1 = v1.crossProduct(v2).normalize();
		Vector n2 = v2.crossProduct(v3).normalize();
		Vector n3 = v3.crossProduct(v1).normalize();
		
		//The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
		if (alignZero(n1.dotProduct(ray.getDir())) > 0 && alignZero(n2.dotProduct(ray.getDir())) > 0 && alignZero(n3.dotProduct(ray.getDir())) > 0)
		{
			return rayPoints;
		}
		else if (alignZero(n1.dotProduct(ray.getDir())) < 0 && alignZero(n2.dotProduct(ray.getDir())) < 0 && alignZero(n3.dotProduct(ray.getDir())) < 0)
		{
			return rayPoints;
		}
		if (isZero(n1.dotProduct(ray.getDir())) || isZero(n2.dotProduct(ray.getDir())) || isZero(n3.dotProduct(ray.getDir())))
		{
			return null; //there is no intersections point
		}
		return null;
	}
	
}

	