package backend.academy.transformations.affine;

import backend.academy.screen.Point;
import java.awt.Color;

/**
 * An affine function model containing coefficients and color
 * @param a the first coefficient of the affine function
 * @param b the second coefficient of the affine function
 * @param c the third coefficient of the affine function
 * @param d the fourth coefficient of the affine function
 * @param e the fifth coefficient of the affine function
 * @param f the sixth coefficient of the affine function
 * @param color the color of the affine function
 */
public record AffineFunction(
    double a, double b, double c, double d, double e, double f, Color color
) {
    /**
     * Maximum color value (not including)
     */
    public static final int COLOR_LIMIT = 256;
    /**
     * Minimum value for a,b,d,e coefficients
     */
    public static final int MIN_COEFF = -1;
    /**
     * Maximum value for a,b,d,e coefficients
     */
    public static final int MAX_COEFF = 1;
    /**
     * Minimum value for c, f coefficients
     */
    public static final int MIN_COEFF_TRANSLATION = -2;
    /**
     * Maximum value for c, f coefficients
     */
    public static final int MAX_COEFF_TRANSLATION = 2;

    /**
     * Applies an affine transformation to a point
     * @param p some point
     * @return the point to which the affine transformation is applied
     */
    public Point doAffineTransformation(Point p) {
        double newX = a * p.x() + b * p.y() + c;
        double newY = d * p.x() + e * p.y() + f;
        return new Point(newX, newY);
    }
}
