package backend.academy.format;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageFormatTest {
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
