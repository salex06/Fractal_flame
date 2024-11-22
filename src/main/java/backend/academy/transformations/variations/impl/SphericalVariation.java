package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;

public class SphericalVariation implements Variation {
    @Override
    public Point apply(Point point) {
        double r = 1.0 / (point.x() * point.x() + point.y() * point.y());
        return new Point(point.x() * r, point.y() * r);
    }

}
