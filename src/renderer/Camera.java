package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Camera
{

	private Point locationPoint;
	private Vector v_to, v_up, v_right;
	private double height, width, distance;
	
	public Camera(Point locationPoint, Vector v_to,Vector v_up)
	{
		if( v_to.dotProduct(v_up) != 0)
		{
			throw new IllegalArgumentException("The vectors aren't orthogonal");
		}
		
		this.v_to = v_to.normalize();
		this.v_up = v_up.normalize();
		v_right = (v_to.crossProduct(v_up)).normalize();
		
		this.locationPoint = locationPoint;	
	}
	
	public Camera setVPSize(double width, double height)
	{
		this.width = width;
		this.height = height;
		
		return this;
	}
	
	public Camera setVPDistance(double distance)
	{
		this.distance = distance;
		return this;
	}
	
	public Ray constructRay(int nX, int nY, int j, int i)
	{
		return null;
	}
	
	

	/**
	 * Getter for locationPoint
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Point value for locationPoint	 
	 */
	public Point getlocationPoint() 
	{
		return locationPoint;
	}

	/**
	 * Getter for vUp
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vUp	 
	 */
	public Vector getv_up() 
	{
		return v_up;
	}

	/**
	 * Getter for vTo
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vTo	 
	 */
	public Vector getv_to() 
	{
		return v_to;
	}
	
	/**
	 * Getter for vRight
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vRight	 
	 */
	public Vector getv_right() 
	{
		return v_right;
	}

	/**
	 * Getter for width
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for width	 
	 */
	public double getWidth() 
	{
		return width;
	}

	/**
	 * Getter for height
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for height	 
	 */
	public double getHeight() 
	{
		return height;
	}

	/**
	 * Getter for distance
	 * 
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for distance	 
	 */
	public double getDistance() 
	{
		return distance;
	}


}
