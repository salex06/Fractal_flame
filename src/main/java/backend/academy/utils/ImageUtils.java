package backend.academy.utils;

import backend.academy.format.ImageFormat;
import backend.academy.image.FractalImage;
import backend.academy.image.ImageGenerationConfig;
import backend.academy.image.Pixel;
import backend.academy.utils.impl.IOHandlerImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @SuppressFBWarnings({"PATH_TRAVERSAL_IN", "PATH_TRAVERSAL_OUT"})
    public static void saveData(FractalImage image, ImageGenerationConfig config, long elapsedTime) throws IOException {
        File dir =
            new File(PATH_TO_ROOT_DIR,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-LL-yyyy_HHmmss")));
        if (!dir.mkdir()) {
            throw new IOException("Ошибка создания директории");
        }
        String logPath = dir + "/info.log";
        IOHandler fileHandler = new IOHandlerImpl(System.in, Files.newOutputStream(Path.of(logPath)));
        fileHandler.write(config.getConfig());
        fileHandler.write("Затраченное время (в секундах): " + elapsedTime / NANOSECONDS_IN_SECOND + '\n');

        String imagePath = dir + "/image." + config.imageFormat().name().toLowerCase();
        ImageUtils.saveImage(image, Path.of(imagePath), config.imageFormat());
    }

    private static final long NANOSECONDS_IN_SECOND = 1_000_000_000;
    private static final String PATH_TO_ROOT_DIR = "src/main/resources/";
}
