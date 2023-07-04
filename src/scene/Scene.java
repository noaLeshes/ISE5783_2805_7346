package scene;
import java.util.List;
import primitives.*;

import lighting.*;

import java.util.LinkedList;

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
	public List<LightSource> lights = new LinkedList<>();
	
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

	/**
	* Sets the list of light sources for the scene.
	* @param lights The list of light sources to be set for the scene.
	* @return The updated Scene object.
	*/
	public Scene setLights(List<LightSource> lights) 
	{
		this.lights = lights;
		return this;
	}
	
}
