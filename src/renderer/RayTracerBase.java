package renderer;
import scene.*;
import primitives.*;


/**
 * @author 	Noa leshes and Miri Ordentlich
 */

public abstract class  RayTracerBase 
{
	protected Scene scene;//The scene to be rendered
	
	 /**
     * Constructs a RayTracerBase object with the given scene.
     * @param scene1 The scene to be rendered.
     */
	public RayTracerBase(Scene scene1) 
	{
		this.scene = scene1;
	}
	
	/**
     * Traces a ray in the scene and calculates the color at the intersection point.
     * @param ray The ray to be traced.
     * @return The color at the intersection point of the ray.
     * @throws IllegalArgumentException if the ray is invalid.
     */
	public abstract Color traceRay(Ray ray) throws IllegalArgumentException;
}
