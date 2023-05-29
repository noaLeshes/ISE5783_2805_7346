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

	public Material getMaterial() 
	{
		return material;
	}

	public Geometry setMaterial(Material material) 
	{
		this.material = material;
		return this;
	}

	
	public Color getEmission() 
	{
		return emission;
	}

	public Geometry setEmission(Color emission) 
	{
		this.emission = emission;
		return this;
	}
}