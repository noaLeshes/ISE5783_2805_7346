package geometries;
import java.util.LinkedList ;
import java.util.Arrays;
import java.util.List;

import primitives.Point;
import primitives.Ray;


public class Geometries implements Intersectable
{
	List<Intersectable> l;
	public Geometries()
	{
		//we chose in LinkedList  because this class works better than linked list when the application demands storing the data and accessing it.
		l = new LinkedList <Intersectable>();
	}

	/**
	 * Constructor that recives list of geomeries and put them in new LinkedList 
	 * 
	 * @author sarit silverstone and rivki adler
	 * @param geometries
	 * */

	public Geometries(Intersectable... geometries)
	{
		l =  new LinkedList <Intersectable>(Arrays.asList(geometries));
	}
	
	/**
	 * A function that add the geometries the receive to the list.
	 * 
	 * @author sarit silverstone and rivki adler
	 * @param geometries 
	 * */

	public void add(Intersectable... geometries)
	{
		if (geometries != null)
		{
			l.addAll(Arrays.asList(geometries));
		}
	}
	
	@Override
	public List<Point> findIntsersections(Ray ray) 
	{
	   List<Point> temp = new LinkedList <Point>();
		for ( Intersectable intersectable : l) 
		{
			List<Point> intersection = intersectable.findIntsersections(ray);
			if (intersection != null)
				temp.addAll(intersection); 
		}
		
		if (temp.isEmpty())
			return null;
		return temp;
		
		
	}
}
