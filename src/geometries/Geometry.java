package geometries;
import primitives.Vector;
import primitives.Color;
import primitives.Material;
import primitives.Point;


public abstract class Geometry extends Intersectable
{
	protected Color emission = Color.BLACK;
	private Material material =  new Material();
    
	/**
     * Calculates and returns the normal vector to the shape at a given point.
     * @param p The point on the shape's surface.
     * @return The normal vector to the shape at the given point.
     */
    public abstract Vector getNormal(Point p);

    /**
     * Returns the material of the geometry.
     * @return The material of the geometry.
     */
	public Material getMaterial() 
	{
		return material;
	}

	 /**
     * Sets the material of the geometry.
     * @param material The material to set.
     * @return The geometry object with the updated material.
     */
	public Geometry setMaterial(Material material) 
	{
		this.material = material;
		return this;
	}

	/**
     * Returns the emission color of the geometry.
     * @return The emission color of the geometry.
     */
	public Color getEmission() 
	{
		return emission;
	}

	 /**
     * Sets the emission color of the geometry.
     * @param emission The emission color to set.
     * @return The geometry object with the updated emission color.
     */
	public Geometry setEmission(Color emission) 
	{
		this.emission = emission;
		return this;
	}
}