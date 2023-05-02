package geometries;
import java.util.List;
import primitives.Point;
import primitives.Ray;

/*
 * @author Miri Ordentlich and Noa Leshes
 * interface for ray intersections
 * */

public interface Intersectable 
{
	/*
	 * findIntsersections is a function that returns all the intersection points with geometry
	 * */
	public List<Point> findIntsersections(Ray ray);
}
