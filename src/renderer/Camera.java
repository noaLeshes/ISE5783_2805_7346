package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.MissingResourceException;

public class Camera
{
	private Point locationPoint;// The location of the camera in 3D space
	private Vector v_to, v_up, v_right;// The orientation vectors of the camera
	private double height, width, distance;// The size and distance of the view plane
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;
	
	/**
	 * Constructs a new Camera object with the given location and orientation vectors.
	 * @param locationPoint The location of the camera in 3D space.
	 * @param v_to The vector pointing in the direction the camera is facing.
	 * @param v_up The vector pointing upwards from the camera.
	 * @throws IllegalArgumentException If the given vectors aren't orthogonal.
	 */
	public Camera(Point locationPoint, Vector v_to,Vector v_up)
	{
		if( v_to.dotProduct(v_up) != 0)
		{
			throw new IllegalArgumentException("The vectors aren't orthogonal");
		}
		this.v_to = v_to.normalize();// normalizing the vectors
		this.v_up = v_up.normalize();
		v_right = (v_to.crossProduct(v_up)).normalize();// calculating v_right
		this.locationPoint = locationPoint;	
	}
	
	 private Color castRay(int nX,int nY,int j,int i)
	 {
		 Ray ray = constructRay(nX, nY, j, i);
		 Color color = rayTracerBase.traceRay(ray);
		 return color;
	 }
	/**
	 * Sets the size of the view plane.
	 * @param width The width of the view plane.
	 * @param height The height of the view plane.
	 * @return This Camera object.
	 */
	public Camera setVPSize(double width, double height)
	{
		this.width = width;
		this.height = height;
		return this;
	}
	
	/**
	 * Sets the distance of the view plane from the camera.
	 * @param distance The distance of the view plane from the camera.
	 * @return This Camera object.
	 */
	public Camera setVPDistance(double distance)
	{
		this.distance = distance;
		return this;
	}
	
	/**
	 * Constructs a Ray that passes through the given pixel.
	 * @param nX The resolution of the pixel in X.
	 * @param nY The resolution of the pixel in Y.
	 * @param j The horizontal index of the pixel.
	 * @param i The vertical index of the pixel.
	 * @return A new Ray that passes through the given pixel.
	 */
	public Ray constructRay(int nX, int nY, int j, int i)
	{		
		Point centerPoint;// The center point of the view plane
		if (isZero(distance))
		{
			centerPoint=locationPoint;
		}
		else
		{
			centerPoint=locationPoint.add(v_to.scale(distance));
		}		
		double rY= height/nY;// The height of a single pixel on the view plane.
		double rX=width/nX;// The width of a single pixel on the view plane.
		double Yi=(i-(nY-1)/2d)*rY;// The distance of the current pixel from the center of the view plane in the Y direction
		double Xj=(j-(nX-1)/2d)*rX;// The distance of the current pixel from the center of the view plane in the X direction
		if(isZero(Xj) && isZero(Yi))
		{
			return new Ray (locationPoint, centerPoint.subtract(locationPoint));
		}		
		Point pixelPoint = centerPoint;// The position of the current pixel on the view plane	
		if(!isZero(Xj))
		{
			pixelPoint = pixelPoint.add(v_right.scale(Xj));
		}
		if(!isZero(Yi))
		{
			pixelPoint = pixelPoint.add(v_up.scale(-Yi));
		}
		Vector pixelVector = pixelPoint.subtract(locationPoint);// The vector that represents the direction from the camera's location to the current pixel on the view plane
		if(pixelPoint.equals(locationPoint))
		{
			return new Ray(locationPoint, new Vector(pixelPoint.getX(),pixelPoint.getY(),pixelPoint.getZ()));
		}
		return new Ray(locationPoint, pixelVector);
	}
	
	public void renderImage() throws MissingResourceException
	{
       try 
       {
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
	    if (rayTracerBase == null)
	     	throw new MissingResourceException("this function must have values in all the fileds", "RayTracerBase", "rayTracerBase");
		if (locationPoint == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Point", "locationPoint");
		if (v_up == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_up");
		if (v_to == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_to");
		if (v_right == null) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_right");
		if (height == 0) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "height");
		if (width == 0) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "width");
		if (distance == 0) 
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "distance");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        
	    for (int i= 0; i< nX; i++)
		{
			for (int j = 0; j < nY; j++)	
			{
				imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
		   }
			
	     }
       }
	   catch(MissingResourceException e)
       {
	    	throw new MissingResourceException("No implemented yet",e.getClassName(),e.getKey());
       }
	}

	public void printGrid(Color color ,int interval)
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");
		
		for (int i = 0; i < imageWriter.getNx(); i++)
		{
			for (int j = 0; j < imageWriter.getNy(); j++)	
			{
				if(i % interval == 0 || j % interval == 0)
				{
				    imageWriter.writePixel(i, j, color);
				}
			}
		}
	}
	
	public void writeToImage()
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");
		imageWriter.writeToImage();
	}
	/**
	 * Getter for locationPoint
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Point value for locationPoint	 
	 */
	public Point getlocationPoint() 
	{
		return locationPoint;
	}
	/**
	 * Getter for v_up
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vUp	 
	 */
	public Vector getv_up() 
	{
		return v_up;
	}

	/**
	 * Getter for v_to
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vTo	 
	 */
	public Vector getv_to() 
	{
		return v_to;
	}
	
	/**
	 * Getter for v_right
	 * @author Noa Leshes & Miri Ordentlich
	 * @return Vector value for vRight	 
	 */
	public Vector getv_right() 
	{
		return v_right;
	}

	/**
	 * Getter for width
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for width	 
	 */
	public double getWidth() 
	{
		return width;
	}

	/**
	 * Getter for height
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for height	 
	 */
	public double getHeight() 
	{
		return height;
	}

	/**
	 * Getter for distance
	 * @author Noa Leshes & Miri Ordentlich
	 * @return double value for distance	 
	 */
	public double getDistance() 
	{
		return distance;
	}
	
	public Camera setImageWriter(ImageWriter imageWriter) 
	{
		this.imageWriter = imageWriter;
		return this;
	}

	public Camera setRayTracerBase(RayTracerBase rayTracerBase) 
	{
		this.rayTracerBase = rayTracerBase;
		return this;
	}
}
