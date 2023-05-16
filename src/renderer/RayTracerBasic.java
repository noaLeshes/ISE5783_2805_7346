package renderer;

import scene.Scene;
import primitives.*;

/**
 * @author 	Noa leshes and Miri Ordentlich
 */

public class RayTracerBasic extends RayTracerBase
{
	
	/**
     * Constructs a RayTracerBasic object with the given scene.
     * @param scene The scene to be rendered.
     */
	public RayTracerBasic(Scene scene)
	{
		super(scene);
	}
	
	private Color calcColor(Point point)
	{
		return scene.ambientLight.getIntensity();
	}
	 /**
     * Traces a ray in the scene and calculates the color at the intersection point.
     * This basic implementation finds the closest intersection point of the ray with the scene's geometries.
     * If an intersection is found, it returns the intensity of the scene's ambient light.
     * If no intersection is found, it returns the background color of the scene.
     * @param ray The ray to be traced.
     * @return The color at the intersection point of the ray.
     * @throws IllegalArgumentException if the ray is invalid.
     */
    @Override
	public primitives.Color traceRay(Ray ray) throws IllegalArgumentException 
	{
		Point closestPoint = ray.findClosestPoint(scene.geometries.findIntsersections(ray));
		return closestPoint == null ? scene.backgroundColor : calcColor(closestPoint);
		
	}
}
