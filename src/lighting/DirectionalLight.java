package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource
{
	private Vector direction;

	/**
     * Constructs a directional light with the given intensity and direction.
     * @param intensity The intensity of the light.
     * @param direction The direction of the light.
     */
	public DirectionalLight(Color intensity, Vector direction) 
	{
		super(intensity);
		this.direction = direction;
	}

	 /**
     * Returns the intensity of the light at a given point.
     * Since the light is directional, the intensity is constant regardless of the point.
     * @param p The point in space.
     * @return The intensity of the light.
     */
	@Override
	public Color getIntensity(Point p)
	{
		return super.getIntensity();
	}
	
	 /**
     * Returns the direction of the light at a given point.
     * Since the light is directional, the direction is constant regardless of the point.
     * @param p The point in space.
     * @return The direction of the light.
     */
	@Override
	public Vector getL(Point p)
	{
		return direction;
	}
	
	
	/**
	 * Returns the distance between the light source and a given point.
	 * Since the light is directional, the distance is considered infinite.
	 * @param point The point in space.
	 * @return The distance between the light source and the point (always infinity).
	 */
	public double getDistance(Point point) 
	{
	    return Double.POSITIVE_INFINITY;
	}

	
}
