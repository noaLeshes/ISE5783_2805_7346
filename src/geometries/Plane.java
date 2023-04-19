package geometries;
import primitives.Point;
import primitives.Vector;;


public class Plane implements Geometry {
	
	// The point on the plane
	final Point point;
	
	// The normal vector to the plane
	final Vector normal; 
	
	/**
	 * Constructs a new plane with the specified point and normal vector.
	 * The normal vector is normalized to have a length of 1.
	 * 
	 * @param p - the point on the plane
	 * @param n - the normal vector to the plane
	 */
    Plane(Point p ,Vector n) {
    	super();
    	point = p;
    	normal = n.normalize(); 			
   	}
    
    /**
     * Constructs a new plane from three non-collinear points.
     * 
     * @param p1 - the first point
     * @param p2 - the second point
     * @param p3 - the third point
     */
    public Plane(Point p1, Point p2, Point p3) {
		normal = null;
		point = p1;
	}
    
    /**
     * Returns the point on the plane.
     * 
     * @return the point on the plane
     */
    public Point getPoint() {
		return point;
	}
    
    /**
  
     * @return the normal vector to the plane
     */
	public Vector getNormal() 
	{
		return normal;
	}

	/**
	 * Returns the normal vector to the plane at the specified point.
	 * 
	 * @param point - the point to get the normal vector at
	 * @return null
	 */
	@Override
	public Vector getNormal(Point point) 
	{
		return normal;
	}   
}