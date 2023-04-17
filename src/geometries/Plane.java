package geometries;
import primitives.Point;
import primitives.Vector;;

public class Plane implements Geometry
{
	
	private Point point;
	private Vector normal; 
	
	// ***************** Constructors ********************** //
   
    Plane(Point p ,Vector n)
    {
    	super();
    	point = p;
    	normal = n.normalize(); 			
   	}
    
    public Plane(Point p1, Point p2, Point p3) 
    {
		normal = null;
		point = p1;
	}
    
   // ***************** Getters/Setters ********************** // 
    
    public Point getPoint() 
    {
		return point;
	}
	public Vector getNormal() 
	{
		return normal;
	}

	// ***************** Funcs ********************** // 

	@Override
	public Vector getNormal(Point point) 
	{
		return null;
	}
    
    
    
    
	

 

}
