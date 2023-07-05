package unittests;




import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

public class Picture 
{
	@Test
	public void PictureTest() 
	{
		Scene scene = new Scene("Picture")
				.setBackgroundColor(Color.RED).setCBR();

		 Camera camera = new Camera(new Point(0,0,1700), new Vector(0, 0, -1), new Vector(0, 1, 0))  
		         .setVPSize(150, 150).setVPDistance(1000); 
		 Material spMaterial = new Material().setkD(0.6).setkS(0.9).setnShininess(3000).setkT(0.0).setkR(0.0);
		 Material spMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial2 = new Material().setkD(0.0).setkS(0.0).setnShininess(2000).setkT(0);
		 Material spMaterial3 = new Material().setkT(0.1).setnShininess(1000);
		 Material trMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial5 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0.0).setkR(0);

		 scene.geometries.add(
				 
				//eyes
				new Sphere(7d, new Point(-18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(7d, new Point(18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(2d, new Point(-15,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),
				new Sphere(2d, new Point(21,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),

				//head
				new Sphere(50d, new Point(0, 0, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(53d, new Point(0, 0, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//body
				new Sphere(45d, new Point(0, -50, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(50d, new Point(0, -54, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//belly button
				new Sphere(2d, new Point(0, -70, -50)).setEmission(Color.BLACK).setMaterial(spMaterial1),

				//shirt
				new Triangle(new Point(-43, -60, -55), new Point(43, -60, -55), new Point(42.5, -35, -55))
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				new Triangle(new Point(-43, -60, -55), new Point(-42.5, -35, -55), new Point(42.5, -35, -55)) 
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				
				//hands
				new Sphere(10d, new Point(45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(10d, new Point(-45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(-45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//legs
				new Sphere(13d, new Point(37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(13d, new Point(-37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(-37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//ears
				new Sphere(20d, new Point(37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(20d, new Point(-37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(23d, new Point(37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(23d, new Point(-37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//nose
				new Triangle(new Point(-6,-10,-20), new Point(6,-10,-20), new Point(0,-15,-20)).setMaterial(trMaterial1),
				
				//mouth
				new Triangle(new Point(-7,-18,-20), new Point(7,-18,-20), new Point(0,-20,-20)).setMaterial(trMaterial1),

				
				//floor
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -115), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
				new Triangle(new Point(-150, -150, -115), new Point(-150, -5, -150), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
		 		
				
				//letters
				new Triangle(new Point(-95,-45,-100), new Point(-95.5,-45,-100), new Point(-95,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-45,-100), new Point(-105.5,-45,-100), new Point(-105,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-48,-95), new Point(-88,-48,-95), new Point(-105,-48.5,-95)).setMaterial(trMaterial1),

				//jar
				new Sphere(23d, new Point(-100, -75, -100)).setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-77, -75, -100), new Point(-77, -35, -100))
				.setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-123, -35, -100), new Point(-77, -35, -100)) 
				.setEmission(Color.CYAN).setMaterial(spMaterial3));
		 		
		 //honey
		 		for(int i=-78; i>= -122; i--)
		 		{
		 				scene.geometries.add(new Sphere(3d, new Point(i, -35, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				if(i%7 == 0)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -37, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -90)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -41, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -110)
		 				{
		 					scene.geometries.add(new Sphere(2d, new Point(i, -60, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 					scene.geometries.add(new Sphere(3d, new Point(i, -62.5, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 		}



	 			scene.geometries.add(new Sphere(200d, new Point(500, 500, -10000)).setEmission(Color.CYAN).setMaterial(spMaterial5));
	 			scene.geometries.add(new Sphere(12d, new Point(-30, 30, 1000)).setEmission(Color.CYAN).setMaterial(spMaterial5));
	 			
		 scene.lights.add(new SpotLight(Color.WHITE, new Point(-250, 400, 1500), new Vector(-40,-1, -2)) 
		         .setkL(0.000000004).setkQ(0.000000006));
		scene.lights.add(new DirectionalLight( new Color(150,150,50), new Vector(-50, -1, -1))); //purplish 

			scene.setBVH();
		
		 camera.setImageWriter(new ImageWriter("Picture", 500, 500)) 
         .setRayTracerBase(new RayTracerBasic(scene)) 
         .renderImage() 
         .writeToImage();
		 
		 
	}

	@Test
	public void PictureDTest() 
	{
		Scene scene = new Scene("PictureD")
				.setBackgroundColor(Color.RED)
				.setCBR();

		 Camera camera = new Camera(new Point(0,0,1700), new Vector(0, 0, -1), new Vector(0, 1, 0))  
		         .setVPSize(150, 150).setVPDistance(1000)
		         .setDepthOfFieldFlag(true).setNumOfPoints(100).setApertureSize(1).setFocalPlaneDis(1800)
		         .setAntiAliasing(false).setGridSize(4);
		 
		 Material spMaterial = new Material().setkD(0.6).setkS(0.9).setnShininess(3000).setkT(0.0).setkR(0.0);
		 Material spMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial2 = new Material().setkD(0.0).setkS(0.0).setnShininess(2000).setkT(0);
		 Material spMaterial3 = new Material().setkT(0.1).setnShininess(1000);
		 Material trMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial5 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0.0).setkR(0);

		 scene.geometries.add(
				 
				//eyes
				new Sphere(7d, new Point(-18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(7d, new Point(18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(2d, new Point(-15,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),
				new Sphere(2d, new Point(21,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),

				//head
				new Sphere(50d, new Point(0, 0, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(53d, new Point(0, 0, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//body
				new Sphere(45d, new Point(0, -50, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(50d, new Point(0, -54, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//belly button
				new Sphere(2d, new Point(0, -70, -50)).setEmission(Color.BLACK).setMaterial(spMaterial1),

				//shirt
				new Triangle(new Point(-43, -60, -55), new Point(43, -60, -55), new Point(42.5, -35, -55))
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				new Triangle(new Point(-43, -60, -55), new Point(-42.5, -35, -55), new Point(42.5, -35, -55)) 
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				
				//hands
				new Sphere(10d, new Point(45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(10d, new Point(-45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(-45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//legs
				new Sphere(13d, new Point(37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(13d, new Point(-37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(-37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//ears
				new Sphere(20d, new Point(37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(20d, new Point(-37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(23d, new Point(37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(23d, new Point(-37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//nose
				new Triangle(new Point(-6,-10,-20), new Point(6,-10,-20), new Point(0,-15,-20)).setMaterial(trMaterial1),
				
				//mouth
				new Triangle(new Point(-7,-18,-20), new Point(7,-18,-20), new Point(0,-20,-20)).setMaterial(trMaterial1),

				
				//floor
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -115), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
				new Triangle(new Point(-150, -150, -115), new Point(-150, -5, -150), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
		 		
				//letters
				new Triangle(new Point(-95,-45,-100), new Point(-95.5,-45,-100), new Point(-95,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-45,-100), new Point(-105.5,-45,-100), new Point(-105,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-48,-95), new Point(-88,-48,-95), new Point(-105,-48.5,-95)).setMaterial(trMaterial1),

				//jar
				new Sphere(23d, new Point(-100, -75, -100)).setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-77, -75, -100), new Point(-77, -35, -100))
				.setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-123, -35, -100), new Point(-77, -35, -100)) 
				.setEmission(Color.CYAN).setMaterial(spMaterial3));
		 		
		 //honey
		 		for(int i=-78; i>= -122; i--)
		 		{
		 				scene.geometries.add(new Sphere(3d, new Point(i, -35, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				if(i%7 == 0)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -37, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -90)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -41, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -110)
		 				{
		 					scene.geometries.add(new Sphere(2d, new Point(i, -60, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 					scene.geometries.add(new Sphere(3d, new Point(i, -62.5, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 		}
		 		
	 			scene.geometries.add(new Sphere(200d, new Point(500, 500, -10000)).setEmission(Color.CYAN).setMaterial(spMaterial5));
	 			scene.geometries.add(new Sphere(12d, new Point(-30, 30, 1000)).setEmission(Color.CYAN).setMaterial(spMaterial5));



		 scene.lights.add(new SpotLight(Color.WHITE, new Point(-250, 400, 1500), new Vector(-40,-1, -2)) 
		         .setkL(0.000000004).setkQ(0.000000006));
		scene.lights.add(new DirectionalLight( new Color(150,150,50), new Vector(-50, -1, -1))); //purplish 

		scene.setBVH();

		
		 camera.setImageWriter(new ImageWriter("PictureD", 500, 500)) 
         .setRayTracerBase(new RayTracerBasic(scene))
         .setMultiThreading(3)
         .setDebugPrint(0.2)
         .renderImageThreaded()
//         .renderImage()
         .writeToImage();
		 
		 
	}

	
	
	
	
	@Test
	public void PictureD1Test() 
	{
		Scene scene = new Scene("PictureD1")
				.setBackgroundColor(Color.RED).setCBR();

		 Camera camera = new Camera(new Point(0,0,1700), new Vector(0, 0, -1), new Vector(0, 1, 0))  
		         .setVPSize(150, 150).setVPDistance(1000)
		         .setDepthOfFieldFlag(false).setNumOfPoints(100).setApertureSize(1).setFocalPlaneDis(1800)
		         .setAntiAliasing(true).setGridSize(4);
		 
		 Material spMaterial = new Material().setkD(0.6).setkS(0.9).setnShininess(3000).setkT(0.0).setkR(0.0);
		 Material spMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial2 = new Material().setkD(0.0).setkS(0.0).setnShininess(2000).setkT(0);
		 Material spMaterial3 = new Material().setkT(0.1).setnShininess(1000);
		 Material trMaterial1 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0);
		 Material spMaterial5 = new Material().setkD(0.0).setkS(0.0).setnShininess(1000).setkT(0.0).setkR(0);

		 scene.geometries.add(
				 
				//eyes
				new Sphere(7d, new Point(-18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(7d, new Point(18, -5, -55)).setEmission(Color.BLACK).setMaterial(spMaterial1),
				new Sphere(2d, new Point(-15,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),
				new Sphere(2d, new Point(21,-2, -50)).setEmission(Color.WHITE).setMaterial(spMaterial2),

				//head
				new Sphere(50d, new Point(0, 0, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(53d, new Point(0, 0, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//body
				new Sphere(45d, new Point(0, -50, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(50d, new Point(0, -54, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),

				//belly button
				new Sphere(2d, new Point(0, -70, -50)).setEmission(Color.BLACK).setMaterial(spMaterial1),

				//shirt
				new Triangle(new Point(-43, -60, -55), new Point(43, -60, -55), new Point(42.5, -35, -55))
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				new Triangle(new Point(-43, -60, -55), new Point(-42.5, -35, -55), new Point(42.5, -35, -55)) 
				.setEmission(Color.BLACK).setMaterial(new Material().setkR(0.2)),
				
				//hands
				new Sphere(10d, new Point(45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(10d, new Point(-45, -47, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(11.5d, new Point(-45, -47, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//legs
				new Sphere(13d, new Point(37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(13d, new Point(-37, -80, -50)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(14.5d, new Point(-37, -80, -55)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//ears
				new Sphere(20d, new Point(37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(20d, new Point(-37, 37, -100)).setEmission(Color.ORANGE).setMaterial(spMaterial),
				new Sphere(23d, new Point(37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				new Sphere(23d, new Point(-37, 37, -150)).setEmission(Color.BLACK).setMaterial(spMaterial),
				
				//nose
				new Triangle(new Point(-6,-10,-20), new Point(6,-10,-20), new Point(0,-15,-20)).setMaterial(trMaterial1),
				
				//mouth
				new Triangle(new Point(-7,-18,-20), new Point(7,-18,-20), new Point(0,-20,-20)).setMaterial(trMaterial1),

				
				//floor
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -115), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
				new Triangle(new Point(-150, -150, -115), new Point(-150, -5, -150), new Point(150, 0, -150))  //floor
				.setEmission(new Color(200,80,79)).setMaterial(new Material().setkR(0.05).setnShininess(20000)),
		 		
				//letters
				new Triangle(new Point(-95,-45,-100), new Point(-95.5,-45,-100), new Point(-95,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-45,-100), new Point(-105.5,-45,-100), new Point(-105,-80,-100)).setMaterial(trMaterial1),
				new Triangle(new Point(-105,-48,-95), new Point(-88,-48,-95), new Point(-105,-48.5,-95)).setMaterial(trMaterial1),

				//jar
				new Sphere(23d, new Point(-100, -75, -100)).setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-77, -75, -100), new Point(-77, -35, -100))
				.setEmission(Color.CYAN).setMaterial(spMaterial3),
				new Triangle(new Point(-123, -75, -100), new Point(-123, -35, -100), new Point(-77, -35, -100)) 
				.setEmission(Color.CYAN).setMaterial(spMaterial3));
		 		
		 //honey
		 		for(int i=-78; i>= -122; i--)
		 		{
		 				scene.geometries.add(new Sphere(3d, new Point(i, -35, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				if(i%7 == 0)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -37, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -90)
		 				{
		 					scene.geometries.add(new Sphere(3d, new Point(i, -41, -100)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 				if(i == -110)
		 				{
		 					scene.geometries.add(new Sphere(2d, new Point(i, -60, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 					scene.geometries.add(new Sphere(3d, new Point(i, -62.5, -50)).setEmission(Color.YELLOW).setMaterial(spMaterial));
		 				}
		 		}

	 			scene.geometries.add(new Sphere(200d, new Point(500, 500, -10000)).setEmission(Color.CYAN).setMaterial(spMaterial5));
	 			scene.geometries.add(new Sphere(12d, new Point(-30, 30, 1000)).setEmission(Color.CYAN).setMaterial(spMaterial5));



		 scene.lights.add(new SpotLight(Color.WHITE, new Point(-250, 400, 1500), new Vector(-40,-1, -2)) 
		         .setkL(0.000000004).setkQ(0.000000006));
		scene.lights.add(new DirectionalLight( new Color(150,150,50), new Vector(-50, -1, -1))); //purplish 

		scene.setBVH();

		
		 camera.setImageWriter(new ImageWriter("PictureD1", 500, 500)) 
         .setRayTracerBase(new RayTracerBasic(scene))
         .setMultiThreading(3)
         .setDebugPrint(0.2)
         .renderImageThreaded()
//         .renderImage()
         .writeToImage();
		 
		 
	}

}
