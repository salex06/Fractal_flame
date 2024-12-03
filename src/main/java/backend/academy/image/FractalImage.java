package backend.academy.image;

import java.awt.Color;
import lombok.Getter;

/**
 * The class represents a fractal image model
 * in the system, stores an array of pixels and
 * image dimensions
 */
@Getter
public class FractalImage {
    private Pixel[][] data;
    private final int width; //по оси x
    private final int height; //по оси y

    /**
     * Parameterized constructor
     *
     * @param height the height of the image (the y-axis)
     * @param width  the width of the image (the x-axis)
     */
    public FractalImage(int height, int width) {
        data = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; ++j) {
                data[i][j] = new Pixel(Color.BLACK, 0, 0);
            }
        }
        this.width = width;
        this.height = height;
    }

    /**
     * Parameterized constructor with array of pixels as a parameter
     *
     * @param data   array of pixels
     * @param height the height of the image (the y-axis)
     * @param width  the width of the image (the x-axis)
     */
    public FractalImage(Pixel[][] data, int height, int width) {
        this(height, width);
        this.data = data;
    }

    /**
     * Checks whether the pixel with the given coordinates
     * is contained in the image
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return {@code true} if it is, otherwise it is not
     */
    public boolean contains(int x, int y) {
        return (x >= 0 & x < width && y >= 0 && y < height);
    }

    /**
     * Returns the pixel
     *
     * @param row the y coordinate
     * @param col the x coordinate
     * @return the found pixel (null if the fractal image doesn't contain this pixel)
     */
    public Pixel pixel(int row, int col) {
        if (!contains(col, row)) {
            return null;
        }
        return data[row][col];
    }

    /**
     * Set the pixel
     *
     * @param row   the y coordinate
     * @param col   the x coordinate
     * @param pixel a new pixel
     */
    public void setPixel(int row, int col, Pixel pixel) {
        if (!contains(col, row)) {
            return;
        }
        data[row][col] = pixel;
    }

    public static final double XMIN_COEFF = -1.777;
    public static final double XMAX_COEFF = 1.777;

    public static final double YMIN_COEFF = -1.0;
    public static final double YMAX_COEFF = 1.0;
}
