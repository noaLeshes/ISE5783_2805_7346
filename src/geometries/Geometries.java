package geometries;
import java.util.LinkedList ;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import primitives.Point;
import primitives.Ray;


public class Geometries extends Intersectable
{
	private List<Intersectable> l;
	
	public Geometries()
	{
		l = new LinkedList <Intersectable>();
	}
	/**
	 * Constructor that recives list of geomeries and puts them in new LinkedList 
	 * @author Miri Ordentlich and Noa Leshes
	 * @param geometries
	 * */
	public Geometries(Intersectable... geometries)
	{
		l = new LinkedList <Intersectable>();
		Collections.addAll(l, geometries);
	}
	
	/**
	 * A function that adds the geometries we receive to the list.
	 * @author Miri Ordentlich and Noa Leshes
	 * @param geometries 
	 * */

	public void add(Intersectable... geometries)
	{
		if (geometries != null)
		{
			Collections.addAll(l,geometries);
		}
	}
	
	/**	  
	* @return a list of the intersection points 
	* @param ray - the ray that intersects with the object		
    */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) 
	{ 
	   List<GeoPoint> temp = null;
		for ( Intersectable intersectable : l) 
		{
			List<GeoPoint> intersection = intersectable.findGeoIntersections(ray);
			if (intersection != null)
			{
				if(temp == null)
				{
					temp = new LinkedList <>();
				}
				temp.addAll(intersection); 
			}
		}
		return temp;	
	}
}
