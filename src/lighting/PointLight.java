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
     * Constructs a point light with the given intensity and position.
     * @param intensity The intensity of the light.
     * @param position  The position of the light source.
     */
	public PointLight(Color intensity, Point position) 
	{
		super(intensity);
		this.position = position;
	}

	/**
     * Sets the constant attenuation factor of the light.
     * @param kC The constant attenuation factor to set.
     * @return The PointLight object with the updated constant attenuation factor.
     */
	public PointLight setkC(double kC) 
	{
		this.kC = kC;
		return this;
	}

	/**
     * Sets the linear attenuation factor of the light.
     * @param kL The linear attenuation factor to set.
     * @return The PointLight object with the updated linear attenuation factor.
     */	public PointLight setkL(double kL) 
	{
		this.kL = kL;
		return this;
	}

     /**
      * Sets the quadratic attenuation factor of the light.
      * @param kQ The quadratic attenuation factor to set.
      * @return The PointLight object with the updated quadratic attenuation factor.
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
 
	/**
	 * Calculates the distance between the light source and the specified point.
	 * @param point The point for which the distance is calculated.
	 * @return The distance between the light source and the point.
	 */
	public double getDistance(Point point) 
	{
	    return position.distance(point);
	}

}
