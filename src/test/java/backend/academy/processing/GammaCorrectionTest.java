package backend.academy.processing;

import backend.academy.image.FractalImage;
import backend.academy.image.Pixel;
import java.awt.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GammaCorrectionTest {
    @Test
    @DisplayName("Ensure gamma correction is performed correctly")
    void ensureGammaCorrectionWorks() {
        Pixel[][] data = new Pixel[][] {
            new Pixel[] {new Pixel(Color.CYAN, 3, 0), new Pixel(Color.RED, 10, 0)},
            new Pixel[] {new Pixel(Color.WHITE, 7, 0), new Pixel(Color.MAGENTA, 25, 0)}
        };

        FractalImage actual = new FractalImage(data, 2, 2);

        new GammaCorrection().process(actual);

        assertEquals(actual.data()[0][0].color().getRGB(), new Color(0, 156, 156).getRGB());
        assertEquals(actual.data()[0][1].color().getRGB(), new Color(218, 0, 0).getRGB());
        assertEquals(actual.data()[1][0].color().getRGB(), new Color(202, 202, 202).getRGB());
        assertEquals(actual.data()[1][1].color().getRGB(), new Color(255, 0, 255).getRGB());
    }
}
