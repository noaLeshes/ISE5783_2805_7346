package geometries;
import primitives.Vector;
import primitives.Point;


public interface Geometry extends Intersectable
{
    /**
     * Calculates and returns the normal vector to the shape at a given point.
     * @param p The point on the shape's surface.
     * @return The normal vector to the shape at the given point.
     */
    public Vector getNormal(Point p);
}