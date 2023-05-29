package primitives;

public class Material 
{
	public Double3 kD = new Double3(0), kS = new Double3(0);
	public int nShininess = 0;

	/**
	 * 
	 * @param kD
	 * @return Material
	 */
	public Material setkD(Double3 kD) 
	{
		this.kD = kD;
		return this;
	}
	
	/**
	 * 
	 * @param kD
	 * @return Material
	 */
	public Material setkD(double kD) 
	{
		this.kD = new Double3(kD);
		return this;
	}
	
	/**
	 * 
	 * @param kS
	 * @return Material
	 */
	public Material setkS(Double3 kS) 
	{
		this.kS = kS;
		return this;

	}
	/**
	 * 
	 * @param kS
	 * @return Material
	 */
	public Material setkS(double kS) 
	{
		this.kS = new Double3(kS);
		return this;

	}
	/**
	 * 
	 * @param nShininess
	 * @return Material
	 */
	public Material setnShininess(int nShininess) 
	{
		this.nShininess = nShininess;
		return this;

	}
	
	
}
