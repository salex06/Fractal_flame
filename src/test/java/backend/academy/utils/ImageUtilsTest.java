package backend.academy.utils;

import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
import backend.academy.image.Pixel;
import backend.academy.utils.impl.IOHandlerImpl;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import static backend.academy.format.ImageFormat.BMP;
import static backend.academy.format.ImageFormat.PNG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImageUtilsTest {
    private static final Path OUTPUT_PNG = Path.of(
        FileSystems.getDefault().getPath(".").toAbsolutePath() + "/src/test/java/backend/academy/utils/output.png");
    private static final Path OUTPUT_BMP = Path.of(
        FileSystems.getDefault().getPath(".").toAbsolutePath() + "/src/test/java/backend/academy/utils/output.bmp");

    private static List<Color> getColors() {
        List<Color> colorsList = new ArrayList<>();
        for (int r = 0; r < 256; ++r) {
            for (int g = 0; g < 256; ++g) {
                for (int b = 0; b < 256; b++) {
                    colorsList.add(new Color(r, g, b));
                }
            }
        }
        return colorsList;
    }

    private static List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<ImageFormat, Path>>> getData() {
        return List.of(
            Map.entry(Map.entry(5, 3), Map.entry(PNG, OUTPUT_PNG)),
            Map.entry(Map.entry(1, 1), Map.entry(PNG, OUTPUT_PNG)),
            Map.entry(Map.entry(4, 4), Map.entry(PNG, OUTPUT_PNG)),
            Map.entry(Map.entry(3, 5), Map.entry(PNG, OUTPUT_PNG)),
            Map.entry(Map.entry(30, 50), Map.entry(PNG, OUTPUT_PNG)),
            Map.entry(Map.entry(5, 3), Map.entry(BMP, OUTPUT_BMP)),
            Map.entry(Map.entry(1, 1), Map.entry(BMP, OUTPUT_BMP)),
            Map.entry(Map.entry(4, 4), Map.entry(BMP, OUTPUT_BMP)),
            Map.entry(Map.entry(3, 5), Map.entry(BMP, OUTPUT_BMP)),
            Map.entry(Map.entry(30, 50), Map.entry(BMP, OUTPUT_BMP))
        );
    }

    @ParameterizedTest
    @MethodSource("getData")
    @DisplayName("Ensure saving images works correctly")
    void ensureSaveImageWorksCorrectly(Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<ImageFormat, Path>> curr)
        throws IOException {
        //Given
        int width = curr.getKey().getKey();
        int height = curr.getKey().getValue();
        ImageFormat format = curr.getValue().getKey();
        Path path = curr.getValue().getValue();
        List<Color> colors = getColors();
        Pixel[][] pixels = new Pixel[height][width];
        int k = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; ++j) {
                pixels[i][j] = new Pixel(colors.get(k++), 0, 0);
            }
        }
        FractalImage mockedImage = mock(FractalImage.class);
        when(mockedImage.width()).thenReturn(width);
        when(mockedImage.height()).thenReturn(height);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                when(mockedImage.pixel(i, j)).thenReturn(pixels[i][j]);
            }
        }

        //When
        ImageUtils.saveImage(mockedImage, path, format);

        //Then
        BufferedImage image = ImageIO.read(path.toFile());
        k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                assertEquals(colors.get(k++).getRGB(), image.getRGB(j, i));
            }
        }
    }

    @Test
    @DisplayName("Ensure saveData method works")
    void ensureSaveDataMethodWorks() throws IOException {
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        IOHandler fileHandler = new IOHandlerImpl(System.in, arr);
        ImageGenerationConfig config = Mockito.mock(ImageGenerationConfig.class);
        when(config.getConfig()).thenReturn("Config\n");
        when(config.imageFormat()).thenReturn(PNG);
        int expectedWidth = 2;
        int expectedHeight = 2;
        FractalImage image = new FractalImage(expectedHeight, expectedWidth);
        String imagePath = OUTPUT_PNG.toString();
        long elapsedTime = 10;
        Color expectedColor = Color.BLACK;
        String expectedLogFile = """
            Config
            Затраченное время (в секундах): 10
            """;

        ImageUtils.saveData(fileHandler, config, image, imagePath, elapsedTime);

        assertEquals(expectedLogFile, arr.toString());
        BufferedImage actual = ImageIO.read(OUTPUT_PNG.toFile());
        for (int i = 0; i < expectedHeight; ++i) {
            for (int j = 0; j < expectedWidth; ++j) {
                assertEquals(expectedColor.getRGB(), actual.getRGB(j, i));
            }
        }
    }
}
