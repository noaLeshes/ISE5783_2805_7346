package scene;

import primitives.*;
import lighting.*;
import geometries.*;

public class Scene 
{
	
	/**
	 * @author Miri Ordentlich and Noa Leshes
	 */
	
	public String name;// The name of the scene
	public Color backgroundColor = Color.BLACK;// The background color of the scene
	public AmbientLight ambientLight = AmbientLight.NONE;// The ambient light in the scene
	public Geometries geometries = new Geometries();// The geometries that present in the scene
	
	/**
     * Constructs a Scene object with the given name
     * @param name The name of the scene
     */
	public Scene(String name) 
	{
		this.name = name;
	}

	/**
     * Sets the background color of the scene
     * @param backgroundColor The background color to set
     * @return The current Scene object
     */
	public Scene setBackgroundColor(Color backgroundColor) 
	{
		this.backgroundColor = backgroundColor;
		return this;
	}

	 /**
     * Sets the ambient light in the scene
     * @param ambientLight The ambient light to set
     * @return The current Scene object
     */
	public Scene setAmbientLight(AmbientLight ambientLight) 
	{
		this.ambientLight = ambientLight;
		return this;
	}

	/**
     * Sets the geometries present in the scene
     * @param geometries The geometries to set
     * @return The current Scene object
     */
	public Scene setGeometries(Geometries geometries) 
	{
		this.geometries = geometries;
		return this;
	}
	
	
}
