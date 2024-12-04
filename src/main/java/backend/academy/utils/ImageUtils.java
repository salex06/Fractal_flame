package backend.academy.utils;

import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
import backend.academy.image.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import javax.imageio.ImageIO;
import static backend.academy.format.ImageFormat.PNG;

/**
 * ImageUtils provides operations
 * to save the generated images to disk
 */
public final class ImageUtils {
    private ImageUtils() {
    }

    /**
     * Save the generated images to disk in various formats
     *
     * @param image    the generated image
     * @param filename the path to the file where the image will be saved
     * @param format   file format (jpeg, png, bmp, etc.)
     * @throws IOException in case of errors writing to the file
     */
    public static void saveImage(FractalImage image, Path filename, ImageFormat format) throws IOException {
        int type = BufferedImage.TYPE_4BYTE_ABGR;
        if (format != PNG) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int width = image.width();
        int height = image.height();

        BufferedImage img = new BufferedImage(width, height, type);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel current = image.pixel(i, j);
                img.setRGB(j, i, current.color().getRGB());
            }
        }

        ImageIO.write(img, format.name().toLowerCase(Locale.ROOT), filename.toFile());
    }

    /**
     * Saves the log file created based on the config, and the generated image
     *
     * @param fileHandler output stream to a file (for log file)
     * @param config      the image configuration
     * @param image       the generated image
     * @param imagePath   the path to the image
     * @param elapsedTime image generation time
     * @throws IOException if any i/o errors occur
     */
    public static void saveData(
        IOHandler fileHandler,
        ImageGenerationConfig config,
        FractalImage image,
        String imagePath,
        long elapsedTime
    ) throws IOException {
        fileHandler.write(config.getConfig());
        fileHandler.write("Затраченное время (в секундах): " + elapsedTime + '\n');
        ImageUtils.saveImage(image, Path.of(imagePath), config.imageFormat());
    }

    /**
     * The default path to the root directory
     */
    public static final String PATH_TO_ROOT_DIR = "src/main/resources/";
}
