package backend.academy.transformations.variations;

import backend.academy.screen.Point;
import java.util.function.Function;

/**
 * The interface provides methods for performing
 * point transformations using different functions (variations)
 */
public interface Variation extends Function<Point, Point> {
}
