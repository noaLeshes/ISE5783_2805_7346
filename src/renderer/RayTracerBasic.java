package renderer;

import scene.Scene;
import primitives.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import lighting.LightSource;

import static primitives.Util.alignZero;

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
	
	/**
	* Calculates the color at the specified intersection point.
	* The color is determined by combining the ambient light intensity,
	* the emission color of the intersected geometry, and the local
	* effects (diffuse and specular reflections) at the point.
	* @param point The intersection point.
	* @param ray The ray that intersected the geometry.
	* @return The color at the intersection point.
	*/
	private Color calcColor(GeoPoint point, Ray ray)
	{
		
		return scene.ambientLight.getIntensity()
				.add(point.geometry.getEmission())
				.add(calcLocalEffects(point, ray));
		
	}
	
	/**
	* Calculates the local effects (diffuse and specular reflections) at the specified intersection point.
	* @param intersection The intersection point and associated geometry information.
	* @param ray The ray that intersected the geometry.
	* @return The color resulting from the local effects at the intersection point.
	*/
	private Color calcLocalEffects (Intersectable.GeoPoint intersection, Ray ray)
	{
		Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
       
        // If the dot product between the normal and the ray direction is zero, return black color
        /* the ray is parallel to the surface. 
         * In this case, there is no contribution from the light sources because 
         * there is no angle between the surface normal and the light direction, 
         * resulting in no diffuse or specular reflection.
         * diffuse= האור המתפשט לכל כיוון
         * specular = האור המוחזר הלאה בזווית הנגדית לזאת שממנה הגיע האור
         * */  
        if (nv == 0)
            return Color.BLACK;
       
        int nShininess = intersection.geometry.getMaterial().nShininess;
        Double3 kd = intersection.geometry.getMaterial().kD;
        Double3 ks = intersection.geometry.getMaterial().kS;
        Color color = Color.BLACK;
        // Iterate over all light sources in the scene
        for (LightSource lightSource : scene.lights) 
        {
            Vector l = lightSource.getL(intersection.point).normalize();
            double nl = alignZero(n.dotProduct(l));
            // If the dot product between the normal and the light direction is greater than zero
            // (indicating the light is on the same side as the normal), perform calculations
            //If the product nl * nv is positive, it means that the light and the ray are on 
            //the same side of the surface
            if (nl * nv > 0) 
            { 
                Color lightIntensity = lightSource.getIntensity(intersection.point);
             	// Add the diffusive and specular components to the overall color
                color = color.add(calcDiffusive(kd, nl, lightIntensity),
                        calcSpecular(ks, l, n,nl, v, nShininess, lightIntensity));
            }
        }
        return color;
	}
	
	/** 
	* Calculates the specular reflection component of the light at the specified intersection point.
	* @param ks The specular reflection coefficient.
	* @param l The direction vector from the light source to the intersection point.
	* @param n The normal vector at the intersection point.
	* @param nl The dot product between the normal vector and the light direction vector.
	* @param v The view direction vector from the camera
	* @param nShininess The shininess value of the material.
	* @param lightIntensity The intensity of the light source at the intersection point.
	* @return The color resulting from the specular reflection.
	*/
	private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v,int nShininess, Color lightIntensity) 
	{
	     l = l.normalize();
	     //points in the direction that light would be perfectly reflected if the surface had a mirror-like reflection property.
	     Vector r = l.subtract(n.scale(2*nl)).normalize();
	    /* the angle between the direction from the intersection point to
		the camera and the direction of the perfect reflection of light.*/
	     double d = alignZero(-v.dotProduct(r));
	     
	  /* If the dot product between the view direction and the reflection vector is less than or equal to 0, return black color, 
	     since the specular reflection would have no impact on the final color
	      reflection direction vector r and the view direction
	      vector v is such that they are pointing in opposite*/
	     if(d <= 0)
	    	 return Color.BLACK;
	  // Calculate and return the specular reflection color
	     return lightIntensity.scale(ks.scale(Math.pow(d,nShininess)));
	}
	
	/**
	* Calculates the diffuse reflection component of the light at the specified intersection point.
	* @param kd The diffuse reflection coefficient.
	* @param nl The dot product between the normal vector and the light direction vector.
	* @param lightIntensity The intensity of the light source at the intersection point.
	* @return The color resulting from the diffuse reflection.
	*/
	private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity)
	{
		// If the dot product between the normal and light direction is negative, take its absolute value
        if(nl < 0) //The light is hitting the surface from the opposite direction of the normal
           nl = -nl;
     // Calculate and return the diffuse reflection color
        return lightIntensity.scale(kd).scale(nl);
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
    	// Find the closest intersection point of the ray with the scene's geometries
		GeoPoint closestPoint = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
		// Calculate and return the color at the intersection point. If no intersection is found, return the background color of the scene
		return closestPoint == null ? scene.backgroundColor : calcColor(closestPoint, ray);
		
	}
}
