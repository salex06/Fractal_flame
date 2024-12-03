package backend.academy.image;

import java.awt.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FractalImageTest {
    private FractalImage actual;

    @Test
    @DisplayName("Ensure parameterized constructor works correctly")
    void ensureParameterizedConstructorWorks() {
        int expectedWidth = 1920;
        int expectedHeight = 1080;
        Color expectedColor = Color.BLACK;
        actual = new FractalImage(expectedHeight, expectedWidth);

        assertEquals(expectedHeight, actual.height());
        assertEquals(expectedWidth, actual.width());
        for (int i = 0; i < actual.height(); ++i) {
            for (int j = 0; j < actual.width(); ++j) {
                assertEquals(expectedColor, actual.data()[i][j].color());
                assertEquals(0, actual.data()[i][j].hitCount());
                assertEquals(0, actual.data()[i][j].normal());
            }
        }
    }

    @ParameterizedTest
    @CsvSource({"1080, 1920, false", "1079, 1919, true", "0,0, true", "500, 500, true", "100000, 10000, false",
        "-1, 10, false"})
    @DisplayName("Ensure contains method works correctly")
    void ensureContainsMethodWorks(int x, int y, boolean expected) {
        actual = new FractalImage(1920, 1080);

        assertEquals(expected, actual.contains(x, y));
    }

    @Test
    @DisplayName("Ensure setPixel and getPixel methods work correctly")
    void ensureGetPixelAndSetPixelWork() {
        actual = new FractalImage(1920, 1080);

        actual.setPixel(0, 0, new Pixel(Color.BLUE, 0, 0));

        Pixel actualPixel = actual.pixel(0, 0);

        assertEquals(Color.BLUE, actualPixel.color());
        assertEquals(0, actualPixel.hitCount());
        assertEquals(0, actualPixel.normal());

    }
}
