package geometries;

public class plane 
{
	private Point point;
	private Vector normal; 
	// ***************** Constructors ********************** //
   
	plane()
    {
    	super();
    	point=new Point(0,0,0);
    	normal=new Vector(); 			
   	}
    plane(Point p ,Vector n)
    {
    	super();
    	this.point=p;
    	this.normal=n; 			
   	}
    plane(Point p ,Vector n,Color color)
    {
        super(color);
    	this.point=p;
    	this.normal=n; 			
   	}
    
   // ***************** Getters/Setters ********************** // 
	public Vector getNormal() 
		{
        return this.normal;
    }

	   public Vector getNormal(Point point) { return null; }
 // ***************** Administration  ******************** // 
	@Override
	public String toString() {
		return "plane [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
