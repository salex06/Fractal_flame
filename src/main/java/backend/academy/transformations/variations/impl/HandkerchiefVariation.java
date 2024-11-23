package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;

/**
 * The class represents a Handkerchief variation (№6)
 */
public class HandkerchiefVariation implements Variation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double theta = Math.atan2(point.y(), point.x());
        double x = r * Math.sin(theta + r);
        double y = r * Math.cos(theta - r);
        return new Point(x, y);
    }
}
