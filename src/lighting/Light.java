package lighting;

import primitives.Color;

abstract class Light 
{
	private Color intensity;
	
    /**
     * Constructs a light source with the given intensity.
     * @param intensity The intensity of the light source.
     */
	protected Light(Color intensity) 
	{
		this.intensity = intensity;
	}

	  /**
     * Returns the intensity of the light source.
     * @return The intensity of the light source.
     */
	public Color getIntensity() 
	{
		return intensity;
	}


	
	
}
