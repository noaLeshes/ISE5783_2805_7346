/**
 * 
 */
package unittests.primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;
import primitives.Util;

/**
 * @author USER
 *
 */
class VectorTests 
{

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector()
	{
        //============ Equivalence Partitions Tests ==============//
        //TC: Test that vector addition is proper. We should get a new vector from the two vectors.
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(1, 2, 4);
		Vector v3 = new Vector(2, 4, 7);
		assertTrue((v1.add(v2).equals(v3)), "ERROR: Vector + Vector does not work correctly");
	     //============ Boundary Partitions Tests ==============//
        //TC: Test that addition of a vector by its opposite throws an error
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1.scale(-1)),"Add fails to throw an exception for opposite vectors");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() 
	{
        //============ Equivalence Partitions Tests ==============//
        //TC: Test that vector scalar multiplication is proper. We should get a new vector multiplied by the double parameter.
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(2, 4, 6);
		assertTrue((v1.scale(2).equals(v2)), "ERROR: Vector * scalar does not work correctly");
		//============ Boundary Tests ==============//
        //TC: Test that checks that scalar multiplication by zero throws an error
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),"Scale fails to throw an exception for multiplying by 0");

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() 
	{
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		// =============== Boundary Values Tests ==================
		// TC01: Scalar product examination of orthogonal vectors is zero
		assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
		// ============ Equivalence Partitions Tests ==============
		// TC01: Normal vector scalar multiplication test
		assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() 
	{
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);
		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
		// TC02: Test cross-product result orthogonality to its operands
		assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared()
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: Checking the correctness of the vector length Squared
		Vector v1 = new Vector(1, 2, 3);
		assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() 
	{
		// ============ Equivalence Partitions Tests ==============
		// TC01: Checking the correctness of the vector length
		assertTrue(isZero(new Vector(0, 3, 4).length() - 5), "ERROR: length() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize()
	{
		Vector v = new Vector(0, 3, 4);
		Vector n = v.normalize();
		// ============ Equivalence Partitions Tests ==============
		// TC01: Simple test
		assertEquals(1d, n.lengthSquared(), 0.00001, "ERROR: the normalized vector is not a unit vector");
		assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),
				"ERROR: the normalized vector is not parallel to the original one");
		assertEquals(new Vector(0, 0.6, 0.8), n, "ERROR: Wrong normalized vector");
	}

}
