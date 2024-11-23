package backend.academy.image;

import java.awt.Color;
import lombok.Getter;

@Getter
public class FractalImage {
    private Pixel[][] data;
    private final int width; //по оси x
    private final int height; //по оси y

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

    public FractalImage(Pixel[][] data, int height, int width) {
        this(height, width);
        this.data = data;
    }

    public boolean contains(int x, int y) {
        return !(x < 0 || x >= height || y < 0 || y >= width);
    }

    public Pixel pixel(int row, int col) {
        return data[row][col];
    }

    public void setPixel(int row, int col, Pixel pixel) {
        data[row][col] = pixel;
    }

    public static final double XMIN_COEFF = -1.777;
    public static final double XMAX_COEFF = 1.777;

    public static final double YMIN_COEFF = -1.0;
    public static final double YMAX_COEFF = 1.0;
}
