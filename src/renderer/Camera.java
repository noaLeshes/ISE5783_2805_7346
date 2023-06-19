package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.MissingResourceException;

import geometries.Plane;

/**
 * @author 	Noa leshes and Miri Ordentlich
 */

public class Camera
{
	private Point locationPoint;// The location of the camera in 3D space
	private Vector v_to, v_up, v_right;// The orientation vectors of the camera
	private double height, width, distance;// The size and distance of the view plane
	private ImageWriter imageWriter;
	private RayTracerBase rayTracerBase;
	
	
	//depth of field
	private boolean dofFlag = false;
	private Plane focalPlane;
	private double focalPlaneDis;
	private Point[] aperturePoints;
	private double apertureSize;
	private int numOfPoints;
	
	
	
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
		 Color color;
		 if(dofFlag)
		 {
			 color = AvBeamColor(ray);
		 }
		 else 
		 {
			 color = rayTracerBase.traceRay(ray);
		 }
		 return color;
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
	
	/**
	 * Renders the image by casting rays through each pixel and calculating the resulting colors
	 * It iterates over each pixel in the image and casts a ray through it to determine the color of that pixel
	 * The resulting color is calculated using the configured RayTracerBase object
	 * @throws MissingResourceException If any required resources (e.g., imageWriter, rayTracerBase, etc.) are missing
	 */
	public Camera renderImage() throws MissingResourceException
	{
       try 
       {
    	   
        // Check if all the required resources are available

		if (imageWriter == null)
		{
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "imageWriter");
		}
		
		if (rayTracerBase == null)
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "RayTracerBase", "rayTracerBase");
		}
		
	    if (locationPoint == null) 
	    {
	     	throw new MissingResourceException("this function must have values in all the fileds", "Point", "locationPoint");
	    }
	    
		if (v_up == null) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_up");
		}
		
		if (v_to == null) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_to");
		}
		
		if (v_right == null) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Vector", "v_right");
		}
		
		if (height == 0) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "height");
		}
		
		if (width == 0) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "width");
		}
		
		if (distance == 0) 
		{
	     	throw new MissingResourceException("this function must have values in all the fileds", "Double", "distance");
		}

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
	    for (int i= 0; i< nX; i++)// Iterate over each pixel in the image
		{
			for (int j = 0; j < nY; j++)	
			{
				// Cast a ray through the current pixel
                // and calculate the resulting color using the configured RayTracerBase object
				imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
		    }
	     }
       }
	   catch(MissingResourceException e)
       {
	    	throw new MissingResourceException("No implemented yet",e.getClassName(),e.getKey());
       }
       return this;
	}

	/**
	 * Prints a grid pattern on the image by setting specific pixels to the specified color at regular intervals
	 * The grid pattern is created by setting pixels at horizontal and vertical intervals to the specified color
	 * @param color    The color to be set for the grid pixels
	 * @param interval The interval at which the grid pixels are set
	 * @throws MissingResourceException If the ImageWriter is missing
	 */
	public void printGrid(Color color ,int interval)
	{
		if (imageWriter == null)
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");
		
		for (int i = 0; i < imageWriter.getNx(); i++)
		{
			for (int j = 0; j < imageWriter.getNy(); j++)	
			{
				if(i % interval == 0 || j % interval == 0)// Set the color for pixels at regular intervals to create a grid pattern
				{
				    imageWriter.writePixel(i, j, color);
				}
			}
		}
	}
	
	/**
	 * Writes the rendered image to the output file specified in the ImageWriter.
	 * The image is written to the file using the format specified by the ImageWriter.
	 * @throws MissingResourceException If the ImageWriter is missing.
	 */
	public void writeToImage()
	{
		if (imageWriter == null)
		{
			throw new MissingResourceException("this function must have values in all the fileds", "ImageWriter", "i");
		}
		imageWriter.writeToImage();
	}
	
	/**
	 * Getter for locationPoint
	 * @return Point value for locationPoint	 
	 */
	public Point getlocationPoint() 
	{
		return locationPoint;
	}
	/**
	 * Getter for v_up
	 * @return Vector value for vUp	 
	 */
	public Vector getv_up() 
	{
		return v_up;
	}

	/**
	 * Getter for v_to
	 * @return Vector value for vTo	 
	 */
	public Vector getv_to() 
	{
		return v_to;
	}
	
	/**
	 * Getter for v_right
	 * @return Vector value for vRight	 
	 */
	public Vector getv_right() 
	{
		return v_right;
	}

	/**
	 * Getter for width
	 * @return double value for width	 
	 */
	public double getWidth() 
	{
		return width;
	}

	/**
	 * Getter for height
	 * @return double value for height	 
	 */
	public double getHeight() 
	{
		return height;
	}

	/**
	 * Getter for distance
	 * @return double value for distance	 
	 */
	public double getDistance() 
	{
		return distance;
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
	 * Sets the ImageWriter object for writing the rendered image.
	 * @param imageWriter The ImageWriter object to be set.
	 * @return This Camera object.
	 */
	public Camera setImageWriter(ImageWriter imageWriter) 
	{
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * Sets the RayTracerBase object for tracing rays in the scene.
	 * @param rayTracerBase The RayTracerBase object to be set.
	 * @return This Camera object.
	 */
	public Camera setRayTracerBase(RayTracerBase rayTracerBase) 
	{
		this.rayTracerBase = rayTracerBase;
		return this;
	}
	
	
	private void initializeAperturePoint()
	{
		int pointsInRow = (int)Math.sqrt(numOfPoints);
		aperturePoints = new Point[pointsInRow * pointsInRow];
		double pointsDistance = (apertureSize * 2) / pointsInRow;
		double s = -(apertureSize + pointsDistance / 2);
		Point initialePoint = locationPoint.add(this.v_up.scale(s).add(this.v_right.scale(s)));
		for(int i = 0; i < pointsInRow; i++)
		{
			for(int j = 0; j < pointsInRow; j++)
			{
				this.aperturePoints[i + (j * pointsInRow)] = initialePoint
						.add(this.v_up.scale((i + 1) * pointsDistance)
						.add(this.v_right.scale((j + 1)*pointsDistance)));
			}
		}
	}
	
	private Color AvBeamColor (Ray ray)
	{
		Color avColor = Color.BLACK;
		Ray apertureRay;
		Color apertureColor;
		Point focalPoint = focalPlane.findGeoIntersections(ray).get(0).point;
		for(Point aperturePoint : aperturePoints )
		{
			apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
			apertureColor = rayTracerBase.traceRay(apertureRay);
			avColor = avColor.add(apertureColor.reduce(numOfPoints));

		}
		System.out.print(avColor.getColor());

		return avColor;
	}

	
	
	
	public boolean isDofFlag() 
	{
		return dofFlag;
	}

	public Camera setDofFlag(boolean myDofFlag) 
	{
		dofFlag = myDofFlag;
		return this;
	}

	public Plane getFocalPlane() 
	{
		return focalPlane;
	}

	public Camera setFocalPlane(Plane myFocalPlane) 
	{
		focalPlane = myFocalPlane;
		return this;
	}

	public double getFocalPlaneDis() 
	{
		return focalPlaneDis;
	}

	public Camera setFocalPlaneDis(double myFocalPlaneDis) 
	{
		focalPlaneDis = myFocalPlaneDis;
		focalPlane = new Plane(locationPoint.add(this.v_to.scale(focalPlaneDis)),v_to);
		return this;
	}

	public Point[] getAperturePoints() 
	{
		return aperturePoints;
	}

	public Camera setAperturePoints(Point[] myAperturePoints) 
	{
		aperturePoints = myAperturePoints;
		return this;
	}

	public double getApertureSize() 
	{
		return apertureSize;
	}

	public Camera setApertureSize(double myApertureSize) 
	{
		apertureSize = myApertureSize;
		if (myApertureSize != 0)
		{
			initializeAperturePoint();
		}
		return this;
	}

	public int getNumOfPoints() 
	{
		return numOfPoints;
	}

	public Camera setNumOfPoints(int myNumOfPoints) 
	{
		numOfPoints = myNumOfPoints;
		return this;
	}

}
