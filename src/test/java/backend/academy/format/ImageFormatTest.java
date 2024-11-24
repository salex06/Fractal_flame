package backend.academy.format;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static backend.academy.format.ImageFormat.BMP;
import static backend.academy.format.ImageFormat.PNG;
import static org.junit.jupiter.api.Assertions.*;

class ImageFormatTest {
    @Test
    @DisplayName("Ensure getImageFormats works")
    void ensureGetImageFormatsWorks(){
        List<ImageFormat> expected = List.of(BMP, PNG);

        List<ImageFormat> actual = ImageFormat.getImageFormats();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Ensure getImageFormatsAsStrings works")
    void ensureGetImageFormatsAsStringsWorks(){
        String expected = """
            [1] - BMP
            [2] - PNG
            """;

        String actual = ImageFormat.getImageFormatsAsStrings();

        assertEquals(expected, actual);
    }
}
