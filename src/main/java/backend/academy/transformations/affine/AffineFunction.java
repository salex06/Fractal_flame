package backend.academy.transformations.affine;

import backend.academy.screen.Point;
import java.awt.Color;

public record AffineFunction(
    double a, double b, double c, double d, double e, double f, Color color
) {
    public static final int COLOR_LIMIT = 256;

    public Point doAffineTransformation(Point p) {
        double newX = a * p.x() + b * p.y() + c;
        double newY = d * p.x() + e * p.y() + f;
        return new Point(newX, newY);
    }
}
