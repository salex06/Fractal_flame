package backend.academy.transformations.variations.impl;

import backend.academy.screen.Point;
import backend.academy.transformations.variations.Variation;

/**
 * The class represents a Sinusoidal variation (№1)
 */
public class SinusoidalVariation implements Variation {
    @Override
    public Point apply(Point point) {
        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
