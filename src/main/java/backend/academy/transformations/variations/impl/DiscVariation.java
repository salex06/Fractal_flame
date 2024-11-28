package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * The class represents a Disc variation (№8)
 */
public class DiscVariation implements Variation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        double coeff = theta / Math.PI;
        double newX = coeff * sin(PI * r);
        double newY = coeff * cos(PI * r);
        return new Point(newX, newY);
    }
}
