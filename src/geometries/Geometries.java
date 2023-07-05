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
	
	/**
	 * create the hierarchy and put into the right boxes
	 */
	public void setBVH() {
		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// which axis we are reffering to
		setBVH(y > x && y > z ? 1 : z > x && z > y ? 2 : 0, 3);
	}

	private void setBVH(int axis, int count) {
	    // If `cbr` is false or there are no geometries to process, return.
	    if (!cbr || count == 0)
	        return;

	    // If the number of geometries in `l` is greater than 4.
	    if (l.size() > 4) 
	    {

	        // Create three `Geometries` objects to hold the split groups of geometries, dividing the current bounding box into 3 different sub-boxes
	        var s = new Geometries(); // left geometries
	        var m = new Geometries(); // middle geometries
	        var r = new Geometries(); // right geometries

	        // Calculate the midpoint coordinates of the current bounding box (`box`).
	        double midX = (box.maxX + box.minX) / 2;
	        double midY = (box.maxY + box.minY) / 2;
	        double midZ = (box.maxZ + box.minZ) / 2;

	        // Split the geometries in `l` into `s`, `m`, and `r` based on the current splitting axis.
	        // The splitting is done by comparing the minimum and maximum coordinates of each geometry
	        // with the midpoint coordinates of the bounding box (`box`).
	        switch (axis) {
	            case 0: // Split along the X-axis
	                for (var g : l) {
	                    if (g.box.minX > midX)
	                        r.add(g); // Geometry is on the right side of the midpoint
	                    else if (g.box.maxX < midX)
	                        s.add(g); // Geometry is on the left side of the midpoint
	                    else
	                        m.add(g); // Geometry intersects the midpoint
	                }
	                break;
	            case 1: // Split along the Y-axis
	                for (var g : l) {
	                    if (g.box.minY > midY)
	                        r.add(g); // Geometry is above the midpoint
	                    else if (g.box.maxY < midY)
	                        s.add(g); // Geometry is below the midpoint
	                    else
	                        m.add(g); // Geometry intersects the midpoint
	                }
	                break;
	            case 2: // Split along the Z-axis
	                for (var g : l) {
	                    if (g.box.minZ > midZ)
	                        r.add(g); // Geometry is in front of the midpoint
	                    else if (g.box.maxZ < midZ)
	                        s.add(g); // Geometry is behind the midpoint
	                    else
	                        m.add(g); // Geometry intersects the midpoint
	                }
	                break;
	        }

	        // Calculate the sizes of the split groups.
	        int nextAxis = (axis + 1) % 3;
	        int lsize = s.l.size();
	        int msize = m.l.size();
	        int rsize = r.l.size();

	        // Clear the current list `l`.
	        l.clear();

	        // If the size of the `s` group is small or the sum of sizes of `m` and `r` is zero,
	        // add the small-sized geometries (`s`) to `l`.
	        // If the sum of sizes of `m` and `r` is also zero, recursively call `setBVH` with the next axis.
	        // This ensures further splitting along different axes.
	        if (lsize <= 2 || msize + rsize == 0) {
	            this.add(s.l);
	            if (msize + rsize == 0)
	                this.setBVH(nextAxis, count - 1);
	        } else {
	            l.add(s);
	        }

	        // Similar logic is applied for the `m` group.
	        if (msize <= 2 || lsize + rsize == 0) {
	            this.add(m.l);
	            if (lsize + rsize == 0)
	                this.setBVH(nextAxis, count - 1);
	        } else {
	            l.add(m);
	        }

	        // Similar logic is applied for the `r` group.
	        if (rsize <= 2 || lsize + msize == 0) {
	            this.add(r.l);
	            if (lsize + msize == 0)
	                this.setBVH(nextAxis, count - 1);
	        } else {
	            l.add(r);
	        }
	    }

	    // Recursively call `setBVH` on any nested `Geometries` objects within `l`.
	    for (var geo : this.l) {
	        if (geo instanceof Geometries geos)
	            geos.setBVH();
	    }
	    return;
	}

}
