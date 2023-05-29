package lighting;
import static primitives.Util.alignZero;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight
{
	private Vector direction;

	/**
	 * 
	 * @param intensity
	 * @param position
	 * @param direction
	 */
	public SpotLight(Color intensity, Point position, Vector direction) 
	{
		super(intensity, position);
		this.direction = direction.normalize();
	}
	
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
