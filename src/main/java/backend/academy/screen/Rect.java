package backend.academy.screen;

import java.security.SecureRandom;

/**
 * The class represents a model of a rectangle -
 * a monitor screen with certain dimensions of the sides
 *
 * @param x      the X-axis coordinate of the upper-left corner of the rectangle
 * @param y      the Y-axis coordinate of the upper-left corner of the rectangle
 * @param width  rectangle width
 * @param height rectangle height
 */
public record Rect(double x, double y, double width, double height) {
    /**
     * Returns a random point that is inside the rectangle
     *
     * @param random random number generator
     * @return random point that is inside the rectangle
     */
    public Point getRandomPoint(SecureRandom random) {
        double xMax = x + width;
        double yMax = y + height;
        return new Point(random.nextDouble(x, xMax), random.nextDouble(y, yMax));
    }

    /**
     * overloading the function {@link #getRandomPoint(SecureRandom)}, if a random number generator is not passed
     *
     * @return random point that is inside the rectangle
     */
    public Point getRandomPoint() {
        SecureRandom random = new SecureRandom();
        return getRandomPoint(random);
    }

    /**
     * Check if the point is inside the rectangle
     *
     * @param p the point being checked
     * @return true if the point is inside the rectangle (otherwise - false)
     */
    public boolean contains(Point p) {
        return (p.x() >= x && p.x() <= x + width) && (p.y() >= y && p.y() <= y + width);
    }
}
