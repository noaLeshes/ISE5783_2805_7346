package primitives;

public class Material 
{
	 /** The diffuse reflection coefficient of the material. */
    public Double3 kD = new Double3(0);
    
    /** The specular reflection coefficient of the material. */
    public Double3 kS = new Double3(0);
    
    /** The transparency coefficient of the material. */
    public Double3 kT = new Double3(0);
    
    /** The reflection coefficient of the material. */
    public Double3 kR = new Double3(0);
    
    /** The shininess value of the material. */
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
     * Sets the transparency coefficient of the material.
     * @param kT The transparency coefficient to set.
     * @return The Material object with the updated transparency coefficient.
     */
	public Material setkT(Double3 kT) 
	{
		this.kT = kT;
		return this;
	}
	
	 /**
     * Sets the transparency coefficient of the material.
     * @param kT The transparency coefficient to set.
     * @return The Material object with the updated transparency coefficient.
     */
	public Material setkT(double kT) 
	{
		this.kT = new Double3(kT);
		return this;
	}
	
    /**
     * Sets the reflection coefficient of the material.
     * @param kR The reflection coefficient to set.
     * @return The Material object with the updated reflection coefficient.
     */
	public Material setkR(Double3 kR) 
	{
		this.kR = kR;
		return this;
	}
	
    /**
     * Sets the reflection coefficient of the material.
     * @param kR The reflection coefficient to set.
     * @return The Material object with the updated reflection coefficient.
     */
	public Material setkR(double kR) 
	{
		this.kR = new Double3(kR);
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
