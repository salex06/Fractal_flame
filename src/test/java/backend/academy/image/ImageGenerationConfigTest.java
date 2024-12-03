package backend.academy.image;

import backend.academy.format.ImageFormat;
import backend.academy.transformations.variations.Variation;
import backend.academy.transformations.variations.impl.SinusoidalVariation;
import backend.academy.transformations.variations.impl.SwirlVariation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageGenerationConfigTest {
    private final int demoWidth = 1920;
    private final int demoHeight = 1080;
    private final int demoAffinesCount = 10;
    private final List<Variation> demoVariationList = List.of(new SinusoidalVariation(), new SwirlVariation());
    private final int demoSamples = 100000;
    private final short demoIterPerSample = 100;
    private final int demoSymmetry = 5;
    private final ImageFormat demoImageFormat = ImageFormat.BMP;
    private final int demoThreadNumber = 10;

    @Test
    @DisplayName("Ensure the fields are initialized correctly")
    void ensureFieldsAreInitializedCorrectly() {
        ImageGenerationConfig actual = new ImageGenerationConfig(
            demoWidth,
            demoHeight,
            demoAffinesCount,
            demoVariationList,
            demoSamples,
            demoIterPerSample,
            demoSymmetry,
            demoImageFormat,
            demoThreadNumber
        );

        assertEquals(demoWidth, actual.width());
        assertEquals(demoHeight, actual.height());
        assertEquals(demoAffinesCount, actual.affinesCount());
        assertEquals(demoVariationList, actual.variationList());
        assertEquals(demoSamples, actual.samples());
        assertEquals(demoIterPerSample, actual.iterPerSample());
        assertEquals(demoSymmetry, actual.symmetry());
        assertEquals(demoImageFormat, actual.imageFormat());
        assertEquals(demoThreadNumber, actual.threadNumber());
    }

    @Test
    @DisplayName("Ensure getConfig method works")
    void ensureGetConfigMethodWorks() {
        String expected = """
            Ширина изображения: 1920
            Высота изображения: 1080
            Количество аффинных преобразований: 10
            Выбранные вариации:\s
            	SinusoidalVariation
            	SwirlVariation
            Количество сэмплов: 100000
            Количество итераций на сэмпл: 100
            Количество осей симметрии: 5
            Количество потоков: 10
            Формат файла: BMP
            """;

        String actual = new ImageGenerationConfig(
            demoWidth,
            demoHeight,
            demoAffinesCount,
            demoVariationList,
            demoSamples,
            demoIterPerSample,
            demoSymmetry,
            demoImageFormat,
            demoThreadNumber
        ).getConfig();

        assertEquals(expected, actual);
    }
}
