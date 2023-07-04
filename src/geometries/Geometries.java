package geometries;
import java.util.LinkedList ;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import geometries.Intersectable.Border;
import primitives.Point;
import primitives.Ray;


public class Geometries extends Intersectable
{
//	private List<Intersectable> l 

	private final List<Intersectable> l = new LinkedList <>();
	private final List<Intersectable> i = new LinkedList<>();

	
	public Geometries()
	{
//		l = new LinkedList <Intersectable>();
	}
	/**
	 * Constructor that recives list of geomeries and puts them in new LinkedList 
	 * @author Miri Ordentlich and Noa Leshes
	 * @param geometries
	 * */
	public Geometries(Intersectable... geometries)
	{
//		l = new LinkedList <Intersectable>();
//		Collections.addAll(l, geometries);
		add(geometries);

	}
	
	public Geometries(List<Intersectable> geometries) 
	{
		add(geometries);
	}
	
	/**
	 * A function that adds the geometries we receive to the list.
	 * @author Miri Ordentlich and Noa Leshes
	 * @param geometries 
	 * */

	public void add(Intersectable... geometries)
	{
//		if (geometries != null)
//		{
//			Collections.addAll(l,geometries);
//		}
		
		add(List.of(geometries));

	}
	
	public void add(List<Intersectable> geometries) {
		if (!cbr) {
			this.l.addAll(geometries);
			return;
		}

		for (var g : geometries) {
			if (g.box == null)
				i.add(g);
			else {
				this.l.add(g);
				if (i.isEmpty()) {
					if (box == null)
						box = new Border();
					if (g.box.minX < box.minX)
						box.minX = g.box.minX;
					if (g.box.minY < box.minY)
						box.minY = g.box.minY;
					if (g.box.minZ < box.minZ)
						box.minZ = g.box.minZ;
					if (g.box.maxX > box.maxX)
						box.maxX = g.box.maxX;
					if (g.box.maxY > box.maxY)
						box.maxY = g.box.maxY;
					if (g.box.maxZ > box.maxZ)
						box.maxZ = g.box.maxZ;
				}
			}
		}
		// if there are inifinite objects
		if (!i.isEmpty())
			box = null;
	}
	
	/**	  
	* @return a list of the intersection points 
	* @param ray - the ray that intersects with the object		
    */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double dis) 
	{ 
		// Initialize a temporary list to store the intersection points
		LinkedList<GeoPoint> temp = null;
	   
		// Iterate over each intersectable geometry in the list
	   for ( Intersectable intersectable : l) 
		{
		   var lPoints = intersectable.findGeoIntersections(ray, dis);
			if (lPoints != null)
			// Find the intersection points between the ray and the current geometry
//		   List<GeoPoint> intersection = intersectable.findGeoIntersections(ray);
			// If there are intersection points, add them to the temporary list
//		   	if (intersection != null)
			{	
		   		// Initialize the temporary list if it's null
				if(temp == null)
				{
					temp  = new LinkedList<>(lPoints);

				}
				// Add all the intersection points to the temporary list
				else
				{
					temp.addAll(lPoints); 
				}
			}
		}
	   for ( Intersectable intersectable : i) 
	   {
			var lPoints = intersectable.findGeoIntersections(ray, dis);
			if (lPoints != null)
				if (temp == null)
					temp = new LinkedList<>(lPoints);
				else
					temp.addAll(lPoints);
		}
		// Return the temporary list of intersection points
		return temp;	
	}
	
	
	public void setBVH() 
	{
		if (!cbr)
			return;
		// min amount of geometries in a box is 2
		if (l.size() <= 4)
			return;

		if (box == null) {
			var finites = new Geometries(l);
			l.clear();
			l.add(finites);
			return;
		}

		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// which axis we are reffering to
		final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';
//		Collections.sort(geometries, //
//				(i1, i2) -> Double.compare(average(i1, axis), average(i2, axis)));

		var s = new Geometries();
		var m = new Geometries();
		var r = new Geometries();
		double midX = (box.maxX + box.minX) / 2;
		double midY = (box.maxY + box.minY) / 2;
		double midZ = (box.maxZ + box.minZ) / 2;
		switch (axis) {
		case 'x':
			for (var g : l) {
				if (g.box.minX > midX)
					r.add(g);
				else if (g.box.maxX < midX)
					s.add(g);
				else
					m.add(g);
			}
			break;
		case 'y':
			for (var g : l) {
				if (g.box.minY > midY)
					r.add(g);
				else if (g.box.maxY < midY)
					s.add(g);
				else
					m.add(g);
			}
			break;
		case 'z':
			for (var g : l) {
				if (g.box.minZ > midZ)
					r.add(g);
				else if (g.box.maxZ < midZ)
					s.add(g);
				else
					m.add(g);
			}
			break;
		}



		l.clear();
		if (s.l.size() <= 2)
			l.addAll(s.l);
		else {
			s.setBVH();
			l.add(s);
		}

		if (m.l.size() <= 2)
			l.addAll(m.l);
		else
			l.add(m);
		
		if (r.l.size() <= 2)
			l.addAll(r.l);
		else {
			r.setBVH();
			l.add(r);
		}
	}
}
