//package geometries;
//import primitives.Vector;
//import primitives.Point;

//public interface Geometry 
//{
//	public abstract  Vector getNormal (Point3D point);
//}
package geometries;
import primitives.Vector;
import primitives.Point;

public interface Geometry 
{
	// ***************** Funcs ********************** // 

	public Vector getNormal(Point p);
}