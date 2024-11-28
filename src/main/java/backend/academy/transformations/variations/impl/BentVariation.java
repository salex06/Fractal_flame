package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;

/**
 * The class represents a Bent variation (№14)
 */
public class BentVariation implements Variation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        Point pNew;
        if (x >= 0 && y >= 0) {
            pNew = new Point(x, y);
        } else if (x < 0 && y >= 0) {
            pNew = new Point(2 * x, y);
        } else if (x >= 0 && y < 0) {
            pNew = new Point(x, y / 2);
        } else {
            pNew = new Point(2 * x, y / 2);
        }
        return pNew;
    }
}
