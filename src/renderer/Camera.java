package renderer;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;
import renderer.PixelManager.Pixel;


import java.util.MissingResourceException;
import java.util.LinkedList;
import java.util.List;

import geometries.Plane;

/**
 * @author 	Noa leshes and Miri Ordentlich
 */

public class Camera
{
	
//	{		Camera parameters
		private Point locationPoint;// The location of the camera in 3D space
		private Vector v_to, v_up, v_right;// The orientation vectors of the camera
		private double height, width, distance;// The size and distance of the view plane
		private ImageWriter imageWriter;
		private RayTracerBase rayTracerBase;
//	}
	
	
//  {       multithreading parameters
		private int numOfThreads = 1;
		private double debugPrint = 0;
		PixelManager p;
//  }		
		
		
//	{ 			depth of field parameters
		/**
		 * Flag indicating whether the depth of field effect is enabled or not.
		 */
		private boolean depthOfFieldFlag = false;

		/**
		 * The focal plane used for calculating depth of field.
		 */
		private Plane focalPlane;

		/**
		 * The distance from the camera to the focal plane.
		 */
		private double focalPlaneDis;

		/**
		 * An array of points representing the locations on the camera's aperture from which rays are shot for depth of field calculations.
		 */
		private Point[] aperturePoints;

		/**
		 * The size of the camera's aperture.
		 */
		private double apertureSize;

		/**
		 * The number of aperture points to be used for depth of field calculations.
		 */
		private int numOfPoints;
//	}	

//	{                     Anti Aliasing
		private boolean antiAliasing = false;
		private int gridSize = 3;	
//	}
		
		
//               *****************Getters and Setters**********
//	{
//		{   		 Camera Getters and Setters 	
		
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
//		}
			
		 
//		{ 			 Depth Of Field 			
			 /**
				 * Sets the depth of field flag for the camera.
				 * @param myDepthOfFieldFlag the value indicating whether depth of field is enabled
				 * @return the camera object with the updated depth of field flag
				 */
				public Camera setDepthOfFieldFlag(boolean myDepthOfFieldFlag) 
				{
				    depthOfFieldFlag = myDepthOfFieldFlag;
				    return this;
				}

				/**
				 * Sets the distance to the focal plane for the camera.
				 * @param myFocalPlaneDis the distance to the focal plane
				 * @return the camera object with the updated focal plane distance and focal plane defined
				 */
				public Camera setFocalPlaneDis(double myFocalPlaneDis)
				{
				    focalPlaneDis = myFocalPlaneDis;
				    focalPlane = new Plane(this.locationPoint.add(this.v_to.scale(focalPlaneDis)), this.v_to);
				    return this;
				}


				/**
				 * Sets the size of the camera aperture.
				 * @param myApertureSize the size of the camera aperture
				 * @return the camera object with the updated aperture size and aperture points initialized
				 */
				public Camera setApertureSize(double myApertureSize)
				{
				    apertureSize = myApertureSize;
				    if (myApertureSize != 0) 
				    {
				        initializeAperturePoint();
				    }
				    return this;
				}


				/**
				 * Sets the number of points on the aperture plane for depth of field.
				 * @param myNumOfPoints the number of points on the aperture plane
				 * @return the camera object with the updated number of points
				 */
				public Camera setNumOfPoints(int myNumOfPoints) 
				{
				    numOfPoints = myNumOfPoints;
				    return this;
				}

//		 }
				
//		{ 					 AntiAliasing 		
				/**
				 * Checks if anti-aliasing is enabled for the camera.
				 * @return {@code true} if anti-aliasing is enabled, {@code false} otherwise
				 */
				 public boolean isAntiAliasing() 
				 {
					 return antiAliasing;
				 }
				
				 /**
				  * Retrieves the grid size of the camera.
				  * @return the grid size
				  */
				 public int getGridSize() 
				 {
					 return gridSize;
				 }
				 
				 /**
				   * Sets the antialiasing flag for the camera.
				 * @param antiAliasing the value indicating whether depth of field is enabled
				 * @return the camera object with the updated antiAliasing flag
				 */
				 public Camera setAntiAliasing(boolean antiAliasing) 
				 {
					 this.antiAliasing = antiAliasing;
				     return this;
				 }
				 
				 /**
					 * Sets the size of the grid.
					 * @param gridSize the size of the camera's grid
					 * @return the camera object with the updated grid size
					 */
				 public Camera setGridSize(int gridSize) 
				 {
				        this.gridSize = gridSize;
				        return this;
				 }
//		}

//	}
				
//               ***************** Funcs **********

//	{		**************** camera funcs ****************
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
	
		/**
		 * Casts a ray through a pixel in the camera's image plane and returns the color of the intersected object.
		 * If the depth of field effect is enabled, it uses the AvBeamColor method for calculating the color.
		 *
		 * @param nX The normalized x-coordinate of the pixel in the image plane.
		 * @param nY The normalized y-coordinate of the pixel in the image plane.
		 * @param j The column index of the pixel in the image plane.
		 * @param i The row index of the pixel in the image plane.
		 * @return The color of the intersected object.
		 */
		private Color castRay(int nX, int nY, float j, float i) {
		    // Construct a ray from the camera through the pixel (nX, nY) on the image plane
		    Ray ray = constructRay(nX, nY, j, i);
	
		    // If depth of field effect is enabled, calculate the color using AvBeamColor method
		    if (depthOfFieldFlag) 
		    {
		        return AvBeamColor(ray);
		    }
	
		    // Otherwise, trace the ray and return the color of the intersected object
		    return rayTracerBase.traceRay(ray);
		}
	
		
		/**
		 * Constructs a Ray that passes through the given pixel.
		 * @param nX The resolution of the pixel in X.
		 * @param nY The resolution of the pixel in Y.
		 * @param j The horizontal index of the pixel.
		 * @param i The vertical index of the pixel.
		 * @return A new Ray that passes through the given pixel.
		 */
		public Ray constructRay(int nX, int nY, float j, float i)
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
	        Color pixelColor;
		    for (int i= 0; i< nX; i++)// Iterate over each pixel in the image
			{
				for (int j = 0; j < nY; j++)	
				{
					// Cast a ray through the current pixel
	                // and calculate the resulting color using the configured RayTracerBase object
					if(antiAliasing)
					{
						pixelColor = fragmentPixelToGrid(i,j);
					}
					if(depthOfFieldFlag)
					{
						pixelColor = castRay(nX, nY, j, i);
					}
					else 
					{
						pixelColor = castRay(nX, nY, j, i);
					}
					
					imageWriter.writePixel(j, i, pixelColor);
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
//	}
	
	 
//	{		**************** depth of field funcs ****************
		
		/**
		 * Initializes the aperture points used for depth of field effect.
		 * The method calculates the positions of aperture points based on the number of points and aperture size.
		 * It populates the {@code aperturePoints} array with the calculated points.
		 */
		private void initializeAperturePoint() 
		{
		    // Calculate the number of points in each row/column based on the square root of the total number of points
		    int pointsInRow = (int) Math.sqrt(numOfPoints);
	
		    // Create a new array to store the aperture points
		    aperturePoints = new Point[pointsInRow * pointsInRow];
	
		    // Calculate the distance between each point based on the aperture size and number of points
		    double pointsDistance = (apertureSize * 2) / pointsInRow;
	
		    // Calculate the initial point on the aperture plane
		    double s = -(apertureSize + pointsDistance / 2);
		    Point initialPoint = locationPoint.add(this.v_up.scale(s).add(this.v_right.scale(s)));
	
		    // Iterate over each row and column to calculate the position of each aperture point
		    for (int i = 0; i < pointsInRow; i++) 
		    {
		        for (int j = 0; j < pointsInRow; j++) 
		        {
		            // Calculate the position of the current aperture point
		            this.aperturePoints[i + (j * pointsInRow)] = initialPoint
		                    .add(this.v_up.scale((i + 1) * pointsDistance)
		                    .add(this.v_right.scale((j + 1) * pointsDistance)));
		        }
		    }
		}
	
		/**
		 * Calculates the average color of multiple rays passing through the aperture of the camera.
		 * The method shoots rays from the aperture points towards the focal point on the focal plane,
		 * and calculates the color of each ray using the configured RayTracerBase object.
		 * The average color is calculated by summing up the colors of all rays and dividing by the number of rays.
		 * @param ray The ray representing the main ray passing through the pixel.
		 * @return The average color of the rays passing through the camera's aperture.
		 */
		private Color AvBeamColor(Ray ray) 
		{
		    Color averageColor = Color.BLACK;
		    int numOfPoints = this.aperturePoints.length;
		    Ray apertureRay;
		    Point focalPoint = this.focalPlane.findGeoIntersections(ray).get(0).point;
		    // Iterate over each aperture point and calculate the color
		    for (Point aperturePoint : this.aperturePoints) {
		        // Create a ray from the aperture point towards the focal point
		        apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
		        // Trace the ray and get the color using the configured RayTracerBase object
		        Color apertureColor = rayTracerBase.traceRay(apertureRay);
		        // Add the color to the average color, reduced by the number of points
		        averageColor = averageColor.add(apertureColor.reduce(numOfPoints));
		    }
	
		    return averageColor;
		}

//  }
	
	
//		{		**************** anti Aliasing funcs ****************

		 /**
	     * It takes a pixel and divides it into a grid of smaller pixels, and then casts a ray through each of the smaller
	     * pixels and averages the color of the smaller pixels to get the color of the original pixel
	     * @param i the x coordinate of the pixel
	     * @param j the x coordinate of the pixel
	     * @return The color of the pixel.
	     */
		private Color fragmentPixelToGrid(int i, int j) 
		{
		    // Get the dimensions of the image
		    int nX = imageWriter.getNx();
		    int nY = imageWriter.getNy();
		    // Calculate the size of each grid cell
		    double grid = 1.0 / this.gridSize;
		    // Initialize the color of the pixel
		    Color pixelColor = Color.BLACK;
		    // Iterate over each fragment within the pixel
		    for (float fragmentI = i; fragmentI < i + 1.0f; fragmentI += grid) {
		        for (float fragmentJ = j; fragmentJ < j + 1.0f; fragmentJ += grid) {
		            // Calculate the color based on the cast rays
		            pixelColor = pixelColor.add(this.castRay(nX, nY, fragmentJ, fragmentI));
		        }
		    }
		    // Reduce the color by averaging it over the number of fragments
		    return pixelColor.reduce(this.gridSize * this.gridSize);
		}
//      {
	    
	    
	    
	    
	    
	    
	    
	    public Camera setMultiThreading(int threads)
	    {
	    	if(threads < 0)
	    	{
	    		throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
	    	}
	    	if(threads != 0)
	    	{
	    		numOfThreads = threads;
	    	}
	    	else
	    	{
	    		int cores = Runtime.getRuntime().availableProcessors();
	    		numOfThreads = cores < 2 ? 1 : cores;
			}
	    	return this;
	    }
	    
	    
	    public Camera setDebugPrint(double d)
	    {
	    	debugPrint = d;
	    	return this;
	    }
	    
	    
	    public Camera renderImageThreaded() 
	    {
 
	    int ny = imageWriter.getNy();
		int nx = imageWriter.getNx();

		PixelManager pixelManager = new PixelManager(ny, nx, debugPrint);

		List<Thread> threads = new LinkedList<>();

		if (!antiAliasing) {
			while (numOfThreads-- > 0) {
				Thread thread = new Thread(() -> {
					PixelManager.Pixel pixel;
					while ((pixel = pixelManager.nextPixel()) != null) {
						Color color = this.castRay(nx, ny, pixel.col(), pixel.row());
						imageWriter.writePixel(pixel.col(), pixel.row(), color);
						pixelManager.pixelDone();
					}
				});
				threads.add(thread);
				thread.start();
			}
		} else {
			while (numOfThreads-- > 0) {
				Thread thread = new Thread(() -> {
					PixelManager.Pixel pixel;
					while ((pixel = pixelManager.nextPixel()) != null) {
						Color color = this.fragmentPixelToGrid(pixel.row(), pixel.col());
						imageWriter.writePixel(pixel.col(), pixel.row(), color);
						pixelManager.pixelDone();
					}
				});
				threads.add(thread);
				thread.start();
			}
		}
		// Wait for all threads to complete
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this;

	}

}