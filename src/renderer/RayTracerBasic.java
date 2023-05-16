package renderer;

import scene.Scene;
import primitives.*;

public class RayTracerBasic extends RayTracerBase
{
	public RayTracerBasic(Scene scene)
	{
		super(scene);
	}
	
	@Override
	public primitives.Color traceRay(Ray ray) throws IllegalArgumentException 
	{
		Point closestPoint = ray.findClosestPoint(scene.geometries.findIntsersections(ray));
		return closestPoint == null ? scene.backgroundColor : scene.ambientLight.getIntensity();
		
	}
}
