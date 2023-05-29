package primitives;

public class Material 
{
	public Double3 kD = new Double3(0), kS = new Double3(0);
	public int nShininess = 0;

	 /**
     * Sets the diffuse reflection coefficient of the material.
     * @param kD The diffuse reflection coefficient to set.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
	public Material setkD(Double3 kD) 
	{
		this.kD = kD;
		return this;
	}
	
	 /**
     * Sets the diffuse reflection coefficient of the material.
     * @param kD The diffuse reflection coefficient to set.
     * @return The Material object with the updated diffuse reflection coefficient.
     */	public Material setkD(double kD) 
	{
		this.kD = new Double3(kD);
		return this;
	}
	
     /**
      * Sets the specular reflection coefficient of the material.
      * @param kS The specular reflection coefficient to set.
      * @return The Material object with the updated specular reflection coefficient.
      */
	public Material setkS(Double3 kS) 
	{
		this.kS = kS;
		return this;
	}
	
	/**
     * Sets the specular reflection coefficient of the material.
     * @param kS The specular reflection coefficient to set.
     * @return The Material object with the updated specular reflection coefficient.
     */
	public Material setkS(double kS) 
	{
		this.kS = new Double3(kS);
		return this;
	}
	
	 /**
     * Sets the shininess value of the material.
     * @param nShininess The shininess value to set.
     * @return The Material object with the updated shininess value.
     */
	public Material setnShininess(int nShininess) 
	{
		this.nShininess = nShininess;
		return this;

	}
	
	
}
