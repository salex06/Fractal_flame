package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;

/**
 * The class represents a Swirl variation (№3)
 */
public class SwirlVariation implements Variation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double rSq = Math.pow(r, 2);
        return new Point(
            point.x() * Math.sin(rSq) - point.y() * Math.cos(rSq),
            point.x() * Math.cos(rSq) + point.y() * Math.sin(rSq)
        );
    }
}
