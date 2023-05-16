package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight 
{
	
	/**
	 * @author Miri Ordentlich and Noa Leshes
	 */
	private Color intensity;// The intensity of the ambient light source
    public static AmbientLight NONE=new AmbientLight(Color.BLACK,Double3.ZERO);//A constant representing no ambient light, This ambient light is completely black and has no effect on the scene.

    /**
     * Constructs an AmbientLight object with the given intensity and scaling factor
     * @param iA The intensity color of the ambient light
     * @param kA The scaling factor for the ambient light intensity
     */
	public AmbientLight(Color iA, Double3 kA)
	{
		intensity = iA.scale(kA);
	}
	
	 /**
     * Constructs an AmbientLight object with the given intensity and scaling factor
     * @param iA The intensity color of the ambient light
     * @param kA The scaling factor for the ambient light intensity
     */
	public AmbientLight(Color iA, double kA)
	{
		intensity = iA.scale(kA);
	}
	
	 /**
     * @return The intensity color of the ambient light
     */
	public Color getIntensity() 
	{
		return intensity;
	}
	

}
