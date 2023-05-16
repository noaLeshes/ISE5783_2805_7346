package renderer;
import scene.*;
import primitives.*;

public abstract class  RayTracerBase 
{
	protected Scene scene;
	
	public RayTracerBase(Scene scene1) 
	{
		this.scene = scene1;
	}
	
	public abstract Color traceRay(Ray ray) throws IllegalArgumentException;
}
