package scene;

import primitives.*;
import lighting.*;
import geometries.*;

public class Scene 
{
	public String name;
	public Color backgroundColor = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();
	
	public Scene(String name) 
	{
		this.name = name;
	}



	public Scene setBackgroundColor(Color backgroundColor) 
	{
		this.backgroundColor = backgroundColor;
		return this;
	}


	public Scene setAmbientLight(AmbientLight ambientLight) 
	{
		this.ambientLight = ambientLight;
		return this;
	}


	public Scene setGeometries(Geometries geometries) 
	{
		this.geometries = geometries;
		return this;
	}
	
	
}
