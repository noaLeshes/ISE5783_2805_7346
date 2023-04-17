package primitives;

import java.util.Objects;

public class Ray
{
	private Point p0;
	private Vector dir;
	
	// ***************** Constructors ********************** //

	public Ray(Point p, Vector v)
	{
		p0 = p;
		dir = v.normalize();
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(dir, p0);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		 return (obj instanceof Ray other)
			     && this.p0.equals(other.p0)
				 && this.dir.equals(other.dir);
	}

	@Override
	public String toString() 
	{
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}
	
	
}