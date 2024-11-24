package backend.academy.format;

import java.util.Arrays;
import java.util.List;

/**
 * The enumeration defines the file extension
 * where the fractal image will be saved
 */
public enum ImageFormat {
    BMP, PNG;

    /**
     * Returns the list of available file formats
     *
     * @return list of available file formats
     */
    public static List<ImageFormat> getImageFormats() {
        return Arrays.stream(ImageFormat.values()).toList();
    }

    /**
     * Returns available file formats in the form of string
     *
     * @return {@code String} - available file formats
     */
    public static String getImageFormatsAsStrings() {
        ImageFormat[] formats = ImageFormat.values();
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (ImageFormat format : formats) {
            builder.append('[').append(i++).append("] - ").append(format.name()).append('\n');
        }
        return builder.toString();
    }
}
