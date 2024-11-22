package backend.academy.image;

import java.awt.Color;

/**
 * The class represents a pixel model
 * that stores its color and the number
 * of "hits" in this pixel
 * @param color pixel color
 * @param hitCount the number of hits in a given pixel
 */
public record Pixel(Color color, int hitCount) {
}
