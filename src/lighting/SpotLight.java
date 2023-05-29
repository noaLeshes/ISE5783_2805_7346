package lighting;
import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight
{
	private Vector direction;

	/**
     * Constructs a spot light with the given intensity, position, and direction.
     * @param intensity  The intensity of the light.
     * @param position   The position of the light source.
     * @param direction  The direction of the spot light.
     */
	public SpotLight(Color intensity, Point position, Vector direction) 
	{
		super(intensity, position);
		this.direction = direction.normalize();
	}
	
	 /**
     * Returns the intensity of the light at the specified point.
     * The intensity is calculated according to the Phong reflection model,
     * taking into account the beam angle of the spot light.
     * @param p The point at which the intensity is calculated.
     * @return The color intensity at the specified point.
     */
	@Override
	public Color getIntensity(Point p)
	{
		//getL(P) => direction vector from the light source to the given point

		//checks if the point is outside of the beam angle of the spotlight
		double pl = alignZero(direction.dotProduct(getL(p)));

		//p and position are the same point
		if(getL(p) == null)
			return Color.BLACK;
		
		//the point isn't in the beam angle of the light source
		if (pl <= 0)
			return Color.BLACK;
		
		//According to Fong model
		return super.getIntensity(p).scale(pl) ;
		
	}
 

}
