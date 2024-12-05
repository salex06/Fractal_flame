package backend.academy.render;

import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
import backend.academy.transformations.variations.impl.BentVariation;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static backend.academy.format.ImageFormat.PNG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RendererTest {
    private Renderer renderer;

    @BeforeEach
    void setup() {
        renderer = new Renderer();
    }

    @ParameterizedTest
    @CsvSource({"1920, 1080", "480, 720", "1000, 1000", "1440, 144"})
    @DisplayName("Ensure the generated fractal image size is correct")
    void ensureTheSizeIsCorrect(int expectedWidth, int expectedHeight) {
        FractalImage blank = new FractalImage(expectedHeight, expectedWidth);
        ImageGenerationConfig expectedConfig = new ImageGenerationConfig(expectedWidth, expectedHeight, 100,
            List.of(new BentVariation()), 100000, (short) 100, 1, PNG, 1);

        FractalImage actual = renderer.render(blank, expectedConfig);

        assertEquals(expectedHeight, actual.height());
        assertEquals(expectedWidth, actual.width());
    }

    @Disabled("using mvn clean causes the test failure (running the test manually gives the correct result)")
    @Test
    @DisplayName("Ensure that multithreading is faster than single-threading")
    void ensureMultithreadingIsFasterThanSingleThreading() {
        ImageGenerationConfig singleThreadConfig = new ImageGenerationConfig(1920, 1080, 100,
            List.of(new BentVariation()), 100000, (short) 100, 1, PNG, 1);
        ImageGenerationConfig multiThreadConfig = new ImageGenerationConfig(1920, 1080, 100,
            List.of(new BentVariation()), 100000, (short) 100, 1, PNG, 8);

        long start = System.nanoTime();
        renderer.render(new FractalImage(1920, 1080), singleThreadConfig);
        long singleThreadTime = System.nanoTime() - start;

        start = System.nanoTime();
        renderer.render(new FractalImage(1920, 1080), multiThreadConfig);
        long multiThreadTime = System.nanoTime() - start;

        assertThat(singleThreadTime).isGreaterThan(multiThreadTime);
    }
}
