package renderer;

import scene.Scene;
import java.util.List;
import primitives.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import lighting.LightSource;
import static primitives.Util.alignZero;

import static primitives.Util.*;

/**
 * @author Noa leshes and Miri Ordentlich
 */

public class RayTracerBasic extends RayTracerBase 
{

	/**
	 * The maximum recursion level for calculating the color of a pixel.
	 * This value determines the maximum number of recursive reflections and refractions allowed.
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	
	/**
	 * The minimum threshold for the transparency factor when calculating colors.
	 * If the transparency factor falls below this threshold, the color is considered fully transparent.
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	/**
	 * The initial coefficient value used in the color calculation.
	 * This value represents the initial contribution of the calculated color to the final pixel color.
	 */
	private static final double INITIAL_K = 1.0;

	
	/**
	 * Constructs a RayTracerBasic object with the given scene.
	 * @param scene The scene to be rendered.
	 */
	public RayTracerBasic(Scene scene)
	{
		super(scene);
	}

	/**
     * Calculates the color at the intersection point of a ray with a geometry in the scene.
     * @param gp  The intersection point and associated geometry information.
     * @param ray The ray that intersected the geometry.
     * @return The color at the intersection point.
     */
	private Color calcColor(GeoPoint gp, Ray ray)
	{
	    // Calculate the color at the intersection point using the overloaded method
	    // Add the ambient light intensity to the overall color
	    // Return the final color at the intersection point
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)).add(scene.ambientLight.getIntensity());
	}

	/**
     * Recursive method to calculate the color at the intersection point, considering local and global effects.
     *
     * @param gp    The intersection point and associated geometry information.
     * @param ray   The ray that intersected the geometry.
     * @param level The recursion level.
     * @param k     The accumulation factor for global effects.
     * @return The color at the intersection point.
     */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) 
	{
		/*
		 * 𝑰𝑷 = 𝒌𝑨 ∙ 𝑰𝑨 + 𝑰𝑬 + (𝒌𝑫 ∙ |𝒍 ∙ 𝒏| + 𝒌𝑺 ∙ (−𝒗 ∙ 𝒓)^ 𝒏𝒔𝒉)) ∙ 𝑰L
		 */
	    // Calculate the emission color at the intersection point
		Color Ie = gp.geometry.getEmission(); 
	    // Calculate the local effects (diffuse and specular reflections) at the intersection point and combine it with the emission color
		Color color = Ie.add(calcLocalEffects(gp, ray, k));
	    // If the recursion level is 1 or below, stop the recursion and return the resulting color
	    // Else calculate the global effects (reflection and refraction) at the intersection point
	    //, add the global effects to the overall color and return the final color at the intersection point
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));// Recursive calculation of global effects

	}

	 /**
     * Calculates the local effects (diffuse and specular reflections) at the specified intersection point.
     * @param intersection The intersection point and associated geometry information.
     * @param ray          The ray that intersected the geometry.
     * @param k            The accumulation factor for global effects.
     * @return The color resulting from the local effects at the intersection point.
     */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) 
	{
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = alignZero(n.dotProduct(v));

		// If the dot product between the normal and the ray direction is zero, return black color		
		/*
		 * the ray is parallel to the surface. In this case, there is no contribution
		 * from the light sources because there is no angle between the surface normal
		 * and the light direction, resulting in no diffuse or specular reflection.
		 * diffuse= האור המתפשט לכל כיוון 
		 * specular = האור המוחזר הלאה בזווית הנגדית לזאת שממנה הגיע האור
		 */
		if (nv == 0)
		{
			return Color.BLACK;
		}

		int nShininess = intersection.geometry.getMaterial().nShininess;
		Double3 kd = intersection.geometry.getMaterial().kD;
		Double3 ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		
		// Iterate over all light sources in the scene
		for (LightSource lightSource : scene.lights) 
		{
			Vector l = lightSource.getL(intersection.point).normalize();
			double nl = alignZero(n.dotProduct(l));
			// If the dot product between the normal and the light direction is greater than
			// zero (indicating the light is on the same side as the normal), perform
			// calculations If the product nl * nv is positive, it means that the light and the ray are
			// on the same side of the surface
			if (nl * nv > 0)
			{
				Double3 ktr = transparency(lightSource, l, n, intersection);
                // If the transparency factor is above the threshold, calculate the local effects
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) 
				{
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					// Add the diffusive and specular components to the overall color
					color = color.add(calcDiffusive(kd, nl, lightIntensity),
							calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}

	/**
     * Calculates the global effects (reflection and refraction) at the specified intersection point.
     * @param gp    The intersection point and associated geometry information.
     * @param v     The view direction vector from the camera.
     * @param level The recursion level.
     * @param k     The accumulation factor for global effects.
     * @return The color resulting from the global effects at the intersection point.
     */
	private Color calcGlobalEffects(GeoPoint gp, Ray v, int level, Double3 k) 
	{
		//kkr - all of the coefficients together 
		Color color = Color.BLACK;
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
        // Calculate reflection effects
		Double3 kkr = material.kR.product(k);
		// if kkr is lower then MIN_CALC_COLOR_K - there is  almost no effect on the the color, stopping the recursion 
		if (!kkr.lowerThan(MIN_CALC_COLOR_K))// if (kkr > MIN_CALC_COLOR_K)
		{
			color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
		}
        // Calculate refraction effects
		Double3 kkt = material.kT.product(k);
		if (!kkt.lowerThan(MIN_CALC_COLOR_K))// if (kkt > MIN_CALC_COLOR_K)
		{
			color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
		}
	    // Return the accumulated color from both reflection and refraction
		return color;
	}

	/**
     * Calculates the color resulting from a global effect (reflection or refraction).
     * @param ray   The ray representing the global effect.
     * @param level The recursion level.
     * @param kx    The reflection or refraction coefficient.
     * @param kkx   The accumulation factor for the global effect.
     * @return The color resulting from the global effect.
     */
	private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) 
	{
	    // Find the closest intersection point of the ray with the scene's geometries
		GeoPoint gp = findClosestIntersection(ray);
   	    // Calculate and return the color at the intersection point of the ray
	    // If no intersection is found, return the background color of the scene
		return (gp == null ? scene.backgroundColor : calcColor(gp, ray, level - 1, kkx).scale(kx));
	}

	/**
     * Constructs a refracted ray based on the intersection point, the incoming ray, and the surface normal.
     * This method simply creates a new ray with the same direction as the incoming ray and the given intersection point and surface normal.     * @param point  The intersection point.
     * @param ray    The incoming ray.
     * @param normal The surface normal at the intersection point.
     * @return The refracted ray.
     */
	private Ray constructRefractedRay(Point point, Ray ray, Vector normal) 
	{
		Vector v = ray.getDir();
		return new Ray(point, v, normal);
	}

    /**
     * Calculates the specular reflection component of the light at the specified intersection point.
     * @param ks             The specular reflection coefficient.
     * @param l              The direction vector from the light source to the intersection point.
     * @param n              The normal vector at the intersection point.
     * @param nl             The dot product between the normal vector and the light direction vector.
     * @param v              The view direction vector from the camera.
     * @param nShininess     The shininess value of the material.
     * @param lightIntensity The intensity of the light source at the intersection point.
     * @return The color resulting from the specular reflection.
     */
	private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) 
	{
		l = l.normalize();
		// points in the direction that light would be perfectly reflected if the
		// surface had a mirror-like reflection property.
		Vector r = l.subtract(n.scale(2 * nl)).normalize();
		/*
		 * the angle between the direction from the intersection point to the camera and
		 * the direction of the perfect reflection of light.
		 */
		double d = alignZero(-v.dotProduct(r));

		/*
		 * If the dot product between the view direction and the reflection vector is
		 * less than or equal to 0, return black color, since the specular reflection
		 * would have no impact on the final color reflection direction vector r and the
		 * view direction vector v is such that they are pointing in opposite
		 */
		if (d <= 0)
			return Color.BLACK;
		// Calculate and return the specular reflection color
		return lightIntensity.scale(ks.scale(Math.pow(d, nShininess)));
	}

	 /**
     * Calculates the diffuse reflection component of the light at the specified intersection point.
     * @param kd             The diffuse reflection coefficient.
     * @param nl             The dot product between the normal vector and the light direction vector.
     * @param lightIntensity The intensity of the light source at the intersection point.
     * @return The color resulting from the diffuse reflection.
     */
	private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) 
	{
		// If the dot product between the normal and light direction is negative, take
		// its absolute value
		if (nl < 0) // The light is hitting the surface from the opposite direction of the normal
			nl = -nl;
		// Calculate and return the diffuse reflection color
		return lightIntensity.scale(kd).scale(nl);
	}

	/**
	 * Traces a ray in the scene and calculates the color at the intersection point.
	 * This basic implementation finds the closest intersection point of the ray
	 * with the scene's geometries. If an intersection is found, it returns the
	 * intensity of the scene's ambient light. If no intersection is found, it
	 * returns the background color of the scene.
	 * @param ray The ray to be traced.
	 * @return The color at the intersection point of the ray.
	 * @throws IllegalArgumentException if the ray is invalid.
	 */
	@Override
	public primitives.Color traceRay(Ray ray) throws IllegalArgumentException
	{
		// Find the closest intersection point of the ray with the scene's geometries
		GeoPoint closestPoint = findClosestIntersection(ray);
		// Calculate and return the color at the intersection point. If no intersection
		// is found, return the background color of the scene
		return closestPoint == null ? scene.backgroundColor : calcColor(closestPoint, ray);
	}

	/**
     * Constructs a reflected ray from a given point, incoming ray, and surface normal.
     * @param point  The intersection point.
     * @param ray    The incoming ray.
     * @param normal The surface normal at the intersection point.
     * @return The reflected ray.
     */
	private Ray constructReflectedRay(Point point, Ray ray, Vector normal) 
	{
		// 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ n
		
		Vector v = ray.getDir();
		double vn = alignZero(v.dotProduct(normal));
		// Check if the dot product between the incoming ray direction and the surface normal is close to zero
	    // If so, the reflection direction cannot be determined and return null
		if (isZero(vn)) 
		{
			return null;
		}
	    // Calculate the reflection direction by reflecting the incoming ray direction about the surface normal
		Vector r = v.subtract(normal.scale(2 * vn));
	    // Construct a new ray with the reflection direction, using the provided intersection point and surface norma
		Ray newRay = new Ray(point, r, normal);
		return newRay;
	}

	/**
     * Finds the closest intersection point of a ray with the scene's geometries.
     * @param ray The ray to be tested for intersection.
     * @return The closest intersection point, or null if no intersection is found.
     */
	private GeoPoint findClosestIntersection(Ray ray) 
	{
		List<GeoPoint> l = scene.geometries.findGeoIntersections(ray);
		if (l == null)
		{
			return null;
		} 
		else 
		{
			return ray.findClosestGeoPoint(l);
		}
	}

	/**
     * Calculates the transparency factor for a light source at an intersection point,
     * considering the material's transparency and the shadow effects.
     * @param light The light source.
     * @param l     The direction vector from the light source to the intersection point.
     * @param n     The normal vector at the intersection point.
     * @param geop  The intersection point and associated geometry information.
     * @return The transparency factor.
     */
	private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) 
	{
		Vector lightDirection = l.scale(-1); // from point to the light source
		Ray lightRay = new Ray(geoPoint.point, lightDirection, n); // refactored ray head move

		double lightDistance = light.getDistance(geoPoint.point);
		var intersections = scene.geometries.findGeoIntersections(lightRay);
	    // If there are no intersections between the intersection point and the light source, return full transparency (1.0)
		if (intersections == null)
		{
			return Double3.ONE;
		}// Initialize the transparency factor

		Double3 ktr = Double3.ONE;// Initialize the transparency factor
	    // Iterate over all intersections between the intersection point and the light source
		for (GeoPoint gp : intersections)
		{
			if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) 
			{
				ktr = gp.geometry.getMaterial().kT.product(ktr);
	            // If the transparency factor falls below a threshold, return full transparency (0.0)
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
				{
					return Double3.ZERO;
				}
			}
		}

		return ktr;

	}

//	/**
//	 * Checks if a point on a surface is unshaded by other objects in the scene.
//	 * 
//	 * @param gp The geometric point on the surface.
//	 * @param l The direction vector from the light source to the point.
//	 * @param n The normal vector at the point.
//	 * @param ls The light source.
//	 * @return True if the point is unshaded, false otherwise.
//	 */
//	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource ls) 
//	{
//	    // Determine the maximum distance to the light source
//	    double maxDistance = ls.getDistance(gp.point);
//	    
//	    // Calculate the direction of the light from the point to the light source
//	    Vector lightDirection = l.scale(-1); // from point to light source
//	    
//	    // Calculate a small epsilon vector in the direction of the surface normal
//	    Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
//	    
//	    // Shift the point slightly along the surface normal
//	    Point point = gp.point.add(epsVector);
//	    
//	    // Create a ray from the shifted point towards the light source
//	    Ray lightRay = new Ray(point, lightDirection);
//	    
//	    // Find all intersections between the ray and the scene's geometries
//	    List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//	    
//	    // If no intersections are found, the point is unshaded
//	    if (intersections == null) 
//	    {
//	        return true;
//	    }
//	    
//	    double dis;
//	    
//	    // Check each intersection point
//	    for (GeoPoint gPoint : intersections) 
//	    {
//	        // Calculate the distance between the intersection point and the original point
//	        dis = gPoint.point.distance(gp.point);
//	        
//	        // If the distance is less than the maximum distance to the light source, the point is shaded
//	        if (dis < maxDistance) 
//	        {
//	            return false;
//	        }
//	    }
//	    
//	    // If no shaded points are found, the point is unshaded
//	    return true;
//	}
//
//	

}
