package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource
{

	//Point Light- a lamp (bulb)
	private Point position;
	private double kC = 1, kL = 0, kQ = 0;
	
	/**
	 * 
	 * @param intensity
	 * @param position
	 */
	public PointLight(Color intensity, Point position) 
	{
		super(intensity);
		this.position = position;
	}

	/**
	 * 
	 * @param kC
	 * @return PointLight
	 */
	public PointLight setkC(double kC) 
	{
		this.kC = kC;
		return this;
		
	}

	/**
	 * 
	 * @param kL
	 * @return PointLight
	 */
	public PointLight setkL(double kL) 
	{
		this.kL = kL;
		return this;

	}

	/**
	 * 
	 * @param kQ
	 * @return PointLight
	 */
	public PointLight setkQ(double kQ) 
	{
		this.kQ = kQ;
		return this;

	}

	/** 
	* Returns the intensity of the light at the specified point.
	* The intensity is calculated according to the Phong reflection model.
	* @param p The point at which the intensity is calculated.
	* @return The color intensity at the specified point.
	*/
	@Override
	public Color getIntensity(Point p)
	{
		//According to Phong model
		return getIntensity().reduce((kC + kL * p.distance(position) + kQ * p.distanceSquared(position)));
	}
	
	/**
	* Returns the direction vector from the light source to the specified point.
	* @param p The point for which the direction vector is calculated.
	* @return The normalized direction vector from the light source to the point.
	*/
	@Override
	public Vector getL(Point p)
	{
		if (p.equals(position))
			return null; 
		return p.subtract(position).normalize();
	}
 
	
	
}
