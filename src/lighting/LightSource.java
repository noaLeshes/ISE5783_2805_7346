package lighting;
import primitives.*;
/**
 * 
 * @author Miri Ordentlich & Noa Leshes
 *
 */
public interface LightSource 
{
	/**
	* Retrieves the intensity of the light at a given point.
	* @param p The point at which the intensity is evaluated.
	* @return The intensity of the light as a Color object.
	*/
	public Color getIntensity(Point p);
	
	/**
	 * Retrieves the direction of the light at a given point.
	 * @param p The point at which the direction is evaluated.
	 * @return The direction of the light as a Vector object.
	 */
	public Vector getL(Point p);
	
	/**
	 * Retrieves the distance between the light source and a given point.
	 * @param point The point at which the distance is evaluated.
	 * @return The distance between the light source and the point.
	 */
	double getDistance(Point point);


}
