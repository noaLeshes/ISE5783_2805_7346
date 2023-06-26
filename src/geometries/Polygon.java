package geometries;

import static primitives.Util.*;

import java.util.List;
import java.util.ArrayList;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/** Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan */
public class Polygon extends Geometry {
   /** List of polygon's vertices */
   protected final List<Point> vertices;
   /** Associated plane in which the polygon lays */
   protected final Plane       plane;
   private final int           size;

   /** Polygon constructor based on vertices list. The list must be ordered by edge
    * path. The polygon must be convex.
    * @param  vertices                 list of vertices according to their order by
    *                                  edge path
    * @throws IllegalArgumentException in any case of illegal combination of
    *                                  vertices:
    *                                  <ul>
    *                                  <li>Less than 3 vertices</li>
    *                                  <li>Consequent vertices are in the same
    *                                  point
    *                                  <li>The vertices are not in the same
    *                                  plane</li>
    *                                  <li>The order of vertices is not according
    *                                  to edge path</li>
    *                                  <li>Three consequent vertices lay in the
    *                                  same line (180&#176; angle between two
    *                                  consequent edges)
    *                                  <li>The polygon is concave (not convex)</li>
    *                                  </ul>
    */
   public Polygon(Point... vertices) {
      if (vertices.length < 3)
         throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
      this.vertices = List.of(vertices);
      size          = vertices.length;

      // Generate the plane according to the first three vertices and associate the
      // polygon with this plane.
      // The plane holds the invariant normal (orthogonal unit) vector to the polygon
      plane         = new Plane(vertices[0], vertices[1], vertices[2]);
      
      if (cbr) 
      {
			box = new Border();
			for (var v : this.vertices) {
				if (v.getX() < box.minX)
					box.minX = v.getX();
				if (v.getY() < box.minY)
					box.minY = v.getY();
				if (v.getZ() < box.minZ)
					box.minZ = v.getZ();
				if (v.getX() > box.maxX)
					box.maxX = v.getX();
				if (v.getY() > box.maxY)
					box.maxY = v.getY();
				if (v.getZ() > box.maxZ)
					box.maxZ = v.getZ();
			}
		}
      
      if (size == 3) return; // no need for more tests for a Triangle

      Vector  n        = plane.getNormal();
      // Subtracting any subsequent points will throw an IllegalArgumentException
      // because of Zero Vector if they are in the same point
      Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
      Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

      // Cross Product of any subsequent edges will throw an IllegalArgumentException
      // because of Zero Vector if they connect three vertices that lay in the same
      // line.
      // Generate the direction of the polygon according to the angle between last and
      // first edge being less than 180 deg. It is hold by the sign of its dot product
      // with
      // the normal. If all the rest consequent edges will generate the same sign -
      // the
      // polygon is convex ("kamur" in Hebrew).
      boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
      for (var i = 1; i < vertices.length; ++i) {
         // Test that the point is in the same plane as calculated originally
         if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
         // Test the consequent edges have
         edge1 = edge2;
         edge2 = vertices[i].subtract(vertices[i - 1]);
         if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
      }
   }

   @Override
   public Vector getNormal(Point point) { return plane.getNormal(); }
   
   
	/**	  
	 * @return a list of the intersection points with the polygon 
	 * @param ray - the ray that intersects with the polygon
	 */
   @Override
   protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)
	{
		List<GeoPoint> rayPoints = plane.findGeoIntersections(ray);
		if (rayPoints == null)
		{
			return null;
		}
		rayPoints.get(0).geometry = this;
		
		//check if the point in out or on the triangle:
		List<Vector> normalsList = new ArrayList<Vector>();
		Vector vI;
		Vector vIplus1; 
		for (int i = 0; i<= vertices.size()-1; i++)
		{
			vI = vertices.get(i).subtract(ray.getP0());
			vIplus1 = vertices.get(i+1).subtract(ray.getP0());
			normalsList.add((vI.crossProduct(vIplus1).normalize()));
		}
		//the last:
		vI = vertices.get(vertices.size()).subtract(ray.getP0());
		vIplus1 = vertices.get(0).subtract(ray.getP0());
		normalsList.add((vI.crossProduct(vIplus1).normalize()));
		
		//The point is inside if all 𝒗 ∙ 𝑵𝒊 have the same sign (+/-)
		int countPositive = 0;
		int countNegative = normalsList.size();
		for (Vector vector : normalsList) 
		{
			if (alignZero((ray.getDir()).dotProduct(vector)) > 0)
			{
				countPositive++;
			}
			else if (alignZero((ray.getDir()).dotProduct(vector)) <= 0)
			{
				countNegative--;
			}
		}
		if (countPositive != normalsList.size() /*all the normals are in the positive side*/ && countNegative != 0 /*all the normals are in the negative side*/)
		{
			return null; //there is no intersection point
		}
		return rayPoints;
	}
}
